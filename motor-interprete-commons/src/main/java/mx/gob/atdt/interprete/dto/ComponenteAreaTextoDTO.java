package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class ComponenteAreaTextoDTO extends ComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2690963339376845931L;
	
	private long idComponenteAreaTexto;
	private CatOrigenLlenadoDTO catOrigenLlenadoDTO;
	private boolean habilitaTextoInterior;
	private String textoInterior;
	private int lineasAltura; 

	/**
	 * 
	 */
	public ComponenteAreaTextoDTO() {
		super();
		this.catOrigenLlenadoDTO = null;
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
	 * @param habilitaTextoInterior
	 * @param textoInterior
	 * @param idOrigenLlenado
	 * @param lineasAltura
	 */
	@SuppressWarnings("java:S107")
	public ComponenteAreaTextoDTO(long idComponenteAreaTexto, Long idComponente, Integer idTipoComponente,
			Long idSubseccionFormulario, int orden, boolean requerido, boolean tooltip, String descripcionTooltip, 
			String tituloCampo, boolean activo, Date fechaCreacion, Date fechaUltimaActualizacion, boolean seccionSincronizada, 
			boolean habilitaTextoInterior, String textoInterior, Integer idOrigenLlenado, int lineasAltura ) {
		
		super(idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip,
				descripcionTooltip, tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);
		
		this.idComponenteAreaTexto = idComponenteAreaTexto;
		if(idOrigenLlenado != null) {
			this.catOrigenLlenadoDTO = new CatOrigenLlenadoDTO(idOrigenLlenado);	
		} else {
			this.catOrigenLlenadoDTO = null;
		}
		
		this.habilitaTextoInterior = habilitaTextoInterior;
		this.textoInterior = textoInterior;
		this.lineasAltura = lineasAltura;
	}
	
	/**
	 * Constructor utilizado por la consulta ComponenteCampoTextoDAO.existeActivoPorOrigenProyecto
	 * @param idComponenteCampoTexto
	 * @param idComponente
	 * @param idTipoComponente
	 * @param activo
	 * @param idOrigenLlenado
	 * @param lineasAltura
	 */
	public ComponenteAreaTextoDTO(long idComponenteAreaTexto, Long idComponente, Integer idTipoComponente,
			boolean activo, Integer idOrigenLlenado, int lineasAltura) {		
		super(idComponente, idTipoComponente, activo);		
		this.idComponenteAreaTexto = idComponenteAreaTexto;
		this.catOrigenLlenadoDTO = new CatOrigenLlenadoDTO(idOrigenLlenado);
		this.lineasAltura = lineasAltura;
	}

	/**
	 * @return the idComponenteAreaTexto
	 */
	public long getIdComponenteAreaTexto() {
		return idComponenteAreaTexto;
	}

	/**
	 * @param idComponenteAreaTexto the idComponenteAreaTexto to set
	 */
	public void setIdComponenteAreaTexto(long idComponenteAreaTexto) {
		this.idComponenteAreaTexto = idComponenteAreaTexto;
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
	 * @return the lineasAltura
	 */
	public int getLineasAltura() {
		return lineasAltura;
	}

	/**
	 * @param lineasAltura the lineasAltura to set
	 */
	public void setLineasAltura(int lineasAltura) {
		this.lineasAltura = lineasAltura;
	}
	
}
