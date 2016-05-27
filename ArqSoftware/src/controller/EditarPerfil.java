package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

import javax.naming.NamingException;
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
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
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
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
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
		String logo = request.getParameter("logo");
		Usuario user=null;
		
		try {
			user=UsuarioDAO.selectById(Integer.parseInt(userId));
			int count=0;
			
			if(username.compareTo("")==0){
				username=user.getNombre();
				count++;
			}
			if(date.compareTo("")==0){
				date=user.getFechaString();
				count++;
			}
			if(password.compareTo("")==0){
				password=user.getPassword();
				count++;
			}else if (password.compareTo("")!=0) {
				password = getMD5(password);
			}
			if(genero==null){
				genero=user.getSexo();
				count++;
			}
			if(descripcion.compareTo("")==0){
				descripcion=user.getDescripcion();
				count++;
			}
			if(logo.compareTo("")==0){
				logo=user.getLogo();
				count++;
			}
			if(count!=6){
				Usuario updated = new Usuario(Integer.parseInt(userId),username,Fechas.getFechaFromWeb(date),genero,"","",password,0,logo,"",descripcion);
				UsuarioDAO.update(updated);
			}		
			response.sendRedirect("/ArqSoftware/Social-Network/home.html");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Metodo que dada una cadena de caracteres que representa el password
	 * de un usuario determinado, le aplica un cifrado para que el password
	 * no sea visible sin aplicarse un descifrado.
	 * 
	 * @param input	cadena de caracteres que representa el password de un usuario
	 * determinado.
	 * 
	 * @return	devuelve una cadena de caracteres que representa el password del
	 * usuario pero cifrado.
	 */
	private static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
