package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.SeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.SeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;

@Stateless
@LocalBean
public class SeccionesFormularioFacade {

	@Inject
	private SeccionesFormularioDAO seccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO estructuraFormularioDAO;


	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(SeccionesFormularioDTO seccion) throws Exception {
		
		//Se actualiza la información de la sección
		seccionesFormularioDAO.actualizar(seccion);
		
		//Se genera la tabla de trámites y su secuencia en BD para el guardado de trámites
		if(!estructuraFormularioDAO.existeTablaTramites()) {
			estructuraFormularioDAO.creaTablaTramites();
			estructuraFormularioDAO.creaSecuenciaTablaTramites();			
		} else {
			if(!estructuraFormularioDAO.existeColumna(Constantes.NOMBRE_BASE_TABLA_TRAMITES, Constantes.NOMBRE_COLUMNA_RUTA_DOC_REVOCADO)) {
				estructuraFormularioDAO.crearNuevaColumnaTabla(
						Constantes.NOMBRE_BASE_TABLA_TRAMITES, 
						Constantes.NOMBRE_COLUMNA_RUTA_DOC_REVOCADO, 
						Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "500"));
			}
			if(!estructuraFormularioDAO.existeColumna(Constantes.NOMBRE_BASE_TABLA_TRAMITES, Constantes.NOMBRE_COLUMNA_MOTIVO_RECHAZO)) {
				estructuraFormularioDAO.crearNuevaColumnaTabla(
						Constantes.NOMBRE_BASE_TABLA_TRAMITES, 
						Constantes.NOMBRE_COLUMNA_MOTIVO_RECHAZO, 
						Constantes.TIPO_DATO_TEXT);
			}
		}
		
		//Se genera la tabla de trámites para firma electrónica y su secuencia, en caso de que aún no exista.
		if(estructuraFormularioDAO.existeTablaTramites()) {
			if(!estructuraFormularioDAO.existeTablaTramitesFirma()) {
				estructuraFormularioDAO.creaTablaTramitesFirma();
				estructuraFormularioDAO.creaSecuenciaTablaFirmaTramites();
			}	
		}		
		
		//Se genera la tabla y secuencia en BD para el guardado de información de la sección actual 
		if(!estructuraFormularioDAO.existeTabla(seccion)) {
			estructuraFormularioDAO.creaNuevaTabla(seccion);
			estructuraFormularioDAO.creaSecuenciaTabla(seccion);
		}
		
		//Se genera la tabla de control componentes en BD para el guardado de información del formulario creado
		if(!estructuraFormularioDAO.existeTablaControl()) {
			estructuraFormularioDAO.creaNuevaTablaControl(seccion);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void generarCamposParaAvisos() {
		if(estructuraFormularioDAO.existeTablaTramites()) {
			if(!estructuraFormularioDAO.existeColumna(Constantes.NOMBRE_BASE_TABLA_TRAMITES, Constantes.NOMBRE_COLUMNA_RUTA_DOC_REVOCADO)) {
				estructuraFormularioDAO.crearNuevaColumnaTabla(
						Constantes.NOMBRE_BASE_TABLA_TRAMITES, 
						Constantes.NOMBRE_COLUMNA_RUTA_DOC_REVOCADO, 
						Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "500"));
			}
			if(!estructuraFormularioDAO.existeColumna(Constantes.NOMBRE_BASE_TABLA_TRAMITES, Constantes.NOMBRE_COLUMNA_MOTIVO_RECHAZO)) {
				estructuraFormularioDAO.crearNuevaColumnaTabla(
						Constantes.NOMBRE_BASE_TABLA_TRAMITES, 
						Constantes.NOMBRE_COLUMNA_MOTIVO_RECHAZO, 
						Constantes.TIPO_DATO_TEXT);
			}
		}
	}
}
