package mx.gob.atdt.interprete.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;

import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.ArchivosRespuestaTokenDTO;
import mx.gob.atdt.interprete.dto.CatTipoPlantillaDTO;

public final class BeanUtils {

    private BeanUtils() {
    }

    public static boolean isNull(final Object object) {
        return object == null;
    }

    public static boolean isNotNull(final Object object) {
        return !isNull(object);
    }

    public static boolean isFalse(boolean expresion) {
        return !expresion;
    }

    public static boolean isEmpty(String string) {
        return isNull(string) || string.isEmpty();
    }

    public static boolean isNotEmpty(String string) {
        return isNotNull(string) && !string.isEmpty();
    }

    public static boolean isEmpty(List<?> list) {
        boolean output = true;
        if (isNotNull(list)) {
            output = list.isEmpty();
        }
        return output;
    }

    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }

    public static boolean isEmpty(Set<?> set) {
        boolean output = true;
        if (isNotNull(set)) {
            output = set.isEmpty();
        }
        return output;
    }
    
    public static boolean isEmptyOtro(List<?> list) {
        boolean output = false;
        if (isNotNull(list)) {
            output = list.isEmpty();
        }
        return output;
    }

    public static boolean isNotEmpty(Set<?> set) {
        return !isEmpty(set);
    }

    public static Date copyDate(Date date) {
        if (date == Constantes.OBJETO_NULO) {
            return null;
        } else {
            return new Date(date.getTime());
        }
    }

    public static <T> T[] copyArray(T[] array) {
        if (array == Constantes.OBJETO_NULO) {
            return null;
        } else {
            return Arrays.copyOf(array, array.length);
        }
    }

    public static boolean isDiferent(Integer number, int value) {
        if (number == Constantes.OBJETO_NULO) {
            return false;
        }
        return number.intValue() != value;
    }

    public static boolean isDiferent(Long number, int value) {
        if (number == Constantes.OBJETO_NULO) {
            return false;
        }
        return number.longValue() != value;
    }

    public static boolean isEquals(Integer number, int value) {
        if (number == Constantes.OBJETO_NULO) {
            return false;
        }
        return number.intValue() == value;
    }

    public static Integer getInteger(Object number) {
        if (number == Constantes.OBJETO_NULO || !(number instanceof Number)) {
            return null;
        }
        return ((Number)number).intValue();
    }
    
    public static Long getLong(Object number) {
        if (number == Constantes.OBJETO_NULO || !(number instanceof Number)) {
            return null;
        }
        return ((Number)number).longValue();
    }
    
    /**
     * Función que convierte un numero double a int
     */
    public static int toInt(double numero){
        return (int)numero;
    }
    
    /**
     * Función que evalua una condición ternaria 
     *
     */
    
    public static <T> T evaluacionTernaria(boolean expresion, T valueTrue, T valueFalse) {
        if (expresion) {
            return valueTrue;
        } else {
            return valueFalse;
        }
    }
    
    /**
     * Función que convierte un Date a String con formato YYYY-MM-dd HH:mm:ss.SSS
     *
     */
    
    public static String convertirDateString(final Date fecha) {
        final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        return sdf.format(fecha);
    }
    
    /**
     * Función que convierte un Date a String con formato YYYY-MM-dd HH:mm:ss
     *
     */
    public static String convertirDateStringTimestamp(final Date fecha) {
        final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(fecha);
    }
    
    /**
     * Función que convierte un Date a String con formato yyyy-MM-dd
     *
     */
    
    public static String convertirDateStringAnioMesDia(final Date fecha) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(fecha);
    }
    
    /**
	 * Función que convierte un Date a String con formato ddMMyyyy
	 *
	 */
	public static String convertirDateStringFirmado(final Date fecha) {
		final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
		return sdf.format(fecha);
	}
	
	/**
     * Función que convierte un Date a String con formato dd/MM/yyyy hh:mm:ss
     *
     */    
    public static String convertirDateStringDiaMesAnioHora(final Date fecha) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
        return sdf.format(fecha);
    }
    
    /**
     * Función que convierte un Date a String con formato dd/MM/yyyy
     *
     */    
    public static String convertirDateStringDiaMesAnio(final Date fecha) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(fecha);
    }
    
    public static String agregarCerosTotalOcho(String numero) {
    	int totalDigitos = 8;
    	String ceros = "";
    	for(int i = numero.length(); i < totalDigitos; i++ ) {
    		ceros += "" + 0;
    	}
    	return ceros + numero;
    }
    
    public static String agregarConsecutivoX() {
    	int totalDigitos = 8;
    	String temporalFlo = "";
    	for(int i = 0; i < totalDigitos; i++ ) {
    		temporalFlo += "X";
    	}
    	return temporalFlo;
    }

    public static String agregarCerosTotalDiez(String numero) {
    	int totalDigitos = 10;
    	String ceros = "";
    	for(int i = numero.length(); i < totalDigitos; i++ ) {
    		ceros += "" + 0;
    	}
    	return ceros + numero;
    }
    
    public static String cambiarXporConsecutivo(String folioConX, String consecutivo) {
    	return folioConX.replace("XXXXXXXX", consecutivo);
    }
    
    public static String capitalizeFirstLetter(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1).toLowerCase();
	}
    
    public static Date sumarAniosFecha(Date fecha, int anios) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.YEAR, anios);
		return calendar.getTime();
	}
    
    public static Date sumarDiasFecha(Date fecha, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_MONTH, dias);
		return calendar.getTime();
	}
    
    public static Date asignarTiempoAFecha(Date fecha, int hora, int minuto, int segungo) {
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		c.set(Calendar.HOUR_OF_DAY, hora);
		c.set(Calendar.MINUTE, minuto);
		c.set(Calendar.SECOND, segungo);
		return c.getTime();
    }
    
    /**
	 * Método auxiliar para encriptar una cadena a Base64
	 * 
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encriptarPassword(String s) throws UnsupportedEncodingException {
		return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));
	}

	/**
	 * Método auxiliar para desencriptar cadena en Base64
	 * 
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String desencriptarPassword(String s) throws UnsupportedEncodingException {
		byte[] decode = Base64.getDecoder().decode(s.getBytes());
		return new String(decode, "utf-8");		
	}
	
	/**
     * Método que genera un folio consecutivo del trámite con la longitud definida
     * @param numero
     * @return
     */
    public static String generarFolioTramiteConsecutivo(String consecutivo) {
    	int totalDigitos = 7;
    	String folio = "";
    	for(int i = consecutivo.length(); i < totalDigitos; i++ ) {
    		folio += "" + 0;
    	}
    	return folio + consecutivo;
    }

    /**
     * Metodo auxiliar para validaciones nulas en cadenas
     * 
     * @param obj
     * @param vdefault
     * @return
     */
    public static String getStringOrDefault(Object obj, String vdefault) {
	    String valor= null;
    	if (obj != Constantes.OBJETO_NULO) {
	        valor = String.valueOf(obj);
	    }else if(vdefault != null) {
	    	valor = vdefault;
	    }
	    return valor;
	}
    
    /***
     * Metodo auxiliar para validar posibles fechas nulas
     * @param dates
     * @return
     */
    public static Date getDate(Object dates) {
        if (dates == Constantes.OBJETO_NULO || !(dates instanceof Date)) {
            return null;
        }
        return (Date)dates;
    }
    
    /**
     * Metodo auxiliar para convertir un String en fecha (Date).
     * @param fecha Cadena de fecha dd/MM/yyyy HH:mm
     * @return Objeto Date
     */
    public static Date convertirStringDate(String fecha){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } catch (ParseException ex){
        	ex.printStackTrace();
        }
        return fechaDate;
    }
    
    /**
     * Funcion para convertir una fecha (Date).
     * @param fecha Cadena de fecha EEEE d 'de' MMMM 'de' yyyy
     * @return Objeto Date
     */
    public static String convertirStringDateMx(final Date fecha){
        final SimpleDateFormat formatoEsMX = new SimpleDateFormat(
              "EEEE d 'de' MMMM 'de' yyyy", new Locale("ES", "MX"));       
        return formatoEsMX.format(fecha);
    }
    /**
     * Metodo para validar el hablilitar bandera firmado de tramites en el proyecto
     * @param lstRespuesta
     * @return
     */
	public static boolean habilitaFirmadoTramites(final List<ArchivosRespuestaTokenDTO> lstRespuesta){
    	List<Integer> lisPlantillas = Arrays.asList(Constantes.INT_PLANTILLA_FIRMADO_ACEPTADO, 
    									Constantes.INT_PLANTILLA_REGISTRO_CONCLUIDO, 
    									Constantes.INT_PLANTILLA_FIRMADO_RECHAZO);
    	Predicate<CatTipoPlantillaDTO> intPrFirmado =  p -> lisPlantillas.contains(p.getIdTipoPlantilla());					
    	return lstRespuesta.stream()
							.anyMatch(pr -> intPrFirmado.test(pr.getCatTipoPlantillaDTO()) && pr.isHabilitaFirma());
    }
	
	/**
	 * Metodo para convetir Date a LocalDateTime
	 * @param fecha
	 * @return
	 */
	public static LocalDateTime convertirDateToLocalDateTime(Date fecha) {
		return isNotNull(fecha)?fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime():null;
	}
	
	/**
	 * Metodo generico para la generacion de archivo csv
	 * escribe archivo usando UTF8
	 * 
	 * @param rutaArchivo
	 * @param nombreArchivo
	 * @param encabezado
	 * @param lstDatos
	 * @throws IOException
	 * @author Ramiro Luna Torres
	 */
	public static void generarCsvDinamico(String rutaArchivo, String nombreArchivo, String[] encabezado, List<StringBuilder> lstDatos) throws IOException {
		String csvSeparator = ",";
		List<String> lineasCsv = new ArrayList<>();		
		String encabezadoCsv = String.join(csvSeparator, encabezado);
		lineasCsv.add(encabezadoCsv);
		for (StringBuilder cadena : lstDatos) {
        	String[] arrayDatos = cadena.toString().split(Constantes.SEPARACION_COLUMNA_CSV);
        	StringBuilder fila = new StringBuilder();
        	for(String dato: arrayDatos) {
        		 fila.append(escaparCSV(dato)).append(csvSeparator);
        	}
        	lineasCsv.add(fila.toString());
        }
		//ruta carpeta
		Path rutaCarpeta = Paths.get(rutaArchivo);
		//path ruta + archivo
		Path pathArchivo = rutaCarpeta.resolve(nombreArchivo);
		Files.createDirectories(rutaCarpeta);
		
	    //agregamos los bytes para reconcimiento de BOOM al abrir archivo
	    byte[] bytesConBom = { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF};
	    Files.write(pathArchivo, bytesConBom, StandardOpenOption.CREATE);
	    Files.write(pathArchivo, lineasCsv, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
	}
    
	/**
	 * Metodo auxiliar para detectar caracteres especiales que pueden
	 * generar saltos de linea en generacion de archivo csv
	 * 
	 * @param valor
	 * @return cadena limpia
	 * @author Ramiro Luna Torres
	 */
	private static String escaparCSV(String valor) {
        if (valor == null) {
            return "";
        }
        if (valor.contains(",") || valor.contains("\"")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }
    
    
//    public static void main(String args[]) {
//    	String x = "ABCD-878-969-XXXXXXXX";
//    	String ceros = "00000008";
//    	
//    	x.replace(ceros,"XXXXXXXX");
//    	System.out.println(x.replace("XXXXXXXX", ceros));
//    }
}
