package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import socialnetwork.ControlUsuarios;

/**
 * Servlet implementation class EditaEquipoMiembros
 */
@WebServlet("/EditaEquipoMiembros")
public class EditaEquipoMiembros extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditaEquipoMiembros() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		editaEquipo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * Elimina del equipo un usuario, ambos indicados como parametro
	 */
	private void editaEquipo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Adquirir parametros codificados en la url
		String usrAux = request.getParameter("usuario");
		String equipoAux = request.getParameter("equipo");
		String accion = request.getParameter("acc");
		
		
		int usuario = Integer.parseInt(usrAux);
		int equipo = Integer.parseInt(equipoAux);
		
		if (accion.compareTo("delete") == 0){
			// Borrado

			ControlUsuarios.eliminarMiembros(equipo, usuario);
		}
		else if (accion.compareTo("add") == 0){
			// Añadir
			// Debug
			System.out.println("Añadir: Usuario " + usuario + " - Equipo " + equipo);
			
			ControlUsuarios.anadirMiembros(equipo, usuario);
		}
		
		response.sendRedirect("/ArqSoftware/Social-Network/profile.html?user=" + equipo);
	}

}
