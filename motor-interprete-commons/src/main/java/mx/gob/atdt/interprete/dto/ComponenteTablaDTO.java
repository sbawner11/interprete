package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ComponenteTablaDTO extends ComponenteDTO implements Serializable {

    private static final long serialVersionUID = 1L;
	private Long idComponenteTabla;
    private boolean permiteAgregarFilas;
    private int tamanioPaginaTabla;
    private int minFilas;
    private int maxFilas;
    private int numeroColumnas;
    
    private Set<DetElementoTablaDTO> columnas = new HashSet<>();
    
	public ComponenteTablaDTO() {
		super();
	}

	public ComponenteTablaDTO(Long idComponenteTabla) {
		super();
		this.idComponenteTabla = idComponenteTabla;
	}

	public boolean isRequerido() {
		return requerido;
	}

	public void setRequerido(boolean requerido) {
		this.requerido = requerido;
	}

	public boolean isPermiteAgregarFilas() {
		return permiteAgregarFilas;
	}

	public void setPermiteAgregarFilas(boolean permiteAgregarFilas) {
		this.permiteAgregarFilas = permiteAgregarFilas;
	}

	public int getTamanioPaginaTabla() {
		return tamanioPaginaTabla;
	}

	public void setTamanioPaginaTabla(int tamanioPaginaTabla) {
		this.tamanioPaginaTabla = tamanioPaginaTabla;
	}

	public int getMinFilas() {
		return minFilas;
	}

	public void setMinFilas(int minFilas) {
		this.minFilas = minFilas;
	}

	public int getMaxFilas() {
		return maxFilas;
	}

	public void setMaxFilas(int maxFilas) {
		this.maxFilas = maxFilas;
	}

	public int getNumeroColumnas() {
		return numeroColumnas;
	}

	public void setNumeroColumnas(int numeroColumnas) {
		this.numeroColumnas = numeroColumnas;
	}

	public Set<DetElementoTablaDTO> getColumnas() {
		return columnas;
	}

	public void setColumnas(Set<DetElementoTablaDTO> columnas) {
		this.columnas = columnas;
	}

	public Long getIdComponenteTabla() {
		return idComponenteTabla;
	}

	public void setIdComponenteTabla(Long idComponenteTabla) {
		this.idComponenteTabla = idComponenteTabla;
	}

	@Override
	public String toString() {
		return "ComponenteTablaDTO [idComponenteTabla=" + idComponenteTabla + 
				", idComponente=" + idComponente + 
				", permiteAgregarFilas=" + permiteAgregarFilas + 
				", tamanioPaginaTabla=" + tamanioPaginaTabla + 
				", minFilas=" + minFilas + 
				", maxFilas=" + maxFilas + 
				", numeroColumnas=" + numeroColumnas + 
				", columnas=" + columnas + "]";
	}


}