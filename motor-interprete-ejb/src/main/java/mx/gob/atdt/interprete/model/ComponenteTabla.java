package mx.gob.atdt.interprete.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "componente_tabla", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(
		    name = "ComponenteTabla.findComponenteTablaByIdComponente",
		    query = "SELECT ct " +
		    		"FROM ComponenteTabla ct " +
		    		"JOIN FETCH ct.columnas " +
		    		"JOIN ct.componente c " +
		    		"WHERE c.idComponente = :idComponente " 
		),
	@NamedQuery(name = "ComponenteTabla.findByIdComponenteTabla", 
	query = "SELECT ct "
	+ " FROM ComponenteTabla ct  "
	+ " WHERE ct.id = :idComponenteTabla")

})
public class ComponenteTabla implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id_componente_tabla", unique = true, nullable = false)
    private Long id;

    @Column(name = "permite_agregar_filas", nullable = false)
    private Boolean permiteAgregarFilas;

    @Column(name = "tamanio_pagina", nullable = false)
    private Integer tamanioPagina;

    @Column(name = "minimo_filas", nullable = false)
    private Integer minimoFilas;

    @Column(name = "maximo_filas", nullable = false)
    private Integer maximoFilas;

    // Relación con columnas
    @OneToMany(mappedBy = "componenteTabla")
    private List<DetElementoTabla> columnas;
    
    // Relación con componente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_componente", nullable = false)
    private Componente componente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public Boolean getPermiteAgregarFilas() {
		return permiteAgregarFilas;
	}

	public void setPermiteAgregarFilas(Boolean permiteAgregarFilas) {
		this.permiteAgregarFilas = permiteAgregarFilas;
	}

	public Integer getTamanioPagina() {
		return tamanioPagina;
	}

	public void setTamanioPagina(Integer tamanioPagina) {
		this.tamanioPagina = tamanioPagina;
	}

	public Integer getMinimoFilas() {
		return minimoFilas;
	}

	public void setMinimoFilas(Integer minimoFilas) {
		this.minimoFilas = minimoFilas;
	}

	public Integer getMaximoFilas() {
		return maximoFilas;
	}

	public void setMaximoFilas(Integer maximoFilas) {
		this.maximoFilas = maximoFilas;
	}

	public List<DetElementoTabla> getColumnas() {
		return columnas;
	}

	public void setColumnas(List<DetElementoTabla> columnas) {
		this.columnas = columnas;
	}


}
