package gateway;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import datos.Usuario;

public class UsuarioDAO {
	
	/**
	 * Selecciona el usuario con identificador indicado
	 * @param idUsuario identificador del usuario
	 * @return devuelve el usuario indicado
	 * @throws SQLException error durante la ejecucion de la sentencia
	 */
	public static Usuario selectById(int idUsuario) throws SQLException{
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		Usuario nuevo = null;
		String query = "select * from Usuario where idUsuarios = '"+idUsuario+"'";
		try {
			// Conexion por Pool de conexiones
			conecta = Connect.getConnectionFromPool();
			// conecta = Connect.getDBConnection();
			
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int id = rs.getInt("idUsuarios");
				String nombre = rs.getString("Nombre");
				String fecha = rs.getString("Fecha");
				String sexo = rs.getString("Sexo");
				String email = rs.getString("Mail");
				String nick = rs.getString("Nick");
				String password = rs.getString("Password");
				int equipo = rs.getInt("Equipo");
				String fondo = rs.getString("Fondo");
				String logo = rs.getString("Logo");
				String descripcion = rs.getString("Descripcion");
				
				Date fecha1 = toDate(fecha);
				nuevo = new Usuario(id, nombre, fecha1, sexo, email, 
					nick, password, equipo, logo, fondo, descripcion);
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
		return nuevo;
	}

	/**
	 * Selecciona los usuarios cuyo nombre contiene 
	 * con el indicado
	 * @param name nombre de usuario
	 * @return devuelve un array de usuarios
	 * @throws SQLException error durante la ejecucion de la sentencia
	 */
	public static ArrayList<Usuario> selectByName(String name) 
			throws SQLException {

		ArrayList<Usuario> users = new ArrayList<Usuario>();
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		String query = "select * from software.Usuario where (nombre LIKE CONCAT('" + name + "', '%'))"
				+ "or (nick LIKE CONCAT('%', '" + name + "', '%'))";
		try {
			// Conexion por Pool de conexiones
			conecta = Connect.getConnectionFromPool();
			// conecta = Connect.getDBConnection();
						
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("idUsuarios");
				String nombre = rs.getString("Nombre");
				String fecha = rs.getString("Fecha");
				String sexo = rs.getString("Sexo");
				String email = rs.getString("Mail");
				String nick = rs.getString("Nick");
				int equipo = rs.getInt("Equipo");
				String password = rs.getString("Password");
				String fondo = rs.getString("Fondo");
				String logo = rs.getString("Logo");
				Date fecha1 = toDate(fecha);
				String descripcion = rs.getString("Descripcion");
				Usuario nuevo = new Usuario(id, nombre, fecha1, sexo, email, 
					nick, password, equipo, logo, fondo, descripcion);
				users.add(nuevo);
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
		return users;

	}
	
	/**
	 * Selecciona el usuario con el mail indicado
	 * @param mail correo del usuario
	 * @return devuelve el usuario indicado
	 * @throws SQLException error durante la ejecucion de la sentencia
	 */
	public static Usuario selectByMail(String mail) throws SQLException {

		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		Usuario nuevo = null;
		String query = "select * from software.Usuario where Mail = '"+mail+"'";
		try {
			// Conexion por Pool de conexiones
			
			conecta = Connect.getConnectionFromPool();
			//conecta = Connect.getDBConnection();
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int id = rs.getInt("idUsuarios");
				String nombre = rs.getString("Nombre");
				String fecha = rs.getString("Fecha");
				String sexo = rs.getString("Sexo");
				String email = rs.getString("Mail");
				String nick = rs.getString("Nick");
				String password = rs.getString("Password");
				String descripcion = rs.getString("Descripcion");
				// Falta comprobar equipo
				
				Date fecha1 = toDate(fecha);
				nuevo = new Usuario(id, nombre, fecha1, sexo, email, 
					nick, password, 0, null, null, descripcion);
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
		return nuevo;

	}
	
	/**
	 * Devuevle los numeros de seguidores y seguidos
	 * de un usaurio dado
	 * @param usuario identificador del usuario
	 * @return resultado[0] = nº seguidores del usuario, 
	 * resultado[1] = nº seguidos del usuario 
	 * @throws SQLException error en la ejecucion de la sentencia
	 */
	public static int[] seguidor_seguido(int usuario) throws SQLException {
		int[] resultado = new int [2];
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String query = "select usuario, seguidores, seguidos from" +
				"(SELECT COUNT(*) AS seguidores, fk_usuario AS usuario FROM software.Seguir GROUP BY fk_usuario) a," +
				"(SELECT COUNT(*) AS seguidos, fk_Seguido AS useguido FROM software.Seguir GROUP BY fk_Seguido) b " +
				"where usuario = useguido AND usuario = " + usuario;
		
		try {
			// Conexion por Pool de conexiones
			conecta = Connect.getConnectionFromPool();
			// conecta = Connect.getDBConnection();
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				resultado[0] = rs.getInt("seguidores");
				resultado[1] = rs.getInt("seguidos");
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
		
		return resultado;
	}
	
	/**
	 * Devuele cierto si usuario sigue a siguiendo, falso
	 * en caso contrario
	 * @param usuario identificador de usuario
	 * @param siguiendo identificador de usuario
	 * @return cierto si usuario sigue a siguiendo
	 * @throws SQLException
	 */
	public static boolean yoSigo(int usuario, int siguiendo) throws SQLException {
		int resultado = 0;
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String query = "SELECT fk_Usuario, fk_Seguido from software.Seguir where "
				+ "fk_Usuario=" + siguiendo + " and fk_Seguido=" + usuario +
				" group by fk_Usuario, fk_Seguido";
		
		try {
			// Conexion por Pool de conexiones
			conecta = Connect.getConnectionFromPool();
			// conecta = Connect.getDBConnection();
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				resultado++;
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
		
		return (resultado > 0);
	}
	
	/**
	 * Devuele cierto si usuario es seguido por siguiendo, falso
	 * en caso contrario
	 * @param usuario identificador de usuario
	 * @param siguiendo identificador de usuario
	 * @return cierto si usuario es seguido por siguiendo
	 * @throws SQLException
	 */
	public static boolean meSigue(int usuario, int siguiendo) throws SQLException {
		int resultado = 0;
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String query = "SELECT fk_Usuario, fk_Seguido from software.Seguir where "
				+ "fk_Usuario=" + usuario + " and fk_Seguido=" + siguiendo +
				" group by fk_Usuario, fk_Seguido";
		
		try {
			// Conexion por Pool de conexiones
			conecta = Connect.getConnectionFromPool();
			// conecta = Connect.getDBConnection();
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				resultado++;
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
		
		return (resultado > 0);
	}
	
	/**
	 * Devuelve los usuario que siguen y son seguidos por
	 * el usuario dado
	 * @param myId identificador del usuario
	 * @return array con usaurios
	 * @throws SQLException error en la ejecucion de la consulta
	 */
	public static ArrayList<Usuario> selectSeguirMutuo(int myId) throws SQLException {

		ArrayList<Usuario> users = new ArrayList<Usuario>();
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		String query = "select * from software.Usuario U, software.Seguir S, software.Seguir P " +
		"where (S.fk_Usuario = P.fk_Seguido and P.fk_Usuario = S.fk_Seguido and S.fk_Usuario = " + myId + ") and S.fk_Seguido = U.idUsuarios";
		try {
			// Conexion por Pool de conexiones
			conecta = Connect.getConnectionFromPool();
			// conecta = Connect.getDBConnection();
			
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("idUsuarios");
				String nombre = rs.getString("Nombre");
				String fecha = rs.getString("Fecha");
				String sexo = rs.getString("Sexo");
				String email = rs.getString("Mail");
				String nick = rs.getString("Nick");
				int equipo = rs.getInt("Equipo");
				String password = rs.getString("Password");
				String fondo = rs.getString("Fondo");
				String logo = rs.getString("Logo");
				Date fecha1 = toDate(fecha);
				String descripcion = rs.getString("Descripcion");
				Usuario nuevo = new Usuario(id, nombre, fecha1, sexo, email, 
					nick, password, equipo, logo, fondo, descripcion);
				users.add(nuevo);
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
		return users;

	}
	
	/**
	 * Devuelve los usuarios que pertenecen a un equipo dado
	 * @param team identificador del equipo
	 * @return array con usaurios
	 * @throws SQLException error en la ejecucion de la consulta
	 */
	public static ArrayList<Usuario> selectByEquipo(int team) throws SQLException {

		ArrayList<Usuario> users = new ArrayList<Usuario>();
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		String query = "select * from software.Usuario, software.Pertenecen " + 
				"where idUsuarios=fk_Miembro and fk_Equipo=" + team;
		try {
			// Conexion por Pool de conexiones
			conecta = Connect.getConnectionFromPool();
			// conecta = Connect.getDBConnection();
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("idUsuarios");
				String nombre = rs.getString("Nombre");
				String fecha = rs.getString("Fecha");
				String sexo = rs.getString("Sexo");
				String email = rs.getString("Mail");
				String nick = rs.getString("Nick");
				int equipo = rs.getInt("Equipo");
				String password = rs.getString("Password");
				String fondo = rs.getString("Fondo");
				String logo = rs.getString("Logo");
				Date fecha1 = toDate(fecha);
				String descripcion = rs.getString("Descripcion");
				Usuario nuevo = new Usuario(id, nombre, fecha1, sexo, email, 
					nick, password, equipo, logo,fondo, descripcion);
				users.add(nuevo);
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
		return users;

	}
	
	/**
	 * Devuelve los usuario que siguen al usuario dado
	 * @param myId identificador del usuario
	 * @return array con usaurios
	 * @throws SQLException error en la ejecucion de la consulta
	 */
	public static ArrayList<Usuario> selectSeguidores(int myId) throws SQLException {

		ArrayList<Usuario> users = new ArrayList<Usuario>();
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		String query = "select * from software.Usuario a, software.Seguir " + 
				"where idUsuarios=fk_Seguido and fk_usuario=" + myId;
		try {
			// Conexion por Pool de conexiones
			conecta = Connect.getConnectionFromPool();
			// conecta = Connect.getDBConnection();
			
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("idUsuarios");
				String nombre = rs.getString("Nombre");
				String fecha = rs.getString("Fecha");
				String sexo = rs.getString("Sexo");
				String email = rs.getString("Mail");
				String nick = rs.getString("Nick");
				int equipo = rs.getInt("Equipo");
				String password = rs.getString("Password");
				String fondo = rs.getString("Fondo");
				String logo = rs.getString("Logo");
				Date fecha1 = toDate(fecha);
				String descripcion = rs.getString("Descripcion");
				Usuario nuevo = new Usuario(id, nombre, fecha1, sexo, email, 
					nick, password, equipo, logo,fondo, descripcion);
				users.add(nuevo);
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
		return users;

	}
	
	/**
	 * Devuelve los usuario que son seguidos por
	 * el usuario dado
	 * @param myId identificador del usuario
	 * @return array con usaurios
	 * @throws SQLException error en la ejecucion de la consulta
	 */
	public static ArrayList<Usuario> selectSeguidos(int myId) throws SQLException {

		ArrayList<Usuario> users = new ArrayList<Usuario>();
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		String query = "select * from software.Usuario a, software.Seguir " + 
				"where idUsuarios=fk_usuario and fk_Seguido=" + myId;
		try {
			// Conexion por Pool de conexiones
			conecta = Connect.getConnectionFromPool();
			// conecta = Connect.getDBConnection();
			
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("idUsuarios");
				String nombre = rs.getString("Nombre");
				String fecha = rs.getString("Fecha");
				String sexo = rs.getString("Sexo");
				String email = rs.getString("Mail");
				String nick = rs.getString("Nick");
				int equipo = rs.getInt("Equipo");
				String password = rs.getString("Password");
				String fondo = rs.getString("Fondo");
				String logo = rs.getString("Logo");
				Date fecha1 = toDate(fecha);
				String descripcion = rs.getString("Descripcion");
				Usuario nuevo = new Usuario(id, nombre, fecha1, sexo, email, 
					nick, password, equipo, logo,fondo, descripcion);
				users.add(nuevo);
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
		return users;

	}
	
	/**
	 * Inseta un usuario en la base de datos
	 * @param usr usuario a insertar
	 * @throws SQLException error durante la insercion
	 */
	public static void insert(Usuario usr) throws SQLException{
		// Conexion por Pool de conexiones
		Connection conecta = null;
		try {
			conecta = Connect.getConnectionFromPool();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// conecta = Connect.getDBConnection();
		
		String query = "INSERT INTO software.Usuario (Nombre, Fecha, Mail, Sexo, Password, Equipo, Nick, Descripcion, Logo) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		System.out.println(usr.getFechaString());
		PreparedStatement preparedStatement = conecta.prepareStatement(query);
		preparedStatement.setString(1, usr.getNombre());
		preparedStatement.setString(2, usr.getFechaString());
		preparedStatement.setString(3, usr.getEmail());
		preparedStatement.setString(4, usr.getSexo());
		preparedStatement.setString(5, usr.getPassword());
		preparedStatement.setInt(6, usr.getEquipo());
		preparedStatement.setString(7, usr.getNick());
		preparedStatement.setString(8, usr.getDescripcion());
		preparedStatement.setString(9, usr.getLogo());
		
		preparedStatement.execute();
		conecta.close();
	}
	
	/**
	 * Inseta un usuario en un equipo
	 * @param usr identificador del usuario a insertar
	 * @param equipo identificador del equipo
	 * @throws SQLException error durante la insercion
	 */
	public static void insertMiembro(int usr, int equipo) throws SQLException{
		Connection conecta = null;
		
		// Conexion por Pool de conexiones
		try {
			conecta = Connect.getConnectionFromPool();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// conecta = Connect.getDBConnection();
		
		String query = "INSERT INTO software.Pertenecen (fk_Equipo, fk_Miembro)"
				+ " VALUES (?, ?)";
		PreparedStatement preparedStatement = conecta.prepareStatement(query);
		preparedStatement.setInt(1, equipo);
		preparedStatement.setInt(2, usr);
		
		preparedStatement.execute();
		conecta.close();
	}
	
	/**
	 * Elimina a un usuario de la base de datos
	 * @param usuario a eliminar
	 * @throws SQLException error durante el borrado
	 */
	public static void delete(Usuario usuario) throws SQLException{
		Connection conecta = null;
		
		// Conexion por Pool de conexiones
		try {
			conecta = Connect.getConnectionFromPool();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// conecta = Connect.getDBConnection();
		
		String query = "DELETE FROM software.Usuario WHERE idUsuarios='"+Integer.toString(usuario.getId())+"';";
		PreparedStatement preparedStatement = conecta.prepareStatement(query);		
		preparedStatement.execute();
		conecta.close();
	}
	
	/**
	 * Elimina a un usuario de un equipo
	 * @param usr identificador del usuario
	 * @param equipo identificador del equipo
	 * @throws SQLException error durante el borrado
	 */
	public static void deleteMiembro(int usr, int equipo) throws SQLException{
		Connection conecta = null;
		
		// Conexion por Pool de conexiones
		try {
			conecta = Connect.getConnectionFromPool();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// conecta = Connect.getDBConnection();
		
		String query = "DELETE FROM software.Pertenecen WHERE fk_Equipo="+
				equipo +" AND fk_Miembro=" + usr;
		PreparedStatement preparedStatement = conecta.prepareStatement(query);		
		preparedStatement.execute();
		conecta.close();
	}
	
	/**
	 * Actualiza la inforomacion de un usaurio
	 * @param usuario a actualizar
	 * @throws SQLException error durante la actualizacion
	 */
	public static void update(Usuario usuario) throws SQLException{
		Connection conecta = null;
		
		// Conexion por Pool de conexiones
		try {
			conecta = Connect.getConnectionFromPool();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// conecta = Connect.getDBConnection();
		
		String query = "UPDATE software.Usuario set Nombre='"+usuario.getNombre()+"', Fecha='"+usuario.getFechaString()+" "
				+ "', Sexo='"+usuario.getSexo()+"', Password='"+usuario.getPassword()+"', Descripcion='"+usuario.getDescripcion()+" "
				+ "', Logo='"+usuario.getLogo()+"' WHERE idUsuarios='"+Integer.toString(usuario.getId())+"';";
		PreparedStatement preparedStatement = conecta.prepareStatement(query);		
		preparedStatement.execute();
		conecta.close();
	}

	/**
	 * Inserta a un usuario como seguidor de otro
	 * @param myId identificador del usaurio seguidor
	 * @param idUser identificador del usaurio que seguira
	 * @throws SQLException error durante la insercion
	 */
	public static void insertUserSeguir(int myId, int idUser) throws SQLException{
		Connection conecta = null;
		
		// Conexion por Pool de conexiones
		try {
			conecta = Connect.getConnectionFromPool();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// conecta = Connect.getDBConnection();
		
		
		String query = "INSERT INTO software.Seguir (fk_Usuario, fk_Seguido) "
				+ " VALUES (?, ?)";
		PreparedStatement preparedStatement = conecta.prepareStatement(query);
		preparedStatement.setInt(1, myId);
		preparedStatement.setInt(2, idUser);
		
		preparedStatement.execute();
		conecta.close();
	}
	
	/**
	 * Elimina a un usuario como seguidor de otro
	 * @param myId identificador del usaurio seguidor
	 * @param idUser identificador del usaurio seguido
	 * @throws SQLException error durante la eliminacion
	 */
	public static void deleteUserSeguir(int myId, int idUser) throws SQLException{
		Connection conecta = null;
		
		// Conexion por Pool de conexiones
		try {
			conecta = Connect.getConnectionFromPool();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// conecta = Connect.getDBConnection();
		
		String query = "DELETE FROM software.Seguir WHERE fk_Usuario = " + myId 
				+ " and fk_Seguido = " + idUser;
		
		PreparedStatement preparedStatement = conecta.prepareStatement(query);
		preparedStatement.execute();
		
		conecta.close();
	}
	
	/**
	 * Devuelve una fecha dada
	 * @param fecha en formato aaaammdd
	 * @return objeto Date con la fecha dada
	 */
	public static Date toDate(String fecha) {
		Date nueva = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			nueva = sdf.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nueva;
	}

}
