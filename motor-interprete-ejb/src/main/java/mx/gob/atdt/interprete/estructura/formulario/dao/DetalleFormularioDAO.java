package mx.gob.atdt.interprete.estructura.formulario.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.CatOrigenLlenadoDTO;
import mx.gob.atdt.interprete.dto.CatTamanioArchivosDTO;
import mx.gob.atdt.interprete.dto.CatTipoArchivoDTO;
import mx.gob.atdt.interprete.dto.CatTipoComponenteDTO;
import mx.gob.atdt.interprete.dto.CatTipoOrdenamientoDTO;
import mx.gob.atdt.interprete.dto.CatValidadoresDTO;
import mx.gob.atdt.interprete.dto.ComponenteAreaTextoDTO;
import mx.gob.atdt.interprete.dto.ComponenteCampoTextoDTO;
import mx.gob.atdt.interprete.dto.ComponenteCargaDocumentosDTO;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxDTO;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxUnicoDTO;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosDomicilioDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesLlaveDTO;
import mx.gob.atdt.interprete.dto.ComponenteDynamicTableDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonaMoralDTO;
import mx.gob.atdt.interprete.dto.ComponenteFechaDTO;
import mx.gob.atdt.interprete.dto.ComponenteInformativoDTO;
import mx.gob.atdt.interprete.dto.ComponenteMenuDesplegableDTO;
import mx.gob.atdt.interprete.dto.ComponenteRadiobotonDTO;
import mx.gob.atdt.interprete.dto.ControlComponentesDTO;
import mx.gob.atdt.interprete.dto.CrcCargaDocumentosTipoArchivoDTO;
import mx.gob.atdt.interprete.dto.DetElementosCheckboxDTO;
import mx.gob.atdt.interprete.dto.DetElementosMenuDTO;
import mx.gob.atdt.interprete.dto.DetElementosRadiobotonDTO;
import mx.gob.atdt.interprete.dto.DynamicColumnConfigDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.SeccionesFormularioDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;

