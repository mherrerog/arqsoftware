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
 * Servlet implementation class ObtenerMiembros
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
		buscarMiembros(request, response);
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
