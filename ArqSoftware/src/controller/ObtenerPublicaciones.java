package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import socialnetwork.ControlPublicaciones;

/**
 * Servlet implementation class ObtenerPublicaciones
 */
@WebServlet("/ObtenerPublicaciones")
public class ObtenerPublicaciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObtenerPublicaciones() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		obtenerPublicaciones(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * Trata la peticion para mostrar las publicaciones en el home
	 */
	private void obtenerPublicaciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cookieName = "userId";
		String userId = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) 
		{
		    for(int i=0; i<cookies.length; i++) 
		    {
		        Cookie cookie = cookies[i];
		        if (cookieName.equals(cookie.getName())) 
		        {
		        	userId = (cookie.getValue());
		        }
		    }
		}
		
		String peticion = request.getParameter("page");
		//int usuario = (int) sesion.getAttribute("usuario");

		String respuesta = "";
		try {
			// Peticion en el home
			if (peticion.compareTo("home") == 0){
				respuesta = ControlPublicaciones.showHome(Integer.parseInt(userId));
			}
			// Peticion en el perfil
			else if (peticion.compareTo("profile") == 0) {
				int actual = Integer.parseInt(userId);
				String usuario = request.getParameter("user");
				System.out.println(usuario);
				if (usuario.compareTo("false") != 0){
					// Peticion de perfil de usuario distinto al propio
					int u = Integer.parseInt(usuario);
					respuesta = ControlPublicaciones.showProfile(actual, u);
				} else {
					// Peticion del perfil del usuario de la sesion					
					respuesta = ControlPublicaciones.showProfile(actual, actual);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Debug
		System.out.println(respuesta);
		
		
		response.setContentType("application/json");
		// Get the printwriter object from response to write the required json object to the output stream      
		PrintWriter out = response.getWriter();
		// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
		out.print(respuesta);
		out.flush();
	}

}
