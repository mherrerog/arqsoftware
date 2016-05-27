package datos;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

import gateway.UsuarioDAO;
import socialnetwork.Fechas;

public class Mensaje {
	
	private int idMensaje;
	private int emisor;
	private int receptor;
	private String fecha;
	private String hora;
	private String cuerpo;
	private int leido;
	private final String PERFIL = "https://image.freepik.com/iconos-gratis/perfil-macho-sombra-de-usuario_318-40244.png";
	
	/**
	 * Metodo constructor
	 * @param emisor identificador del emisor del mensaje
	 * @param receptor identificador del receptor del mensaje
	 * @param fecha momento de envio del mensaje
	 * @param hora momento de envio del mensaje
	 * @param cuerpo contenido del mensaje
	 * @param leido es 1 si el mensaje ha sido leído, 0 en caso contrario
	 */
	public Mensaje(int emisor, int receptor, String fecha, String hora, String cuerpo, int leido) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.fecha = fecha;
		this.hora = hora;
		this.cuerpo = cuerpo;
		this.leido = leido;
	}
	
	/**
	 * Metodo constructor
	 * @paam id identificador del mensaje
	 * @param emisor identificador del emisor del mensaje
	 * @param receptor identificador del receptor del mensaje
	 * @param fecha momento de envio del mensaje
	 * @param hora momento de envio del mensaje
	 * @param cuerpo contenido del mensaje
	 * @param leido es 1 si el mensaje ha sido leído, 0 en caso contrario
	 */
	public Mensaje(int id, int emisor, int receptor, String fecha, String hora, String cuerpo, int leido) {
		this.idMensaje = id;
		this.emisor = emisor;
		this.receptor = receptor;
		this.fecha = fecha;
		this.hora = hora;
		this.cuerpo = cuerpo;
		this.leido = leido;
	}

	/**
	 * Devuelve 1 si el mensaje ha sido leido, 
	 * 0 en caso contrario
	 * @return valor de leido
	 */
	public int getLeido() {
		return leido;
	}

	/**
	 * Devuelve el emisor del mensaje
	 * @return identificador del emisor del mensaje
	 */
	public int getEmisor() {
		return emisor;
	}

	/**
	 * Devuelve el receptor del mensaje
	 * @return identificador del receptor del mensaje
	 */
	public int getReceptor() {
		return receptor;
	}

	/**
	 * Devuelve la fecha del mensaje
	 * @return fecha del mensaje en formato aaaammdd
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Devuelve la hora del mensaje
	 * @return hora del mensaje en formato hhmm
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * Devuelve el cuerpo del mensaje
	 * @return cuerpo del mensaje en formato hhmm
	 */
	public String getCuerpo() {
		return cuerpo;
	}

	/**
	 * Devuelve la cadena que representa dicho objeto en JSON
	 * @return cadena en formato JSON del mensaje
	 */
	public String toJSON() {
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
		obj.put("id_mensj", idMensaje);
		obj.put("emisor", user.getNombre());
		obj.put("emisor_nick", user.getNick());
		if (user.getLogo() != null){
			obj.put("logo", user.getLogo());
		}else{
			obj.put("logo", PERFIL);
		}
		obj.put("id", user.getId());
		obj.put("receptor", receptor);
		obj.put("hora", horatoweb);
		obj.put("cuerpo", cuerpo);
		obj.put("leido", leido);
		
		
		return obj.toString();
	}

	
	/**
	 * Devuelve un vector de mensajes en formato JSON
	 * @param vector mensajes a parsear
	 * @return vector de mensajes en formato JSON
	 */
	public static String toJSON(ArrayList<Mensaje> vector) {
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
}
