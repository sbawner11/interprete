package mx.gob.atdt.interprete.componentes.bean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.commons.dto.CatAsentamientosDTO;
import mx.gob.atdt.interprete.commons.dto.CatCodigosPostalesDTO;
import mx.gob.atdt.interprete.commons.dto.CatMunicipiosDTO;
import mx.gob.atdt.interprete.commons.dto.CatEstadosDTO;
import mx.gob.atdt.interprete.dao.CatAsentamientosDAO;
import mx.gob.atdt.interprete.dao.CatCodigosPostalesDAO;
import mx.gob.atdt.interprete.dao.CatEstadosDAO;
import mx.gob.atdt.interprete.dao.CatMunicipiosDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class DomicilioBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7843876046796279105L;

	private static final Logger LOGGER = LoggerFactory.getLogger(DomicilioBean.class);

	@Inject
	private CatCodigosPostalesDAO catCodigosPostalesDAO;
	
	@Inject
	private CatEstadosDAO catEstadosDAO;

	@Inject
	private CatMunicipiosDAO catMunicipiosDAO;

	@Inject
	private CatAsentamientosDAO catAsentamientosDAO;

	private String codigoPostal;

	@PostConstruct
	public void inicializar() {
//		LOGGER.info("DomicilioBean ::::   " + this.toString());
	}
	
	/**
	 * Método que realiza la búsqueda de Estado mediante el Código Postal
	 * capturado.
	 * 
	 * @param codigoPostal
	 * @return
	 */
	public List<CatEstadosDTO> obtenerEstado(String codigoPostal) {
		List<CatEstadosDTO> lstEstadosDTO = new ArrayList<>();
		if (codigoPostal != null && !codigoPostal.isEmpty()) {
			List<CatCodigosPostalesDTO> lstCodigosPostales = catCodigosPostalesDAO.buscarPorCodigoPostal(codigoPostal);
			if (lstCodigosPostales != null) {
				lstEstadosDTO = catEstadosDAO
						.buscarPorIdEstado((lstCodigosPostales.get(0).getCatEstadosDTO().getIdEstado()));
			}
		}
		return lstEstadosDTO;

	}

	/**
	 * Método que realiza la búsqueda de Municipio mediante el Código Postal
	 * capturado.
	 * 
	 * @param codigoPostal
	 * @return
	 */
	public List<CatMunicipiosDTO> obtenerAlcaldia(String codigoPostal) {
		List<CatMunicipiosDTO> lstMunicipiosDTO = new ArrayList<CatMunicipiosDTO>();
		if (codigoPostal != null && !codigoPostal.isEmpty()) {
			List<CatCodigosPostalesDTO> lstCodigosPostales = catCodigosPostalesDAO.buscarPorCodigoPostal(codigoPostal);
			if (lstCodigosPostales != null) {
				lstMunicipiosDTO = catMunicipiosDAO
						.buscarPorIdMunicipio(lstCodigosPostales.get(0).getCatMunicipiosDTO().getIdMunicipio());
			}
		}
		return lstMunicipiosDTO;

	}

	/**
	 * Método que realiza la búsqueda de Alcaldía mediente el Código Postal
	 * capturado.
	 * 
	 * @param codigoPostal
	 * @return
	 */
	public List<CatAsentamientosDTO> obtenerColonias(String codigoPostal) {
		List<CatAsentamientosDTO> lstAsentamientos = new ArrayList<CatAsentamientosDTO>();
		if (codigoPostal != null && !codigoPostal.isEmpty()) {
			List<CatCodigosPostalesDTO> lstCodigosPostales = catCodigosPostalesDAO.buscarPorCodigoPostal(codigoPostal);
			if (lstCodigosPostales != null) {
				for (int i = 0; i < lstCodigosPostales.size(); i++) {
					CatAsentamientosDTO asentamiento = new CatAsentamientosDTO();
					asentamiento = catAsentamientosDAO.buscarPorIdMunicipio(
							lstCodigosPostales.get(i).getCatAsentamientosDTO().getIdAsentamiento());
					lstAsentamientos.add(asentamiento);
				}
			}
		}
		return lstAsentamientos;
	}
	
	/**GETTER´s y SETTER´s**/

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

}
