package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class RespuestaGeneracionLCDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("DatosGenerales")
    private RespuestaDatosGeneralesDTO respuestaDatosGenerales;
    
    @JsonProperty("Acuse")
    private AcuseDTO acuseDTO;
    
    @JsonProperty("Errores")
    private List<ErrorDTO> errores;

    public RespuestaGeneracionLCDTO() {
    	
    }

	public RespuestaDatosGeneralesDTO getRespuestaDatosGenerales() {
		return respuestaDatosGenerales;
	}

	public void setRespuestaDatosGenerales(RespuestaDatosGeneralesDTO respuestaDatosGenerales) {
		this.respuestaDatosGenerales = respuestaDatosGenerales;
	}

	public AcuseDTO getAcuseDTO() {
		return acuseDTO;
	}

	public void setAcuseDTO(AcuseDTO acuseDTO) {
		this.acuseDTO = acuseDTO;
	}

	public List<ErrorDTO> getErrores() {
		return errores;
	}

	public void setErrores(List<ErrorDTO> errores) {
		this.errores = errores;
	}

    
}