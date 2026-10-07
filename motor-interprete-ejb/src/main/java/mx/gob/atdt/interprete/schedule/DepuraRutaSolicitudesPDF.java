package mx.gob.atdt.interprete.schedule;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.common.infra.Environment;

@Singleton
@Startup
public class DepuraRutaSolicitudesPDF {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepuraRutaSolicitudesPDF.class);
	
	private static final String PATH_PLANTILLAS = "formatos/";

	@Resource
	TimerService timerService;

	@Schedules({ 
		@Schedule(hour = "5", minute = "30", persistent = false)
	})
	public void eliminarArchivos() {
		LOGGER.info("Depuración de plantillas");
		Date fechaLimite = new Date();
		eliminarDocumentosPathTmp(fechaLimite, Environment.getPathPlantillasClientePdf() + PATH_PLANTILLAS);
	}

	/**
	 * Método que se utiliza para eliminar los archivos de la carpeta tmp
	 * 
	 * @param fechaAuxiliarEliminacion
	 * @param pathTmp
	 */
	private void eliminarDocumentosPathTmp(Date fechaAuxiliarEliminacion, String pathTmp) {
		try {
			File comprobantesPDF = new File(pathTmp);
			if (comprobantesPDF.isDirectory()) {
				File[] comprobantesArray = comprobantesPDF.listFiles();
				if (comprobantesArray.length > 0) {
					for (int i = 0; i < comprobantesArray.length; i++) {
						Date fechaDocumento = new Date(comprobantesArray[i].lastModified());
						if (fechaDocumento.before(fechaAuxiliarEliminacion)) {
							if (!comprobantesArray[i].delete()) {
								LOGGER.warn("No se pudo eliminar el archivo: " + comprobantesArray[i].getName());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error al eliminar los documentos de la carpeta tmp: ", e);
		}
	}

}
