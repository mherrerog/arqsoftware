package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datos.Usuario;
import gateway.UsuarioDAO;
import socialnetwork.Fechas;

/**
 * Servlet implementation class EditarPerfil
 */
@WebServlet("/EditarPerfil")
public class EditarPerfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditarPerfil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		Usuario deleted = new Usuario(Integer.parseInt(userId),"",null,"","","","",0,"","","");
		try {
			UsuarioDAO.delete(deleted);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("/ArqSoftware/Social-Network/index.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		String username = request.getParameter("username");
		String date = request.getParameter("date");
		String password = request.getParameter("password");
		String genero = request.getParameter("genero");
		String descripcion = request.getParameter("descripcion");
		Usuario user=null;
		
		try {
			user=UsuarioDAO.selectById(Integer.parseInt(userId));
			int count=0;
			
			if(username==""){
				username=user.getNombre();
				count++;
			}
			if(date==""){
				date=user.getFechaString();
				count++;
			}
			if(password==""){
				password=user.getPassword();
				count++;
			}
			if(genero==null){
				genero=user.getSexo();
				count++;
			}
			if(descripcion==""){
				descripcion=user.getDescripcion();
				count++;
			}
			
			if(count!=5){
				Usuario updated = new Usuario(Integer.parseInt(userId),username,Fechas.getFechaFromWeb(date),genero,"","",password,0,"","",descripcion);
				UsuarioDAO.update(updated);
			}		
			response.sendRedirect("/ArqSoftware/Social-Network/home.html");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
