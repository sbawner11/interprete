package mx.gob.atdt.interprete.commons.utils;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Carlos
 *
 */
public class Utils {
	
	
	
	/**
	 * Constructor por defecto de la clase
	 */
	private Utils() {
		/** Constructor vacío para que no se pueda instanciar la clase **/
	}

	
	public static CharSequence randomChars() {
		StringBuilder result = new StringBuilder(64);
		SecureRandom random = new SecureRandom();
		for (int i = 0, count = result.capacity(); i < count; ++i) {
			result.append(Constantes.STATE_CHARS.charAt(random.nextInt(Constantes.STATE_CHARS.length())));
		}
		return result.toString();
	}
	
	/**
	 * Método auxiliar que genera la cadena State para iniciar firmado de trámites
	 * @return
	 */
	public static CharSequence randomCharsStateFirma() {
		StringBuilder result = new StringBuilder(36);
		SecureRandom random = new SecureRandom();
		for (int i = 0, count = result.capacity(); i < count; ++i) {
			result.append(Constantes.STATE_CHARS_FIRMA.charAt(random.nextInt(Constantes.STATE_CHARS_FIRMA.length())));
		}
		return result.toString();
	}
	
	/**
	 * Funci\u00f3n que convierte un Date a String con formato ddMMyyyy
	 *
	 */
	public static String convertirDateStringFirmado(final Date fecha){
		final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
		return sdf.format(fecha);
	}
}
