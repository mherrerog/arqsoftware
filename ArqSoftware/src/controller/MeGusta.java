package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gateway.PublicacionDAO;

/**
 * Servlet implementation class MeGusta
 */
@WebServlet("/MeGusta")
public class MeGusta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MeGusta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addLike(request, response);
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
	private void addLike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Obtener usuario
		//HttpSession sesion = request.getSession();
		//int usuario = (int) sesion.getAttribute("usuario");
		int usuario = 1;
		int publicacion = Integer.parseInt(request.getParameter("pub"));
		
		try {
			PublicacionDAO.insertLike(usuario, publicacion);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("/ArqSoftware/Social-Network/home.html");
	}

}
