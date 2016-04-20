package datos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mensaje {
	
	private int emisor;
	private int receptor;
	private String fecha;
	private String hora;
	private String cuerpo;
	
	/**
	 * @param emisor
	 * @param receptor
	 * @param fecha
	 * @param hora
	 * @param cuerpo
	 */
	public Mensaje(int emisor, int receptor, String fecha, String hora, String cuerpo) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.fecha = fecha;
		this.hora = hora;
		this.cuerpo = cuerpo;
	}

	// Getters
	public int getEmisor() {
		return emisor;
	}

	public int getReceptor() {
		return receptor;
	}

	public String getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setEmisor(int emisor) {
		this.emisor = emisor;
	}

	public void setReceptor(int receptor) {
		this.receptor = receptor;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	/**
	 * Devuelve la cadena que representa dicho objeto en JSON
	 */
	public String toJSON(){
		String rs = "";
		
		return rs;
	}

	/**
	 * 
	 */
	public String toString(){
		Date d = toDate(fecha, hora);
		return "From: " + emisor + " --- Date: " + d + "\nTo:   " + receptor + "\n " + cuerpo;
	}
	
	/**
	 * Formato fecha: aaaammdd Formato hora: hhmm
	 */
	private static Date toDate(String fecha, String hora) {
		Date nueva = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmyyyyMMdd");
		try {
			nueva = sdf.parse(hora+fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nueva;
	}
}
