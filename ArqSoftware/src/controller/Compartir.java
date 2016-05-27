package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import socialnetwork.ControlPublicaciones;

/**
 * Clase correspondiente a la capa de presentacion, concretamente esta clase implementa al servlet 
 * que se encarga de gestionar las peticiones de compartir publicaciones.
 * <p>
 * @author Grupo 1 - Arquitectura Software. Universidad de Zaragoza.
 *
 */
@WebServlet("/Compartir")
public class Compartir extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Compartir() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		compartir(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * Metodo que perimite a un usuario compartir una determinada publicacion
	 */
	private void compartir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		int publicacion = Integer.parseInt(request.getParameter("pub"));
		ControlPublicaciones.borrarPublicacion(Integer.parseInt(userId), publicacion);
		response.sendRedirect("/ArqSoftware/Social-Network/home.html");
	}

}
