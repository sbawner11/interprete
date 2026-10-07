package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CadenasDigitalesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6716792192827293717L;
	private String cadena;
	private String selloDigitalFirma;
	private String selloDigitalTimestamp;
	
	public CadenasDigitalesDTO() {

	}

	/**
	 * @return the cadena
	 */
	public String getCadena() {
		return cadena;
	}

	/**
	 * @param cadena the cadena to set
	 */
	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

	/**
	 * @return the selloDigitalFirma
	 */
	public String getSelloDigitalFirma() {
		return selloDigitalFirma;
	}

	/**
	 * @param selloDigitalFirma the selloDigitalFirma to set
	 */
	public void setSelloDigitalFirma(String selloDigitalFirma) {
		this.selloDigitalFirma = selloDigitalFirma;
	}

	/**
	 * @return the selloDigitalTimestamp
	 */
	public String getSelloDigitalTimestamp() {
		return selloDigitalTimestamp;
	}

	/**
	 * @param selloDigitalTimestamp the selloDigitalTimestamp to set
	 */
	public void setSelloDigitalTimestamp(String selloDigitalTimestamp) {
		this.selloDigitalTimestamp = selloDigitalTimestamp;
	}	

	@Override
	public String toString() {
		return "CadenasDigitalesDTO [cadena=" + cadena + ", selloDigitalFirma=" + selloDigitalFirma
				+ ", selloDigitalTimestamp=" + selloDigitalTimestamp + "]";
	}
	
}
