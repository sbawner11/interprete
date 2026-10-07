package mx.gob.atdt.interprete.util;

import java.io.Serializable;
import java.util.function.Supplier;
/**
 * Funcional inteface extiende de Serializable 
 * para seguir con el patron serializable
 * Se utiliza para poder aplicar la carga mediante lazyLoad
 * @param <T>
 * @author Ramiro Luna Torres
 */
@FunctionalInterface
public interface SupplierLazyProvider<T> extends Supplier<T>, Serializable {

}
