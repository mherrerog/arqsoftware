package datos;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import gateway.UsuarioDAO;
import socialnetwork.Fechas;

public class Mensaje {
	
	private int emisor;
	private int receptor;
	private String fecha;
	private String hora;
	private String cuerpo;
	private int leido;
	
	/**
	 * @param emisor
	 * @param receptor
	 * @param fecha
	 * @param hora
	 * @param cuerpo
	 */
	public Mensaje(int emisor, int receptor, String fecha, String hora, String cuerpo, int leido) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.fecha = fecha;
		this.hora = hora;
		this.cuerpo = cuerpo;
		this.leido = leido;
	}

	public int getLeido() {
		return leido;
	}

	public void setLeido(int leido) {
		this.leido = leido;
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
		JSONObject obj = new JSONObject();
		
		String horatoweb = Fechas.getFechaToWeb(Fechas.getFechaFromWeb(fecha,hora));
		
		Usuario user = null;
		try {
			user = UsuarioDAO.selectById(emisor);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Atributos de publicacion
		obj.put("id", user.getId());
		obj.put("emisor", user.getNombre());
		obj.put("emisor_nick", user.getNick());
		obj.put("receptor", receptor);
		obj.put("hora", horatoweb);
		obj.put("cuerpo", cuerpo);
		obj.put("leido", leido);
		
		
		return obj.toString();
	}

	
	/**
	 * Devuelve la cadena que representa dicho objeto en JSON
	 */
	public static String toJSON(ArrayList<Mensaje> vector){
		String rs = "{\"mensajes\": [\n";
		for (Mensaje d: vector){
			
			rs += d.toJSON() + ",\n";
		}
		
		// Consulta vacia?
		if (!vector.isEmpty()){
			int end = rs.lastIndexOf(',');
			rs = rs.substring(0, end);	// Elimina la última coma puesta
		}
		
		return rs + "\n]}";
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
