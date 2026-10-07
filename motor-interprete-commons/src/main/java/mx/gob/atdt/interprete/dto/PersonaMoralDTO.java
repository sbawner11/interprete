package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class PersonaMoralDTO implements Serializable {

	private static final long serialVersionUID = -4904016547634388250L;
	private Long idPersonaMoral;
    private Long idUsuarioLlaveCdmx;
    private String rfc;
    private String razonSocial;
    private LocalDate vigenciaCertificado;
    private boolean certificadoVigente;
    public PersonaMoralDTO() {
    }
    
    public PersonaMoralDTO(Long idPersonaMoral, String rfc, String razonSocial, LocalDate vigenciaCertificado, boolean certificadoVigente) {
    	this.idPersonaMoral = idPersonaMoral;
    	this.rfc = rfc;
        this.razonSocial = razonSocial;
        this.vigenciaCertificado = vigenciaCertificado;
        this.certificadoVigente = certificadoVigente;
    }
    /**
     * Se agrega constructor personalizado para webhook
     * @param idPersonaMoral
     * @param idUsuarioLlaveCdmx
     * @param rfc
     * @param razonSocial
     * @param vigenciaCertificado
     * @param certificadoVigente
     */
    public PersonaMoralDTO(Long idPersonaMoral, Long idUsuarioLlaveCdmx, String rfc, String razonSocial, LocalDate vigenciaCertificado, boolean certificadoVigente) {
    	this.idPersonaMoral = idPersonaMoral;
    	this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
    	this.rfc = rfc;
        this.razonSocial = razonSocial;
        this.vigenciaCertificado = vigenciaCertificado;
        this.certificadoVigente = certificadoVigente;
    }

    public Long getIdPersonaMoral() {
        return idPersonaMoral;
    }

    public void setIdPersonaMoral(Long idPersonaMoral) {
        this.idPersonaMoral = idPersonaMoral;
    }

    public Long getIdUsuarioLlaveCdmx() {
        return idUsuarioLlaveCdmx;
    }

    public void setIdUsuarioLlaveCdmx(Long idUsuarioLlaveCdmx) {
        this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public LocalDate getVigenciaCertificado() {
        return vigenciaCertificado;
    }

    public void setVigenciaCertificado(LocalDate vigenciaCertificado) {
        this.vigenciaCertificado = vigenciaCertificado;
    }

    public boolean isCertificadoVigente() {
        return certificadoVigente;
    }

    public void setCertificadoVigente(boolean certificadoVigente) {
        this.certificadoVigente = certificadoVigente;
    }
}