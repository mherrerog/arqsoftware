package gateway;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import datos.Usuario;

public class UsuarioDAO {
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public static Usuario selectById(int idUsuario) throws SQLException{
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		Usuario nuevo = null;
		String query = "select * from ASoftware.Usuario where idUsuarios = '"+idUsuario+"'";
		try {
			conecta = Connect.getDBConnection();
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
	 * 
	 */
	public static ArrayList<Usuario> selectByName(String name) throws SQLException {

		ArrayList<Usuario> users = new ArrayList<Usuario>();
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		String query = "select * from ASoftware.Usuario where (nombre LIKE CONCAT('" + name + "', '%'))"
				+ "or (nick LIKE CONCAT('%', '" + name + "', '%'))";
		try {
			conecta = Connect.getDBConnection();
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
					nick, password, equipo, fondo, logo, descripcion);
				users.add(nuevo);
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
		return users;

	}
	
	/**
	 * Devuelve el usuario cuyo mail corresponde al correo introducido
	 */
	
	public static Usuario selectByMail(String mail) throws SQLException {

		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		Usuario nuevo = null;
		String query = "select * from ASoftware.Usuario where Mail = '"+mail+"'";
		try {
			conecta = Connect.getDBConnection();
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
	 * Devuelve un array con dos enteros
	 * resultado[0] = nº seguidores del usuario
	 * resultado[1] = nº seguidos del usuario 
	 */
	public static int[] seguidor_seguido(int usuario) throws SQLException {
		int[] resultado = new int [2];
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String query = "select usuario, seguidores, seguidos from" +
				"(SELECT COUNT(*) AS seguidores, fk_usuario AS usuario FROM ASoftware.Seguir GROUP BY fk_usuario) a," +
				"(SELECT COUNT(*) AS seguidos, fk_Seguido AS useguido FROM ASoftware.Seguir GROUP BY fk_Seguido) b " +
				"where usuario = useguido AND usuario = " + usuario;
		
		try {
			conecta = Connect.getDBConnection();
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
	 * Devuelve un true si usuario sigue a siguiendo
	 */
	public static boolean yoSigo(int usuario, int siguiendo) throws SQLException {
		int resultado = 0;
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String query = "SELECT fk_Usuario, fk_Seguido from ASoftware.Seguir where "
				+ "fk_Usuario=" + siguiendo + " and fk_Seguido=" + usuario +
				" group by fk_Usuario, fk_Seguido";
		
		try {
			conecta = Connect.getDBConnection();
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				resultado++;
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
		
		return (resultado > 0);
	}
	
	/**
	 * Devuelve un true si usuario sigue a siguiendo
	 */
	public static boolean meSigue(int usuario, int siguiendo) throws SQLException {
		int resultado = 0;
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String query = "SELECT fk_Usuario, fk_Seguido from ASoftware.Seguir where "
				+ "fk_Usuario=" + usuario + " and fk_Seguido=" + siguiendo +
				" group by fk_Usuario, fk_Seguido";
		
		try {
			conecta = Connect.getDBConnection();
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				resultado++;
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
		
		return (resultado > 0);
	}
	
	/**
	 * 
	 */
	public static ArrayList<Usuario> selectSeguirMutuo(int myId) throws SQLException {

		ArrayList<Usuario> users = new ArrayList<Usuario>();
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		String query = "select * from ASoftware.Usuario U, ASoftware.Seguir S, ASoftware.Seguir P " +
		"where (S.fk_Usuario = P.fk_Seguido and P.fk_Usuario = S.fk_Seguido and S.fk_Usuario = " + myId + ") and S.fk_Seguido = U.idUsuarios";
		try {
			conecta = Connect.getDBConnection();
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
					nick, password, equipo, fondo, logo, descripcion);
				users.add(nuevo);
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
		return users;

	}
	
	/**
	 * 
	 */
	public static ArrayList<Usuario> selectByEquipo(int team) throws SQLException {

		ArrayList<Usuario> users = new ArrayList<Usuario>();
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		String query = "select * from ASoftware.Usuario, ASoftware.Pertenecen " + 
				"where idUsuarios=fk_Miembro and fk_Equipo=" + team;
		try {
			conecta = Connect.getDBConnection();
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
	 * Inserta un usuario en la BD
	 */
	public static void insert(Usuario usr) throws SQLException{
		Connection conecta = Connect.getDBConnection();
		
		String query = "INSERT INTO ASoftware.Usuario (Nombre, Fecha, Mail, Sexo, Password, Equipo, Nick, Descripcion) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
		
		preparedStatement.execute();
		conecta.close();
	}
	
	/**
	 * Inserta un usuario en la BD
	 */
	public static void insertMiembro(int usr, int equipo) throws SQLException{
		Connection conecta = Connect.getDBConnection();
		
		String query = "INSERT INTO ASoftware.Pertenecen (fk_Equipo, fk_Miembro)"
				+ " VALUES (?, ?)";
		PreparedStatement preparedStatement = conecta.prepareStatement(query);
		preparedStatement.setInt(1, equipo);
		preparedStatement.setInt(2, usr);
		
		preparedStatement.execute();
		conecta.close();
	}
	
	public static void delete(Usuario usuario) throws SQLException{
		Connection conecta = Connect.getDBConnection();
		
		String query = "DELETE FROM ASoftware.Usuario WHERE idUsuarios='"+Integer.toString(usuario.getId())+"';";
		PreparedStatement preparedStatement = conecta.prepareStatement(query);		
		preparedStatement.execute();
		conecta.close();
	}
	
	public static void deleteMiembro(int usr, int equipo) throws SQLException{
		Connection conecta = Connect.getDBConnection();
		
		String query = "DELETE FROM ASoftware.Pertenecen WHERE fk_Equipo="+
				equipo +" AND fk_Miembro=" + usr;
		PreparedStatement preparedStatement = conecta.prepareStatement(query);		
		preparedStatement.execute();
		conecta.close();
	}
	
	public static void update(Usuario usuario) throws SQLException{
		Connection conecta = Connect.getDBConnection();
		
		String query = "UPDATE ASoftware.Usuario set Nombre='"+usuario.getNombre()+"', Fecha='"+usuario.getFechaString()+" "
				+ "', Sexo='"+usuario.getSexo()+"', Password='"+usuario.getPassword()+"', Descripcion='"+usuario.getDescripcion()+" "
				+ "' WHERE idUsuarios='"+Integer.toString(usuario.getId())+"';";
		PreparedStatement preparedStatement = conecta.prepareStatement(query);		
		preparedStatement.execute();
		conecta.close();
	}

	/**
	 * Inserta una nueva realcion de seguimiento en la BD
	 */
	public static void insertUserSeguir(int myId, int idUser) throws SQLException{
		Connection conecta = Connect.getDBConnection();
		
		
		String query = "INSERT INTO ASoftware.Seguir (fk_Usuario, fk_Seguido) "
				+ " VALUES (?, ?)";
		PreparedStatement preparedStatement = conecta.prepareStatement(query);
		preparedStatement.setInt(1, myId);
		preparedStatement.setInt(2, idUser);
		
		preparedStatement.execute();
		conecta.close();
	}
	
	/**
	 * Borra una realcion de seguimiento existente en la BD
	 */
	public static void deleteUserSeguir(int myId, int idUser) throws SQLException{
		Connection conecta = Connect.getDBConnection();
		
		String query = "DELETE FROM ASoftware.Seguir WHERE fk_Usuario = " + myId 
				+ " and fk_Seguido = " + idUser;
		
		PreparedStatement preparedStatement = conecta.prepareStatement(query);
		preparedStatement.execute();
		
		conecta.close();
	}
	
	/**
	 * Formato fecha: aaaammdd
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
