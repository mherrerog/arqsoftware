package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import socialnetwork.ControlPublicaciones;

/**
 * Servlet implementation class ObtenerComentarios
 */
@WebServlet("/ObtenerComentarios")
public class ObtenerComentarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObtenerComentarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		obtenerComentarios(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String acc = request.getParameter("acc");
		if (acc.compareTo("new") == 0){
			nuevoComentario(request, response);
		} 
		else {
			doGet(request, response);			
		}
	}
	
	/**
	 * Obtiene los comentarios de la publicación solicitada
	 */
	private void obtenerComentarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String publicacion = "";
		publicacion = request.getParameter("pub");
		int pub = Integer.parseInt(publicacion);
		
		String respuesta = "";
		try {
			respuesta = ControlPublicaciones.getComentarios(pub);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Debug
		System.out.println(respuesta);
		
		response.setContentType("application/json");
		// Get the printwriter object from response to write the required json object to the output stream      
		PrintWriter out = response.getWriter();
		// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
		out.print(respuesta);
		out.flush();
	}
	
	/**
	 * Almacena un nuevo comentario en la publicación indicada
	 */
	private void nuevoComentario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int usuario = 0;
		
		// Buscar en la cookies el usuario de la sesión
		String cookieName = "userId";
		String userId = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null){
		    for(int i=0; i<cookies.length; i++){
		        Cookie cookie = cookies[i];
		        if (cookieName.equals(cookie.getName())){
		        	userId = (cookie.getValue());
		        	usuario = Integer.parseInt(userId);
		        }
		    }
		}
		
		String comentario = request.getParameter("textComentario");
		int publicacion = Integer.parseInt(request.getParameter("idPublicacion"));
		
		// Debug
		System.out.println(">Pub: " + publicacion);
		
		ControlPublicaciones.insertComentario(usuario, publicacion, comentario);
		
		response.sendRedirect("/ArqSoftware/Social-Network/home.html");
	}

}
