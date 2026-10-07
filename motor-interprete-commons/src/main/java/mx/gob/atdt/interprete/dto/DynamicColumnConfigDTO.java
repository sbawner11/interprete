package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

/**
 * DTO que representa la configuración de una columna en la tabla dinámica
 * 
 */
public class DynamicColumnConfigDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String nombre;
    private String tipo; // NUMERICO, ALFANUMERICO, o FECHA
    private int longitudMaxima;
    private boolean requerido;
    private String tooltip;
    private int orden;
    
    
    public DynamicColumnConfigDTO() {
        this.longitudMaxima = 200;
        this.requerido = false;
        this.orden = 0;
    }
    
    public DynamicColumnConfigDTO(String nombre, String tipo, int longitudMaxima, boolean requerido) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.longitudMaxima = longitudMaxima;
        this.requerido = requerido;
    }
    
   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public int getLongitudMaxima() { return longitudMaxima; }
    public void setLongitudMaxima(int longitudMaxima) { this.longitudMaxima = longitudMaxima; }
    
    public boolean isRequerido() { return requerido; }
    public void setRequerido(boolean requerido) { this.requerido = requerido; }
    
    public String getTooltip() { return tooltip; }
    public void setTooltip(String tooltip) { this.tooltip = tooltip; }
    
    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }
    
    public boolean isNumerico() { return "NUMERICO".equalsIgnoreCase(tipo); }
    public boolean isAlfanumerico() { return "ALFANUMERICO".equalsIgnoreCase(tipo); }
    public boolean isFecha() { return "FECHA".equalsIgnoreCase(tipo); }

}