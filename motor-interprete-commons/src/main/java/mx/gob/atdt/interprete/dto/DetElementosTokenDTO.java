package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class DetElementosTokenDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5400725414915770556L;

	private Long idElementoToken;
	private ArchivosRespuestaTokenDTO archivosRespuestaTokenDTO;
	private CatOrigenTokenDTO catOrigenTokenDTO;
	private Long idComponente;
	private String nombreToken;
	private String estructuraFolio;
	private int idFormatoFecha;
	private String longitudFolio;
	private String campoPersonalizado;
	private int orden;
	private boolean activo;
	private String tooltip;

	private  CatAtributosComponentesDTO catAtributosComponentesDTO;
	
	private boolean datoObligatorio;
	
	public DetElementosTokenDTO() {

	}

	public DetElementosTokenDTO(int orden, String descripcion, Integer idOrigenToken, boolean activo) {
		this.orden = orden;
		this.nombreToken = descripcion;
		this.catOrigenTokenDTO = new CatOrigenTokenDTO(idOrigenToken);
		this.activo = activo;
	}

	/**
	 * Contructor utilizado para el namedQuery
	 * DetElementosToken.findByIdArchivoRespuesta
	 * 
	 * @param idElementoToken
	 * @param archivosRespuestaTokenDTO
	 * @param nombreToken
	 * @param idOrigenToken
	 * @param estructuraFolio
	 * @param idFormatoFecha
	 * @param longitudFolio
	 * @param campoPersonalizado
	 * @param idComponente
	 * @param orden
	 * @param activo
	 */
	public DetElementosTokenDTO(Long idElementoToken, Long idArchivosRespuestaToken, String nombreToken,
			Integer idOrigenToken, String estructuraFolio, int idFormatoFecha, String longitudFolio,
			String campoPersonalizado, Long idComponente, int orden, boolean activo, Long idAtributoComponente) {
		this.idElementoToken = idElementoToken;
		this.archivosRespuestaTokenDTO = new ArchivosRespuestaTokenDTO(idArchivosRespuestaToken);
		this.nombreToken = nombreToken;
		this.catOrigenTokenDTO = new CatOrigenTokenDTO(idOrigenToken);
		this.estructuraFolio = estructuraFolio;
		this.idFormatoFecha = idFormatoFecha;
		this.longitudFolio = longitudFolio;
		this.campoPersonalizado = campoPersonalizado;
		this.idComponente = idComponente;
		this.orden = orden;
		this.activo = activo;
		if (idAtributoComponente != null) {
		this.catAtributosComponentesDTO = new CatAtributosComponentesDTO(idAtributoComponente);
		}
	}

	/**
	 * Contructor utilizado para el namedQuery DetElementosToken.findByIdComponente
	 * 
	 * @param idElementoToken
	 * @param nombreToken
	 * @param idOrigenToken
	 * @param estructuraFolio
	 * @param idFormatoFecha
	 * @param longitudFolio
	 * @param campoPersonalizado
	 * @param idComponente
	 * @param orden
	 * @param activo
	 */
	public DetElementosTokenDTO(Long idElementoToken, String nombreToken, Integer idOrigenToken, String estructuraFolio,
			int idFormatoFecha, String longitudFolio, String campoPersonalizado, Long idComponente, int orden,
			boolean activo) {
		super();
		this.idElementoToken = idElementoToken;
		this.nombreToken = nombreToken;
		this.catOrigenTokenDTO = new CatOrigenTokenDTO(idOrigenToken);
		this.estructuraFolio = estructuraFolio;
		this.idFormatoFecha = idFormatoFecha;
		this.longitudFolio = longitudFolio;
		this.campoPersonalizado = campoPersonalizado;
		this.idComponente = idComponente;
		this.orden = orden;
		this.activo = activo;
	}

	/**
	 * @return the idElementoToken
	 */
	public Long getIdElementoToken() {
		return idElementoToken;
	}

	/**
	 * @param idElementoToken the idElementoToken to set
	 */
	public void setIdElementoToken(Long idElementoToken) {
		this.idElementoToken = idElementoToken;
	}

	/**
	 * @return the archivosRespuestaTokenDTO
	 */
	public ArchivosRespuestaTokenDTO getArchivosRespuestaTokenDTO() {
		return archivosRespuestaTokenDTO;
	}

	/**
	 * @param archivosRespuestaTokenDTO the archivosRespuestaTokenDTO to set
	 */
	public void setArchivosRespuestaTokenDTO(ArchivosRespuestaTokenDTO archivosRespuestaTokenDTO) {
		this.archivosRespuestaTokenDTO = archivosRespuestaTokenDTO;
	}

	/**
	 * @return the catOrigenTokenDTO
	 */
	public CatOrigenTokenDTO getCatOrigenTokenDTO() {
		return catOrigenTokenDTO;
	}

	/**
	 * @param catOrigenTokenDTO the catOrigenTokenDTO to set
	 */
	public void setCatOrigenTokenDTO(CatOrigenTokenDTO catOrigenTokenDTO) {
		this.catOrigenTokenDTO = catOrigenTokenDTO;
	}

	/**
	 * @return the idComponente
	 */
	public Long getIdComponente() {
		return idComponente;
	}

	/**
	 * @param idComponente the idComponente to set
	 */
	public void setIdComponente(Long idComponente) {
		this.idComponente = idComponente;
	}

	/**
	 * @return the nombreToken
	 */
	public String getNombreToken() {
		return nombreToken;
	}

	/**
	 * @param nombreToken the nombreToken to set
	 */
	public void setNombreToken(String nombreToken) {
		this.nombreToken = nombreToken;
	}

	/**
	 * @return the estructuraFolio
	 */
	public String getEstructuraFolio() {
		return estructuraFolio;
	}

	/**
	 * @param estructuraFolio the estructuraFolio to set
	 */
	public void setEstructuraFolio(String estructuraFolio) {
		this.estructuraFolio = estructuraFolio;
	}

	/**
	 * @return the idFormatoFecha
	 */
	public int getIdFormatoFecha() {
		return idFormatoFecha;
	}

	/**
	 * @param idFormatoFecha the idFormatoFecha to set
	 */
	public void setIdFormatoFecha(int idFormatoFecha) {
		this.idFormatoFecha = idFormatoFecha;
	}

	/**
	 * @return the longitudFolio
	 */
	public String getLongitudFolio() {
		return longitudFolio;
	}

	/**
	 * @param longitudFolio the longitudFolio to set
	 */
	public void setLongitudFolio(String longitudFolio) {
		this.longitudFolio = longitudFolio;
	}

	/**
	 * @return the campoPersonalizado
	 */
	public String getCampoPersonalizado() {
		return campoPersonalizado;
	}

	/**
	 * @param campoPersonalizado the campoPersonalizado to set
	 */
	public void setCampoPersonalizado(String campoPersonalizado) {
		this.campoPersonalizado = campoPersonalizado;
	}

	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the tooltip
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * @param tooltip the tooltip to set
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	public CatAtributosComponentesDTO getCatAtributosComponentesDTO() {
		return catAtributosComponentesDTO;
	}

	public void setCatAtributosComponentesDTO(CatAtributosComponentesDTO catAtributosComponentesDTO) {
		this.catAtributosComponentesDTO = catAtributosComponentesDTO;
	}

	public boolean isDatoObligatorio() {
		return datoObligatorio;
	}

	public void setDatoObligatorio(boolean datoObligatorio) {
		this.datoObligatorio = datoObligatorio;
	}

	@Override
	public String toString() {
		return "DetElementosTokenDTO [idElementoToken=" + idElementoToken + ", archivosRespuestaTokenDTO="
				+ archivosRespuestaTokenDTO + ", catOrigenTokenDTO=" + catOrigenTokenDTO + ", idComponente="
				+ idComponente + ", nombreToken=" + nombreToken + ", estructuraFolio=" + estructuraFolio
				+ ", idFormatoFecha=" + idFormatoFecha + ", longitudFolio=" + longitudFolio + ", campoPersonalizado="
				+ campoPersonalizado + ", orden=" + orden + ", activo=" + activo + ", tooltip=" + tooltip + "]";
	}

}
