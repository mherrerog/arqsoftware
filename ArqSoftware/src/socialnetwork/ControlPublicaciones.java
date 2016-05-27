package socialnetwork;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import datos.Comentario;
import datos.Publicacion;
import datos.Usuario;
import gateway.PublicacionDAO;
import gateway.UsuarioDAO;

/**
 * Clase correspondiente a la capa de logica de aplicacion, concretamente esta clase
 * tiene el objetivo de soportar la logica asociada a las publicaciones.
 * 
 * @author Grupo 1 - Arquitectura Software. Universidad de Zaragoza.
 *
 */
public class ControlPublicaciones {
	
	/**
	 * Metodo encargado de enviar una peticion a la base de datos para registar
	 * que el usuario identificado por el id: usuario, le ha dado al boton "me gusta"
	 * de la publicacion identificada por el id: publicacion.
	 * 
	 * @param usuario	Entero correspondiente al identificador del usuario.
	 * @param publicacion	Entero correspondiente al identificador de la publicacion.
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
	 * Metodo encargado de enviar una peticion a la base de datos para insertar 
	 * una nueva publicacion en la base de datos. Los atributos asociados 
	 * a esta nueva publicacion son los reflejados a continuacion.
	 * 
	 * @param autor		Entero correspondiente al identificador del usuario.
	 * @param texto		String correspondiente al texto que compone el cuerpo de
	 * la publicacion.
	 * @param fecha		String correspondiente a la fecha en la que se realizo la
	 * publicacion.
	 * @param imagen	String correspondiente a una URL valida de una imagen.
	 * @param video		String correspondiente a una URL valida de un video de youtube.
	 * @param ruta		String correspondiente al nombre del archivo .gpx que corresponde
	 * a una ruta.
	 * @param deporte	String correspondiente a al deporte que esta asociado a la
	 * publicacion.
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
	 * Crea una nueva publicación en la base de datos,
	 * almacena el link según su contenido
	 */
	
	public static void nuevaPub(int autor, String texto, Date fecha,
			String link, String deporte){
		
		// Url de youtube ej: https://www.youtube.com/watch?v=F9ZS49zGqIg
		// almacenar solo F9ZS49zGqIg		
		String youtube = null;
		String ruta = null;
		String img = null;
		
		if (link == null){
			return;
		}
		
		if (link.contains("youtube.com/watch?v=")){
			// Link youtube
			String [] url = link.split("v=");
			if (url.length == 2){
				youtube = url[1];
			} else {
				System.out.println("URL introducida de forma incorrecta (YouTube)");
				youtube = null;
			}
		} else if (link.contains(".gpx") || link.contains(".GPX")){
			ruta = link;
		} else if (link.contains(".png") || link.contains(".PNG")){
			// Imagen PNG
			img = link;
		} else if (link.contains(".gif") || link.contains(".GIF")){
			// Imagen GIF
			img = link;
		} else if (link.contains(".jpeg") || link.contains(".JPEG")){
			// Imagen JPEG
			img = link;
		} else if (link.contains(".jpg") || link.contains(".JPG")){
			// Imagen JPG
			img = link;
		} else {
			System.out.println("Formato desconocido");
		}
		
		Publicacion nueva = new Publicacion(autor, texto, fecha, img, youtube, 
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
	 * @throws NamingException 
	 */
	public static String showHome(int usuario) throws SQLException, NamingException{
		ArrayList<Publicacion> pubs = PublicacionDAO.selectForHome(usuario);
		String publicaciones = Publicacion.toJSON(pubs);
		System.out.println(publicaciones);
		
		return publicaciones;
	}
	
	/**
	 * Devuelve en formato JSON las publicaciones para el perfil de un usuario
	 * @throws NamingException 
	 */
	public static String showProfile(int actual, int usuario) throws SQLException, NamingException{
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
	 * @throws NamingException 
	 */
	public static String getComentarios(int publicacion) throws NamingException{
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
	 * Inserta un like a una publicacion
	 */
	public static void insertLike(int autor, int publicacion){
		try {
			PublicacionDAO.insertLike(autor, publicacion);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Elimina la publicacion cuyo id es pasado como parametro
	 */
	public static void borrarPublicacion(int publicacion){
		try {
			PublicacionDAO.delete(publicacion);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * El usuario user comparte la publicacion cuyo id es pasado como parametro
	 */
	public static void borrarPublicacion(int user, int publicacion){
		try {
			PublicacionDAO.insertShare(user, publicacion);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
