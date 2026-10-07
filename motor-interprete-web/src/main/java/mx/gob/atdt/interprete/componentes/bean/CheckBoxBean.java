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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import mx.gob.atdt.interprete.dto.DetElementosCheckboxDTO;

@Named
@SessionScoped
public class CheckBoxBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9214062020822453675L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckBoxBean.class);
	
	@PostConstruct
	public void inicializarComponente() {
//		LOGGER.info("CheckBoxBean ::::   " + this.toString());
	}
	
	/**
	 * Método de apoyo para obtener el valor convertido en Lista de Elementos a partir de una cadena Json
	 * 
	 * @param strJsonOpciones
	 * @return
	 */
	public List<DetElementosCheckboxDTO> obtenerOpcionesCheck(String strJsonOpciones){
		Gson gson = new Gson();
		java.lang.reflect.Type tipoLista = new TypeToken<ArrayList<DetElementosCheckboxDTO>>(){}.getType();
		ArrayList<DetElementosCheckboxDTO> lstElementosCheck = gson.fromJson(strJsonOpciones, tipoLista);
		
		return lstElementosCheck;
	}
	
	/**
	 * Método de apoyo para obtener el valor convertido a Json de las opciones seleccionadas del Checkbox grupo
	 * 
	 * @param lstOpciones
	 * @return
	 */
	public List<SelectItem> obtenerOpcionSeleccionada(List<DetElementosCheckboxDTO> lstOpciones) {
		List<SelectItem> lstOpcion = new ArrayList<SelectItem>();
		if(lstOpciones != null) {
			lstOpcion.add(new SelectItem(new Gson().toJson(lstOpciones), new Gson().toJson(lstOpciones)));
		}
	    
		return lstOpcion;
	}	
}
