package gateway;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import datos.Comentario;
import datos.Publicacion;
import socialnetwork.Fechas;

public class PublicacionDAO {

	/**
	 * Elimina una publicación en la BD
	 * @param idPublicacion identificador de la publicación
	 * @throws SQLException error al ejecutar la sentencia
	 */
	public static void delete(int idPublicacion) throws SQLException {
		String query = "DELETE FROM Publicacion WHERE " + "(idPublicacion = ?)";
		Connection conecta = null;
		//conecta = Connect.getDBConnection();
		try {
			conecta = Connect.getConnectionFromPool();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement preparedStmt = conecta.prepareStatement(query);
		preparedStmt.setInt(1, idPublicacion);

		// execute the preparedstatement
		preparedStmt.execute();
		// close connection
		conecta.close();
	}

	
	/**
	 * Inserta una publicación en la BD
	 * @param pub publicacion a insertar
	 * @throws SQLException error al ejecutar la sentencia
	 */
	public static void insert(Publicacion pub) throws SQLException {

		Connection conecta = null;
		//conecta = Connect.getDBConnection();
		try {
			conecta = Connect.getConnectionFromPool();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query = "INSERT INTO software.Publicacion "
				+ "(Fecha, Hora, Emisor, Texto, Imagen, Video, Ruta, Deporte) VALUES " + "( ?, ?, ?, ?, ?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conecta.prepareStatement(query);
		preparedStmt.setString(1, pub.getFechaString());
		preparedStmt.setString(2, pub.getHoraString());
		preparedStmt.setInt(3, pub.getAutor());
		preparedStmt.setString(4, pub.getTexto());
		preparedStmt.setString(5, pub.getImagen());
		preparedStmt.setString(6, pub.getVideo());
		preparedStmt.setString(7, pub.getRuta());
		preparedStmt.setString(8, pub.getDeporte());

		// execute the preparedstatement
		preparedStmt.execute();
		// close connection
		conecta.close();

	}
	
	/**
	 * Inserta like de un usuario a publicación en la BD
	 * @param usuario identificador del usuario a insertar
	 * @param pub identificador de publicacion a insertar
	 * @throws SQLException error al ejecutar la sentencia
	 */
	public static void insertLike(int usuario, int pub) throws SQLException {

		Connection conecta = null;
		//conecta = Connect.getDBConnection();
		try {
			conecta = Connect.getConnectionFromPool();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query = "INSERT INTO software.Megusta VALUES " + "( ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conecta.prepareStatement(query);
		preparedStmt.setInt(1, usuario);
		preparedStmt.setInt(2, pub);

		// execute the preparedstatement
		preparedStmt.execute();
		// close connection
		conecta.close();

	}
	
	/**
	 * Inserta un compartir en la BD entre un usuario y una publicacion
	 * @param usuario identificador del usuario a insertar
	 * @param pub identificador de publicacion a insertar
	 * @throws SQLException  error al ejecutar la sentencia
	 */
	public static void insertShare(int usuario, int pub) throws SQLException {

		Connection conecta = null;
		//conecta = Connect.getDBConnection();
		try {
			conecta = Connect.getConnectionFromPool();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query = "INSERT INTO software.Compartir VALUES " + "( ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conecta.prepareStatement(query);
		preparedStmt.setInt(1, usuario);
		preparedStmt.setInt(2, pub);

		// execute the preparedstatement
		preparedStmt.execute();
		// close connection
		conecta.close();

	}

	/**
	 * Selecciona las publicaciones que aparecen en el home de un usario
	 * @param idUsuario identificador del usuario
	 * @return ArrayList<Publicacion> publicaciones para el home del usuario
	 * @throws SQLException error al ejecutar la sentencia
	 */
	public static ArrayList<Publicacion> selectForHome(int idUsuario) throws SQLException {
		return select(idUsuario, 1);
	}
	
	/**
	 * Selecciona las publicaciones que aparecen en el perfil de un usario
	 * @param idUsuario identificador del usuario
	 * @return ArrayList<Publicacion> publicaciones para el perfil del usuario
	 * @throws SQLException error al ejecutar la sentencia
	 */
	public static ArrayList<Publicacion> selectForProfile(int idUsuario) throws SQLException {
		return select(idUsuario, 2);
	}
	
	/**
	 * Devuelve el numero de likes de una publicacion
	 * @param publicacion identificador de la publicacion
	 * @return int numero de likes de una publicacion
	 * @throws SQLException error al ejecutar la sentencia
	 */
	public static int selectLikes(int publicacion) throws SQLException {
		int likes = 0;
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		String query = "select count(*) as likes, pk_publicacion_mg from software.Megusta where " +
				"pk_publicacion_mg=" + publicacion + " group by pk_publicacion_mg";
		try {
			//conecta = Connect.getDBConnection();
			conecta = Connect.getConnectionFromPool();
			
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				likes = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conecta != null) {
				conecta.close();
			} 
		}
		return likes;
	}

	/**
	 * Realiza una selección de comentarios de 
	 * una publicación
	 * @param publicacion identificador de la publicacion a insertar
	 * @return ArrayList<Comentario> comentarios de la publicacion
	 * @throws SQLException error al ejecutar la sentencia
	 */
	public static ArrayList<Comentario> selectComentarios(int publicacion) throws SQLException {
		ArrayList<Comentario> posts = new ArrayList<Comentario>();
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		String query = "SELECT * FROM software.Comentario where Publicacion_com = " +
				publicacion + " order by Fecha desc, Hora desc";
		try {
			conecta = Connect.getDBConnection();
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("idComentario");
				//int pub = rs.getInt("Publicacion_com");
				String hora_aux = rs.getString("Hora");
				String fecha_aux = rs.getString("Fecha");
				Date fecha = toDate(fecha_aux, hora_aux);
				int autor = rs.getInt("Usuario_com"); // Autor
				String texto = rs.getString("Contenido");
				// Instanciar publicacion
				Comentario nuevo = new Comentario(id, autor, publicacion, fecha, texto);
				posts.add(nuevo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conecta != null) {
				conecta.close();
			}

		}
		return posts;
	}
	
	/**
	 * Inserta un comentario en la BD 
	 * @param com comentario a insertar
	 * @throws SQLException error al ejecutar la sentencia
	 */
	public static void insertComentario(Comentario com) throws SQLException {
		
		// Fecha y hora
		String fecha = Fechas.getFechaString(com.getFecha());
		String hora = Fechas.getHoraString(com.getFecha());

		Connection conecta = null;
		//conecta = Connect.getDBConnection();
		try {
			conecta = Connect.getConnectionFromPool();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query = "INSERT INTO software.Comentario"
				+ "(Publicacion_com, Hora, Fecha, Usuario_com, Contenido) "
				+ "VALUES " + "( ?, ?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conecta.prepareStatement(query);
		preparedStmt.setInt(1, com.getPublicacion());
		preparedStmt.setString(2, hora);
		preparedStmt.setString(3, fecha);
		preparedStmt.setInt(4, com.getAutor());
		preparedStmt.setString(5, com.getTexto());

		// execute the preparedstatement
		preparedStmt.execute();
		// close connection
		conecta.close();

	}
	
	/**
	 * Realiza una selección de publicaciones
	 * @param idUsuario identificador del usuario
	 * @param modo = 1 -> select para el home, 
	 * 		modo = 2 -> select de publicaciones de un usuario
	 * @return ArrayList<Publicacion> publicaciones del usuario
	 * @throws SQLException error al ejecutar la sentencia
	 */
	private static ArrayList<Publicacion> select(int idUsuario, int modo) throws SQLException {
		ArrayList<Publicacion> posts = new ArrayList<Publicacion>();
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		String query = "";
		if (modo == 1) { // select para el Home de un usuario <- Sin implementar
			query = "select distinct A.idPublicacion, A.Fecha, A.Hora, A.Emisor, A.Texto, A.Imagen, A.Video, A.Ruta, A.Deporte from ("
					+ "(select idPublicacion, Fecha, Hora, Emisor, Texto, Imagen, Video, Ruta, Deporte " +
					"from software.Publicacion p, software.Seguir s where " +
					"p.emisor = s.fk_Seguido AND s.fk_Usuario=" + idUsuario + ")"
					+ "UNION"
					+ "(select idPublicacion, Fecha, Hora, Emisor, Texto, Imagen, Video, Ruta, Deporte " +
					"from software.Publicacion p where " +
					"p.Emisor = "+ idUsuario +")) A order by A.Fecha desc, A.Hora desc";
		} else if (modo == 2) { // select de publicaciones de un usario o compartidas
			
			query = "SELECT DISTINCT idPublicacion, Fecha, Hora, Emisor, Texto, Imagen, "
					+ "Video, Ruta, Deporte FROM software.Publicacion "
					+ "WHERE Emisor = " + idUsuario + " OR idPublicacion IN ("
					+ "SELECT pk_publicacion_c FROM software.Compartir WHERE "
					+ "pk_usuario_c = " + idUsuario + ") "
					+ "ORDER BY Fecha DESC , Hora DESC";
		}
		try {
			//conecta = Connect.getDBConnection();
			conecta = Connect.getConnectionFromPool();
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt(1);
				String fecha_aux = rs.getString(2);
				String hora_aux = rs.getString(3);
				Date fecha = toDate(fecha_aux, hora_aux);
				int autor = rs.getInt(4); // Autor
				String texto = rs.getString(5);
				String img = rs.getString(6);
				String video = rs.getString(7);
				String ruta = rs.getString(8);
				String deporte = rs.getString(9);
				// Instanciar publicacion
				Publicacion nuevo = new Publicacion(id, autor, texto, fecha, img, video, 
						ruta, deporte);
				posts.add(nuevo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conecta != null) {
				conecta.close();
			}

		}
		return posts;
	}

	/**
	 * Devuevle un objeto fecha a partir de los strings de fecha 
	 * y hora
	 * @param fecha con formato aaaammdd
	 * @param hora con formato hhmm
	 * @return Date fecha indicada
	 */
	private static Date toDate(String fecha, String hora) {
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
