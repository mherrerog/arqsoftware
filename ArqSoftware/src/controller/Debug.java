package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Debug
 */
@WebServlet("/Debug")
public class Debug extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PATH = "/var/lib/openshift/56e1800089f5cf548100012e/"
			+ "jbossews/errores/Debug.txt";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Debug() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File f = new File(PATH);
		FileWriter fw = null;
		PrintWriter pw = null;
		try{
			fw = new FileWriter(f, true);
			pw  = new PrintWriter(fw);
            
			String linea = request.getParameter("descripcion");
			
			pw.println("Error:");
			pw.println(linea);
			pw.println("===================================");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
	           if (null != pw)
	              pw.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
		
		response.sendRedirect("/ArqSoftware/Social-Network/home.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
