package controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datos.Usuario;
import socialnetwork.ControlUsuarios;
import socialnetwork.Fechas;

/**
 * Clase correspondiente a la capa de presentacion, concretamente esta clase implementa al servlet 
 * que se encarga de gestionar las peticiones de inicio de sesion de usuarios.
 * <p>
 * @author Grupo 1 - Arquitectura Software. Universidad de Zaragoza.
 *
 */
@WebServlet("/InicioSesion")
public class InicioSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InicioSesion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		int metodo = Integer.parseInt(request.getParameter("id"));
		
		if (metodo == 2) {
			Cookie userCookie = new Cookie("userId", "");
			userCookie.setMaxAge(0);
			response.addCookie(userCookie);
			response.sendRedirect("/ArqSoftware/Social-Network/index.html");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		int metodo = Integer.parseInt(request.getParameter("id"));
		
		/**
		 * Si metodo == 0 -> inicio de sesion de un usuario.
		 * si metodo == 1 -> registro de un nuevo usuario.
		 */
		if (metodo == 0) {
			Usuario user;
			int i = inicioSesion(request);
			if (i == 0){
				user = ControlUsuarios.buscarUser(request.getParameter("mail"));
				Cookie userCookie = new Cookie("userId", ""+user.getId());
				userCookie.setMaxAge(60*15); //15 minutos
				response.addCookie(userCookie);
				response.sendRedirect("/ArqSoftware/Social-Network/home.html");
			}else{
				String error = "";
				if (i == 1) error = "passwd";
				else if (i == 2) error = "nulo";
				//Informar error al usuario.
				response.sendRedirect("/ArqSoftware/Social-Network/index.html?error=" 
						+ error);
			}
		}else if (metodo == 1) {
			String email = request.getParameter("email");
			int error = registroUsuario(request);
			if ( error == 0){
				Usuario user;
				user = ControlUsuarios.buscarUser(email);
				Cookie userCookie = new Cookie("userId", ""+user.getId());
				userCookie.setMaxAge(-1); 
				response.addCookie(userCookie);
				response.sendRedirect("/ArqSoftware/Social-Network/home.html");
			}else{
				//Informar error al usuario.
				String err = "";
				if (error == 1) err = "passwd";
				else if (error == 2) err = "mail";
				response.sendRedirect("/ArqSoftware/Social-Network/signup.html?error=" + err);
			} 
		}
	}
	
	/**
	 * Consulta el inicio de sesión en la base de datos
	 * @return 
	 * 	0 -> OK
	 * 	1 -> Contraseña no coincide
	 * 	2 -> Usuario no registrado
	 */
	private static int inicioSesion(HttpServletRequest request) {
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		
		Usuario iniciandoSesion = ControlUsuarios.buscarUser(mail);
		if (iniciandoSesion == null){
			// Usuario desconocido
			return 2;
		}
		if (iniciandoSesion.getPassword().compareTo(getMD5(password))==0){
			// Proceso correcto
			return 0;
		}else{
			// Contraseña incorrecta
			return 1;
		}
	}
	
	/**
	 * Inserta un usuario en la base de datos
	 * @return 
	 * 	0 -> OK
	 * 	1 -> Contraseña no coincide
	 * 	2 -> Usuario registrado
	 */
	private static int registroUsuario(HttpServletRequest request) {
		String username = request.getParameter("username");
		String date = request.getParameter("date");
		Date fecha = Fechas.getFechaFromWeb(date);
		String nick = request.getParameter("nick");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password_confirm = request.getParameter("password_confirm");
		String genero = request.getParameter("genero");
		String team = request.getParameter("team");
		String logo = request.getParameter("logo");
		String descripcion = request.getParameter("descripcion");
		int equipo = 0;
		if (team != null) {
			equipo = 1;
		}
		
		if (password.compareTo(password_confirm) == 0){
			String pass = getMD5(password);
			ControlUsuarios.anadirUsuario(username, fecha, genero, email, 
					nick, pass, equipo, logo, null, descripcion);
			return 0;
			
		}else{
			return 1;
		}
	}
	
	/**
	 * Metodo que dada una cadena de caracteres que representa el password
	 * de un usuario determinado, le aplica un cifrado para que el password
	 * no sea visible sin aplicarse un descifrado.
	 * 
	 * @param input	cadena de caracteres que representa el password de un usuario
	 * determinado.
	 * 
	 * @return	devuelve una cadena de caracteres que representa el password del
	 * usuario pero cifrado.
	 */
	private static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
