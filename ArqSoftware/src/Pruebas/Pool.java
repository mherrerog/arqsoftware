package Pruebas;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pool {
	
	public static void test1 () throws NamingException{
		System.out.println(">Pooozo");
		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/mysql");
		Connection con = null;
		Statement stmt = null;
		
		try {
			con = ds.getConnection();
			
			stmt = con.createStatement();
			// execute query
			ResultSet rs = stmt.executeQuery("select * from ASoftware.Usuario");
			
			while (rs.next()){
				String nombre = rs.getString(2);
				System.out.println(">" + nombre);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Pooozo");
		
	}

}
