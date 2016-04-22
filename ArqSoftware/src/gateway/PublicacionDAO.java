package gateway;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import datos.Comentario;
import datos.Publicacion;
import datos.Usuario;

public class PublicacionDAO {

	/**
	 * Elimina una publicación en la BD
	 * 
	 * @throws SQLException
	 */
	public static void delete(int idPublicacion) throws SQLException {
		String query = "DELETE FROM ASoftware.Publicacion WHERE " + "(idPublicacion = ?)";
		Connection conn = Connect.getDBConnection();
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setInt(1, idPublicacion);

		// execute the preparedstatement
		preparedStmt.execute();
		// close connection
		conn.close();
	}

	
	/**
	 * Inserta una publicación en la BD
	 * 
	 * @throws SQLException
	 */
	public static void insert(Publicacion pub) throws SQLException {

		Connection conn = Connect.getDBConnection();
		String query = "INSERT INTO ASoftware.Publicacion "
				+ "(Fecha, Hora, Emisor, Texto, Imagen, Video, Ruta, Deporte) VALUES " + "( ?, ?, ?, ?, ?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
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
		conn.close();

	}
	
	/**
	 * Inserta like de un usuario a publicación en la BD
	 * 
	 * @throws SQLException
	 */
	public static void insertLike(int usuario, int pub) throws SQLException {

		Connection conn = Connect.getDBConnection();
		String query = "INSERT INTO ASoftware.Megusta VALUES " + "( ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setInt(1, usuario);
		preparedStmt.setInt(2, pub);

		// execute the preparedstatement
		preparedStmt.execute();
		// close connection
		conn.close();

	}
	
	/**
	 * Inserta un compartir en la BD entre un usuario y una publicacion
	 * 
	 * @throws SQLException
	 */
	public static void insertShare(int usuario, int pub) throws SQLException {

		Connection conn = Connect.getDBConnection();
		String query = "INSERT INTO ASoftware.Compartir VALUES " + "( ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setInt(1, usuario);
		preparedStmt.setInt(2, pub);

		// execute the preparedstatement
		preparedStmt.execute();
		// close connection
		conn.close();

	}

	/**
	 * Selecciona las publicaciones que aparecen en el home de un usario
	 */
	public static ArrayList<Publicacion> selectForHome(int idUsuario) throws SQLException {
		return select(idUsuario, 1);
	}
	
	/**
	 * Selecciona las publicaciones que aparecen en el perfil de un usario
	 */
	public static ArrayList<Publicacion> selectForProfile(int idUsuario) throws SQLException {
		return select(idUsuario, 2);
	}

	/**
	 * Selecciona las publicaciones de un usario
	 */
	public static ArrayList<Publicacion> selectById(int idUsuario) throws SQLException {
		return select(idUsuario, 2);
	}
	
	/**
	 * Devuelve el numero de likes de una publicacion
	 */
	public static int selectLikes(int publicacion) throws SQLException {
		int likes = 0;
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		String query = "select count(*) as likes, pk_publicacion_mg from ASoftware.Megusta where " +
				"pk_publicacion_mg=" + publicacion + " group by pk_publicacion_mg";
		try {
			conecta = Connect.getDBConnection();
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				likes = rs.getInt(1);
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
		return likes;
	}

	/**
	 * Realiza una selección de publicaciones
	 * Modo = 1 -> select para el home
	 * Modo = 2 -> select de publicaciones de un usuario
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
					"from ASoftware.Publicacion p, ASoftware.Seguir s where " +
					"p.emisor = s.fk_Seguido AND s.fk_Usuario=" + idUsuario + ")"
					+ "UNION"
					+ "(select idPublicacion, Fecha, Hora, Emisor, Texto, Imagen, Video, Ruta, Deporte " +
					"from ASoftware.Publicacion p where " +
					"p.Emisor = "+ idUsuario +")) A order by A.Fecha desc, A.Hora desc";
		} else if (modo == 2) { // select de publicaciones de un usario o compartidas
			
			query = "SELECT DISTINCT idPublicacion, Fecha, Hora, Emisor, Texto, Imagen, "
					+ "Video, Ruta, Deporte FROM ASoftware.Publicacion "
					+ "WHERE Emisor = " + idUsuario + " OR idPublicacion IN ("
					+ "SELECT pk_publicacion_c FROM ASoftware.Compartir WHERE "
					+ "pk_usuario_c = " + idUsuario + ") "
					+ "ORDER BY Fecha DESC , Hora DESC";
		}
		try {
			conecta = Connect.getDBConnection();
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
	 * Formato fecha: aaaammdd Formato hora: hhmm
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
