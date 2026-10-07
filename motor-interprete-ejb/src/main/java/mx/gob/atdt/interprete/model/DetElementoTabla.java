package mx.gob.atdt.interprete.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "det_elementos_tabla", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "DetElementoTabla.findByIdComponenteTabla", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetElementoTablaDTO("
			+ " det.id,det.tituloHeader, ct.id, det.requerido, det.tooltip, det.longitudCelda,"
			+ " tc.id, tc.descripcion, det.ordenColumna, det.activo) "
			+ " FROM DetElementoTabla det "
			+ "  JOIN det.componenteTabla ct "
			+ "  JOIN det.tipoCampo tc "
			+ " WHERE det.componenteTabla.id = :idComponenteTabla")
})

public class DetElementoTabla implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_elemento_tabla", unique = true, nullable = false)
    private Long id;

    @Column(name = "titulo_header")
    private String tituloHeader;

    @Column(name = "requerido", nullable = false)
    private Boolean requerido;

    @Column(name = "tooltip")
    private String tooltip;

    @Column(name = "longitu_celda")
    private Integer longitudCelda;

    // FK → componente_tabla
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_componente_tabla", nullable = false)
    private ComponenteTabla componenteTabla;

    // FK → catálogo tipo campo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cat_tipo_campo", nullable = false)
    private CatTipoCampo tipoCampo;
    
    @Column(name = "orden_columna")
    private Integer ordenColumna;
    
    @Column(name = "activo", nullable = false)
    private boolean activo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTituloHeader() {
		return tituloHeader;
	}

	public void setTituloHeader(String tituloHeader) {
		this.tituloHeader = tituloHeader;
	}

	public Boolean getRequerido() {
		return requerido;
	}

	public void setRequerido(Boolean requerido) {
		this.requerido = requerido;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public Integer getLongitudCelda() {
		return longitudCelda;
	}

	public void setLongitudCelda(Integer longitudCelda) {
		this.longitudCelda = longitudCelda;
	}

	public ComponenteTabla getComponenteTabla() {
		return componenteTabla;
	}

	public void setComponenteTabla(ComponenteTabla componenteTabla) {
		this.componenteTabla = componenteTabla;
	}

	public CatTipoCampo getTipoCampo() {
		return tipoCampo;
	}

	public void setTipoCampo(CatTipoCampo tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	public Integer getOrdenColumna() {
		return ordenColumna;
	}

	public void setOrdenColumna(Integer ordenColumna) {
		this.ordenColumna = ordenColumna;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
