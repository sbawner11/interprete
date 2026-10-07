package mx.gob.atdt.interprete.facade;

import java.time.Year;
import java.util.Date;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.dao.ControlSecuenciaLCDAO;
import mx.gob.atdt.interprete.dto.sat.dto.ControlSecuenciaLCDTO;
import mx.gob.atdt.interprete.dto.DetLineaCapturaDTO;

@Stateless
@LocalBean
public class SincronizacionSecuenciaLCFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(SincronizacionSecuenciaLCFacade.class);
    private static final int ANIOS_A_GENERAR = 10;

    @Inject
    private ControlSecuenciaLCDAO controlSecuenciaDAO;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void sincronizarSecuencias(final DetLineaCapturaDTO detLineaCaptura) {

        String cveDependencia = rellenarCadena(
                detLineaCaptura.getDependenciaPagoDTO().getIdDependenciaPago() + "", 3);
        String uad = rellenarCadena(
                detLineaCaptura.getUnidadAdministrativaPagoDTO().getIdUnidadAdministrativaPago() + "", 3);
        Long valorInicial = detLineaCaptura.getSecuencia();

        LOGGER.info("Sincronizando secuencias LC: dep={}, uad={}, valorInicial={}",
                cveDependencia, uad, valorInicial);

        int anioActual = Year.now().getValue();

        for (int i = 0; i < ANIOS_A_GENERAR; i++) {
            String anio = String.format("%02d", (anioActual + i) % 100);
            String nombreSecuencia = String.format("seq_lc_%s_%s_%s",
                    cveDependencia, uad, anio);
            procesarSecuencia(cveDependencia, uad, anio, nombreSecuencia, valorInicial);
        }
    }

    private void procesarSecuencia(final String cveDependencia,
                                    final String uad,
                                    final String anio,
                                    final String nombreSecuencia,
                                    final Long valorInicial) {

        ControlSecuenciaLCDTO control = controlSecuenciaDAO
                .buscarPorDepUaAnio(cveDependencia, uad, anio);

        if (control == null) {
            LOGGER.info("Creando secuencia nueva: {}", nombreSecuencia);

            // ⬇️ Llamada al DAO, NO a em
            controlSecuenciaDAO.crearSecuenciaSiNoExiste(nombreSecuencia, valorInicial);

            control = new ControlSecuenciaLCDTO();
            control.setCveDependencia(cveDependencia);
            control.setUnidadAdministrativa(uad);
            control.setAnio(anio);
            control.setNombreSecuencia(nombreSecuencia);
            control.setValorInicial(valorInicial);
            control.setValorActual(valorInicial);
            control.setActivo(true);
            control.setFechaCreacion(new Date());
            controlSecuenciaDAO.guardar(control);

        } else if (control.getValorInicial() == null
                || !control.getValorInicial().equals(valorInicial)) {

            LOGGER.info("Actualizando secuencia: {} de {} a {}",
                    nombreSecuencia, control.getValorInicial(), valorInicial);

            controlSecuenciaDAO.avanzarSecuencia(nombreSecuencia, valorInicial);

            control.setValorInicial(valorInicial);
            control.setValorActual(valorInicial);
            control.setFechaActualizacion(new Date());
            controlSecuenciaDAO.actualizar(control);
        } else {
            LOGGER.info("Secuencia {} ya está en el valor correcto", nombreSecuencia);
        }
    }

    public Long obtenerSiguienteFolio(String cveDependencia, String uad, String anio) {
        String nombreSecuencia = String.format("seq_lc_%s_%s_%s",
                cveDependencia, uad, anio);

        ControlSecuenciaLCDTO control = controlSecuenciaDAO
                .buscarPorDepUaAnio(cveDependencia, uad, anio);

        if (control == null) {
            throw new IllegalStateException(
                    "No existe secuencia configurada para dep=" + cveDependencia
                    + ", uad=" + uad + ", anio=" + anio);
        }

        // ⬇️ Llamada al DAO, NO a em
        Long folio = controlSecuenciaDAO.obtenerSiguienteFolio(nombreSecuencia);

        control.setValorActual(folio);
        control.setFechaActualizacion(new Date());
        controlSecuenciaDAO.actualizar(control);

        return folio;
    }

    private String rellenarCadena(String valor, int longitud) {
        return String.format("%1$" + longitud + "s", valor).replace(' ', '0');
    }
}