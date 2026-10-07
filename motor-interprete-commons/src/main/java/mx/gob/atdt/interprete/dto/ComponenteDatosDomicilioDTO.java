package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class ComponenteDatosDomicilioDTO extends ComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7706305982295737620L;

	private Long idComponenteDatosDomicilio;
	private boolean habilitaCalle;
	private boolean calleObligatorio;
	private String textoInteriorCalle;
	private boolean habilitaNumeroExterior;
	private boolean numeroExteriorObligatorio;
	private String textoInteriorNumeroExterior;
	private boolean habilitaNumeroInterior;
	private boolean numeroInteriorObligatorio;
	private String textoInteriorNumeroInterior;
	private boolean habilitaCodigoPostal;
	private boolean codigoPostalObligatorio;
	private String textoInteriorCodigoPostal;
	private boolean habilitaColonia;
	private boolean coloniaObligatorio;
	private String textoInteriorColonia;
	private boolean habilitaAlcaldia;
	private boolean alcaldiaObligatorio;
	private String textoInteriorAlcaldia;
	private boolean habilitaEstado;
	private boolean estadoObligatorio;
	private String textoInteriorEstado;
	
	//Variables para colocar el valor ingresado como respuesta
	private String calle;
	private String numeroExterior;
	private String numeroInterior;
	private String codigoPostal;
	private String colonia;
	private String alcaldia;
	private String estado;

	/**
	 * 
	 */
	public ComponenteDatosDomicilioDTO() {
	}

	/**
	 * Constructor utilizado en la NamedQuery ComponenteDatosDomicilio.findByIdComponente
	 * 
	 * @param idComponenteDatosDomicilio
	 * @param idComponente
	 * @param idTipoComponente
	 * @param idSubseccionFormulario
	 * @param orden
	 * @param requerido
	 * @param tooltip
	 * @param descripcionTooltip
	 * @param tituloCampo
	 * @param activo
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param seccionSincronizada
	 * @param habilitaCalle
	 * @param calleObligatorio
	 * @param textoInteriorCalle
	 * @param habilitaNumeroExterior
	 * @param numeroExteriorObligatorio
	 * @param textoInteriorNumeroExterior
	 * @param habilitaNumeroInterior
	 * @param numeroInteriorObligatorio
	 * @param textoInteriorNumeroInterior
	 * @param habilitaCodigoPostal
	 * @param codigoPostalObligatorio
	 * @param textoInteriorCodigoPostal
	 * @param habilitaColonia
	 * @param coloniaObligatorio
	 * @param textoInteriorColonia
	 * @param habilitaAlcaldia
	 * @param alcaldiaObligatorio
	 * @param textoInteriorAlcaldia
	 * @param habilitaEstado
	 * @param estadoObligatorio
	 * @param textoInteriorEstado
	 */
	public ComponenteDatosDomicilioDTO(Long idComponenteDatosDomicilio, Long idComponente, Integer idTipoComponente,
			Long idSubseccionFormulario, int orden, boolean requerido, boolean tooltip, String descripcionTooltip,
			String tituloCampo, boolean activo, Date fechaCreacion, Date fechaUltimaActualizacion, boolean seccionSincronizada,
			boolean habilitaCalle, boolean calleObligatorio, String textoInteriorCalle, boolean habilitaNumeroExterior,
			boolean numeroExteriorObligatorio, String textoInteriorNumeroExterior, boolean habilitaNumeroInterior,
			boolean numeroInteriorObligatorio, String textoInteriorNumeroInterior, boolean habilitaCodigoPostal,
			boolean codigoPostalObligatorio, String textoInteriorCodigoPostal, boolean habilitaColonia,
			boolean coloniaObligatorio, String textoInteriorColonia, boolean habilitaAlcaldia,
			boolean alcaldiaObligatorio, String textoInteriorAlcaldia, boolean habilitaEstado,
			boolean estadoObligatorio, String textoInteriorEstado) {
		super(idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip, descripcionTooltip,
				tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);
		this.idComponenteDatosDomicilio = idComponenteDatosDomicilio;
		this.habilitaCalle = habilitaCalle;
		this.calleObligatorio = calleObligatorio;
		this.textoInteriorCalle = textoInteriorCalle;
		this.habilitaNumeroExterior = habilitaNumeroExterior;
		this.numeroExteriorObligatorio = numeroExteriorObligatorio;
		this.textoInteriorNumeroExterior = textoInteriorNumeroExterior;
		this.habilitaNumeroInterior = habilitaNumeroInterior;
		this.numeroInteriorObligatorio = numeroInteriorObligatorio;
		this.textoInteriorNumeroInterior = textoInteriorNumeroInterior;
		this.habilitaCodigoPostal = habilitaCodigoPostal;
		this.codigoPostalObligatorio = codigoPostalObligatorio;
		this.textoInteriorCodigoPostal = textoInteriorCodigoPostal;
		this.habilitaColonia = habilitaColonia;
		this.coloniaObligatorio = coloniaObligatorio;
		this.textoInteriorColonia = textoInteriorColonia;
		this.habilitaAlcaldia = habilitaAlcaldia;
		this.alcaldiaObligatorio = alcaldiaObligatorio;
		this.textoInteriorAlcaldia = textoInteriorAlcaldia;
		this.habilitaEstado = habilitaEstado;
		this.estadoObligatorio = estadoObligatorio;
		this.textoInteriorEstado = textoInteriorEstado;
	}

	public Long getIdComponenteDatosDomicilio() {
		return idComponenteDatosDomicilio;
	}

	public void setIdComponenteDatosDomicilio(Long idComponenteDatosDomicilio) {
		this.idComponenteDatosDomicilio = idComponenteDatosDomicilio;
	}

	public boolean isHabilitaCalle() {
		return habilitaCalle;
	}

	public void setHabilitaCalle(boolean habilitaCalle) {
		this.habilitaCalle = habilitaCalle;
	}

	public boolean isCalleObligatorio() {
		return calleObligatorio;
	}

	public void setCalleObligatorio(boolean calleObligatorio) {
		this.calleObligatorio = calleObligatorio;
	}

	public String getTextoInteriorCalle() {
		return textoInteriorCalle;
	}

	public void setTextoInteriorCalle(String textoInteriorCalle) {
		this.textoInteriorCalle = textoInteriorCalle;
	}

	public boolean isHabilitaNumeroExterior() {
		return habilitaNumeroExterior;
	}

	public void setHabilitaNumeroExterior(boolean habilitaNumeroExterior) {
		this.habilitaNumeroExterior = habilitaNumeroExterior;
	}

	public boolean isNumeroExteriorObligatorio() {
		return numeroExteriorObligatorio;
	}

	public void setNumeroExteriorObligatorio(boolean numeroExteriorObligatorio) {
		this.numeroExteriorObligatorio = numeroExteriorObligatorio;
	}

	public String getTextoInteriorNumeroExterior() {
		return textoInteriorNumeroExterior;
	}

	public void setTextoInteriorNumeroExterior(String textoInteriorNumeroExterior) {
		this.textoInteriorNumeroExterior = textoInteriorNumeroExterior;
	}

	public boolean isHabilitaNumeroInterior() {
		return habilitaNumeroInterior;
	}

	public void setHabilitaNumeroInterior(boolean habilitaNumeroInterior) {
		this.habilitaNumeroInterior = habilitaNumeroInterior;
	}

	public boolean isNumeroInteriorObligatorio() {
		return numeroInteriorObligatorio;
	}

	public void setNumeroInteriorObligatorio(boolean numeroInteriorObligatorio) {
		this.numeroInteriorObligatorio = numeroInteriorObligatorio;
	}

	public String getTextoInteriorNumeroInterior() {
		return textoInteriorNumeroInterior;
	}

	public void setTextoInteriorNumeroInterior(String textoInteriorNumeroInterior) {
		this.textoInteriorNumeroInterior = textoInteriorNumeroInterior;
	}

	public boolean isHabilitaCodigoPostal() {
		return habilitaCodigoPostal;
	}

	public void setHabilitaCodigoPostal(boolean habilitaCodigoPostal) {
		this.habilitaCodigoPostal = habilitaCodigoPostal;
	}

	public boolean isCodigoPostalObligatorio() {
		return codigoPostalObligatorio;
	}

	public void setCodigoPostalObligatorio(boolean codigoPostalObligatorio) {
		this.codigoPostalObligatorio = codigoPostalObligatorio;
	}

	public String getTextoInteriorCodigoPostal() {
		return textoInteriorCodigoPostal;
	}

	public void setTextoInteriorCodigoPostal(String textoInteriorCodigoPostal) {
		this.textoInteriorCodigoPostal = textoInteriorCodigoPostal;
	}

	public boolean isHabilitaColonia() {
		return habilitaColonia;
	}

	public void setHabilitaColonia(boolean habilitaColonia) {
		this.habilitaColonia = habilitaColonia;
	}

	public boolean isColoniaObligatorio() {
		return coloniaObligatorio;
	}

	public void setColoniaObligatorio(boolean coloniaObligatorio) {
		this.coloniaObligatorio = coloniaObligatorio;
	}

	public String getTextoInteriorColonia() {
		return textoInteriorColonia;
	}

	public void setTextoInteriorColonia(String textoInteriorColonia) {
		this.textoInteriorColonia = textoInteriorColonia;
	}

	public boolean isHabilitaAlcaldia() {
		return habilitaAlcaldia;
	}

	public void setHabilitaAlcaldia(boolean habilitaAlcaldia) {
		this.habilitaAlcaldia = habilitaAlcaldia;
	}

	public boolean isAlcaldiaObligatorio() {
		return alcaldiaObligatorio;
	}

	public void setAlcaldiaObligatorio(boolean alcaldiaObligatorio) {
		this.alcaldiaObligatorio = alcaldiaObligatorio;
	}

	public String getTextoInteriorAlcaldia() {
		return textoInteriorAlcaldia;
	}

	public void setTextoInteriorAlcaldia(String textoInteriorAlcaldia) {
		this.textoInteriorAlcaldia = textoInteriorAlcaldia;
	}
	
	public boolean isHabilitaEstado() {
		return habilitaEstado;
	}

	public void setHabilitaEstado(boolean habilitaEstado) {
		this.habilitaEstado = habilitaEstado;
	}

	public boolean isEstadoObligatorio() {
		return estadoObligatorio;
	}

	public void setEstadoObligatorio(boolean estadoObligatorio) {
		this.estadoObligatorio = estadoObligatorio;
	}
	
	public String getTextoInteriorEstado() {
		return textoInteriorEstado;
	}

	public void setTextoInteriorEstado(String textoInteriorEstado) {
		this.textoInteriorEstado = textoInteriorEstado;
	}
	
	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}

	/**
	 * @return the numeroExterior
	 */
	public String getNumeroExterior() {
		return numeroExterior;
	}

	/**
	 * @param numeroExterior the numeroExterior to set
	 */
	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	/**
	 * @return the numeroInterior
	 */
	public String getNumeroInterior() {
		return numeroInterior;
	}

	/**
	 * @param numeroInterior the numeroInterior to set
	 */
	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}


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

	/**
	 * @return the colonia
	 */
	public String getColonia() {
		return colonia;
	}

	/**
	 * @param colonia the colonia to set
	 */
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	/**
	 * @return the alcaldia
	 */
	public String getAlcaldia() {
		return alcaldia;
	}

	/**
	 * @param alcaldia the alcaldia to set
	 */
	public void setAlcaldia(String alcaldia) {
		this.alcaldia = alcaldia;
	}
	
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}


