package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorValidacionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("Tramite/NumeroTramite")
    private Integer numeroTramite;
    
    @JsonProperty("Tramite/Homoclave")
    private String homoclave;
    
    @JsonProperty("Tramite/Variante")
    private String variante;
    
    @JsonProperty("Código del Error")
    private Integer codigoError;
    
    @JsonProperty("Campo")
    private String campo;
    
    @JsonProperty("Descripción del Error")
    private String descripcionError;

    
    public Integer getNumeroTramite() {
        return numeroTramite;
    }

    public void setNumeroTramite(Integer numeroTramite) {
        this.numeroTramite = numeroTramite;
    }

    public String getHomoclave() {
        return homoclave;
    }

    public void setHomoclave(String homoclave) {
        this.homoclave = homoclave;
    }

    public String getVariante() {
        return variante;
    }

    public void setVariante(String variante) {
        this.variante = variante;
    }

    public Integer getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(Integer codigoError) {
        this.codigoError = codigoError;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getDescripcionError() {
        return descripcionError;
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }
}