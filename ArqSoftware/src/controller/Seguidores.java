package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import socialnetwork.ControlUsuarios;

/**
 * Clase correspondiente a la capa de presentacion, concretamente esta clase implementa al servlet 
 * que se encarga de gestionar las peticiones para obtener los usuarios seguidos y seguidores que 
 * posea un usuario determinado.
 * <p>
 * @author Grupo 1 - Arquitectura Software. Universidad de Zaragoza.
 *
 */
@WebServlet("/Seguidores")
public class Seguidores extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Seguidores() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		int metodo = Integer.parseInt(request.getParameter("metodo"));
		String id = request.getParameter("id");
		String result = "";
		
		if (metodo == 0) {result = ControlUsuarios.buscarSeguidos(Integer.parseInt(id));}
		else if (metodo == 1) {result = ControlUsuarios.buscarSeguidores(Integer.parseInt(id));}
		
		response.setContentType("application/json");
		// Get the printwriter object from response to write the required json object to the output stream      
		PrintWriter out = response.getWriter();
		// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
		out.print(result);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");		
	}
	

}
