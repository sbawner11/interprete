package mx.gob.atdt.interprete.schedule;

import java.io.File;
import java.util.Date;
import java.util.Objects;

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
public class DepuraPathTemporalSchedule {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepuraPathTemporalSchedule.class);

	@Resource
	TimerService timerService;

	@Schedules({ 
		@Schedule(hour = "03", minute = "00", persistent = false)
	})
	public void eliminarArchivosTemporales() {
		LOGGER.info("Inicio Se depura path de archivos temporales :  " + new Date());
		try {
			File carpetaArchivosTemp = new File(Environment.getPathArchivosTemporales());
			
			eliminaArchivosPath(carpetaArchivosTemp);
			 			
		} catch (Exception e) {
			LOGGER.error("Error al eliminar archivos de la carpeta temporal: ", e);
		}
	}
	
	/**
	 * Método auxiliar que elimina los archivos del directorio indicado
	 * @param directorio
	 */
	public void eliminaArchivosPath(File directorio) {
        for (File fileTmp: Objects.requireNonNull(directorio.listFiles())) {
            if (fileTmp.isDirectory()) {
            	eliminaArchivosPath(fileTmp);
            } else {
            	fileTmp.delete();
            }
        }
    }	
}
