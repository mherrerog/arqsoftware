package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
		
		obtenerComentarios(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * 
	 */
	private void obtenerComentarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String publicacion = "";
		publicacion = request.getParameter("pub");
		int pub = Integer.parseInt(publicacion);
		
		String respuesta = ControlPublicaciones.getComentarios(pub);
		
		// Debug
		System.out.println(respuesta);
		
		response.setContentType("application/json");
		// Get the printwriter object from response to write the required json object to the output stream      
		PrintWriter out = response.getWriter();
		// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
		out.print(respuesta);
		out.flush();
	}

}
