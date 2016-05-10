package socialnetwork;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import datos.Comentario;
import datos.Publicacion;
import datos.Usuario;
import gateway.PublicacionDAO;
import gateway.UsuarioDAO;

public class ControlPublicaciones {
	
	/**
	 * Añade como me gusta la publicacion al usuario indicado
	 */
	public static void meGusta(int usuario, int publicacion){
		try {
			PublicacionDAO.insertLike(usuario, publicacion);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Crea una nueva publicación en la base de datos
	 */
	public static void nuevaPub(int autor, String texto, Date fecha,
			String imagen, String video, String ruta, String deporte){
		
		// Url de youtube ej: https://www.youtube.com/watch?v=F9ZS49zGqIg
		// almacenar solo F9ZS49zGqIg
		String youtube = "";		
		if (video != null){
			String [] url = video.split("v=");
			if (url.length == 2){
				youtube = url[1];
			} else {
				System.out.println("URL introducida de forma incorrecta (YouTube)");
				youtube = null;
			}
		}
		
		Publicacion nueva = new Publicacion(autor, texto, fecha, imagen, youtube, 
				ruta, deporte);
		//Insertar publicación
		try {
			PublicacionDAO.insert(nueva);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(nueva);
		
		
	}
	
	/**
	 * Devuelve en formato JSON las publicaciones para el home de un usuario
	 */
	public static String showHome(int usuario) throws SQLException{
		ArrayList<Publicacion> pubs = PublicacionDAO.selectForHome(usuario);
		String publicaciones = Publicacion.toJSON(pubs);
		System.out.println(publicaciones);
		
		return publicaciones;
	}
	
	/**
	 * Devuelve en formato JSON las publicaciones para el perfil de un usuario
	 */
	public static String showProfile(int actual, int usuario) throws SQLException{
		ArrayList<Publicacion> pubs = PublicacionDAO.selectForProfile(usuario);
		String publicaciones = Publicacion.toJSON(pubs);
		publicaciones=publicaciones.substring(1);
		Usuario u = UsuarioDAO.selectById(usuario);
		// Perfil de usuario -> equipo?
		boolean equipo = (u.getEquipo() > 0);
		String usuarioJSON = u.toJSON(actual);
		
		String respuesta = "";
		
		if (equipo){
			String miembros = ControlUsuarios.buscarMiembros(usuario);
			miembros = miembros.substring(12, miembros.length()-1);
			// Equipo
			respuesta = "{\"usuario\": ["
					+ usuarioJSON + "], \"jugadores\":" + miembros + ", "
					+ publicaciones;
		} else {
			// No equipo
			respuesta = "{\"usuario\": ["
					+ usuarioJSON + "], " + publicaciones +
					"";
		}		
		
		return respuesta;
	}
	
	/**
	 * Devuelve en formato JSON los comentarios asociados a una publicación
	 */
	public static String getComentarios(int publicacion){
		String result = "";
		try {
			ArrayList<Comentario> comentarios = PublicacionDAO.selectComentarios(publicacion);
			result = Comentario.toJSON(comentarios);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Inserta un comentario en un publicacion
	 */
	public static void insertComentario(int autor, int publicacion, String texto){
		Date f = new Date();
		Comentario comentario = new Comentario(autor, publicacion, f, texto);
		try {
			PublicacionDAO.insertComentario(comentario);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Formato fecha: aaaammdd Formato hora: hhmm
	 */
	public static Date toDate(String fecha, String hora) {
		Date nueva = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmyyyyMMdd");
		try {
			nueva = sdf.parse(hora+fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nueva;
	}

}
