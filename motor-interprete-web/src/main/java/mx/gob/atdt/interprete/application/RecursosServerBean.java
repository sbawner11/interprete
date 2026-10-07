package mx.gob.atdt.interprete.application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.omnifaces.cdi.Eager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.commons.utils.Constantes;

@Eager // Que se construya al iniciar el Wildfly
@Named("recursosServerBean")
@ApplicationScoped
public class RecursosServerBean implements Serializable{

	private static final long serialVersionUID = -4164144662918844949L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RecursosServerBean.class);
	private static final String CARPETA_PLANTILLAS = "plantillaRegistro/";
	private static final String PLANTILLA_LLAVE = "plantilla_llave.pdf";
	private static final String PLANTILLA_NO_LLAVE = "plantilla_no_llave.pdf";
	
	public RecursosServerBean() {
	}
	
	@PostConstruct
	public void init() {
		try {
			LOGGER.info("---------------> RecursosServerBean: Inicializando la validación de existencia de recursos (plantillas PDF) ");
			File rutaPlantillas = new File(Environment.getPathPlantillasClientePdf());
			if(rutaPlantillas == null || !rutaPlantillas.exists()) {
				rutaPlantillas.setReadable(true, false);
				rutaPlantillas.setWritable(true, false);
				boolean isCreatePathPlantillas = rutaPlantillas.mkdirs();
				if(isCreatePathPlantillas) {
					LOGGER.info("--------> Se creó la carpeta destino de las plantillas exitosamente ... ");
					descargarRecurso(Environment.getUrlFileServerMotor()+CARPETA_PLANTILLAS+PLANTILLA_LLAVE, Environment.getPathPlantillasClientePdf()+PLANTILLA_LLAVE, PLANTILLA_LLAVE);
					descargarRecurso(Environment.getUrlFileServerMotor()+CARPETA_PLANTILLAS+PLANTILLA_NO_LLAVE, Environment.getPathPlantillasClientePdf()+PLANTILLA_NO_LLAVE, PLANTILLA_NO_LLAVE);
				}
			} else {
				File[] arrayPlantillas = rutaPlantillas.listFiles();
				if(arrayPlantillas == null || arrayPlantillas.length == 0) {
					LOGGER.info("--------> La carpeta de plantillasRegistro esta vacía ");
					descargarRecurso(Environment.getUrlFileServerMotor()+CARPETA_PLANTILLAS+PLANTILLA_LLAVE, Environment.getPathPlantillasClientePdf()+PLANTILLA_LLAVE, PLANTILLA_LLAVE);
					descargarRecurso(Environment.getUrlFileServerMotor()+CARPETA_PLANTILLAS+PLANTILLA_NO_LLAVE, Environment.getPathPlantillasClientePdf()+PLANTILLA_NO_LLAVE, PLANTILLA_NO_LLAVE);
				} else {
					File filePlantillaLlave = new File(Environment.getPathPlantillasClientePdf()+PLANTILLA_LLAVE);
					if(filePlantillaLlave == null || !filePlantillaLlave.exists()) {
						LOGGER.info("--------> No existe la plantilla " + PLANTILLA_LLAVE);
						descargarRecurso(Environment.getUrlFileServerMotor()+CARPETA_PLANTILLAS+PLANTILLA_LLAVE, Environment.getPathPlantillasClientePdf()+PLANTILLA_LLAVE, PLANTILLA_LLAVE);
					} else {
						//TODO Se agregan líneas temporales para realizar copiado de plantilla nuevamente por ajustes
						LOGGER.info("--------> Se actualiza plantilla con nueva configuración -----  ");
						descargarRecurso(Environment.getUrlFileServerMotor()+CARPETA_PLANTILLAS+PLANTILLA_LLAVE, Environment.getPathPlantillasClientePdf()+PLANTILLA_LLAVE, PLANTILLA_LLAVE);
					}
					File filePlantillaNoLlave = new File(Environment.getPathPlantillasClientePdf()+PLANTILLA_NO_LLAVE);
					if(filePlantillaNoLlave == null || !filePlantillaNoLlave.exists()) {
						LOGGER.info("--------> No existe la plantilla " + PLANTILLA_NO_LLAVE);
						descargarRecurso(Environment.getUrlFileServerMotor()+CARPETA_PLANTILLAS+PLANTILLA_NO_LLAVE, Environment.getPathPlantillasClientePdf()+PLANTILLA_NO_LLAVE, PLANTILLA_NO_LLAVE);
					} else {
						//TODO Se agregan líneas temporales para realizar copiado de plantilla nuevamente por ajustes
						LOGGER.info("--------> Se actualiza plantilla con nueva configuración -----  ");
						descargarRecurso(Environment.getUrlFileServerMotor()+CARPETA_PLANTILLAS+PLANTILLA_NO_LLAVE, Environment.getPathPlantillasClientePdf()+PLANTILLA_NO_LLAVE, PLANTILLA_NO_LLAVE);
					}
				}
			}
			LOGGER.info("---------------> RecursosServerBean: Finaliza la validación de existencia de recursos (plantillas PDF) ");
		} catch (Exception e) {
			LOGGER.error("Error al decargar los recursos que necesita el cliente: ", e);
		}
	}
	
	/**
	 * Método auxiliar para descargar y copiar un archivo desde una URL
	 * @param urlRecurso
	 * @param pathRecursoDestino
	 * @param nombreRecurso
	 * @throws IOException
	 */
	private void descargarRecurso(String urlRecurso, String pathRecursoDestino, String nombreRecurso) throws IOException {
		InputStream ins = null;
		OutputStream outs = null;
		try {
			LOGGER.info("--------> Inicia descarga y copiado del recurso: " + nombreRecurso);
			URL urlPlantilla = new URL(urlRecurso); 
			File plantilla = new File(pathRecursoDestino);
			plantilla.setReadable(true, false);
			plantilla.setWritable(true, false);
			
			ins = urlPlantilla.openStream();
			outs = new FileOutputStream(plantilla);
			
			int read = Constantes.INT_VALOR_CERO;
	    	byte[] bytes = new byte[Constantes.TAMAÑO_BUFFER];
	    	while ((read = ins.read(bytes)) != -1) {
	    		outs.write(bytes, Constantes.INT_VALOR_CERO, read);
	    	}
	    	ins.close();
	    	outs.flush();
	    	outs.close();		
	    	LOGGER.info("--------> Finaliza descarga y copiado del recurso: " + nombreRecurso);
		} catch (IOException e) {
			LOGGER.error("Error al descargar y/o copiar el recuerso " + nombreRecurso + ": ", e);
			throw new IOException("No se pudo realizar la descarga y/o copiado del recurso necesario en el cliente " + nombreRecurso);
		} finally {
			if(ins != null) {
				try {
					ins.close();
				} catch (Exception e) {
					LOGGER.error("Error al cerrar el inputStream: ", e);
				}
			}
			if(outs != null) {
				try {
					outs.close();
				} catch (Exception e) {
					LOGGER.error("Error al cerrar el outputStream: ", e);
				}
			}
		}
	}

}
