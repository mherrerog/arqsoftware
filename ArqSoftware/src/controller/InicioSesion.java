package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datos.Usuario;
import gateway.UsuarioDAO;
import socialnetwork.Fechas;

/**
 * Servlet implementation class InicioSesion
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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int metodo = Integer.parseInt(request.getParameter("id"));
		
		/**
		 * Si metodo == 0 -> inicio de sesion de un usuario.
		 * si metodo == 1 -> registro de un nuevo usuario.
		 */
		if (metodo == 0) {
			
			if (inicioSesion(request)){
				Usuario user;
				try {
					user = UsuarioDAO.selectByMail((String) request.getParameter("mail"));
					Cookie userCookie = new Cookie("userId", ""+user.getId());
					userCookie.setMaxAge(60*15); //15 minutos
					response.addCookie(userCookie);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response.sendRedirect("/ArqSoftware/Social-Network/home.html");
			}else{
				//Informar error al usuario.
				PrintWriter out = response.getWriter();
				out.print("ERROR");
				out.flush();
			}
		}else if (metodo == 1) {
			if (registroUsuario(request)){
				HttpSession mySession = request.getSession();
				mySession.setAttribute("userMail", request.getAttribute("mail"));
				response.sendRedirect("/ArqSoftware/Social-Network/home.html");
			}else{
				//Informar error al usuario.
				PrintWriter out = response.getWriter();
				out.print("ERROR");
				out.flush();
			}
		}
	}
	
	private static boolean inicioSesion(HttpServletRequest request) {
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		
		try {
			Usuario iniciandoSesion = UsuarioDAO.selectByMail(mail);
			if (iniciandoSesion.getPassword().compareTo(password)==0){
				return true;
			}else{
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private static boolean registroUsuario(HttpServletRequest request) {
		String username = request.getParameter("username");
		String date = request.getParameter("date");
		Date fecha = Fechas.getFechaFromWeb(date);
		String nick = request.getParameter("nick");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password_confirm = request.getParameter("password_confirm");
		String genero = request.getParameter("genero");
		String team = request.getParameter("team");
		String descripcion = request.getParameter("descripcion");
		int equipo = 0;
		if (team != null) {
			equipo = 1;
		}
		
		try {
			if (password.compareTo(password_confirm) == 0){
				Usuario nuevo = new Usuario(username, fecha, genero, email, 
						nick, password, equipo, null, null, descripcion);
				System.out.println(nuevo);
				//Passwords iguales, realizamos el insert.
				UsuarioDAO.insert(nuevo);
				return true;
				
			}else{
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
