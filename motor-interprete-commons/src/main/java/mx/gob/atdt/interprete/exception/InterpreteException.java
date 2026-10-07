package mx.gob.atdt.interprete.exception;

/**
 * Clase que implemente una excepción de negocio para las validaciones del intérprete.
 * 
 * @author karlos
 *
 */
public class InterpreteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5104003398626611809L;

	public InterpreteException(final String msj) {
		super(msj);
	}
	
}