package mx.gob.atdt.interprete.util;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.util.SerializableSupplier;

/**
 * Clase generica para aplicar carga tipo LazyDataModel
 * un tipo de carga a solicitud de acuerdo con el paginado
 * @param <T>
 * @author Ramiro Luna Torres
 */
public class GenericLazyDataModel<T> extends LazyDataModel<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5146759891902140187L;
	
	private final LazyDataModelProvider<T> dataProvider;
	private final SerializableSupplier<Integer> countProvider;

	 
	/**
	 * Constructor que recibe las funciones de carga y conteo
	 * @param dataProvider
	 * @param countProvider
	 */
	public GenericLazyDataModel(LazyDataModelProvider<T> dataProvider, SerializableSupplier<Integer> countProvider) {
		this.dataProvider = dataProvider;
		this.countProvider = countProvider;
	}

	/**
	 *     
	 */
	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return countProvider.get();
	}
	
	/**
	 * 
	 */
	@Override
	public List<T> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		return dataProvider.load(first, pageSize, sortBy, filterBy);
	}
}
