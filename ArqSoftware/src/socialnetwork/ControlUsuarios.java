package socialnetwork;

import java.sql.SQLException;
import java.util.ArrayList;

import datos.Usuario;
import gateway.UsuarioDAO;

public class ControlUsuarios {
	
	public static String buscarUsuarios(String nombre){
		String result = "";
		try {
			ArrayList<Usuario> users = UsuarioDAO.selectByName(nombre);
			result = Usuario.toJSON(users);
			// System.out.println(result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static String buscarMiembros(int equipo){
		String result = "";
		try {
			ArrayList<Usuario> users = UsuarioDAO.selectByEquipo(equipo);
			result = Usuario.toJSON(users);
			// System.out.println(result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
