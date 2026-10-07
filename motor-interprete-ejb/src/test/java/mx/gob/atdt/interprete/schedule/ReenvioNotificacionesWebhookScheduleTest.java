package mx.gob.atdt.interprete.schedule;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import mx.gob.atdt.interprete.dto.CatEstatusTramiteDTO;
import mx.gob.atdt.interprete.dto.CatTipoNotificacionDTO;
import mx.gob.atdt.interprete.dto.ConfiguracionWebhookDTO;
import mx.gob.atdt.interprete.dto.NotificacionMovimientoTramiteDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;

/**
 * Pruebas unitarias para ReenvioNotificacionesWebhookSchedule
 * Nota: Las pruebas de integración completa requieren contenedor EJB
 */
public class ReenvioNotificacionesWebhookScheduleTest {

	private ConfiguracionWebhookDTO configuracionValida;
	private ConfiguracionWebhookDTO configuracionDeshabilitada;
	private ConfiguracionWebhookDTO configuracionSinUrl;

	@Before
	public void setUp() {
		configuracionValida = crearConfiguracionWebhook(true, "http://ejemplo.com/webhook");
		configuracionDeshabilitada = crearConfiguracionWebhook(false, "http://ejemplo.com/webhook");
		configuracionSinUrl = crearConfiguracionWebhook(true, null);
	}

	@Test
	public void testConfiguracionWebhookValida() {
		assertNotNull(configuracionValida);
		assertTrue(configuracionValida.isHabilitaEnvioNotificaciones());
		assertNotNull(configuracionValida.getUrlAplicacionNotificaciones());
		assertFalse(configuracionValida.getUrlAplicacionNotificaciones().trim().isEmpty());
	}

	@Test
	public void testConfiguracionWebhookDeshabilitada() {
		assertNotNull(configuracionDeshabilitada);
		assertFalse(configuracionDeshabilitada.isHabilitaEnvioNotificaciones());
	}

	@Test
	public void testConfiguracionWebhookSinUrl() {
		assertNotNull(configuracionSinUrl);
		assertTrue(configuracionSinUrl.isHabilitaEnvioNotificaciones());
		assertNull(configuracionSinUrl.getUrlAplicacionNotificaciones());
	}

	@Test
	public void testCreacionNotificacionMovimientoTramiteDTO() {
		TramiteDTO tramite = crearTramiteDTO();
		CatTipoNotificacionDTO tipoNotificacion = new CatTipoNotificacionDTO(1, "Registro de trámite");

		NotificacionMovimientoTramiteDTO notificacion = new NotificacionMovimientoTramiteDTO(
			1L,
			tipoNotificacion,
			tramite,
			new Date(),
			false
		);

		assertNotNull(notificacion);
		assertEquals(1L, notificacion.getIdNotificacionMovimiento());
		assertFalse(notificacion.isEnvioConfirmado());
		assertNotNull(notificacion.getTramiteDTO());
		assertEquals("TEST-001", notificacion.getTramiteDTO().getFolioSeguimiento());
	}

	@Test
	public void testTramiteDTOParaNotificaciones() {
		Date fechaCreacion = new Date();
		Date fechaRevision = new Date();

		TramiteDTO tramite = new TramiteDTO(100L, "FOLIO-2026-001", 1, "En proceso", fechaCreacion, fechaRevision);

		assertNotNull(tramite);
		assertEquals(Long.valueOf(100L), tramite.getIdTramite());
		assertEquals("FOLIO-2026-001", tramite.getFolioSeguimiento());
		assertNotNull(tramite.getCatEstatusTramiteDTO());
		assertEquals(1, tramite.getCatEstatusTramiteDTO().getIdEstatusTramite());
		assertNotNull(tramite.getFechaCreacion());
		assertNotNull(tramite.getFechaRevision());
	}

