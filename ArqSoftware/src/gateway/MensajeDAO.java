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
	public static void insert(int emisor, int receptor, String fecha, String hora, String cuerpo) throws SQLException{

		Connection conn = Connect.getDBConnection();
		
		String query = "INSERT INTO ASoftware.Mensaje" +
			"(Emisor_M, Receptor, Fecha, Hora, Cuerpo)  VALUES" +
			"( ?, ?, ?,?, ?)";
		
		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setInt(1, emisor);
		preparedStmt.setInt(2, receptor);
		preparedStmt.setString(3, fecha);
		preparedStmt.setString(4, hora);
		preparedStmt.setString(5, cuerpo);

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
		String query = "DELETE FROM ASoftware.Mensaje WHERE " + "(idPublicacion = ?)";
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
				+ "where (Emisor_M=" +idUsuario +" or Receptor=" +idUsuario +")"
				+ " order by fecha, hora";
		try {
			conecta = Connect.getDBConnection();
			stmt = conecta.createStatement();
			// execute query
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				int emisor = rs.getInt(2);
				int receptor = rs.getInt(3);
				String fecha = rs.getString(4);
				String hora = rs.getString(5);
				String cuerpo = rs.getString(6);
				Mensaje nuevo = new Mensaje(emisor, receptor, fecha, hora, cuerpo);
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
