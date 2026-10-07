package mx.gob.atdt.interprete.dto.webhook;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
/**
 * Clases wrapper para notificacion webhook
 * @author Ramiro Luna Torres
 * RequestWebhookDTO
 */
public class RequestWebhookDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5162597079889499976L;
	
	private long idProyecto;
	private long idTramite;
	private String folioSeguimiento;
	private long idUsuarioLlaveMX;
	private String curp;
	private String rfc;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") 
	private LocalDateTime fechaCreacion;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") 
	private LocalDateTime fechaRevision;
	private int idEstatusTramite;
	private int idTipoNotificacion;
	private String documentoGenerado;
	
	public RequestWebhookDTO() {
		
	}
	
	
	public RequestWebhookDTO(long idProyecto, long idTramite, String folioSeguimiento, LocalDateTime fechaCreacion, int idEstatusTramite) {
		super();
		this.idProyecto = idProyecto;
		this.idTramite = idTramite;
		this.folioSeguimiento = folioSeguimiento;
		this.fechaCreacion = fechaCreacion;
		this.idEstatusTramite = idEstatusTramite;
	}
	
	public long getIdProyecto() {
		return idProyecto;
	}
	public void setIdProyecto(long idProyecto) {
		this.idProyecto = idProyecto;
	}
	public long getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(long idTramite) {
		this.idTramite = idTramite;
	}
	public String getFolioSeguimiento() {
		return folioSeguimiento;
	}
	public void setFolioSeguimiento(String folioSeguimiento) {
		this.folioSeguimiento = folioSeguimiento;
	}
	public long getIdUsuarioLlaveMX() {
		return idUsuarioLlaveMX;
	}
	public void setIdUsuarioLlaveMX(long idUsuarioLlaveMX) {
		this.idUsuarioLlaveMX = idUsuarioLlaveMX;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public LocalDateTime getFechaRevision() {
		return fechaRevision;
	}
	public void setFechaRevision(LocalDateTime fechaRevision) {
		this.fechaRevision = fechaRevision;
	}
	public int getIdEstatusTramite() {
		return idEstatusTramite;
	}
	public void setIdEstatusTramite(int idEstatusTramite) {
		this.idEstatusTramite = idEstatusTramite;
	}
	public int getIdTipoNotificacion() {
		return idTipoNotificacion;
	}
	public void setIdTipoNotificacion(int idTipoNotificacion) {
		this.idTipoNotificacion = idTipoNotificacion;
	}
	public String getDocumentoGenerado() {
		return documentoGenerado;
	}
	public void setDocumentoGenerado(String documentoGenerado) {
		this.documentoGenerado = documentoGenerado;
	}


	@Override
	public String toString() {
		return "RequestWebhookDTO [idProyecto=" + idProyecto + ", idTramite=" + idTramite + ", folioSeguimiento="
				+ folioSeguimiento + ", idUsuarioLlaveMX=" + idUsuarioLlaveMX + ", curp=" + curp + ", rfc=" + rfc
				+ ", fechaCreacion=" + fechaCreacion + ", fechaRevision=" + fechaRevision + ", idEstatusTramite="
				+ idEstatusTramite + ", idTipoNotificacion=" + idTipoNotificacion + ", documentoGenerado="
				+ documentoGenerado + "]";
	}	
}
