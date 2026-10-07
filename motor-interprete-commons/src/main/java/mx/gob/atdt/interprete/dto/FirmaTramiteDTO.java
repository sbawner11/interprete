package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.commons.utils.Utils;

public class FirmaTramiteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8482934958565530325L;

	private UsuarioDTO usuarioTramite;
	private TramiteDTO tramite;
	private UsuarioDTO usuarioFirmante;
	private Date fechaFirmado;
		
	/**
	 * 
	 */
	public FirmaTramiteDTO() {
	}
	
	/**
	 * Método auxiliar que obtiene la información que será enviada para firma del archivo de respuesta del trámite
	 * @return
	 */
	public String getCadenaFirmado() {
		StringBuilder strCadenaFirmado = new StringBuilder();
		
		strCadenaFirmado.append(tramite.getProyectoDTO().getIdProyecto()).append(Constantes.SEPARADOR_FIRMADO);
		strCadenaFirmado.append(tramite.getIdTramite()).append(Constantes.SEPARADOR_FIRMADO);
		strCadenaFirmado.append(tramite.getFolioSeguimiento()).append(Constantes.SEPARADOR_FIRMADO);
		if(usuarioTramite != null) {
			strCadenaFirmado.append(usuarioTramite.getIdUsuarioLlaveCdmx()).append(Constantes.SEPARADOR_FIRMADO);		
			strCadenaFirmado.append(usuarioTramite.getCurp()).append(Constantes.SEPARADOR_FIRMADO);	
		}		
		strCadenaFirmado.append(usuarioFirmante.getIdUsuarioLlaveCdmx()).append(Constantes.SEPARADOR_FIRMADO);
		strCadenaFirmado.append(usuarioFirmante.getCurp()).append(Constantes.SEPARADOR_FIRMADO);		
		strCadenaFirmado.append(tramite.getCatEstatusTramiteDTO().getIdEstatusTramite()).append(Constantes.SEPARADOR_FIRMADO);		
		strCadenaFirmado.append(Utils.convertirDateStringFirmado(tramite.getFechaCreacion())).append(Constantes.SEPARADOR_FIRMADO);
		strCadenaFirmado.append(Utils.convertirDateStringFirmado(fechaFirmado));
						
		return strCadenaFirmado.toString();
	}
	
	/**GETTER´s y SETTER´s**/

	/**
	 * @return the usuarioTramite
	 */
	public UsuarioDTO getUsuarioTramite() {
		return usuarioTramite;
	}

	/**
	 * @param usuarioTramite the usuarioTramite to set
	 */
	public void setUsuarioTramite(UsuarioDTO usuarioTramite) {
		this.usuarioTramite = usuarioTramite;
	}

	/**
	 * @return the tramite
	 */
	public TramiteDTO getTramite() {
		return tramite;
	}

	/**
	 * @param tramite the tramite to set
	 */
	public void setTramite(TramiteDTO tramite) {
		this.tramite = tramite;
	}
	
	/**
	 * @return the usuarioFirmante
	 */
	public UsuarioDTO getUsuarioFirmante() {
		return usuarioFirmante;
	}

	/**
	 * @param usuarioFirmante the usuarioFirmante to set
	 */
	public void setUsuarioFirmante(UsuarioDTO usuarioFirmante) {
		this.usuarioFirmante = usuarioFirmante;
	}

	/**
	 * @return the fechaFirmado
	 */
	public Date getFechaFirmado() {
		return fechaFirmado;
	}

	/**
	 * @param fechaFirmado the fechaFirmado to set
	 */
	public void setFechaFirmado(Date fechaFirmado) {
		this.fechaFirmado = fechaFirmado;
	}	
}
