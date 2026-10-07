package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;




public class ComponenteCampoTextoDTO extends ComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2690963339376845931L;
	
	private long idComponenteCampoTexto;
	private CatOrigenLlenadoDTO catOrigenLlenadoDTO;
	private CatValidadoresDTO catValidadoresDTO;
	private boolean alfanumerico;
	private boolean numerico;
	private boolean habilitaTextoInterior;
	private String textoInterior;
	private boolean validadores;
	private Long valorMinimo;
	private Long valorMaximo;
	private boolean permiteDecimales;

	/**
	 * 
	 */
	public ComponenteCampoTextoDTO() {
		super();
		this.catOrigenLlenadoDTO = new CatOrigenLlenadoDTO();
		this.catValidadoresDTO = new CatValidadoresDTO();
	}
	
	/**
	 * Constructor utilizado por la NamedQuery ComponenteCampoTexto.findComponentesCampoTextoByIdComponente
	 * 
	 * @param idComponenteCampoTexto
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
	 * @param alfanumerico
	 * @param numerico
	 * @param habilitaTextoInterior
	 * @param textoInterior
	 * @param idOrigenLlenado
	 * @param validadores
	 * @param idValidador
	 * @param valorMinimo
	 * @param valorMaximo
	 * @param permiteDecimales
	 */
	public ComponenteCampoTextoDTO(long idComponenteCampoTexto, Long idComponente, Integer idTipoComponente,
			Long idSubseccionFormulario, int orden, boolean requerido, boolean tooltip, String descripcionTooltip, 
			String tituloCampo, boolean activo, Date fechaCreacion, Date fechaUltimaActualizacion, boolean seccionSincronizada, 
			boolean alfanumerico, boolean numerico, boolean habilitaTextoInterior, String textoInterior, Integer idOrigenLlenado , 
			boolean validadores, Integer idValidador, Long valorMinimo, Long valorMaximo, boolean permiteDecimales) {
		
		super(idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip,
				descripcionTooltip, tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);
		
		this.idComponenteCampoTexto = idComponenteCampoTexto;
		this.catOrigenLlenadoDTO = new CatOrigenLlenadoDTO(idOrigenLlenado);
		this.catValidadoresDTO = new CatValidadoresDTO(idValidador);;
		this.alfanumerico = alfanumerico;
		this.numerico = numerico;
		this.habilitaTextoInterior = habilitaTextoInterior;
		this.textoInterior = textoInterior;
		this.validadores = validadores;
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		this.permiteDecimales = permiteDecimales;
	}
	
	/**
	 * Constructor utulizado en la NamedQuery = ComponenteCampoTexto.findById
	 * @param idComponenteCampoTexto
	 * @param alfanumerico
	 * @param numerico
	 * @param habilitaTextoInterior
	 * @param textoInterior
	 * @param idOrigenLlenado
	 * @param validadores
	 * @param idValidador
	 * @param valorMinimo
	 * @param valorMaximo
	 * @param permiteDecimales
	 */
	public ComponenteCampoTextoDTO(long idComponenteCampoTexto,
			boolean alfanumerico, boolean numerico, boolean habilitaTextoInterior, String textoInterior, Integer idOrigenLlenado , 
			boolean validadores, Integer idValidador, Long valorMinimo, Long valorMaximo, boolean permiteDecimales) {
		this.idComponenteCampoTexto = idComponenteCampoTexto;
		this.catOrigenLlenadoDTO = new CatOrigenLlenadoDTO(idOrigenLlenado);
		this.catValidadoresDTO = new CatValidadoresDTO(idValidador);;
		this.alfanumerico = alfanumerico;
		this.numerico = numerico;
		this.habilitaTextoInterior = habilitaTextoInterior;
		this.textoInterior = textoInterior;
		this.validadores = validadores;
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		this.permiteDecimales = permiteDecimales;
	}
	
	/**
	 * Constructor utilizado por la consulta ComponenteCampoTextoDAO.existeActivoPorOrigenProyecto
	 * @param idComponenteCampoTexto
	 * @param idComponente
	 * @param idTipoComponente
	 * @param activo
	 * @param idOrigenLlenado
	 */
	public ComponenteCampoTextoDTO(long idComponenteCampoTexto, Long idComponente, Integer idTipoComponente,
			boolean activo, Integer idOrigenLlenado) {		
		super(idComponente, idTipoComponente, activo);		
		this.idComponenteCampoTexto = idComponenteCampoTexto;
		this.catOrigenLlenadoDTO = new CatOrigenLlenadoDTO(idOrigenLlenado);		
	}

	/**
	 * @return the idComponenteCampoTexto
	 */
	public long getIdComponenteCampoTexto() {
		return idComponenteCampoTexto;
	}

	/**
	 * @param idComponenteCampoTexto the idComponenteCampoTexto to set
	 */
	public void setIdComponenteCampoTexto(long idComponenteCampoTexto) {
		this.idComponenteCampoTexto = idComponenteCampoTexto;
	}

	/**
	 * @return the catOrigenLlenadoDTO
	 */
	public CatOrigenLlenadoDTO getCatOrigenLlenadoDTO() {
		return catOrigenLlenadoDTO;
	}

	/**
	 * @param catOrigenLlenadoDTO the catOrigenLlenadoDTO to set
	 */
	public void setCatOrigenLlenadoDTO(CatOrigenLlenadoDTO catOrigenLlenadoDTO) {
		this.catOrigenLlenadoDTO = catOrigenLlenadoDTO;
	}

	/**
	 * @return the catValidadoresDTO
	 */
	public CatValidadoresDTO getCatValidadoresDTO() {
		return catValidadoresDTO;
	}

	/**
	 * @param catValidadoresDTO the catValidadoresDTO to set
	 */
	public void setCatValidadoresDTO(CatValidadoresDTO catValidadoresDTO) {
		this.catValidadoresDTO = catValidadoresDTO;
	}
	

	/**
	 * @return the alfanumerico
	 */
	public boolean isAlfanumerico() {
		return alfanumerico;
	}

	/**
	 * @param alfanumerico the alfanumerico to set
	 */
	public void setAlfanumerico(boolean alfanumerico) {
		this.alfanumerico = alfanumerico;
	}

	/**
	 * @return the numerico
	 */
	public boolean isNumerico() {
		return numerico;
	}

	/**
	 * @param numerico the numerico to set
	 */
	public void setNumerico(boolean numerico) {
		this.numerico = numerico;
	}

	/**
	 * @return the habilitaTextoInterior
	 */
	public boolean isHabilitaTextoInterior() {
		return habilitaTextoInterior;
	}

	/**
	 * @param habilitaTextoInterior the habilitaTextoInterior to set
	 */
	public void setHabilitaTextoInterior(boolean habilitaTextoInterior) {
		this.habilitaTextoInterior = habilitaTextoInterior;
	}

	/**
	 * @return the textoInterior
	 */
	public String getTextoInterior() {
		return textoInterior;
	}

	/**
	 * @param textoInterior the textoInterior to set
	 */
	public void setTextoInterior(String textoInterior) {
		this.textoInterior = textoInterior;
	}

	/**
	 * @return the validadores
	 */
	public boolean isValidadores() {
		return validadores;
	}

	/**
	 * @param validadores the validadores to set
	 */
	public void setValidadores(boolean validadores) {
		this.validadores = validadores;
	}

	/**
	 * @return the valorMinimo
	 */
	public Long getValorMinimo() {
		return valorMinimo;
	}

	/**
	 * @param valorMinimo the valorMinimo to set
	 */
	public void setValorMinimo(Long valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	/**
	 * @return the valorMaximo
	 */
	public Long getValorMaximo() {
		return valorMaximo;
	}

	/**
	 * @param valorMaximo the valorMaximo to set
	 */
	public void setValorMaximo(Long valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public boolean isPermiteDecimales() {
		return permiteDecimales;
	}

	public void setPermiteDecimales(boolean permiteDecimales) {
		this.permiteDecimales = permiteDecimales;
	}

}
