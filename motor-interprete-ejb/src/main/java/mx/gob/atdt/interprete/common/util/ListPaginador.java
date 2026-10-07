package mx.gob.atdt.interprete.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que sirve para guardar el resultado de un query paginado y utilizarlo en una tabla (p:dataTable)
 * @author Raúl SG
 * @param <T> El tipo de objeto que alojará la lista
 */
@SuppressWarnings("serial")
public class ListPaginador<T> extends ArrayList<T> implements Serializable {
	
    private final List<T> rows;
    private final int total;
    private int pageSize;

	/**
     * Constructor de la Clase
     * @param rows La lista de objetos, es decir, los renglones a iterar en una tabla que contenga un paginador
     * @param total El total de registros que debe mostrar la tabla. Por ejemplo, si el total es 50 y el pageSize es 10, entonces el paginador de la tabla mostrará 5 hojas.
     * @param pageSize El tamaño de cada hoja del paginador, es decir, el múmero de renglones
     */
    public ListPaginador(List<T> rows, int total, int pageSize){
        this.pageSize = pageSize;
        this.rows = rows;
        this.total = total;
    }

	/**
     * Método sobreescrito del ArrayList que será llamado por un p:dataTable y que obtendrá el renglón de datos solicitado.
     */
    @Override
    public T get(int index) {
        index = index%pageSize;
        return rows.get(index);
    }

    /**
     *  Método sobreescrito del ArrayList que nos da el tamaño ficticio de la lista, es decir, el tamaño total de resultados de una consulta sin paginar.
     *  Esto sirve para decirle al usuario por ejemplo que se encontraron 1000 registros pero en sí la lista solo tiene 10 registros
     *   Si se desea obtener el tamaño real de esta lista ver el método sizeReal();
     */
    @Override
    public int size() {
        return total;
    }
    
    @Override
    public boolean isEmpty(){
    	return total == 0;
    }
    
    /**
     * Método que obtiene el tamaño real de la lista
     * @return
     */
    public int sizeReal(){
    	return rows.size();
    }

    /**
     * Método que devuelve la lista genérica que se pasó en el constructor.
     * @return
     */
    public List<T> getRows(){
    	return rows;
    }
}
