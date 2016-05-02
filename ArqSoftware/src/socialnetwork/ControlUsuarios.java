package socialnetwork;

import java.sql.SQLException;
import java.util.ArrayList;

import datos.Usuario;
import gateway.UsuarioDAO;

public class ControlUsuarios {
	
	/**
	 * Devuelve un array con todos los usuario
	 * que su nombre coincida con el indicado, en formato JSON
	 */
	public static String buscarUsuarios(String nombre, int usuarioId){
		String result = "";
		try {
			ArrayList<Usuario> users = UsuarioDAO.selectByName(nombre);
			Usuario usuario = new Usuario(usuarioId);
			result = usuario.toJSON(users, usuarioId);
			
			// Debug
			System.out.println(result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Devuelve un array con todos los miembros
	 * del equipo indicado en formato JSON
	 */
	public static String buscarMiembros(int equipo){
		String result = "";
		try {
			ArrayList<Usuario> users = UsuarioDAO.selectByEquipo(equipo);
			result = Usuario.toJSON(users, equipo);
			// System.out.println(result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Añade al equipo indicado el usuario pasado como parametro
	 */
	public static void anadirMiembros(int equipo, int usuario){
		try {
			UsuarioDAO.insertMiembro(usuario, equipo);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Eliminar del equipo indicado el usuario pasado como parametro
	 */
	public static void eliminarMiembros(int equipo, int usuario){
		try {
			UsuarioDAO.deleteMiembro(usuario, equipo);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
