package mx.gob.atdt.widget.application;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.omnifaces.cdi.Eager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.DetEstadoSistemaDTO;
import mx.gob.atdt.interprete.dto.HomeProgramaSocialDTO;
import mx.gob.atdt.interprete.dto.HomeTramiteDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.runnable.IndexRunnable;

@Eager // Que se construya al iniciar el Wildfly
@Named("indexBean")
@ApplicationScoped
public class IndexBean implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexBean.class);

	private static final long serialVersionUID = 8307053242647384419L;

	private ScheduledExecutorService schedulerIndexHome;

	private ProyectoDTO proyectoDTO;

	private HomeTramiteDTO homeTramiteDTO;

	private HomeProgramaSocialDTO homeProgramaDTO;

	private DetEstadoSistemaDTO estadoSistemaDTO;

	@PostConstruct
	public void inicializar() {
		proyectoDTO = new ProyectoDTO();
		homeTramiteDTO = new HomeTramiteDTO();
		homeProgramaDTO = new HomeProgramaSocialDTO();
		IndexRunnable indexRunnable = new IndexRunnable(this);
		schedulerIndexHome = Executors.newSingleThreadScheduledExecutor();
		schedulerIndexHome.scheduleAtFixedRate(indexRunnable, 0, 2, TimeUnit.MINUTES);
	}

	public IndexBean() {

	}
	
	/**
	 * Método que se invoca al actualizar index cada 2 minutos, con la finalidad de que al termino de una sincronziación, 
	 * la pantalla de mantenimiento se pueda visualizar de manera automática.
	 */
	public void refrescarIndex() {
		LOGGER.debug("actualizar index " + new Date());
	}

	@PreDestroy
	public void destroy() {
		try {
			schedulerIndexHome.shutdown();
		} catch (Exception e) {
			LOGGER.warn("No se pudo apagar el schedulerIndexHome"); /* Catch silencioso */
		}
	}

	/**
	 * Método que inicializa la vista del Aviso de privacidad
	 * 
	 * @return
	 */
	public String incializarAvisoSimplificado() {
		return Constantes.RETURN_AVISO_SIMPLIFICADO_PAGE + Constantes.JSF_REDIRECT;
	}

	/**
	 * Método que inicializa la vista del Aviso de privacidad Integal
	 * 
	 * @return
	 */
	public String incializarAvisoIntegral() {
		return Constantes.RETURN_AVISO_INTEGRAL_PAGE + Constantes.JSF_REDIRECT;
	}

	/** GETTER´s y SETTER´s **/

	public ProyectoDTO getProyectoDTO() {
		return proyectoDTO;
	}

	public void setProyectoDTO(ProyectoDTO proyectoDTO) {
		this.proyectoDTO = proyectoDTO;
	}

	public HomeTramiteDTO getHomeTramiteDTO() {
		return homeTramiteDTO;
	}

	public void setHomeTramiteDTO(HomeTramiteDTO homeTramiteDTO) {
		this.homeTramiteDTO = homeTramiteDTO;
	}

	public HomeProgramaSocialDTO getHomeProgramaDTO() {
		return homeProgramaDTO;
	}

	public void setHomeProgramaDTO(HomeProgramaSocialDTO homeProgramaDTO) {
		this.homeProgramaDTO = homeProgramaDTO;
	}

	public DetEstadoSistemaDTO getEstadoSistemaDTO() {
		return estadoSistemaDTO;
	}

	public void setEstadoSistemaDTO(DetEstadoSistemaDTO estadoSistemaDTO) {
		this.estadoSistemaDTO = estadoSistemaDTO;
	}
}
