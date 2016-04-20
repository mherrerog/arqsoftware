package socialnetwork;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import datos.Publicacion;
import gateway.PublicacionDAO;

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
		
		Publicacion nueva = new Publicacion(autor, texto, fecha, imagen, video, 
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
	
	public static String showHome(int usuario) throws SQLException{
		ArrayList<Publicacion> pubs = PublicacionDAO.selectForHome(usuario);
		String publicaciones = Publicacion.toJSON(pubs);
		System.out.println(publicaciones);
		
		return publicaciones;
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
