package mx.gob.atdt.interprete.exception;

/**
 * Clase que implemente una excepción de negocio para las validaciones de los servicios 
 * del intérprete.
 * @author karlos
 *
 */
public class ServiciosException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6212192648926898906L;

	public ServiciosException(final String msj) {
		super(msj);
	}
	
}