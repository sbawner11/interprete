package mx.gob.atdt.interprete.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
/**
 * Funcional inteface extiende de Serializable 
 * para seguir con el patron serializable
 * Se utiliza para poder aplicar la carga mediante lazyLoad
 * 
 * @param <T>
 * @author Ramiro Luna Torres
 */
@FunctionalInterface
public interface LazyDataModelProvider<T> extends Serializable{
	
	List<T> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy);
}
