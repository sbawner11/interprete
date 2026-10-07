package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Clase wrapper para generar la respuesta en el back
 * @author Ramiro Luna Torres
 * GeneraComprobanteDTO
 */
public class GeneraComprobanteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6950789220884052960L;
	
	private List<SeccionesFormularioDTO> lstSeccionesTramiteDTO;
	private Map<String, ControlComponentesDTO> mapControlComponentesRespuestas;
	private DetSecurityDomainDTO securityDomainDTO;	
	private ArchivosRespuestaTokenDTO archivosRespuestaTokenRegistroDTO;
	private ArchivosRespuestaTokenDTO archivosRespuestaTokenAceptacionDTO;
	private ArchivosRespuestaTokenDTO archivosRespuestaTokenRechazoDTO;
	private ArchivosRespuestaTokenDTO archivosRespuestaTokenConclusionDTO;
	private DetAccesoLLaveDTO accesoLlaveDTO;
	private DetFirmaDigitalDTO firmaDTO;
	private boolean existenFormatosConfigurados;
	
	private boolean habilitaFirmadoTramites;
	
	public List<SeccionesFormularioDTO> getLstSeccionesTramiteDTO() {
		return lstSeccionesTramiteDTO;
	}
	public void setLstSeccionesTramiteDTO(List<SeccionesFormularioDTO> lstSeccionesTramiteDTO) {
		this.lstSeccionesTramiteDTO = lstSeccionesTramiteDTO;
	}
	public Map<String, ControlComponentesDTO> getMapControlComponentesRespuestas() {
		return mapControlComponentesRespuestas;
	}
	public void setMapControlComponentesRespuestas(Map<String, ControlComponentesDTO> mapControlComponentesRespuestas) {
		this.mapControlComponentesRespuestas = mapControlComponentesRespuestas;
	}
	
	public DetSecurityDomainDTO getSecurityDomainDTO() {
		return securityDomainDTO;
	}
	public void setSecurityDomainDTO(DetSecurityDomainDTO securityDomainDTO) {
		this.securityDomainDTO = securityDomainDTO;
	}
	public boolean isHabilitaFirmadoTramites() {
		return habilitaFirmadoTramites;
	}
	public void setHabilitaFirmadoTramites(boolean habilitaFirmadoTramites) {
		this.habilitaFirmadoTramites = habilitaFirmadoTramites;
	}
	
	public ArchivosRespuestaTokenDTO getArchivosRespuestaTokenRegistroDTO() {
		return archivosRespuestaTokenRegistroDTO;
	}

	public void setArchivosRespuestaTokenRegistroDTO(ArchivosRespuestaTokenDTO archivosRespuestaTokenRegistroDTO) {
		this.archivosRespuestaTokenRegistroDTO = archivosRespuestaTokenRegistroDTO;
	}

	public ArchivosRespuestaTokenDTO getArchivosRespuestaTokenAceptacionDTO() {
		return archivosRespuestaTokenAceptacionDTO;
	}

	public void setArchivosRespuestaTokenAceptacionDTO(ArchivosRespuestaTokenDTO archivosRespuestaTokenAceptacionDTO) {
		this.archivosRespuestaTokenAceptacionDTO = archivosRespuestaTokenAceptacionDTO;
	}

	public ArchivosRespuestaTokenDTO getArchivosRespuestaTokenRechazoDTO() {
		return archivosRespuestaTokenRechazoDTO;
	}

	public void setArchivosRespuestaTokenRechazoDTO(ArchivosRespuestaTokenDTO archivosRespuestaTokenRechazoDTO) {
		this.archivosRespuestaTokenRechazoDTO = archivosRespuestaTokenRechazoDTO;
	}

	public ArchivosRespuestaTokenDTO getArchivosRespuestaTokenConclusionDTO() {
		return archivosRespuestaTokenConclusionDTO;
	}

	public void setArchivosRespuestaTokenConclusionDTO(ArchivosRespuestaTokenDTO archivosRespuestaTokenConclusionDTO) {
		this.archivosRespuestaTokenConclusionDTO = archivosRespuestaTokenConclusionDTO;
	}

	public DetAccesoLLaveDTO getAccesoLlaveDTO() {
		return accesoLlaveDTO;
	}

	public void setAccesoLlaveDTO(DetAccesoLLaveDTO accesoLlaveDTO) {
		this.accesoLlaveDTO = accesoLlaveDTO;
	}

	public DetFirmaDigitalDTO getFirmaDTO() {
		return firmaDTO;
	}

	public void setFirmaDTO(DetFirmaDigitalDTO firmaDTO) {
		this.firmaDTO = firmaDTO;
	}
	
	
	public boolean isExistenFormatosConfigurados() {
		return existenFormatosConfigurados;
	}
	public void setExistenFormatosConfigurados(boolean existenFormatosConfigurados) {
		this.existenFormatosConfigurados = existenFormatosConfigurados;
	}	
}
