package mx.gob.atdt.interprete.componentes.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class RadioBotonBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6258996432103323447L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RadioBotonBean.class);

	@PostConstruct
	public void inicializarComponente() {
//		LOGGER.info("RadioBotonBean ::::   " + this.toString());
	}
	
	/**
	 * Método de apoyo para obtener la opción seleccionada en el componente RadioBoton
	 * 
	 * @param opcionSeleccionada
	 * @return
	 */
	public List<SelectItem> obtenerOpcionSeleccionada(String opcionSeleccionada) {
		List<SelectItem> lstOpcion = new ArrayList<SelectItem>();
		if(opcionSeleccionada != null) {
			if(opcionSeleccionada.equals("0")) {
				lstOpcion.add(new SelectItem(true, "true"));
			} else {
				lstOpcion.add(new SelectItem(false, "false"));	
			}
		} else {
			lstOpcion.add(new SelectItem(false, "false"));
		}		
		return lstOpcion;
	}
}
