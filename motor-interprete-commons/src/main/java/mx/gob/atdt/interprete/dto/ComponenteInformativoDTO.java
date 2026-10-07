package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;


public class ComponenteInformativoDTO extends ComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2690963339376845931L;
	
	private Long idComponenteInformativo;
	private String textoInformativo;

	/**
	 * 
	 */
	public ComponenteInformativoDTO() {
		super();

	}
	
	/**
	 * Constructor utilizado por la NamedQuery ComponenteCampoTexto.findComponentesCampoTextoByIdComponente
	 * 
	 * @param idComponenteInformativo;
	 * @param idComponente
	 * @param textoInformativo;
	 */
	public ComponenteInformativoDTO(long idComponenteInformativo, Long idComponente, Integer idTipoComponente,
			Long idSubseccionFormulario, int orden, boolean requerido, boolean tooltip, String descripcionTooltip, 
			String tituloCampo, boolean activo, Date fechaCreacion, Date fechaUltimaActualizacion, boolean seccionSincronizada, String textoInformativo) {
		
		super(idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip,
				descripcionTooltip, tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);
		
		this.idComponenteInformativo = idComponenteInformativo;
		this.textoInformativo = textoInformativo;
	}

	public Long getIdComponenteInformativo() {
		return idComponenteInformativo;
	}

	public void setIdComponenteInformativo(Long idComponenteInformativo) {
		this.idComponenteInformativo = idComponenteInformativo;
	}

	public String getTextoInformativo() {
		return textoInformativo;
	}

	public void setTextoInformativo(String textoInformativo) {
		this.textoInformativo = textoInformativo;
	}


	
}
