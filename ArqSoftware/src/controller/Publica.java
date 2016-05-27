package controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import socialnetwork.ControlPublicaciones;

/**
 * Clase correspondiente a la capa de presentacion, concretamente esta clase implementa al servlet 
 * que se encarga de gestionar las peticiones para añadir una nueva publicacion.
 * <p>
 * @author Grupo 1 - Arquitectura Software. Universidad de Zaragoza.
 *
 */
@WebServlet("/Publica")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)   // 50MB
public class Publica extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Publica() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		nuevaPublicacion(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		nuevaPublicacion(request, response);
	}

	/**
	 * Gestiona la peticion de insertar una nueva publicacion.
	 */
	private void nuevaPublicacion(HttpServletRequest request, HttpServletResponse response){
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
		
		// Obtener campos del formulario
		String texto = request.getParameter("textPublicacion");
		Date fecha = new Date();
		
		String link = request.getParameter("youtube");
		String aux = null;
		
		try {
			aux = Descarga.downloadMap(request);
		} catch (ServletException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (aux != null) link = aux;
		
		String deporte = request.getParameter("deporte");
		
		ControlPublicaciones.nuevaPub(Integer.parseInt(userId),texto,fecha,link,deporte);
		
		try {
			response.sendRedirect("/ArqSoftware/Social-Network/home.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
