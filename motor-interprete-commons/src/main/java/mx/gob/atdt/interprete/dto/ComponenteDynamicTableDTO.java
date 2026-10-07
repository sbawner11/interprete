package mx.gob.atdt.interprete.dto;

import java.util.Date;
import java.util.List;

public class ComponenteDynamicTableDTO extends ComponenteDTO {

    private static final long serialVersionUID = 1L;

    private String tituloTabla;
    private boolean permiteAgregarFilas;
    private int minimoFilas;
    private int maximoFilas;
    private int tamanioPagina;
    private List<DynamicColumnConfigDTO> columnas;

   
    public ComponenteDynamicTableDTO() {
        super();
    }

    public ComponenteDynamicTableDTO(Long idComponente, Integer idTipoComponente,
                                      Long idSubSeccionFormulario, Integer orden,
                                      boolean requerido, boolean tooltip, String descripcionTooltip,
                                      String tituloCampo, boolean activo, Date fechaCreacion,
                                      Date fechaUltimaActualizacion, boolean seccionSincronizada) {
        super(idComponente, idTipoComponente, idSubSeccionFormulario, orden, requerido,
              tooltip, descripcionTooltip, tituloCampo, activo, fechaCreacion,
              fechaUltimaActualizacion, seccionSincronizada);
    }

    public String getTituloTabla() { return tituloTabla; }
    public void setTituloTabla(String tituloTabla) { this.tituloTabla = tituloTabla; }

    public boolean isPermiteAgregarFilas() { return permiteAgregarFilas; }
    public void setPermiteAgregarFilas(boolean permiteAgregarFilas) { this.permiteAgregarFilas = permiteAgregarFilas; }

    public int getMinimoFilas() { return minimoFilas; }
    public void setMinimoFilas(int minimoFilas) { this.minimoFilas = minimoFilas; }

    public int getMaximoFilas() { return maximoFilas; }
    public void setMaximoFilas(int maximoFilas) { this.maximoFilas = maximoFilas; }

    public int getTamanioPagina() { return tamanioPagina; }
    public void setTamanioPagina(int tamanioPagina) { this.tamanioPagina = tamanioPagina; }

    public List<DynamicColumnConfigDTO> getColumnas() { return columnas; }
    public void setColumnas(List<DynamicColumnConfigDTO> columnas) { this.columnas = columnas; }
}