@Stateless
@LocalBean
public class DetalleFormularioDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetalleFormularioDAO.class);
	
	
	/**
	 * Método que realiza la consulta del detalle del proyecto del intérprete.
	 * @return
	 * @throws Exception
	 */
	public ProyectoDTO consultaProyecto() throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" p.id_proyecto, d.id_dependencia, d.descripcion as descDependencia, e.id_estatus_proyecto, e.descripcion as descEstatus,  ");
		strQuery.append(" t.id_tipo_proyecto, t.descripcion, p.id_usuario_llave_cdmx, p.nombre_proyecto, p.acronimo, ");
		strQuery.append(" p.habilita_captcha, p.habilita_acceso_llave, p.habilita_pago_linea,  ");
		strQuery.append(" p.habilita_gestion_usuarios, p.habilita_firma_digital, p.habilita_detalle_legales, p.habilita_analytics, p.habilita_security_domain, ");
		strQuery.append(" p.fecha_creacion, p.aviso ");
		strQuery.append(" FROM motor_interprete.proyecto  p ");
		strQuery.append(" JOIN motor_interprete.cat_dependencia d on d.id_dependencia = p.id_dependencia ");
		strQuery.append(" JOIN motor_interprete.cat_estatus_proyecto e on e.id_estatus_proyecto = p.id_estatus_proyecto ");
		strQuery.append(" JOIN motor_interprete.cat_tipo_proyecto  t on t.id_tipo_proyecto = p.id_tipo_proyecto  ");
		strQuery.append(" WHERE 1 = 1 ");

		List<ProyectoDTO> lstProyectos = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), null, null);
		
		for (Object[] row : rows) {
			ProyectoDTO proyecto = new ProyectoDTO();
			proyecto.setIdProyecto(Long.parseLong(String.valueOf(row[0])));
			proyecto.getCatDependenciaDTO().setIdDependencia((Integer) row[1]);
			proyecto.getCatDependenciaDTO().setDescripcion((String) row[2]);
			proyecto.getCatEstatusProyectoDTO().setIdEstatusProyecto((Integer) row[3]);
			proyecto.getCatEstatusProyectoDTO().setDescripcion((String) row[4]);
			proyecto.getCatTipoProyectoDTO().setIdTipoProyecto((Integer) row[5]);
			proyecto.getCatTipoProyectoDTO().setDescripcion((String) row[6]);
			proyecto.setNombreProyecto((String) row[8]);
			proyecto.setAcronimo((String) row[9]);
			proyecto.setHabilitaCaptcha((boolean) row[10]);
			proyecto.setHabilitaAccesoLlave((boolean) row[11]);
			proyecto.setHabilitaPagoLinea((boolean) row[12]);
			proyecto.setHabilitaGestionUsuarios((boolean) row[13]);
			proyecto.setHabilitaFirmaDigital((boolean) row[14]);
			proyecto.setHabilitaDetalleLegales((boolean) row[15]);
			proyecto.setHabilitaAnalytics((boolean) row[16]);
			proyecto.setHabilitaSecurityDomain((boolean) row[17]);
			proyecto.setFechaCreacion((Date) row[18]);
			proyecto.setAviso((boolean) row[19]);

			lstProyectos.add(proyecto);
		}
		
		return  !lstProyectos.isEmpty() ? lstProyectos.get(0) : null;
	}
	
	/**
	 * Método que realiza la consulta de las secciones registradas en el proyecto.
	 * @param proyectoDTO
	 * @return
	 * @throws Exception
	 */
	public ArrayList<SeccionesFormularioDTO> consultarSeccionesPorProyecto(ProyectoDTO proyectoDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT sf.id_seccion_formulario, sf.id_proyecto, sf.nombre_seccion, sf.orden, sf.activo ");		
		strQuery.append("FROM motor_interprete.secciones_formulario sf ");
		strQuery.append("WHERE sf.id_proyecto = :idProyecto ");
		strQuery.append("AND sf.activo = true ");
		strQuery.append("ORDER BY sf.orden ");
	
		ArrayList<SeccionesFormularioDTO> lstSeccionesFormulario = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idProyecto", proyectoDTO.getIdProyecto());
		
		for (Object[] row : rows) {
			SeccionesFormularioDTO seccionFormularioDTO = new SeccionesFormularioDTO();
			seccionFormularioDTO.setIdSeccionFormulario(Long.parseLong(String.valueOf(row[0])));
			seccionFormularioDTO.setProyectoDTO(new ProyectoDTO(Long.parseLong(String.valueOf(row[1]))));
			seccionFormularioDTO.setNombreSeccion((String) row[2]);
			seccionFormularioDTO.setOrden((int) row[3]);
			seccionFormularioDTO.setActivo((boolean) row[4]);
			
			lstSeccionesFormulario.add(seccionFormularioDTO);
		}
		
		return  !lstSeccionesFormulario.isEmpty() ? lstSeccionesFormulario : null;
	}
		
	/**
	 * Método que realiza la consulta de las Subsecciones registradas en la sección.
	 * @param seccionDTO
	 * @return
	 * @throws Exception
	 */
	public List<SubSeccionesFormularioDTO> consultarSubseccionesPorSeccion(SeccionesFormularioDTO seccionDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("ssf.id_subseccion_formulario, sf.id_seccion_formulario, ssf.nombre_subseccion, ssf.orden, ssf.activo ");
		strQuery.append("FROM motor_interprete.subsecciones_formulario ssf ");
		strQuery.append("JOIN motor_interprete.secciones_formulario sf on sf.id_seccion_formulario = ssf.id_seccion_formulario ");
		strQuery.append("WHERE sf.id_seccion_formulario = :idSeccion ");
		strQuery.append("AND ssf.activo = true ");
		strQuery.append("ORDER BY sf.orden, ssf.orden");

		List<SubSeccionesFormularioDTO> lstSubsecciones = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idSeccion", seccionDTO.getIdSeccionFormulario());
		
		for (Object[] row : rows) {
			SubSeccionesFormularioDTO subsecciones = new SubSeccionesFormularioDTO();
			subsecciones.setIdSubseccionFormulario(Long.parseLong(String.valueOf(row[0])));
			subsecciones.setSeccionesFormularioDTO(new SeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			subsecciones.setNombreSubseccion((String) row[2]);
			subsecciones.setOrden((int) row[3]);
			subsecciones.setActivo((boolean) row[4]);
			
			lstSubsecciones.add(subsecciones);
		}

		return  !lstSubsecciones.isEmpty() ? lstSubsecciones : null;
	}
		
	/**
	 * Método que realiza la consulta de Componentes registrados por Subsección.
	 * @param subseccionDTO
	 * @return
	 * @throws Exception
	 */
	public List<ComponenteDTO> consultarComponentesPorSubseccion(SubSeccionesFormularioDTO subseccionDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden ");
		strQuery.append("FROM motor_interprete.componente c ");
		strQuery.append("WHERE c.id_subseccion_formulario = :idSubseccionFormulario ");
		strQuery.append("AND c.activo = true ");
		strQuery.append("ORDER BY  c.orden ");

		List<ComponenteDTO> lstComponentes = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idSubseccionFormulario", subseccionDTO.getIdSubseccionFormulario());
		
		for (Object[] row : rows) {
			ComponenteDTO componente = new ComponenteDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			
			lstComponentes.add(componente);
		}

		return  !lstComponentes.isEmpty() ? lstComponentes : null;
	}
	
	/**
	 * Método que realiza la consulta del detalle de componentes de tipo campo de texto por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteCampoTextoDTO consultarDetalleCampoTexto(ComponenteDTO componenteDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("cct.id_componente_campo_texto, cct.alfanumerico, cct.numerico, ");
		strQuery.append("cct.habilita_texto_interior, cct.texto_interior, cct.id_origen_llenado, ");
		strQuery.append("cct.validadores, cct.id_validador, cct.valor_minimo, cct.valor_maximo, cct.permite_decimales  ");		
		strQuery.append("FROM motor_interprete.componente_campo_texto cct ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON cct.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteCampoTextoDTO> lstCampoDeTexto = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", componenteDTO.getIdComponente());
		
		for (Object[] row : rows) {
			ComponenteCampoTextoDTO componente = new ComponenteCampoTextoDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteCampoTexto(Long.parseLong(String.valueOf(row[11])));
			componente.setAlfanumerico((boolean) row[12]);
			componente.setNumerico((boolean) row[13]);
			componente.setHabilitaTextoInterior((boolean) row[14]);
			componente.setTextoInterior((String) row[15]);
			componente.setCatOrigenLlenadoDTO(row[16] != null ? new CatOrigenLlenadoDTO(Integer.parseInt(String.valueOf(row[16]))) : null);
			componente.setValidadores((boolean) row[17]);
			componente.setCatValidadoresDTO(row[18] != null ? new CatValidadoresDTO((Integer) row[18]) : null);
			componente.setValorMinimo(row[19] != null ? Long.parseLong(String.valueOf(row[19])) : null);
			componente.setValorMaximo(row[20] != null ? Long.parseLong(String.valueOf(row[20])) : null);
			componente.setPermiteDecimales((boolean) row[21]);
			
			lstCampoDeTexto.add(componente);
		}

		return  !lstCampoDeTexto.isEmpty() ? lstCampoDeTexto.get(0) : null;
	}
	
	/**
	 * Método que realiza la consulta del detalle de componentes de tipo campo de texto por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteCampoTextoDTO consultarDetalleCampoTextoIdComponente(Long idComponente) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("cct.id_componente_campo_texto, cct.alfanumerico, cct.numerico, ");
		strQuery.append("cct.habilita_texto_interior, cct.texto_interior, cct.id_origen_llenado, ");
		strQuery.append("cct.validadores, cct.id_validador, cct.valor_minimo, cct.valor_maximo, cct.permite_decimales ");		
		strQuery.append("FROM motor_interprete.componente_campo_texto cct ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON cct.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteCampoTextoDTO> lstCampoDeTexto = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", idComponente);
		
		for (Object[] row : rows) {
			ComponenteCampoTextoDTO componente = new ComponenteCampoTextoDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteCampoTexto(Long.parseLong(String.valueOf(row[11])));
			componente.setAlfanumerico((boolean) row[12]);
			componente.setNumerico((boolean) row[13]);
			componente.setHabilitaTextoInterior((boolean) row[14]);
			componente.setTextoInterior((String) row[15]);
			componente.setCatOrigenLlenadoDTO(row[16] != null ? new CatOrigenLlenadoDTO(Integer.parseInt(String.valueOf(row[16]))) : null);
			componente.setValidadores((boolean) row[17]);
			componente.setCatValidadoresDTO(row[18] != null ? new CatValidadoresDTO((Integer) row[18]) : null);
			componente.setValorMinimo(row[19] != null ? Long.parseLong(String.valueOf(row[19])) : null);
			componente.setValorMaximo(row[20] != null ? Long.parseLong(String.valueOf(row[20])) : null);
			componente.setPermiteDecimales((boolean) row[21]);
			
			lstCampoDeTexto.add(componente);
		}

		return !lstCampoDeTexto.isEmpty() ? lstCampoDeTexto.get(0) : null;
	}
	
	/**
	 * Método que realiza la consulta del detalle de componentes de tipo Fecha por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteFechaDTO consultarDetalleFecha(ComponenteDTO componenteDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("cf.id_componente_fecha, cf.dias_inhabiles, cf.fecha_menor_hoy, ");
		strQuery.append("cf.fecha_mayor_hoy, cf.fecha_inicio, cf.fecha_limite ");		
		strQuery.append("FROM motor_interprete.componente_fecha cf ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON cf.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteFechaDTO> lstFecha = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", componenteDTO.getIdComponente());
		
		for (Object[] row : rows) {
			ComponenteFechaDTO componente = new ComponenteFechaDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteFecha(Long.parseLong(String.valueOf(row[11])));
			componente.setDiasInhabiles(row[12] != null ? (boolean) row[12] : null);
			componente.setFechaMenorHoy(row[13] != null ? (boolean) row[13] : null);
			componente.setFechaMayorHoy(row[14] != null ? (boolean) row[14] : null);			
			componente.setFechaInicio(row[15] != null ? (Date) row[15] : null);
			componente.setFechaLimite(row[16] != null ? (Date) row[16] : null);
			
			lstFecha.add(componente);
		}

		return  !lstFecha.isEmpty() ? lstFecha.get(0) : null;
	}
	
	/**
	 * Método que realiza la consulta del detalle de componentes de tipo CheckBox único por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteCheckboxUnicoDTO consultarDetalleCheckUnico(ComponenteDTO componenteDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("ccu.id_componente_checkbox_unico, ccu.texto ");		
		strQuery.append("FROM motor_interprete.componente_checkbox_unico ccu ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON ccu.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteCheckboxUnicoDTO> lstCheckUnico = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", componenteDTO.getIdComponente());
		
		for (Object[] row : rows) {
			ComponenteCheckboxUnicoDTO componente = new ComponenteCheckboxUnicoDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteCheckboxUnico(Long.parseLong(String.valueOf(row[11])));
			componente.setTexto((String) row[12]);
			
			lstCheckUnico.add(componente);
		}

		return !lstCheckUnico.isEmpty() ? lstCheckUnico.get(0) : null;
	}
	
	/**
	 * Método que realiza la consulta del detalle de componentes de tipo CheckBox Grupo por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteCheckboxDTO consultarDetalleCheckGrupo(ComponenteDTO componenteDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("cc.id_componente_checkbox, cc.habilita_todos_ninguno ");		
		strQuery.append("FROM motor_interprete.componente_checkbox cc ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON cc.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteCheckboxDTO> lstCheckGrupo = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", componenteDTO.getIdComponente());
		
		for (Object[] row : rows) {
			ComponenteCheckboxDTO componente = new ComponenteCheckboxDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteCheckbox(Long.parseLong(String.valueOf(row[11])));
			componente.setHabilitaTodosNinguno((boolean) row[12]);
			
			lstCheckGrupo.add(componente);
		}

		return  !lstCheckGrupo.isEmpty() ? lstCheckGrupo.get(0) : null;
	}
	
	/**
	 * Método que realiza la consulta de elementos que confirman un CheckBox Grupo por el Id de componente Checkbox.
	 * @param componenteCheckboxDTO
	 * @return
	 * @throws Exception
	 */
	public List<DetElementosCheckboxDTO> consultarElementosCheckGrupo(ComponenteCheckboxDTO componenteCheckboxDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT dec2.id_elemento_checkbox, dec2.id_componente_checkbox, ");
		strQuery.append("dec2.descripcion_elemento, dec2.activo, dec2.orden ");
		strQuery.append("FROM motor_interprete.det_elementos_checkbox dec2 ");
		strQuery.append("WHERE dec2.id_componente_checkbox = :id_componente_checkbox ");
		strQuery.append("AND dec2.activo = true ");
		strQuery.append("ORDER BY dec2.orden ");

		List<DetElementosCheckboxDTO> lstElementosCheck = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente_checkbox", componenteCheckboxDTO.getIdComponenteCheckbox());
		
		for (Object[] row : rows) {
			DetElementosCheckboxDTO elemento = new DetElementosCheckboxDTO();
			elemento.setIdElementoCheckbox(Long.parseLong(String.valueOf(row[0])));
			elemento.setComponenteCheckboxDTO(new ComponenteCheckboxDTO(Long.parseLong(String.valueOf(row[1]))));
			elemento.setDescripcionElemento((String) row[2]);
			elemento.setActivo((boolean) row[3]);
			elemento.setOrden((int) row[4]);
				
			lstElementosCheck.add(elemento);
		}

		return  !lstElementosCheck.isEmpty() ? lstElementosCheck : null;
	}
		
	/**
	 * Método que realiza la consulta del detalle de componentes de tipo RadioBoton por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteRadiobotonDTO consultarDetalleRadioBoton(ComponenteDTO componenteDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("cr.id_componente_radioboton, cr.habilita_opcion_otro, cr.texto_interior_otro ");		
		strQuery.append("FROM motor_interprete.componente_radioboton cr ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON cr.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteRadiobotonDTO> lstRadioBoton = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", componenteDTO.getIdComponente());
		
		for (Object[] row : rows) {
			ComponenteRadiobotonDTO componente = new ComponenteRadiobotonDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteRadioboton(Long.parseLong(String.valueOf(row[11])));
			componente.setHabilitaOpcionOtro((boolean) row[12]);
			componente.setTextoInteriorOtro((String) row[13]);
			
			lstRadioBoton.add(componente);
		}

		return !lstRadioBoton.isEmpty() ? lstRadioBoton.get(0) : null;
	}
	
	/**
	 * Método que realiza la consulta de elementos que conforman un Radioboton por el Id de componente Radioboton.
	 * @param componenteRadioBotonDTO
	 * @return
	 * @throws Exception
	 */
	public List<DetElementosRadiobotonDTO> consultarElementosRadioBoton(ComponenteRadiobotonDTO componenteRadioBotonDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT der.id_elemento_radioboton, der.id_componente_radioboton, ");
		strQuery.append("der.descripcion_elemento, der.activo, der.orden ");
		strQuery.append("FROM motor_interprete.det_elementos_radioboton der ");
		strQuery.append("WHERE der.id_componente_radioboton = :id_componente_radioboton ");
		strQuery.append("AND der.activo = true ");
		strQuery.append("ORDER BY der.orden ");

		List<DetElementosRadiobotonDTO> lstElementosRadio = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente_radioboton", componenteRadioBotonDTO.getIdComponenteRadioboton());
		
		for (Object[] row : rows) {
			DetElementosRadiobotonDTO elemento = new DetElementosRadiobotonDTO();
			elemento.setIdElementoRadioboton(Long.parseLong(String.valueOf(row[0])));
			elemento.setComponenteRadiobotonDTO(new ComponenteRadiobotonDTO(Long.parseLong(String.valueOf(row[1]))));
			elemento.setDescripcionElemento((String) row[2]);
			elemento.setActivo((boolean) row[3]);
			elemento.setOrden((int) row[4]);
				
			lstElementosRadio.add(elemento);
		}

		return !lstElementosRadio.isEmpty() ? lstElementosRadio : null;
	}
	
	/**
	 * Método que realiza la consulta del detalle de componentes de tipo Domicilio por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteDatosDomicilioDTO consultarDetalleDomicilio(ComponenteDTO componenteDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("cdd.id_componente_datos_domicilio, cdd.habilita_calle, cdd.calle_obligatorio, cdd.texto_interior_calle, ");
		strQuery.append("cdd.habilita_numero_exterior, cdd.numero_exterior_obligatorio, cdd.texto_interior_numero_exterior, ");
		strQuery.append("cdd.habilita_numero_interior, cdd.numero_interior_obligatorio, cdd.texto_interior_numero_interior, ");
		strQuery.append("cdd.habilita_codigo_postal, cdd.codigo_postal_obligatorio, cdd.texto_interior_codigo_postal, ");
		strQuery.append("cdd.habilita_colonia, cdd.colonia_obligatorio, cdd.texto_interior_colonia, ");
		strQuery.append("cdd.habilita_alcaldia, cdd.alcaldia_obligatorio, cdd.texto_interior_alcaldia, ");	
		strQuery.append("cdd.habilita_estado, cdd.estado_obligatorio, cdd.texto_interior_estado ");		
		
		strQuery.append("FROM motor_interprete.componente_datos_domicilio cdd ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON cdd.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteDatosDomicilioDTO> lstDomicilio = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", componenteDTO.getIdComponente());
		
		for (Object[] row : rows) {
			ComponenteDatosDomicilioDTO componente = new ComponenteDatosDomicilioDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteDatosDomicilio(Long.parseLong(String.valueOf(row[11])));
			componente.setHabilitaCalle((boolean) row[12]);
			componente.setCalleObligatorio((boolean) row[13]);
			componente.setTextoInteriorCalle((String) row[14]);			
			componente.setHabilitaNumeroExterior((boolean) row[15]);
			componente.setNumeroExteriorObligatorio((boolean) row[16]);
			componente.setTextoInteriorNumeroExterior((String) row[17]);			
			componente.setHabilitaNumeroInterior((boolean) row[18]);
			componente.setNumeroInteriorObligatorio((boolean) row[19]);
			componente.setTextoInteriorNumeroInterior((String) row[20]);			
			componente.setHabilitaCodigoPostal((boolean) row[21]);
			componente.setCodigoPostalObligatorio((boolean) row[22]);
			componente.setTextoInteriorCodigoPostal((String) row[23]);			
			componente.setHabilitaColonia((boolean) row[24]);
			componente.setColoniaObligatorio((boolean) row[25]);
			componente.setTextoInteriorColonia((String) row[26]);			
			componente.setHabilitaAlcaldia((boolean) row[27]);
			componente.setAlcaldiaObligatorio((boolean) row[28]);
			componente.setTextoInteriorAlcaldia((String) row[29]);		
			componente.setHabilitaEstado((boolean) row[30]);
			componente.setEstadoObligatorio((boolean) row[31]);
			componente.setTextoInteriorEstado((String) row[32]);
			
			lstDomicilio.add(componente);
		}

		return  !lstDomicilio.isEmpty() ? lstDomicilio.get(0) : null;
	}
	
	/**
	 * Método que realiza la consulta del detalle de componentes de tipo Carga de documentos por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteCargaDocumentosDTO consultarDetalleCargaDocumentos(ComponenteDTO componenteDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("cdoc.id_componente_carga, cdoc.id_tamanio_archivo, cdoc.documento_unico ");		
		strQuery.append("FROM motor_interprete.componente_carga_documentos cdoc ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON cdoc.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteCargaDocumentosDTO> lstCargaDocumentos = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", componenteDTO.getIdComponente());
		
		for (Object[] row : rows) {
			ComponenteCargaDocumentosDTO componente = new ComponenteCargaDocumentosDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteCarga(Long.parseLong(String.valueOf(row[11])));
			componente.setCatTamanioArchivosDTO(new CatTamanioArchivosDTO((int) row[12]));
			componente.setDocumentoUnico((boolean) row[13]);

			lstCargaDocumentos.add(componente);
		}

		return  !lstCargaDocumentos.isEmpty() ? lstCargaDocumentos.get(0) : null;
	}
	
	/**
	 * Método que realiza la consulta de elementos tipos de archivo para el componente de Carga de documentos mediante el el Id de componente Carga documentos.
	 * @param componenteRadioBotonDTO
	 * @return
	 * @throws Exception
	 */
	public List<CrcCargaDocumentosTipoArchivoDTO> consultarTiposArchivoComponenteCargaDocumentos(ComponenteCargaDocumentosDTO componenteCargaDocumentosDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ccdta.id_carga_documento_tipo_archivo, ccdta.id_componente_carga, ");
		strQuery.append("ccdta.id_tipo_archivo, ccdta.activo ");
		strQuery.append("FROM motor_interprete.crc_carga_documentos_tipo_archivo ccdta ");
		strQuery.append("WHERE ccdta.id_componente_carga = :id_componente_carga ");		

		List<CrcCargaDocumentosTipoArchivoDTO> lstElementosTiposArchivo = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente_carga", componenteCargaDocumentosDTO.getIdComponenteCarga());
		
		for (Object[] row : rows) {
			CrcCargaDocumentosTipoArchivoDTO elemento = new CrcCargaDocumentosTipoArchivoDTO();
			elemento.setIdCargaDocumentoTipoArchivo(Long.parseLong(String.valueOf(row[0])));
			elemento.setComponenteCargaDocumentosDTO(new ComponenteCargaDocumentosDTO(Long.parseLong(String.valueOf(row[1]))));
			elemento.setCatTipoArchivoDTO(new CatTipoArchivoDTO((int) row[2]));
			elemento.setActivo((boolean) row[3]);
				
			lstElementosTiposArchivo.add(elemento);
		}

		return  !lstElementosTiposArchivo.isEmpty() ? lstElementosTiposArchivo : null;
	}
		
	/**
	 * Método que realiza la consulta del detalle de componentes de tipo Informativo por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteInformativoDTO consultarDetalleInformativo(ComponenteDTO componenteDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("ci.id_componente_informativo, ci.texto_informativo ");		
		strQuery.append("FROM motor_interprete.componente_informativo ci ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON ci.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteInformativoDTO> lstComponenteInformativo = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", componenteDTO.getIdComponente());
		
		for (Object[] row : rows) {
			ComponenteInformativoDTO componente = new ComponenteInformativoDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteInformativo(Long.parseLong(String.valueOf(row[11])));
			componente.setTextoInformativo((String) row[12]);

			lstComponenteInformativo.add(componente);
		}

		return  !lstComponenteInformativo.isEmpty() ? lstComponenteInformativo.get(0) : null;
	}
	
	/**
	 * Método que realiza la consulta del detalle de componentes de tipo Datos personales por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteDatosPersonalesDTO consultarDetalleDatosPersonales(ComponenteDTO componenteDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("cdp.id_componente_datos_personales, cdp.habilita_renapo, cdp.habilita_curp, ");
		strQuery.append("cdp.curp_obligatorio, cdp.texto_interior_curp, cdp.habilita_nombre, cdp.nombre_obligatorio, cdp.texto_interior_nombre, ");
		strQuery.append("cdp.habilita_primer_apellido, cdp.primer_apellido_obligatorio, cdp.texto_interior_primer_apellido, ");
		strQuery.append("cdp.habilita_segundo_apellido, cdp.segundo_apellido_obligatorio, cdp.texto_interior_segundo_apellido, ");
		strQuery.append("cdp.habilita_telefono, cdp.telefono_obligatorio, cdp.texto_interior_telefono, ");
		strQuery.append("cdp.habilita_correo_electronico, cdp.correo_electronico_obligatorio, cdp.texto_interior_correo_electronico ");
		
		strQuery.append("FROM motor_interprete.componente_datos_personales cdp ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON cdp.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteDatosPersonalesDTO> lstComponenteDatosPersonalesSinLlave = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", componenteDTO.getIdComponente());
		
		for (Object[] row : rows) {
			ComponenteDatosPersonalesDTO componente = new ComponenteDatosPersonalesDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteDatosPersonales(Long.parseLong(String.valueOf(row[11])));
			componente.setHabilitaRenapo((boolean) row[12]);
			componente.setHabilitaCurp((boolean) row[13]);
			componente.setCurpObligatorio((boolean) row[14]);
			componente.setTextoInteriorCurp((String) row[15]);			
			componente.setHabilitaNombre((boolean) row[16]);
			componente.setNombreObligatorio((boolean) row[17]);
			componente.setTextoInteriorNombre((String) row[18]);			
			componente.setHabilitaPrimerApellido((boolean) row[19]);			
			componente.setPrimerApellidoObligatorio((boolean) row[20]);
			componente.setTextoInteriorPrimerApellido((String) row[21]);
			componente.setHabilitaSegundoApellido((boolean) row[22]);			
			componente.setSegundoApellidoObligatorio((boolean) row[23]);
			componente.setTextoInteriorSegundoApellido((String) row[24]);
			componente.setHabilitaTelefono((boolean) row[25]);			
			componente.setTelefonoObligatorio((boolean) row[26]);
			componente.setTextoInteriorTelefono((String) row[27]);			
			componente.setHabilitaCorreoElectronico((boolean) row[28]);			
			componente.setCorreoElectronicoObligatorio((boolean) row[29]);
			componente.setTextoInteriorCorreoElectronico((String) row[30]);
			
			lstComponenteDatosPersonalesSinLlave.add(componente);
		}

		return  !lstComponenteDatosPersonalesSinLlave.isEmpty() ? lstComponenteDatosPersonalesSinLlave.get(0) : null;
	}
	
	/**
	 * Método que realiza la consulta del detalle de componentes de tipo Datos personales con Llave por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteDatosPersonalesLlaveDTO consultarDetalleDatosPersonalesConLlave(ComponenteDTO componenteDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("cdpl.id_componente_datos_personales, cdpl.habilita_curp, ");
		strQuery.append("cdpl.habilita_nombre, cdpl.habilita_primer_apellido, cdpl.habilita_segundo_apellido, ");
		strQuery.append("cdpl.habilita_telefono, cdpl.habilita_correo_electronico, cdpl.habilita_fecha_nacimiento, cdpl.habilita_sexo ");		
		strQuery.append("FROM motor_interprete.componente_datos_personales_llave cdpl ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON cdpl.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteDatosPersonalesLlaveDTO> lstComponenteDatosPersonalesConLlave = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", componenteDTO.getIdComponente());
		
		for (Object[] row : rows) {
			ComponenteDatosPersonalesLlaveDTO componente = new ComponenteDatosPersonalesLlaveDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteDatosPersonales(Long.parseLong(String.valueOf(row[11])));
			componente.setHabilitaCurp((boolean) row[12]);
			componente.setHabilitaNombre((boolean) row[13]);
			componente.setHabilitaPrimerApellido((boolean) row[14]);
			componente.setHabilitaSegundoApellido((boolean) row[15]);
			componente.setHabilitaTelefono((boolean) row[16]);
			componente.setHabilitaCorreoElectronico((boolean) row[17]);
			componente.setHabilitaFechaNacimiento((boolean) row[18]);
			componente.setHabilitaSexo((boolean) row[19]);
			
			lstComponenteDatosPersonalesConLlave.add(componente);
		}

		return  !lstComponenteDatosPersonalesConLlave.isEmpty() ? lstComponenteDatosPersonalesConLlave.get(0) : null;
	}

	/**
	 * Método que realiza la consulta del detalle de componentes de tipo Datos persona moral por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteDatosPersonaMoralDTO consultarDetalleDatosPersonaMoral(ComponenteDTO componenteDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("cdpm.id_componente_datos_persona_moral, cdpm.habilita_rfc, ");
		strQuery.append("cdpm.habilita_persona_moral, cdpm.habilita_fecha_vigencia ");		
		strQuery.append("FROM motor_interprete.componente_datos_persona_moral cdpm ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON cdpm.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteDatosPersonaMoralDTO> lstComponenteDatosPersonaMoral = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", componenteDTO.getIdComponente());
		
		for (Object[] row : rows) {
			ComponenteDatosPersonaMoralDTO componente = new ComponenteDatosPersonaMoralDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteDatosPersonaMoral(Long.parseLong(String.valueOf(row[11])));
			componente.setHabilitaRfc((boolean) row[12]);
			componente.setHabilitaPersonaMoral((boolean) row[13]);
			componente.setHabilitaFechaVigencia((boolean) row[14]);
			
			lstComponenteDatosPersonaMoral.add(componente);
		}

		return !lstComponenteDatosPersonaMoral.isEmpty() ? lstComponenteDatosPersonaMoral.get(0) : null;
	}
	
	/**
	 * Método que realiza la consulta del detalle de componentes de tipo Menú desplegable por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteMenuDesplegableDTO consultarDetalleMenuDesplegable(ComponenteDTO componenteDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("cmd.id_componente_menu, cmd.id_origen_llenado, ");
		strQuery.append("cmd.habilita_opcion_otro, cmd.texto_interior_otro, ");		
		strQuery.append("cmd.id_tipo_ordenamiento ");		
		strQuery.append("FROM motor_interprete.componente_menu_desplegable cmd ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON cmd.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteMenuDesplegableDTO> lstMenuDesplegable = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", componenteDTO.getIdComponente());
		
		for (Object[] row : rows) {
			ComponenteMenuDesplegableDTO componente = new ComponenteMenuDesplegableDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteMenuDesplegable(Long.parseLong(String.valueOf(row[11])));
			componente.setCatOrigenLlenadoDTO(new CatOrigenLlenadoDTO((int) row[12]));
			componente.setHabilitaTextoInteriorOtro((boolean) row[13]);
			componente.setTextoInteriorOtro((String) row[14]);
			if (row[15] != null) {
				componente.setCatTipoOrdenamientoDTO(new CatTipoOrdenamientoDTO(Long.valueOf(String.valueOf(row[15]))));
			}			
						
			lstMenuDesplegable.add(componente);
		}

		return  !lstMenuDesplegable.isEmpty() ? lstMenuDesplegable.get(0) : null;
	}
	
	/**
	 * Método que realiza la consulta de elementos que conforman el Menú desplegable mediante el Id de componente MenuDesplegable.
	 * @param componenteMenuDesplegableDTO
	 * @return
	 * @throws Exception
	 */
	public List<DetElementosMenuDTO> consultarElementosElementosMenuDesplegable(ComponenteMenuDesplegableDTO componenteMenuDesplegableDTO) throws Exception {

		Long idTipoOrdenamiento = componenteMenuDesplegableDTO.getCatTipoOrdenamientoDTO() != null
		        ? componenteMenuDesplegableDTO.getCatTipoOrdenamientoDTO().getIdTipoOrdenamiento()
		        : null;
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT dem.id_elemento_menu, dem.descripcion_elemento, dem.activo ");
		strQuery.append("FROM motor_interprete.det_elementos_menu dem ");
		strQuery.append("WHERE dem.id_componente_menu = :id_componente_menu ");
		strQuery.append("AND dem.activo = true ");
		if (idTipoOrdenamiento != null && idTipoOrdenamiento.equals(Constantes.ID_ORDEN_ASCENDENTE)) {
		    strQuery.append("ORDER BY dem.descripcion_elemento ASC ");
		} else if (idTipoOrdenamiento != null && idTipoOrdenamiento.equals(Constantes.ID_ORDEN_DESCENDENTE)) {
			strQuery.append("ORDER BY dem.descripcion_elemento DESC ");
		} else {
			strQuery.append("ORDER BY dem.id_elemento_menu ASC ");
		}	
		List<DetElementosMenuDTO> lstElementosMenu = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente_menu", componenteMenuDesplegableDTO.getIdComponenteMenuDesplegable());

		for (Object[] row : rows) {
			DetElementosMenuDTO elemento = new DetElementosMenuDTO();
			elemento.setIdElementoMenu(Long.parseLong(String.valueOf(row[0])));
			elemento.setDescripcionElemento((String) row[1]);
			elemento.setActivo((boolean) row[2]);
				
			lstElementosMenu.add(elemento);
		}
		
		return  !lstElementosMenu.isEmpty() ? lstElementosMenu : null;
	}
	
	/**
	 * Método que realiza la consulta del detalle de componentes de tipo campo de texto por el Id de componente.
	 * @param componenteDTO
	 * @return
	 * @throws Exception
	 */
	public ComponenteAreaTextoDTO consultarDetalleAreaTexto(ComponenteDTO componenteDTO) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT c.id_componente, c.id_subseccion_formulario, c.id_tipo_componente, c.orden, ");
		strQuery.append("c.requerido, c.tooltip, c.descripcion_tooltip, c.titulo_campo, c.activo, ");
		strQuery.append("c.fecha_creacion, c.fecha_ultima_actualizacion, ");		
		strQuery.append("cat.id_componente_area_texto, ");
		strQuery.append("cat.habilita_texto_interior, cat.texto_interior, cat.id_origen_llenado, ");
		strQuery.append("cat.lineas_altura ");		
		strQuery.append("FROM motor_interprete.componente_area_texto cat ");
		strQuery.append("INNER JOIN motor_interprete.componente c ");
		strQuery.append("ON cat.id_componente = c.id_componente ");		
		strQuery.append("WHERE c.id_componente = :id_componente ");

		List<ComponenteAreaTextoDTO> lstAreaDeTexto = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "id_componente", componenteDTO.getIdComponente());
		
		for (Object[] row : rows) {
			ComponenteAreaTextoDTO componente = new ComponenteAreaTextoDTO();
			componente.setIdComponente(Long.parseLong(String.valueOf(row[0])));
			componente.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(Long.parseLong(String.valueOf(row[1]))));
			componente.setCatTipoComponenteDTO(new CatTipoComponenteDTO((int) row[2]));
			componente.setOrden((int) row[3]);
			componente.setRequerido((boolean) row[4]);
			componente.setTooltip((boolean) row[5]);
			componente.setDescripcionTooltip((String) row[6]);
			componente.setTituloCampo((String) row[7]);
			componente.setActivo((boolean) row[8]);
			componente.setFechaCreacion((Date) row[9]);
			componente.setFechaUltimaActualizacion((Date) row[10]);
			
			componente.setIdComponenteAreaTexto(Long.parseLong(String.valueOf(row[11])));
			componente.setHabilitaTextoInterior((boolean) row[12]);
			componente.setTextoInterior((String) row[13]);
			componente.setCatOrigenLlenadoDTO(row[14] != null ? new CatOrigenLlenadoDTO(Integer.parseInt(String.valueOf(row[14]))) : null);
			componente.setLineasAltura((int) row[15]);
			
			lstAreaDeTexto.add(componente);
		}

		return  !lstAreaDeTexto.isEmpty() ? lstAreaDeTexto.get(0) : null;
	}
	
	/**
	 * Método que consulta los elementos de la tabla de Contol componentes, tabla que indica por cada componentes cual es su
	 * campo-tabla que le corresponde para el guardado de datos de los formularios.
	 * 	
	 * @return
	 * @throws Exception
	 */
	public List<ControlComponentesDTO> consultarControlComponentes() throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT cc.id, cc.nombre_tabla, cc.nombre_columna, cc.id_componente, cc.id_tipo_componente, ");
		strQuery.append("cc.nombre_componente, cc.orden, cc.fecha_creacion ");
		strQuery.append("FROM motor_interprete.control_componentes cc ");
		strQuery.append("ORDER BY cc.id ");
		
		List<ControlComponentesDTO> lstControlComponentes = new ArrayList<>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), null, null);
		
		for (Object[] row : rows) {
			ControlComponentesDTO elemento = new ControlComponentesDTO();
			elemento.setId(Long.parseLong(String.valueOf(row[0])));
			elemento.setNombreTabla((String) row[1]);
			elemento.setNombreColumna((String) row[2]);
			elemento.setIdComponente(Long.parseLong(String.valueOf(row[3])));
			elemento.setIdTipoComponente((int) row[4]) ;
			elemento.setNombreComponente((String) row[5]);
			elemento.setOrden((int) row[6]);
			elemento.setFechaCreacion((Date) row[7]);
				
			lstControlComponentes.add(elemento);
		}

		return !lstControlComponentes.isEmpty() ? lstControlComponentes : null;
	}
		
	/**
	 * Método auxiliar que realiza la ejecución de la consulta enviada.
	 * @param consulta
	 * @param nombreParametro
	 * @param valorParametro
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> ejecutarConsulta(String consulta, String nombreParametro, Long valorParametro) throws Exception {
		List<Object[]> rows = null; 
				
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createNativeQuery(consulta);	
			if(BeanUtils.isNotNull(nombreParametro)) {
				query.setParameter(nombreParametro, valorParametro);
			}
			rows = query.getResultList();
			
			entityManager.getTransaction().commit();

		} catch (Throwable e) {
			if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("Error:  ", e);
			throw new Exception("Error en consulta. " + e);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}

		return rows;
	}
	
	/**
	 * Consulta el detalle del componente tabla dinámica
	 */
	 public ComponenteDynamicTableDTO consultarDetalleTablaDinamica(ComponenteDTO componente) throws Exception {
	        ComponenteDynamicTableDTO dto = new ComponenteDynamicTableDTO();
	        
	        String sql = "SELECT ct.id_componente_tabla, ct.permite_agregar_filas, ct.tamanio_pagina, " +
	                 "ct.minimo_filas, ct.maximo_filas, c.activo " +
	                 "FROM motor_interprete.componente_tabla ct " +
	                 "INNER JOIN motor_interprete.componente c ON c.id_componente = ct.id_componente " +
	                 "WHERE ct.id_componente = :idComponente";
	        
	        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
	        EntityManager entityManager = entityManagerFactory.createEntityManager();
	        try {
	            entityManager.getTransaction().begin();
	            
	            Query query = entityManager.createNativeQuery(sql);
	            query.setParameter("idComponente", componente.getIdComponente());
	            
	            List<Object[]> results = query.getResultList();
	            if (!results.isEmpty()) {
	                Object[] row = results.get(0);
	                dto.setIdComponente(componente.getIdComponente());
	                dto.setCatTipoComponenteDTO(componente.getCatTipoComponenteDTO());
	                dto.setSubSeccionesFormularioDTO(componente.getSubSeccionesFormularioDTO());
	                dto.setOrden(componente.getOrden());
	                dto.setRequerido(componente.isRequerido());
	                dto.setTooltip(componente.isTooltip());
	                dto.setDescripcionTooltip(componente.getDescripcionTooltip());
	                dto.setTituloCampo(componente.getTituloCampo());
	                dto.setActivo(componente.isActivo());
	                
	                dto.setPermiteAgregarFilas((boolean) row[1]);
	                dto.setTamanioPagina((int) row[2]);
	                dto.setMinimoFilas((int) row[3]);
	                dto.setMaximoFilas((int) row[4]);
	                dto.setActivo((boolean) row[5]); 
	                
	                // Cargar columnas desde la nueva tabla det_elementos_tabla
	                dto.setColumnas(consultarElementosTablaDinamica(dto.getIdComponente()));
	            }
	            
	            entityManager.getTransaction().commit();
	        } catch (Exception e) {
	            if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
	                entityManager.getTransaction().rollback();
	            }
	            LOGGER.error("Error al consultar detalle de tabla dinámica", e);
	            throw e;
	        } finally {
	            entityManager.close();
	            entityManagerFactory.close();
	        }
	        
	        return dto;
	 }
	 
	 
 
 	/**
     * Consulta los elementos (columnas) de la tabla dinámica desde det_elementos_tabla
     */
    public List<DynamicColumnConfigDTO> consultarElementosTablaDinamica(Long idComponente) throws Exception {
        List<DynamicColumnConfigDTO> columnas = new ArrayList<>();
        
        String sql = "SELECT det.id_elemento_tabla, det.titulo_header, det.id_cat_tipo_campo, " +
                     "det.requerido, det.tooltip, det.longitu_celda, det.orden_columna, tcc.descripcion " +
                     "FROM motor_interprete.det_elementos_tabla det " +
                     "INNER JOIN motor_interprete.componente_tabla ct ON ct.id_componente_tabla = det.id_componente_tabla " +
                     "INNER JOIN motor_interprete.cat_tipo_campo tcc ON tcc.id_cat_tipo_campo = det.id_cat_tipo_campo " +
                     "WHERE ct.id_componente = :idComponente " +
                     "AND det.activo = true " +  
                     "ORDER BY det.orden_columna ASC";
        
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("idComponente", idComponente);
            
            List<Object[]> results = query.getResultList();
            for (Object[] row : results) {
                DynamicColumnConfigDTO columna = new DynamicColumnConfigDTO();
                columna.setId(((Number) row[0]).longValue());
                columna.setNombre((String) row[1]);  
                
                int idTipoCampo = (int) row[2];
                columna.setTipo(idTipoCampo == 1 ? "NUMERICO" : (idTipoCampo == 3 ? "FECHA" : "ALFANUMERICO"));
                columna.setRequerido((boolean) row[3]);
                columna.setTooltip((String) row[4]);
                
                int longitud = row[5] != null ? (int) row[5] : (idTipoCampo == 1 ? 25 : 200);
                columna.setLongitudMaxima(longitud);
                columna.setOrden((int) row[6]);
                
                columnas.add(columna);
            }
            
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            LOGGER.error("Error al consultar elementos de tabla dinámica", e);
            throw e;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        
        return columnas;
    }
	
	
}
