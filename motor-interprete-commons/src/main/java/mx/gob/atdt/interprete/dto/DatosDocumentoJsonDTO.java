package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class DatosDocumentoJsonDTO implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -8580923699732105718L;
	
	private String ruta;
	private String nombre;
	 
	public DatosDocumentoJsonDTO(String ruta, String nombre) {
		super();
		this.ruta = ruta;
		this.nombre = nombre;
	}
	
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	 
	 

}
