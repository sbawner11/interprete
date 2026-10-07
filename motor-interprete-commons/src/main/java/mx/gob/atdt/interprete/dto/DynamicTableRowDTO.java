package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * DTO que representa una fila de datos en la tabla dinámica
 * 
 */
public class DynamicTableRowDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String uuid;
    private int numeroFila;
    private String tipoFila; // "BASE" o "DINAMICA"
    private Map<String, Object> datos;
    private boolean eliminado;

    public DynamicTableRowDTO() {
        this.uuid = java.util.UUID.randomUUID().toString();
        this.datos = new HashMap<>();
        this.eliminado = false;
    }

    /**
     * Constructor
     * 
     * @param numeroFila Número de fila
     * @param tipoFila Tipo de fila (BASE/DINAMICA)
     */
    public DynamicTableRowDTO(int numeroFila, String tipoFila) {
        this();
        this.numeroFila = numeroFila;
        this.tipoFila = tipoFila;
    }

    /**
     * 
     * @param id ID de la fila
     * @param uuid Identificador único
     * @param numeroFila Número de fila
     * @param tipoFila Tipo de fila (BASE/DINAMICA)
     * @param datos Datos de la fila
     * @param eliminado Indica si la fila está eliminada
     */
    public DynamicTableRowDTO(Long id, String uuid, int numeroFila, String tipoFila, 
                           Map<String, Object> datos, boolean eliminado) {
        this.id = id;
        this.uuid = uuid;
        this.numeroFila = numeroFila;
        this.tipoFila = tipoFila;
        this.datos = datos != null ? datos : new HashMap<>();
        this.eliminado = eliminado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getNumeroFila() {
        return numeroFila;
    }

    public void setNumeroFila(int numeroFila) {
        this.numeroFila = numeroFila;
    }

    public String getTipoFila() {
        return tipoFila;
    }

    public void setTipoFila(String tipoFila) {
        this.tipoFila = tipoFila;
    }

    public Map<String, Object> getDatos() {
        return datos;
    }

    public void setDatos(Map<String, Object> datos) {
        this.datos = datos != null ? datos : new HashMap<>();
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    /**
     * Obtiene el valor de una columna específica
     * 
     * @param columnaNombre Nombre de la columna
     * @return Valor de la columna o null si no existe
     */
    public Object getValor(String columnaNombre) {
        return datos != null ? datos.get(columnaNombre) : null;
    }

    /**
     * Establece el valor de una columna específica
     * 
     * @param columnaNombre Nombre de la columna
     * @param valor Valor a establecer
     */
    public void setValor(String columnaNombre, Object valor) {
        if (datos == null) {
            datos = new HashMap<>();
        }
        datos.put(columnaNombre, valor);
    }

    /**
     * Verifica si la fila es de tipo base
     * 
     * @return true si tipoFila es "BASE"
     */
    public boolean isBase() {
        return "BASE".equalsIgnoreCase(tipoFila);
    }

    /**
     * Verifica si la fila es de tipo dinámica (agregada por ciudadano)
     * 
     * @return true si tipoFila es "DINAMICA"
     */
    public boolean isDinamica() {
        return "DINAMICA".equalsIgnoreCase(tipoFila);
    }

    /**
     * Verifica si la fila tiene al menos un campo no vacío
     * 
     * @return true si tiene al menos un campo con valor
     */
    public boolean tieneDatos() {
        if (datos == null || datos.isEmpty()) {
            return false;
        }
        return datos.values().stream()
                .anyMatch(v -> v != null && !v.toString().trim().isEmpty());
    }

    @Override
    public String toString() {
        return "DynamicTableRow{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", numeroFila=" + numeroFila +
                ", tipoFila='" + tipoFila + '\'' +
                ", datos=" + datos +
                ", eliminado=" + eliminado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicTableRowDTO that = (DynamicTableRowDTO) o;
        return uuid != null && uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}