//	@Override
//	public String toString() {
//		return "ComponenteDatosDomicilioDTO [idComponenteDatosDomicilio=" + idComponenteDatosDomicilio
//				+ ", habilitaCalle=" + habilitaCalle + ", calleObligatorio=" + calleObligatorio
//				+ ", textoInteriorCalle=" + textoInteriorCalle + ", habilitaNumeroExterior=" + habilitaNumeroExterior
//				+ ", numeroExteriorObligatorio=" + numeroExteriorObligatorio + ", textoInteriorNumeroExterior="
//				+ textoInteriorNumeroExterior + ", habilitaNumeroInterior=" + habilitaNumeroInterior
//				+ ", numeroInteriorObligatorio=" + numeroInteriorObligatorio + ", textoInteriorNumeroInterior="
//				+ textoInteriorNumeroInterior + ", habilitaCodigoPostal=" + habilitaCodigoPostal
//				+ ", codigoPostalObligatorio=" + codigoPostalObligatorio + ", textoInteriorCodigoPostal="
//				+ textoInteriorCodigoPostal + ", habilitaColonia=" + habilitaColonia + ", coloniaObligatorio="
//				+ coloniaObligatorio + ", textoInteriorColonia=" + textoInteriorColonia + ", habilitaAlcaldia="
//				+ habilitaAlcaldia + ", alcaldiaObligatorio=" + alcaldiaObligatorio + ", textoInteriorAlcaldia="
//				+ textoInteriorAlcaldia + "]";
//	}
}
