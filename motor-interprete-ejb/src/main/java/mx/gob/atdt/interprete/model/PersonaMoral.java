package mx.gob.atdt.interprete.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import mx.gob.atdt.interprete.dto.PersonaMoralDTO;

import java.time.LocalDate;

@Entity
@Table(name = "persona_moral", schema = "motor_interprete",
       indexes = {
           @Index(name = "personamoral_idusuariollavecdmx_idx", columnList = "id_usuario_llave_cdmx"),
           @Index(name = "personamoral_rfc_idx", columnList = "rfc")
       })
@NamedQueries({
	@NamedQuery(name = "PersonaMoral.findById", 
			query = "SELECT new mx.gob.atdt.interprete.dto.PersonaMoralDTO(p.idPersonaMoral, p.rfc, p.razonSocial, p.vigenciaCertificado, p.certificadoVigente) "
					+ "	FROM PersonaMoral p "
					+ "	WHERE p.usuario.idUsuarioLlaveCdmx = :idUsuario"),	
	
	@NamedQuery(name = "PersonaMoral.findByUsuarioIdAndRfc",
	        query = "SELECT p FROM PersonaMoral p "
	              + "WHERE p.usuario.idUsuarioLlaveCdmx = :idUsuario "
	              + "AND p.rfc = :rfc"),
	
	@NamedQuery(name = "PersonaMoral.findByUsuarioIdAndPersonaMoralId",
    		query = "SELECT new mx.gob.atdt.interprete.dto.PersonaMoralDTO(p.idPersonaMoral, p.rfc, p.razonSocial, p.vigenciaCertificado, p.certificadoVigente) "
    				+ "FROM PersonaMoral p "
    				+ "WHERE p.usuario.idUsuarioLlaveCdmx = :idUsuario "
    				+ "AND p.idPersonaMoral = :idPersonaMoral"),	
	
	@NamedQuery(
	        name = "PersonaMoral.existeRegistro",
	        query = "SELECT COUNT(p) FROM PersonaMoral p WHERE p.usuario.idUsuarioLlaveCdmx = :idUsuario"),
	@NamedQuery(name = "PersonaMoral.findByPersonaMoralId",
			query = "SELECT new mx.gob.atdt.interprete.dto.PersonaMoralDTO(p.idPersonaMoral, u.idUsuarioLlaveCdmx,"
					+ " p.rfc, p.razonSocial, p.vigenciaCertificado, p.certificadoVigente) "
					+ " FROM PersonaMoral p"
					+ " JOIN p.usuario u"
					+ " WHERE p.idPersonaMoral = :idPersonaMoral"),
	
})
public class PersonaMoral implements java.io.Serializable {

   
	private static final long serialVersionUID = 1L;
	private Long idPersonaMoral;
    private Usuario usuario;
    private String rfc;
    private String razonSocial;
    private LocalDate vigenciaCertificado;
    private boolean certificadoVigente = true;

   
    public PersonaMoral() {
    }
    
    public PersonaMoral(PersonaMoralDTO dto, EntityManager em) {
    	
        this.rfc = dto.getRfc();
        this.razonSocial = dto.getRazonSocial();
        this.vigenciaCertificado = dto.getVigenciaCertificado();
        this.certificadoVigente = dto.isCertificadoVigente();
        this.usuario = em.find(Usuario.class, dto.getIdUsuarioLlaveCdmx());
        if (this.usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + dto.getIdUsuarioLlaveCdmx());
        }
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona_moral")
    public Long getIdPersonaMoral() {
        return idPersonaMoral;
    }

    public void setIdPersonaMoral(Long idPersonaMoral) {
        this.idPersonaMoral = idPersonaMoral;
    }

    
    @ManyToOne
    @JoinColumn(name = "id_usuario_llave_cdmx", referencedColumnName = "id_usuario_llave_cdmx", nullable = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(nullable = false, length = 13)
    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    @Column(name = "razon_social", nullable = false, length = 300)
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    @Column(name = "vigencia_certificado", nullable = false)
    public LocalDate getVigenciaCertificado() {
        return vigenciaCertificado;
    }

    public void setVigenciaCertificado(LocalDate vigenciaCertificado) {
        this.vigenciaCertificado = vigenciaCertificado;
    }

    @Column(name = "certificado_vigente", nullable = false)
    public boolean isCertificadoVigente() {
        return certificadoVigente;
    }

    public void setCertificadoVigente(boolean certificadoVigente) {
        this.certificadoVigente = certificadoVigente;
    }
}