	@Test
	public void testTipoNotificacionRegistro() {
		CatTipoNotificacionDTO tipoRegistro = new CatTipoNotificacionDTO(1, "Registro de trámite");
		assertEquals(1, tipoRegistro.getIdTipoNotificacion());
		assertEquals("Registro de trámite", tipoRegistro.getDescripcion());
	}

	@Test
	public void testTipoNotificacionActualizacion() {
		CatTipoNotificacionDTO tipoActualizacion = new CatTipoNotificacionDTO(2, "Actualización de estatus de trámite");
		assertEquals(2, tipoActualizacion.getIdTipoNotificacion());
		assertEquals("Actualización de estatus de trámite", tipoActualizacion.getDescripcion());
	}

	@Test
	public void testNotificacionPendienteIdentificacion() {
		NotificacionMovimientoTramiteDTO pendiente = crearNotificacionPendiente();
		assertFalse("Notificación pendiente debe tener envio_confirmado = false", 
			pendiente.isEnvioConfirmado());
		assertNull("Notificación pendiente debe tener fecha_notificacion = null",
			pendiente.getFechaNotificacion());
	}

	@Test
	public void testNotificacionEnviadaIdentificacion() {
		NotificacionMovimientoTramiteDTO enviada = crearNotificacionEnviada();
		assertTrue("Notificación enviada debe tener envio_confirmado = true", 
			enviada.isEnvioConfirmado());
		assertNotNull("Notificación enviada debe tener fecha_notificacion != null",
			enviada.getFechaNotificacion());
	}

	@Test
	public void testCriteriosPendienteCompletos() {
		// Una notificación pendiente cumple: envio_confirmado=false AND fecha_notificacion IS NULL
		NotificacionMovimientoTramiteDTO pendiente = crearNotificacionPendiente();
		boolean esPendiente = !pendiente.isEnvioConfirmado() && pendiente.getFechaNotificacion() == null;
		assertTrue("Debe cumplir ambos criterios para ser pendiente", esPendiente);
	}

	@Test
	public void testCriteriosEnviadaCompletos() {
		// Una notificación enviada tiene: envio_confirmado=true AND fecha_notificacion != NULL
		NotificacionMovimientoTramiteDTO enviada = crearNotificacionEnviada();
		boolean esEnviada = enviada.isEnvioConfirmado() && enviada.getFechaNotificacion() != null;
		assertTrue("Debe cumplir ambos criterios para estar enviada", esEnviada);
	}

	// Métodos auxiliares para crear datos de prueba

	private ConfiguracionWebhookDTO crearConfiguracionWebhook(boolean habilitado, String url) {
		ConfiguracionWebhookDTO config = new ConfiguracionWebhookDTO();
		config.setIdConfiguracionWebhook(1L);
		config.setUsuario("usuario_test");
		config.setContrasenia("password_test");
		config.setUrlAplicacionNotificaciones(url);
		config.setHabilitaEnvioNotificaciones(habilitado);
		config.setActivo(true);
		config.setFechaCreacion(new Date());
		return config;
	}

	private TramiteDTO crearTramiteDTO() {
		return new TramiteDTO(1L, "TEST-001", 1, "En proceso", new Date(), new Date());
	}

	private NotificacionMovimientoTramiteDTO crearNotificacionPendiente() {
		TramiteDTO tramite = crearTramiteDTO();
		CatTipoNotificacionDTO tipoNotificacion = new CatTipoNotificacionDTO(1, "Registro de trámite");
		// Pendiente: envio_confirmado=false, fecha_notificacion=null
		return new NotificacionMovimientoTramiteDTO(1L, tipoNotificacion, tramite, null, false);
	}

	private NotificacionMovimientoTramiteDTO crearNotificacionEnviada() {
		TramiteDTO tramite = crearTramiteDTO();
		CatTipoNotificacionDTO tipoNotificacion = new CatTipoNotificacionDTO(1, "Registro de trámite");
		// Enviada: envio_confirmado=true, fecha_notificacion=fecha actual
		return new NotificacionMovimientoTramiteDTO(2L, tipoNotificacion, tramite, new Date(), true);
	}
}
