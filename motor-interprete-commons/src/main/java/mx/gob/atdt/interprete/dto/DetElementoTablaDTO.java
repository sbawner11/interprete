package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Objects;

public class DetElementoTablaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
	private String tituloHeader;
	private Long idComponenteTabla;
    private boolean requerido;
    private String tooltip;
    private int longitudCelda;
    private CatTipoCampoDTO catTipoCampoDTO;
    private int ordenColumna;
    private boolean activo;
    private boolean esColumnaNueva;
    
	public DetElementoTablaDTO() {
		this.catTipoCampoDTO = null;
	}

	@SuppressWarnings("java:S107")
	public DetElementoTablaDTO(Long id, String tituloHeader, Long idComponenteTabla, 
			boolean requerido, String tooltip, int longitudCelda,
			Integer idTipoCampo, String descripcion,
			int ordenColumna, boolean activo) {
		this.id = id;
		this.tituloHeader = tituloHeader;
		this.idComponenteTabla= idComponenteTabla;
		this.requerido = requerido;
		this.tooltip= tooltip;
		this.longitudCelda = longitudCelda;
		this.catTipoCampoDTO = new CatTipoCampoDTO(idTipoCampo, descripcion);
		this.ordenColumna = ordenColumna;
		this.activo = activo;
	}
    
	public String getTituloHeader() {
		return tituloHeader;
	}
	public void setTituloHeader(String tituloHeader) {
		this.tituloHeader = tituloHeader;
	}

	public boolean isRequerido() {
		return requerido;
	}
	public void setRequerido(boolean requerido) {
		this.requerido = requerido;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public int getLongitudCelda() {
		return longitudCelda;
	}
	public void setLongitudCelda(int longitudCelda) {
		this.longitudCelda = longitudCelda;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdComponenteTabla() {
		return idComponenteTabla;
	}
	public void setIdComponenteTabla(Long idComponenteTabla) {
		this.idComponenteTabla = idComponenteTabla;
	}
	public CatTipoCampoDTO getCatTipoCampoDTO() {
		return catTipoCampoDTO;
	}
	public void setCatTipoCampoDTO(CatTipoCampoDTO catTipoCampoDTO) {
		this.catTipoCampoDTO = catTipoCampoDTO;
	}
	
	public int getOrdenColumna() {
		return ordenColumna;
	}
	public void setOrdenColumna(int ordenColumna) {
		this.ordenColumna = ordenColumna;
	}

	public boolean getEsColumnaNueva() {
		return esColumnaNueva;
	}
	public void setEsColumnaNueva(boolean esColumnaNueva) {
		this.esColumnaNueva = esColumnaNueva;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof DetElementoTablaDTO)) return false;
	    DetElementoTablaDTO that = (DetElementoTablaDTO) o;
	    return Objects.equals(id, that.id);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "DetElementoTablaDTO [id=" + id + ", tituloHeader=" + tituloHeader + ", idComponenteTabla="
				+ idComponenteTabla + ", requerido=" + requerido + ", tooltip=" + tooltip + ", longitudCelda="
				+ longitudCelda + ", catTipoCampoDTO=" + catTipoCampoDTO + ", ordenColumna=" + ordenColumna
				+ ", activo=" + activo + ", esColumnaNueva=" + esColumnaNueva + "]";
	}
	
}