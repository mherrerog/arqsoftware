package gateway;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	
	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://127.10.249.130/";
	private static final String DB_USER = "adminG8qpMFy";
	private static final String DB_PASSWORD = "ubZnrpb5PUge";
	
	
	
	/**
	 * Devuelve una conexion por JDBC a la base de datos 
	 * @return conexion a la base de datos
	 */
	public static Connection getDBConnection() {
		
		java.sql.Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER).newInstance();
			//System.out.println("Registro exitoso");

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;
	}
	
	/**
	 * Devuevle una conexión con la base de datos a
	 * través de un pool de conexiones
	 * @throws SQLException error durante la conexión
	 * @throws NamingException el pool de conexiones no se
	 * ha configurado correctamente
	 * @return conexión con la base de datos
	 */
	public static Connection getConnectionFromPool() throws SQLException, NamingException{
		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/MySQLDS");
		
		return ds.getConnection();
	}
	
	/**
	 * Ejecuta la sentencia indicada en la BD
	 * @param query sentencia a ejecutar
	 * @throws SQLException error al ejecutar la sentencia
	 */
	public static void ejecutaSentencia(String query) throws SQLException{
		Connection conecta = null;
		Statement stmt = null;
		try {
			conecta = Connect.getDBConnection();
			stmt = conecta.createStatement();
			// execute query
			stmt.executeQuery(query);
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

	}

}
