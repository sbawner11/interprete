package mx.gob.atdt.interprete.backoffice.bean;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.primefaces.PrimeFaces;
import mx.gob.atdt.interprete.util.WebResources;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.CatEstatusCargaMasivaDAO;
import mx.gob.atdt.interprete.dao.DetArchivosCargaMasivaDAO;
import mx.gob.atdt.interprete.dao.DetRegistrosCargaMasivaDAO;
import mx.gob.atdt.interprete.dto.CatEstatusCargaMasivaDTO;
import mx.gob.atdt.interprete.dto.DetArchivosCargaMasivaDTO;
import mx.gob.atdt.interprete.dto.DetRegistrosCargaMasivaDTO;


@Named("asignacionMasivaSolicitudesBean")
@ViewScoped
public class AsignacionMasivaSolicitudesBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(AsignacionMasivaSolicitudesBean.class);

    @Inject
    private DetArchivosCargaMasivaDAO archivosDAO;
    @Inject
    private CatEstatusCargaMasivaDAO estatusDAO;
    @Inject
    private DetRegistrosCargaMasivaDAO registrosDAO;
    private List<DetArchivosCargaMasivaDTO> listaCargas;  
    private Date fechaDesde;
    private Date fechaHasta;
    private Integer estatusFiltro;
    private String usuarioCargaFiltro;
    private String usuarioAsignadoFiltro;
    private List<CatEstatusCargaMasivaDTO> listaEstatusFiltro;

    
    @PostConstruct
    public void init() {
    	
        listaEstatusFiltro = estatusDAO.buscarTodos();
        if (listaEstatusFiltro == null) {
            listaEstatusFiltro = new ArrayList<>();
        }
        
        cargarLista();
        
        // Si hay cargas en proceso, iniciar el poll
        if (hayCargasEnProceso()) {
            PrimeFaces.current().executeScript("PF('pollActualizacion').start();");
        }
    }

    private void cargarLista() {
    	
        listaCargas = archivosDAO.buscarTodos();
        if (listaCargas == null) {
            listaCargas = new ArrayList<>();
        }
    }

    public void filtrar() {
        listaCargas = archivosDAO.buscarConFiltros(
            fechaDesde, fechaHasta, estatusFiltro, 
            usuarioCargaFiltro, usuarioAsignadoFiltro
        );
        if (listaCargas == null) {
            listaCargas = new ArrayList<>();
        }
    }


    public void limpiarFiltros() {
        fechaDesde = null;
        fechaHasta = null;
        estatusFiltro = null;
        usuarioCargaFiltro = null;
        usuarioAsignadoFiltro = null;
        cargarLista();
    }
    

    public String irAsignarSolicitudes() {
        return Constantes.URL_ASIGNAR_DISTRIBUCION + Constantes.JSF_REDIRECT;
    }

    
    public byte[] obtenerArchivoResultado(Long idArchivo) {
        try {
            List<DetRegistrosCargaMasivaDTO> registros = registrosDAO.buscarPorIdArchivo(idArchivo);
            if (registros == null || registros.isEmpty()) {
                LOGGER.warn("No hay registros para la carga ID: {}", idArchivo);
                return null;
            }

            StringBuilder csv = new StringBuilder();
            csv.append("\uFEFF");
            csv.append("ID_ELEMENTO,DESCRIPCION_ELEMENTO,RESULTADO\n");

            for (DetRegistrosCargaMasivaDTO registro : registros) {
                
            	String idElemento = registro.getIdElemento() != null ? 
                        registro.getIdElemento().toString() : "";
                String descripcion = escapeCsv(registro.getDescripcionElemento());
                String resultado = escapeCsv(registro.getResultado());
                
                csv.append(idElemento).append(",")
                   .append(descripcion).append(",")
                   .append(resultado).append("\n");
            }

            return csv.toString().getBytes(StandardCharsets.UTF_8);
            
        } catch (Exception e) {
            LOGGER.error("Error al generar archivo CSV para carga ID: {}", idArchivo, e);
            return null;
        }
    }

    /**
     * Escapa caracteres especiales para CSV
     */
    private String escapeCsv(String valor) {
        if (valor == null) {
            return "";
        }
        if (valor.contains("\"") || valor.contains(",") || valor.contains("\n") || valor.contains("\r")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }
    
    public void descargarResultado(DetArchivosCargaMasivaDTO carga) {
        try {
            if (carga == null || carga.getIdArchivoCargaMasiva() == null) {
                WebResources.validationMessage("descarga_sin_archivo");
                return;
            }

            byte[] contenido = obtenerArchivoResultado(carga.getIdArchivoCargaMasiva());
            
            if (contenido == null) {
                WebResources.validationMessage("descarga_archivo_no_disponible");
                return;
            }

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            
            response.setContentType("text/csv; charset=UTF-8");
            response.setHeader("Content-Disposition", 
                    "attachment; filename=\"" + carga.getNombreArchivoOrigen());
            response.setContentLength(contenido.length);
            // Cabeceras para evitar caché
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            response.getOutputStream().write(contenido);
            response.getOutputStream().flush();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception e) {
            LOGGER.error("Error al descargar archivo CSV para carga ID: {}", 
                    carga != null ? carga.getIdArchivoCargaMasiva() : "null", e);
            WebResources.errorMessage("descarga_error");
        }
    }
    
    /**
     * Recarga la lista de cargas desde la base de datos.
     * Si no hay cargas en proceso, detiene el poll.
     */
    public void recargarLista() {
        cargarLista();
        if (!hayCargasEnProceso()) {
            // Detener el poll automáticamente
            PrimeFaces.current().executeScript("PF('pollActualizacion').stop();");
        }
    }

    /**
     * Verifica si hay alguna carga con estatus "En proceso" (id=3).
     */
    private boolean hayCargasEnProceso() {
        if (listaCargas == null) return false;
        for (DetArchivosCargaMasivaDTO carga : listaCargas) {
            if (carga.getEstatusCargaDTO().getIdEstatusCarga() == Constantes.ID_ESTATUS_CARGA_MASIVA_EN_PROCESO) {
                return true;
            }
        }
        return false;
    }

    public List<DetArchivosCargaMasivaDTO> getListaCargas() {
        return listaCargas;
    }

    public void setListaCargas(List<DetArchivosCargaMasivaDTO> listaCargas) {
        this.listaCargas = listaCargas;
    }
    
    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Integer getEstatusFiltro() {
        return estatusFiltro;
    }

    public void setEstatusFiltro(Integer estatusFiltro) {
        this.estatusFiltro = estatusFiltro;
    }

    public List<CatEstatusCargaMasivaDTO> getListaEstatusFiltro() {
        return listaEstatusFiltro;
    }
    public String getUsuarioCargaFiltro() {
        return usuarioCargaFiltro;
    }

    public void setUsuarioCargaFiltro(String usuarioCargaFiltro) {
        this.usuarioCargaFiltro = usuarioCargaFiltro;
    }

    public String getUsuarioAsignadoFiltro() {
        return usuarioAsignadoFiltro;
    }

    public void setUsuarioAsignadoFiltro(String usuarioAsignadoFiltro) {
        this.usuarioAsignadoFiltro = usuarioAsignadoFiltro;
    }
}