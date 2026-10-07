package mx.gob.atdt.interprete.componentes.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.dto.DynamicColumnConfigDTO;
import mx.gob.atdt.interprete.dto.DynamicTableRowDTO;

@Named("dynamicTableStateful")
@ViewScoped
public class DynamicTableStatefulBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicTableStatefulBean.class);

    @Inject
    private DynamicTableBean dynamicTableBean;

    // Métodos que obtienen valores por componente
    public List<DynamicTableRowDTO> getFilas(ComponenteDTO componente) {
        return dynamicTableBean.getFilas(componente.getIdComponente());
    }

    public List<DynamicColumnConfigDTO> getColumnas(ComponenteDTO componente) {
        return dynamicTableBean.getColumnas(componente.getIdComponente());
    }

    public String getTitulo(ComponenteDTO componente) {
        return dynamicTableBean.getTitulo(componente.getIdComponente());
    }

    public int getMinRows(ComponenteDTO componente) {
        return dynamicTableBean.getMinRows(componente.getIdComponente());
    }

    public int getMaxRows(ComponenteDTO componente) {
        return dynamicTableBean.getMaxRows(componente.getIdComponente());
    }

    public int getPageSize(ComponenteDTO componente) {
        return dynamicTableBean.getPageSize(componente.getIdComponente());
    }

    public boolean isAllowAddingRows(ComponenteDTO componente) {
        return dynamicTableBean.isAllowAddingRows(componente.getIdComponente());
    }

    public boolean isTablaRequerida(ComponenteDTO componente) {
        return dynamicTableBean.isTablaRequerida(componente.getIdComponente());
    }

    public int getTotalFilasActivas(ComponenteDTO componente) {
        return dynamicTableBean.getTotalFilasActivas(componente.getIdComponente());
    }

    public int getTotalFilasLlenas(ComponenteDTO componente) {
        return dynamicTableBean.getTotalFilasLlenas(componente.getIdComponente());
    }

    public boolean isMinimoAlcanzado(ComponenteDTO componente) {
        return dynamicTableBean.isMinimoAlcanzado(componente.getIdComponente());
    }
   
    public Map<String, Object> getNuevaFilaDatos(ComponenteDTO componente) {
        return dynamicTableBean.getNuevaFilaDatos(componente.getIdComponente());
    }

    public DynamicTableRowDTO getFilaSeleccionada(ComponenteDTO componente) {
        return dynamicTableBean.getFilaSeleccionada(componente.getIdComponente());
    }

    public void setFilaSeleccionada(DynamicTableRowDTO fila, ComponenteDTO componente) {
        dynamicTableBean.setFilaSeleccionada(componente.getIdComponente(), fila);
    }

    public String getMensajeDeValidaciones(ComponenteDTO componente) {
        return dynamicTableBean.getMensajeDeValidaciones(componente.getIdComponente());
    }

    // Acciones
    public void prepararNuevaFila(ComponenteDTO componente) {
        dynamicTableBean.prepararNuevaFila(componente.getIdComponente());
    }

    public void cancelarAgregarFila(ComponenteDTO componente) {
        dynamicTableBean.cancelarAgregarFila(componente.getIdComponente());
    }

    public void agregarFila(ComponenteDTO componente) {
        dynamicTableBean.agregarFila(componente.getIdComponente());
    }

    public void eliminarFila(ComponenteDTO componente) {
        dynamicTableBean.eliminarFila(componente.getIdComponente());
    }

    public void guardarEdicionFila(ComponenteDTO componente) {
        dynamicTableBean.guardarEdicionFila(componente.getIdComponente());
    }

    public boolean verAcciones() {
        return dynamicTableBean.verAcciones();
    }

    public String getYearRange() { return dynamicTableBean.getYearRange(); }
    public java.math.BigDecimal generarMaxValue(Integer longitudMaxima) { return dynamicTableBean.generarMaxValue(longitudMaxima); }
    public boolean esTooltipValido(DynamicColumnConfigDTO columna, ComponenteDTO componente) { return dynamicTableBean.esTooltipValido(columna); }
}