package gateway;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import datos.User;
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
				
				Date fecha1 = toDate(fecha);
				nuevo = new Usuario(id, nombre, fecha1, sexo, email, 
					nick, password, equipo, logo, fondo);
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
				Usuario nuevo = new Usuario(id, nombre, fecha1, sexo, email, 
					nick, password, equipo, fondo, logo);
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
				// Falta comprobar equipo
				
				Date fecha1 = toDate(fecha);
				nuevo = new Usuario(id, nombre, fecha1, sexo, email, 
					nick, password, 0, null, null);
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
	 * Inserta un usuario en la BD
	 */
	public static void insert(Usuario usr) throws SQLException{
		Connection conecta = Connect.getDBConnection();
		
		String query = "INSERT INTO ASoftware.Usuario (Nombre, Fecha, Mail, Sexo, Password, Equipo, Nick) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = conecta.prepareStatement(query);
		preparedStatement.setString(1, usr.getNombre());
		preparedStatement.setString(2, usr.getFechaString());
		preparedStatement.setString(3, usr.getEmail());
		preparedStatement.setString(4, usr.getSexo());
		preparedStatement.setString(5, usr.getPassword());
		preparedStatement.setInt(6, usr.getEquipo());
		preparedStatement.setString(7, usr.getNick());
		
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
