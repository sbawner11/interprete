package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class ComponenteCheckboxUnicoDTO extends ComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 594087065265286069L;
	private Long idComponenteCheckboxUnico;
	private String texto;

	/**
	 * 
	 */
	public ComponenteCheckboxUnicoDTO() {
		super();
		super.setCatTipoComponenteDTO(new CatTipoComponenteDTO());
	}
	
	public ComponenteCheckboxUnicoDTO(Long idComponenteCheckboxUnico, String texto, Long idComponente, Integer idTipoComponente,
			Long idSubseccionFormulario, int orden, boolean requerido, boolean tooltip, String descripcionTooltip,
			String tituloCampo, boolean activo, Date fechaCreacion, Date fechaUltimaActualizacion,
			boolean seccionSincronizada) {
		
		super(idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip, descripcionTooltip,
				tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);
		
		this.idComponenteCheckboxUnico = idComponenteCheckboxUnico;
		this.texto = texto;
	}

	/**
	 * @param idComponenteCheckbox
	 * @param habilitaTodosNinguno
	 */
	public ComponenteCheckboxUnicoDTO(Long idComponenteCheckboxUnico, String texto) {
		this.idComponenteCheckboxUnico = idComponenteCheckboxUnico;
		this.texto = texto;
	}

	/**
	 * @return the idComponenteCheckboxUnico
	 */
	public Long getIdComponenteCheckboxUnico() {
		return idComponenteCheckboxUnico;
	}

	/**
	 * @param idComponenteCheckboxUnico the idComponenteCheckboxUnico to set
	 */
	public void setIdComponenteCheckboxUnico(Long idComponenteCheckboxUnico) {
		this.idComponenteCheckboxUnico = idComponenteCheckboxUnico;
	}

	/**
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param texto the texto to set
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

}