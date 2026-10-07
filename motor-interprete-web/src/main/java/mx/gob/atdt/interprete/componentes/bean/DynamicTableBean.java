package mx.gob.atdt.interprete.componentes.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.acceso.bean.AuthenticatorBean;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.dto.DynamicColumnConfigDTO;
import mx.gob.atdt.interprete.dto.DynamicTableRowDTO;
import mx.gob.atdt.interprete.dto.DynamicTableStateDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.formulario.facade.FormularioFacade;
import mx.gob.atdt.interprete.util.BeanUtils;
import mx.gob.atdt.interprete.util.WebResources;

@Named("dynamicTableBean")
@SessionScoped
public class DynamicTableBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicTableBean.class);

    @Inject
    private FormularioFacade formularioFacade;
    
    @Inject
    private AuthenticatorBean authenticatorBean;

    // Mapa que almacena el estado de cada tabla por su ID de componente
    private Map<Long, DynamicTableStateDTO> tablaStates = new HashMap<>();
    
    // Mapa para los datos temporales de nueva fila por componente
    private Map<Long, Map<String, Object>> nuevaFilaDatosMap = new HashMap<>();
    
    // Mapa para la fila seleccionada por componente
    private Map<Long, DynamicTableRowDTO> filaSeleccionadaMap = new HashMap<>();
    // Mapa copia para la fila seleccionada por componente
    private Map<Long, DynamicTableRowDTO> filaSeleccionadaMapOriginal = new HashMap<>();
    
    // Mapa para mensajes de validación por componente
    private Map<Long, String> mensajeValidacionMap = new HashMap<>();
    
    private TramiteDTO tramiteActualDTO;
   
    
    public List<DynamicTableRowDTO> getFilas(Long idComponente) {
        DynamicTableStateDTO state = getState(idComponente);
        return state != null ? state.getFilas() : new ArrayList<>();
    }

    public List<DynamicColumnConfigDTO> getColumnas(Long idComponente) {
        DynamicTableStateDTO state = getState(idComponente);
        return state != null ? state.getColumnas() : new ArrayList<>();
    }
    
    public String getTitulo(Long idComponente) {
        DynamicTableStateDTO state = getState(idComponente);
        return state != null ? state.getTitulo() : "Tabla dinámica";
    }
    
    public int getMinRows(Long idComponente) {
        DynamicTableStateDTO state = getState(idComponente);
        return state != null ? state.getMinRows() : 1;
    }
    
    public int getMaxRows(Long idComponente) {
        DynamicTableStateDTO state = getState(idComponente);
        return state != null ? state.getMaxRows() : 200;
    }
    
    public int getPageSize(Long idComponente) {
        DynamicTableStateDTO state = getState(idComponente);
        return state != null ? state.getPageSize() : 20;
    }
    
    public boolean isAllowAddingRows(Long idComponente) {
        DynamicTableStateDTO state = getState(idComponente);
        return state != null && state.isAllowAddingRows();
    }
    
    public boolean isTablaRequerida(Long idComponente) {
        DynamicTableStateDTO state = getState(idComponente);
        return state != null && state.isTablaRequerida();
    }
    
    public int getTotalFilasActivas(Long idComponente) {
        return getFilas(idComponente).size();
    }
    
    public int getTotalFilasLlenas(Long idComponente) {
        int count = 0;
        for (DynamicTableRowDTO fila : getFilas(idComponente)) {
            if (fila.tieneDatos()) {
                count++;
            }
        }
        return count;
    }
    
    public boolean isMinimoAlcanzado(Long idComponente) {
        return getTotalFilasLlenas(idComponente) >= getMinRows(idComponente);
    }
       
    public Map<String, Object> getNuevaFilaDatos(Long idComponente) {
        return nuevaFilaDatosMap.computeIfAbsent(idComponente, k -> new HashMap<>());
    }
    
    public void setNuevaFilaDatos(Long idComponente, Map<String, Object> datos) {
        nuevaFilaDatosMap.put(idComponente, datos);
    }
    
    public DynamicTableRowDTO getFilaSeleccionada(Long idComponente) {
        return filaSeleccionadaMap.get(idComponente);
    }
    
    public void setFilaSeleccionada(Long idComponente, DynamicTableRowDTO fila) {
        filaSeleccionadaMap.put(idComponente, fila);
        filaSeleccionadaMapOriginal.put(idComponente, copiarFila(fila));
    }
    
    private DynamicTableRowDTO copiarFila(DynamicTableRowDTO origen) {

        if (origen == null) {
            return null;
        }

        DynamicTableRowDTO copia = new DynamicTableRowDTO();

        copia.setId(origen.getId());
        copia.setUuid(origen.getUuid());
        copia.setNumeroFila(origen.getNumeroFila());
        copia.setTipoFila(origen.getTipoFila());

        if (origen.getDatos() != null) {
            copia.setDatos(new HashMap<>(origen.getDatos()));
        }

        return copia;
    }
    
    public String getMensajeDeValidaciones(Long idComponente) {
        return mensajeValidacionMap.get(idComponente);
    }
    
    public void setMensajeDeValidaciones(Long idComponente, String mensaje) {
        mensajeValidacionMap.put(idComponente, mensaje);
    }
      
    private DynamicTableStateDTO getState(Long idComponente) {
        return tablaStates.get(idComponente);
    }
    
    private DynamicTableStateDTO getOrCreateState(Long idComponente) {
        return tablaStates.computeIfAbsent(idComponente, DynamicTableStateDTO::new);
    }

    public void inicializarComponente(ComponenteDTO componente, TramiteDTO tramiteDTO) {
        Long idComp = componente.getIdComponente();
        Long idTramite = tramiteDTO.getIdTramite();       
        
        DynamicTableStateDTO state = getOrCreateState(idComp);
        
        // Guardar el ID del trámite en el estado
        state.setIdTramite(idTramite);
        
        // Si ya se inicializó, solo recargamos datos
        if (state.getColumnas() != null && !state.getColumnas().isEmpty()) {
            recargarDatos(idComp, idTramite);
            return;
        }
        
        this.tramiteActualDTO = tramiteDTO;
        cargarConfiguracionEnState(state, idComp);
        cargarDatosExistentesEnState(state, idTramite, idComp);
        
        // Inicializar nextRowIndex
        state.setNextRowIndex(state.getFilas().size() + 1);
    }
    
    private void cargarConfiguracionEnState(DynamicTableStateDTO state, Long idComponente) {
              
        Map<String, Object> config = formularioFacade.obtenerConfiguracionTabla(idComponente);
        
        if (config != null && !config.isEmpty()) {
            state.setTitulo((String) config.getOrDefault("titulo", "Tabla dinámica"));
            state.setMinRows((int) config.getOrDefault("minimo_filas", 1));
            state.setMaxRows((int) config.getOrDefault("maximo_filas", 200));
            state.setAllowAddingRows((boolean) config.getOrDefault("permite_agregar_filas", true));
            state.setPageSize((int) config.getOrDefault("tamanio_pagina", 20));
            state.setTablaRequerida((boolean) config.getOrDefault("tabla_requerida", false));
        }
        
        List<DynamicColumnConfigDTO> columnas = formularioFacade.obtenerColumnasTabla(idComponente);
        state.setColumnas(columnas);
    }
    
    public void guardarDatosTabla(Long idComponente, Long idTramite) {
        
        DynamicTableStateDTO state = tablaStates.get(idComponente);
        if (state == null || idTramite == null) return;
        
        for (DynamicTableRowDTO fila : state.getFilas()) {
            try {
                if (fila.getId() == null) {
                    formularioFacade.guardarFilaTabla(idTramite, idComponente, fila);
                } else {
                    formularioFacade.actualizarFilaTabla(idTramite, idComponente, fila);
                }
            } catch (Exception e) {
                LOGGER.error("Error al guardar fila para componente {}", idComponente, e);
            }
        }
    }
    
    private void cargarDatosExistentesEnState(DynamicTableStateDTO state, Long idTramite, Long idComponente) {
        state.setFilas(formularioFacade.obtenerDatosTabla(idTramite, idComponente));
        for (DynamicTableRowDTO fila : state.getFilas()) {
            if (fila.getUuid() == null) {
                fila.setUuid(java.util.UUID.randomUUID().toString());
            }
        }
    }
    
    public void recargarDatos(Long idComponente, Long idTramite) {
        if (idTramite != null && idComponente != null) {
            DynamicTableStateDTO state = getOrCreateState(idComponente);
            List<DynamicTableRowDTO> datos = formularioFacade.obtenerDatosTabla(idTramite, idComponente);
            state.setFilas(datos);
            state.setNextRowIndex(datos.size() + 1);
        }
    }
    
    public void limpiarEstadoSeccion(List<ComponenteDTO> componentesSeccion) {
        if (componentesSeccion != null) {
            for (ComponenteDTO comp : componentesSeccion) {
                if (comp.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_TABLA) {
                    tablaStates.remove(comp.getIdComponente());
                    nuevaFilaDatosMap.remove(comp.getIdComponente());
                    filaSeleccionadaMap.remove(comp.getIdComponente());
                    filaSeleccionadaMapOriginal.remove(comp.getIdComponente());
                    mensajeValidacionMap.remove(comp.getIdComponente());                  
                }
            }
        }
    }
    
    public void limpiar() {
        tablaStates.clear();
        nuevaFilaDatosMap.clear();
        filaSeleccionadaMap.clear();
        filaSeleccionadaMapOriginal.clear();
        mensajeValidacionMap.clear();
    }
    
   
    
    public void prepararNuevaFila(Long idComponente) {
        
        setMensajeDeValidaciones(idComponente, null);
        Map<String, Object> nuevaFilaDatos = getNuevaFilaDatos(idComponente);
        nuevaFilaDatos.clear();
        for (DynamicColumnConfigDTO columna : getColumnas(idComponente)) {
            if ("FECHA".equals(columna.getTipo())) {
                nuevaFilaDatos.put(columna.getNombre(), LocalDate.now());
            } else {
                nuevaFilaDatos.put(columna.getNombre(), "");
            }
        }
    }
    
    public void cancelarAgregarFila(Long idComponente) {
    	restaurarFilaSeleccionada(idComponente);
        setMensajeDeValidaciones(idComponente, null);
        getNuevaFilaDatos(idComponente).clear();
        
    }
    
    public void restaurarFilaSeleccionada(Long idComponente) {

        DynamicTableRowDTO actual = filaSeleccionadaMap.get(idComponente);
        DynamicTableRowDTO original = filaSeleccionadaMapOriginal.get(idComponente);

        if (actual == null || original == null) {
            return;
        }

        actual.setId(original.getId());
        actual.setUuid(original.getUuid());
        actual.setNumeroFila(original.getNumeroFila());
        actual.setTipoFila(original.getTipoFila());

        actual.setDatos(new HashMap<>(original.getDatos()));
    }
    
    private boolean camposObligatoriosFaltantes(Long idComponente, Map<String, Object> datosFila) {
        List<DynamicColumnConfigDTO> columnas = getColumnas(idComponente);
        if (columnas == null || columnas.isEmpty()) return true;
        List<String> camposRequeridosVacios = new ArrayList<>();
        for (DynamicColumnConfigDTO columna : columnas) {
            if (columna.isRequerido()) {
                Object valor = datosFila.get(columna.getNombre());
                if (valor == null || valor.toString().trim().isEmpty()) {
                    camposRequeridosVacios.add(columna.getNombre());
                }
            }
        }
        if (!camposRequeridosVacios.isEmpty()) {
            setMensajeDeValidaciones(idComponente, "Faltan campos obligatorios: " + String.join(", ", camposRequeridosVacios));
            return true;
        }
        return false;
    }
    
    public void agregarFila(Long idComponente) {
       
        Map<String, Object> nuevaFilaDatos = getNuevaFilaDatos(idComponente);
        
        if (camposObligatoriosFaltantes(idComponente, nuevaFilaDatos)) {           
            FacesContext.getCurrentInstance().validationFailed();
            return;
        }
        
        DynamicTableStateDTO state = getState(idComponente);
        if (state == null) {
            LOGGER.error("No se encontró estado para componente {}", idComponente);
            return;
        }
        
        if (getTotalFilasActivas(idComponente) >= state.getMaxRows()) {
            WebResources.addValidationMessage("msg_max_filas", false);
            return;
        }
        
        DynamicTableRowDTO nuevaFila = new DynamicTableRowDTO();
        nuevaFila.setUuid(UUID.randomUUID().toString());
        nuevaFila.setNumeroFila(state.getNextRowIndex());
        nuevaFila.setTipoFila("DINAMICO");
        
        Map<String, Object> datos = new HashMap<>();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        for (DynamicColumnConfigDTO columna : getColumnas(idComponente)) {
            Object valor = nuevaFilaDatos.get(columna.getNombre());
            if ("FECHA".equals(columna.getTipo())) {
                if (valor instanceof java.time.LocalDate) {
                    datos.put(columna.getNombre(), ((java.time.LocalDate) valor).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                } else if (valor instanceof java.util.Date) {
                    datos.put(columna.getNombre(), sdf.format((java.util.Date) valor));
                } else {
                    datos.put(columna.getNombre(), valor != null ? valor.toString() : "");
                }
            } else {
                datos.put(columna.getNombre(), valor != null ? valor : "");
            }
        }
        nuevaFila.setDatos(datos);
        
        state.getFilas().add(nuevaFila);
        state.setNextRowIndex(state.getNextRowIndex() + 1);
        
        // Guardar en BD usando el idTramite guardado en el estado
        Long idTramite = state.getIdTramite();
        
        
        if (idTramite != null && idComponente != null) {
            try {
                formularioFacade.guardarFilaTabla(idTramite, idComponente, nuevaFila);
                
                WebResources.addSuccessMessage("msj_registro_guardado_exitoso", false);
            } catch (Exception e) {
                LOGGER.error("Error al guardar fila en BD para componente {}", idComponente, e);
                WebResources.addErrorMessage("Error al guardar: " + e.getMessage(), false);
            }
        } else {
            LOGGER.error("NO se puede guardar - idTramite: {}, idComponente: {}", idTramite, idComponente);
            WebResources.addErrorMessage("Error: No se pudo identificar el trámite o componente", false);
        }
        
        // Limpiar datos temporales
        getNuevaFilaDatos(idComponente).clear();
        setMensajeDeValidaciones(idComponente, null);
    }
    
    public void eliminarFila(Long idComponente) {
        
        DynamicTableRowDTO filaSeleccionada = getFilaSeleccionada(idComponente);
        if (filaSeleccionada != null) {
            DynamicTableStateDTO state = getState(idComponente);
            if (state != null && state.getIdTramite() != null) {
                formularioFacade.eliminarFilaTabla(state.getIdTramite(), idComponente, filaSeleccionada.getNumeroFila());
                state.getFilas().removeIf(f -> f.getUuid().equals(filaSeleccionada.getUuid()));
            }
            setFilaSeleccionada(idComponente, null);
            WebResources.addSuccessMessage("msj_registro_eliminado_exitoso", false);
        }
    }
    
    public void guardarEdicionFila(Long idComponente) {
       
        DynamicTableRowDTO filaSeleccionada = getFilaSeleccionada(idComponente);
        
        if (filaSeleccionada == null) {
            WebResources.addValidationMessage("msj_no_registro_modificar", false);
            return;
        }
        
        if (camposObligatoriosFaltantes(idComponente, filaSeleccionada.getDatos())) {
        	FacesContext.getCurrentInstance().validationFailed();
            return;
        }
        
        DynamicTableStateDTO state = getState(idComponente);
        if (state == null || state.getIdTramite() == null) {
            WebResources.addErrorMessage("msg_inicializacion_tramite", false);
            return;
        }
        
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            for (DynamicColumnConfigDTO columna : getColumnas(idComponente)) {
                Object valor = filaSeleccionada.getDatos().get(columna.getNombre());
                if ("FECHA".equals(columna.getTipo())) {
                    if (valor instanceof java.util.Date) {
                        filaSeleccionada.getDatos().put(columna.getNombre(), sdf.format((java.util.Date) valor));
                    } else if (valor instanceof java.time.LocalDate) {
                        filaSeleccionada.getDatos().put(columna.getNombre(), ((java.time.LocalDate) valor).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    }
                } else if ("NUMERICO".equals(columna.getTipo())) {
                    if (valor instanceof Double) {
                        filaSeleccionada.getDatos().put(columna.getNombre(), String.valueOf(((Double) valor).intValue()));
                    } else if (valor instanceof Integer) {
                        filaSeleccionada.getDatos().put(columna.getNombre(), String.valueOf(valor));
                    }
                }
            }
            formularioFacade.actualizarFilaTabla(state.getIdTramite(), idComponente, filaSeleccionada);
            WebResources.addSuccessMessage("msj_registro_modificado_exitoso", false);
            setMensajeDeValidaciones(idComponente, null);
            setFilaSeleccionada(idComponente, null);
        } catch (Exception e) {
            WebResources.errorMessage("msg_error_edit_registro", false);
            LOGGER.error("Error al actualizar fila en BD", e);
        }
    }
    
    public boolean verAcciones() {
    	
    	if (tramiteActualDTO == null) {
    		return false;
    	}
    	
    	boolean verColumAccion=false;
    	List<Integer> estatusValidos = Arrays.asList(Constantes.ID_ESTATUS_EN_CAPTURA,Constantes.ID_ESTATUS_CORRECIONES);
    	IntPredicate prEstatusVal = estatusValidos::contains;
    	Predicate<UsuarioDTO> prUsuarioPro = p -> p.getIdUsuarioLlaveCdmx() == authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx();
    	if(BeanUtils.isNotNull(authenticatorBean.getUsuarioLogueado()) 
    			&& prUsuarioPro.test(this.tramiteActualDTO.getUsuario()) 
    			&& prEstatusVal.test(this.tramiteActualDTO.getCatEstatusTramiteDTO().getIdEstatusTramite())) {
    		verColumAccion = true;
    	}
    	return verColumAccion;
    }
    
    // Métodos auxiliares
    public String getYearRange() { return "1900:2100"; }
    
    public java.math.BigDecimal generarMaxValue(Integer longitudMaxima) {
        if (longitudMaxima == null || longitudMaxima <= 0) {
            return new java.math.BigDecimal("999999999999999999");
        }
        StringBuilder max = new StringBuilder();
        for (int i = 0; i < longitudMaxima; i++) max.append("9");
        return new java.math.BigDecimal(max.toString());
    }
    
    public boolean esTooltipValido(DynamicColumnConfigDTO columna) {
        return columna.getTooltip() != null && !columna.getTooltip().trim().isEmpty();
    }
    
	public TramiteDTO getTramiteActualDTO() {
		return tramiteActualDTO;
	}

	public void setTramiteActualDTO(TramiteDTO tramiteActualDTO) {
		this.tramiteActualDTO = tramiteActualDTO;
	}
}