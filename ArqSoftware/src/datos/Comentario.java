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
	
	/**
	 * Metodo constructor
	 */
	public Comentario(int id, int autor, int publicacion, Date fecha, String texto) {
		this.id = id;
		this.autor = autor;
		this.publicacion = publicacion;
		this.fecha = fecha;
		this.texto = texto;
	}
	
	/**
	 * 
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
		obj.put("logo", usuario.getLogo());
		obj.put("fecha", f);
		obj.put("texto", texto);
		
		return obj.toString();
	}
	
	/**
	 * Devuelve un String en formato JSON los comentarios
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
	public int getId() {
		return id;
	}

	public int getAutor() {
		return autor;
	}

	public int getPublicacion() {
		return publicacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getTexto() {
		return texto;
	}
	

}
