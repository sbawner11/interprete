package mx.gob.atdt.interprete.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import mx.gob.atdt.interprete.dto.CatAtributosComponentesDTO;

@Entity
@Table(name = "det_elementos_token", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "DetElementosToken.findByIdArchivoRespuesta", 
		query = "SELECT new mx.gob.atdt.interprete.dto.DetElementosTokenDTO("
		+ " det.idElementoToken, art.idArchivoRespuesta, det.nombreToken, "
		+ " det.catOrigenToken.idOrigenToken, det.estructuraFolio, det.idFormatoFecha, "
		+ " det.longitudFolio, det.campoPersonalizado, det.componente.idComponente, "
		+ " det.orden, det.activo, cac.idAtributoComponente) "
		+ " FROM DetElementosToken det "
		+ " JOIN det.archivosRespuestaToken art "
		+ " LEFT JOIN det.catAtributosComponentes cac"
		+ " WHERE art.idArchivoRespuesta =:idArchivoRespuesta"
		+ " AND det.activo = true "
		+ " ORDER BY det.orden"),
	@NamedQuery(name = "DetElementosToken.findByIdComponente", 
	    query = "SELECT new mx.gob.atdt.interprete.dto.DetElementosTokenDTO("
	    + " det.idElementoToken, det.nombreToken, det.catOrigenToken.idOrigenToken, "
	    + " det.estructuraFolio, det.idFormatoFecha, det.longitudFolio, "
	    + " det.campoPersonalizado, det.componente.idComponente, det.orden, det.activo) "
	    + " FROM DetElementosToken det "
	    + " WHERE det.componente.idComponente =:idComponente") 
})
public class DetElementosToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2140176910875561284L;
	
	private Long idElementoToken;
	private ArchivosRespuestaToken archivosRespuestaToken;
	private CatOrigenToken catOrigenToken;
	private Componente componente;	
	private String nombreToken;
	private String estructuraFolio;
	private int idFormatoFecha;
	private String longitudFolio;
	private String campoPersonalizado;
	private int orden;
	private boolean activo;
	
	private CatAtributosComponentes catAtributosComponentes;

	public DetElementosToken() {
	}

	public DetElementosToken(long idElementoToken, ArchivosRespuestaToken archivosRespuestaToken,
			CatOrigenToken catOrigenToken, String nombreToken, int orden, boolean activo) {
		this.idElementoToken = idElementoToken;
		this.archivosRespuestaToken = archivosRespuestaToken;
		this.catOrigenToken = catOrigenToken;
		this.nombreToken = nombreToken;
		this.orden = orden;
		this.activo = activo;
	}

	public DetElementosToken(long idElementoToken, ArchivosRespuestaToken archivosRespuestaToken,
			CatOrigenToken catOrigenToken, Componente componente, String nombreToken, String estructuraFolio,
			Integer idFormatoFecha, String longitudFolio, String campoPersonalizado, int orden, boolean activo, 
			CatAtributosComponentes catAtributosComponentes) {
		this.idElementoToken = idElementoToken;
		this.archivosRespuestaToken = archivosRespuestaToken;
		this.catOrigenToken = catOrigenToken;
		this.componente = componente;
		this.nombreToken = nombreToken;
		this.estructuraFolio = estructuraFolio;
		this.idFormatoFecha = idFormatoFecha;
		this.longitudFolio = longitudFolio;
		this.campoPersonalizado = campoPersonalizado;
		this.orden = orden;
		this.activo = activo;
		this.catAtributosComponentes = catAtributosComponentes;
	}

	@Id
	@Column(name = "id_elemento_token", unique = true, nullable = false)
	public Long getIdElementoToken() {
		return idElementoToken;
	}

	public void setIdElementoToken(Long idElementoToken) {
		this.idElementoToken = idElementoToken;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_archivo_respuesta", nullable = false)
	public ArchivosRespuestaToken getArchivosRespuestaToken() {
		return archivosRespuestaToken;
	}

	public void setArchivosRespuestaToken(ArchivosRespuestaToken archivosRespuestaToken) {
		this.archivosRespuestaToken = archivosRespuestaToken;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_origen_token", nullable = false)
	public CatOrigenToken getCatOrigenToken() {
		return this.catOrigenToken;
	}

	public void setCatOrigenToken(CatOrigenToken catOrigenToken) {
		this.catOrigenToken = catOrigenToken;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente")
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@Column(name = "nombre_token", nullable = false, length = 200)
	public String getNombreToken() {
		return nombreToken;
	}

	public void setNombreToken(String nombreToken) {
		this.nombreToken = nombreToken;
	}
	
	@Column(name = "estructura_folio", nullable = false, length = 100)
	public String getEstructuraFolio() {
		return estructuraFolio;
	}

	public void setEstructuraFolio(String estructuraFolio) {
		this.estructuraFolio = estructuraFolio;
	}

	@Column(name = "id_formato_fecha", nullable = false)
	public int getIdFormatoFecha() {
		return idFormatoFecha;
	}

	public void setIdFormatoFecha(int idFormatoFecha) {
		this.idFormatoFecha = idFormatoFecha;
	}

	@Column(name = "longitud_folio", nullable = false, length = 100)
	public String getLongitudFolio() {
		return longitudFolio;
	}

	public void setLongitudFolio(String longitudFolio) {
		this.longitudFolio = longitudFolio;
	}

	@Column(name = "campo_personalizado", nullable = false, length = 30)
	public String getCampoPersonalizado() {
		return campoPersonalizado;
	}

	public void setCampoPersonalizado(String campoPersonalizado) {
		this.campoPersonalizado = campoPersonalizado;
	}	

	@Column(name = "orden", nullable = false)
	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_atributo_componente")
	public CatAtributosComponentes getCatAtributosComponentes() {
		return this.catAtributosComponentes;
	}
	
	public void setCatAtributosComponentes(CatAtributosComponentes catAtributosComponentes) {
		this.catAtributosComponentes = catAtributosComponentes;
	}

}
