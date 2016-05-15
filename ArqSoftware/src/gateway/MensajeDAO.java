package gateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import datos.Mensaje;


public class MensajeDAO {
	
	/**
	 * Metodo de inserción a la BD
	 * @throws SQLException 
	 */
	public static void insert(Mensaje ms) throws SQLException{

		// Datos del mensaje
		int emisor = ms.getEmisor();
		int receptor = ms.getReceptor();
		String fecha = ms.getFecha();
		String hora = ms.getHora();
		String cuerpo = ms.getCuerpo();
		Connection conn = Connect.getDBConnection();
		
		String query = "INSERT INTO ASoftware.Mensaje" +
			"(Emisor_M, Receptor, Fecha, Hora, Cuerpo, Leido)  VALUES" +
			"( ?, ?, ?,?, ?,?)";
		
		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setInt(1, emisor);
		preparedStmt.setInt(2, receptor);
		preparedStmt.setString(3, fecha);
		preparedStmt.setString(4, hora);
		preparedStmt.setString(5, cuerpo);
		preparedStmt.setInt(6, 0);

		// execute the preparedstatement
		preparedStmt.execute();
		// close connection
		conn.close();
		
	}
	
	/**
	 * Elimina un mensaje de la BD
	 * 
	 * @throws SQLException
	 */
	public static void delete(int idMensaje) throws SQLException {
		String query = "DELETE FROM ASoftware.Mensaje WHERE " + "(idMensaje = ?)";
		Connection conn = Connect.getDBConnection();
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setInt(1, idMensaje);

		// execute the preparedstatement
		preparedStmt.execute();
		// close connection
		conn.close();
	}
	
	public static ArrayList<Mensaje> selectByUsuario(int idUsuario) throws SQLException {

		ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
		Connection conecta = null;
		ResultSet rs = null;
		Statement stmt = null;
		String query = "select * from ASoftware.Mensaje "
				+ "where (Receptor=" +idUsuario + ") order by fecha desc, hora desc";
		try {
			conecta = Connect.getDBConnection();
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt(1);
				int emisor = rs.getInt(2);
				int receptor = rs.getInt(3);
				String fecha = rs.getString(4);
				String hora = rs.getString(5);
				int leido = rs.getInt(6);
				String cuerpo = rs.getString(7);
				Mensaje nuevo = new Mensaje(id, emisor, receptor, fecha, hora, cuerpo, leido);
				mensajes.add(nuevo);
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
		return mensajes;

	}

}
