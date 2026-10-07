package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatosLineaCapturaRespuestaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("Importe")
    private Integer importe;
    
    @JsonProperty("FechaVigencia")
    private String fechaVigencia;
    
    @JsonProperty("TipoPago")
    private Integer tipoPago;
    
    @JsonProperty("LineaCaptura")
    private String lineaCaptura;

    public Integer getImporte() {
        return importe;
    }

    public void setImporte(Integer importe) {
        this.importe = importe;
    }

    public String getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(String fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public Integer getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(Integer tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getLineaCaptura() {
        return lineaCaptura;
    }

    public void setLineaCaptura(String lineaCaptura) {
        this.lineaCaptura = lineaCaptura;
    }
}