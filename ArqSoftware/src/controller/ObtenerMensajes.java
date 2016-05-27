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

import socialnetwork.ControlMensajes;

/**
 * Clase correspondiente a la capa de presentacion, concretamente esta clase implementa al servlet 
 * que se encarga de gestionar las peticiones para obtener los mensajes que posea un usuario determinado.
 * <p>
 * @author Grupo 1 - Arquitectura Software. Universidad de Zaragoza.
 *
 */
@WebServlet("/ObtenerMensajes")
public class ObtenerMensajes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObtenerMensajes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		obtenerMensajes(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		insertarMensaje(request,response);
	}
	
	/**
	 * Trata la peticion para mostrar las publicaciones en el home
	 */
	private void obtenerMensajes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		String respuesta = "";
		String respuesta1 = "";
		try {
			respuesta = ControlMensajes.usersSeguidoresMutuos(Integer.parseInt(userId));
			respuesta = respuesta.substring(0, respuesta.length()-1);
			respuesta1 = ControlMensajes.listMensajesUser(Integer.parseInt(userId));
			respuesta1 = respuesta1.substring(1, respuesta1.length());
			respuesta = respuesta + ", " + respuesta1;
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
	
	/**
	 * Gestiona las peticiones de insertar un mensaje en la base de datos
	 */
	private void insertarMensaje(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
		int idReceptor = Integer.parseInt(request.getParameter("idReceptor"));
		String cuerpo = request.getParameter("cuerpoMensaje");
		
		ControlMensajes.envioMensaje(Integer.parseInt(userId), idReceptor, cuerpo);
		
		response.sendRedirect("/ArqSoftware/Social-Network/mensajes.html");
	}

}
