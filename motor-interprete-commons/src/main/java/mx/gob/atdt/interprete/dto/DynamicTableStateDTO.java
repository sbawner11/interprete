package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DynamicTableStateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idComponente;
    private Long idTramite;
    private List<DynamicTableRowDTO> filas;
    private int nextRowIndex = 1;
    private String titulo;
    private int minRows = 1;
    private int maxRows = 200;
    private boolean allowAddingRows = true;
    private int pageSize = 20;
    private boolean tablaRequerida = false;
    private List<DynamicColumnConfigDTO> columnas;

    public DynamicTableStateDTO(Long idComponente) {
        this.idComponente = idComponente;
        this.filas = new ArrayList<>();
        this.columnas = new ArrayList<>();
    }

    public Long getIdComponente() { return idComponente; }
    public void setIdComponente(Long idComponente) { this.idComponente = idComponente; }
    public Long getIdTramite() { return idTramite; }
    public void setIdTramite(Long idTramite) { this.idTramite = idTramite; }
    public List<DynamicTableRowDTO> getFilas() { return filas; }
    public void setFilas(List<DynamicTableRowDTO> filas) { this.filas = filas; }
    public int getNextRowIndex() { return nextRowIndex; }
    public void setNextRowIndex(int nextRowIndex) { this.nextRowIndex = nextRowIndex; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getMinRows() { return minRows; }
    public void setMinRows(int minRows) { this.minRows = minRows; }
    public int getMaxRows() { return maxRows; }
    public void setMaxRows(int maxRows) { this.maxRows = maxRows; }
    public boolean isAllowAddingRows() { return allowAddingRows; }
    public void setAllowAddingRows(boolean allowAddingRows) { this.allowAddingRows = allowAddingRows; }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public boolean isTablaRequerida() { return tablaRequerida; }
    public void setTablaRequerida(boolean tablaRequerida) { this.tablaRequerida = tablaRequerida; }
    public List<DynamicColumnConfigDTO> getColumnas() { return columnas; }
    public void setColumnas(List<DynamicColumnConfigDTO> columnas) { this.columnas = columnas; }
}