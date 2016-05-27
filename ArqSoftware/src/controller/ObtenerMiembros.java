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
 * que se encarga de gestionar las peticiones para obtener los miembros que posea un equipo determinado.
 * <p>
 * @author Grupo 1 - Arquitectura Software. Universidad de Zaragoza.
 *
 */
@WebServlet("/ObtenerMiembros")
public class ObtenerMiembros extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObtenerMiembros() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		buscarMiembros(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}
	
	/**
	 * Gestiona la peticion de buscar todos aquellos usuarios que pertenezcan a un equipo
	 */
	private void buscarMiembros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int equipo = 0;
		equipo = Integer.parseInt(request.getParameter("user"));
		String result = ControlUsuarios.buscarMiembros(equipo);
		
		//Debug
		System.out.println("> Equipo \n" +result);
		
		response.setContentType("application/json");
		// Get the printwriter object from response to write the required json object to the output stream      
		PrintWriter out = response.getWriter();
		// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
		out.print(result);
		out.flush();		
	}

}
