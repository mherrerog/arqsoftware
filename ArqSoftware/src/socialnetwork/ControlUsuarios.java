package socialnetwork;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import datos.Usuario;
import gateway.UsuarioDAO;

/**
 * Clase correspondiente a la capa de logica de aplicacion, concretamente esta clase
 * tiene el objetivo de soportar la logica asociada a las usuarios.
 * 
 * @author Grupo 1 - Arquitectura Software. Universidad de Zaragoza.
 *
 */
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
	 * Añade al usuario pasado como parametro a la base de datos
	 */
	public static void anadirUsuario(String username, Date fecha, String genero, String email, 
			String nick, String pass, int equipo, String logo, String fondo, String descripcion){
		try {
			Usuario nuevo = new Usuario(username, fecha, genero, email, 
					nick, pass, equipo, logo, fondo, descripcion);
			//Passwords iguales, realizamos el insert.
			UsuarioDAO.insert(nuevo);
			
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
	
	/**
	 * Devuelve el usuario cuyo mail es pasado como parametro
	 */
	public static Usuario buscarUser(String mail){
		Usuario users = null;
		try {
			users = UsuarioDAO.selectByMail(mail);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	/**
	 * Devuelve el usuario cuyo id es pasado como parametro
	 */
	public static Usuario buscarUserId(int userId){
		Usuario users = null;
		try {
			users = UsuarioDAO.selectById(userId);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	/**
	 * Actualiza los campos del usuario cuyo id es userId
	 */
	public static void updateUser(int userId, String username, String date, String genero,
			String password, String logo, String descripcion){
		try {
			Usuario updated = new Usuario(userId,username,Fechas.getFechaFromWeb(date),
					genero,"","",password,0,logo,"",descripcion);
			UsuarioDAO.update(updated);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Elimina el usuario cuyo id es pasado como parametro
	 */
	public static void borrarUser(int userId){
		try {
			Usuario deleted = new Usuario(userId,"",null,"","","","",0,"","","");
			UsuarioDAO.delete(deleted);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sigue o deja de seguir el usuario userId al usuario user dependiendo de metodo:
	 * <p>
	 * Si metodo == 0 -> seguir al usuario user.
	 * si metodo == 1 -> dejar de seguir al usuario user.
	 */
	public static void seguirODejarSeguir(String userId, int user, int metodo){
		if (metodo == 0) {
			try {
				UsuarioDAO.insertUserSeguir(Integer.parseInt(userId), user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (metodo == 1) {
			try {
				UsuarioDAO.deleteUserSeguir(Integer.parseInt(userId), user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Metodo que busca los seguidores de un determinado usuario.
	 */
	public static String buscarSeguidores(int usuarioId){
		String result = "";
		try {
			ArrayList<Usuario> users = UsuarioDAO.selectSeguidores(usuarioId);
			result = Usuario.toJSON(users, usuarioId);
			
			// Debug
			System.out.println(result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Metodo que busca a aquellos usuarios que son seguidos por el usuario "usuarioId".
	 */
	public static String buscarSeguidos(int usuarioId){
		String result = "";
		try {
			ArrayList<Usuario> users = UsuarioDAO.selectSeguidos(usuarioId);
			result = Usuario.toJSON(users, usuarioId);
			
			// Debug
			System.out.println(result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
