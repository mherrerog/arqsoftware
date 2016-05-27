package datos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import gateway.UsuarioDAO;
import socialnetwork.Fechas;

public class Comentario {
	
	private int id;
	private int autor;
	private int publicacion;
	private Date fecha;
	private String texto;
	private final String PERFIL = "https://image.freepik.com/iconos-gratis/perfil-macho-sombra-de-usuario_318-40244.png";	
	
	/**
	 * Metodo constructor
	 * @param id identificador del comentario
	 * @param autor identificador del autor
	 * @param publicacion identificador de la publicacion
	 * @param fecha momento de creacion del comentario
	 * @param texto contenido del comentario
	 */
	public Comentario(int id, int autor, int publicacion, Date fecha, String texto) {
		this.id = id;
		this.autor = autor;
		this.publicacion = publicacion;
		this.fecha = fecha;
		this.texto = texto;
	}
	
	/**
	 * Metodo constructor
	 * @param autor identificador del autor
	 * @param publicacion identificador de la publicacion
	 * @param fecha momento de creacion del comentario
	 * @param texto contenido del comentario
	 */
	public Comentario(int autor, int publicacion, Date fecha, String texto) {
		this.autor = autor;
		this.publicacion = publicacion;
		this.fecha = fecha;
		this.texto = texto;
	}
	
	/**
	 * Pasa a formato JSON el comentario
	 * @return comentario en formato JSON
	 * @throws SQLException error al ejecutar una sentencia
	 */
	public String toJSON() throws SQLException{
			
		Usuario usuario = UsuarioDAO.selectById(autor);
		
		String f = Fechas.getFechaToWeb(fecha);
		
		JSONObject obj = new JSONObject();
		
		// Atributos de publicacion
		obj.put("Id", id);
		obj.put("idUsuario", autor);
		obj.put("nombre", usuario.getNombre());
		obj.put("nick", usuario.getNick());
		if (usuario.getLogo() != null){
			obj.put("logo", usuario.getLogo());
		}else{
			obj.put("logo", PERFIL);
		}
		obj.put("fecha", f);
		obj.put("texto", texto);
		
		return obj.toString();
	}
	
	/**
	 * Crea un vector de comentarios en formato JSON
	 * @param vector comentarios a parsear
	 * @return devuelve el vector dado en formato JSON
	 * @throws SQLException error al ejecutar una sentencia
	 */
	public static String toJSON(ArrayList<Comentario> vector) throws SQLException{
		
		String rs = "{\"comentarios\": [\n";
		for (Comentario d: vector){
			
			rs += d.toJSON() + ",\n";
		}
		
		// Consulta vacia?
		if (!vector.isEmpty()){
			int end = rs.lastIndexOf(',');
			rs = rs.substring(0, end);	// Elimina la última coma puesta
		}
		
		return rs + "\n]}";
	}

	//Setters & Getters
	/**
	 * Devuelve el identificador del comentario
	 * @return devuelve el identificador del comentario
	 */
	public int getId() {
		return id;
	}

	/**
	 * Devuelve el identificador del autor
	 * @return devuelve el identificador del autor
	 */
	public int getAutor() {
		return autor;
	}

	/**
	 * Devuelve el identificador de la publicacion
	 * @return devuelve el identificador de la publicacion
	 */
	public int getPublicacion() {
		return publicacion;
	}

	/**
	 * Devuelve la fecha del comentario
	 * @return devuelve la fecha del comentario
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Devuelve el contenido del comentario
	 * @return devuelve el contenido del comentario
	 */
	public String getTexto() {
		return texto;
	}
	

}
