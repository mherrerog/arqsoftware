package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UploadServlet
 */
public class Descarga {

	private static final String RUTA = "pruebas";

	public static void downloadImg(HttpServletRequest request)
			throws ServletException, IOException {
		
		// Debug
		System.out.println("Procesando descarga...");
					
		// gets absolute path of the web application
		String appPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String savePath = appPath + File.separator + RUTA;

		// creates the save directory if it does not exists
		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		Part p = request.getPart("myPhoto");
		String fileName = extractFileName(p);
		
		// Debug
		String path = new File(".").getCanonicalPath();
		System.out.println("> " + path + "/" +fileName);
		
		//p.write(path + "/img/" + fileName);
	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private static String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

	/*
	 * public static void downloadImg(HttpServletRequest request) throws
	 * ServletException, IOException {
	 * 
	 * // Create path components to save the file String path = RUTA; Part
	 * filePart = request.getPart("myPhoto"); //String fileName =
	 * getFileName(filePart); String fileName = "prueba.png";
	 * 
	 * OutputStream out = null; InputStream filecontent = null;
	 * 
	 * try { File f = new File(path + File.separator + fileName);
	 * f.getParentFile().mkdirs(); f.createNewFile(); out = new
	 * FileOutputStream(f); filecontent = filePart.getInputStream();
	 * 
	 * int read = 0; final byte[] bytes = new byte[1024];
	 * 
	 * while ((read = filecontent.read(bytes)) != -1) { out.write(bytes, 0,
	 * read); }
	 * 
	 * // Debug System.out.println("New file " + fileName + " created at " +
	 * path);
	 * 
	 * } catch (FileNotFoundException fne) { // Error fne.printStackTrace();
	 * System.err.println("Error durante la descarga del archivo"); } finally {
	 * if (out != null) { out.close(); } if (filecontent != null) {
	 * filecontent.close(); } } }
	 * 
	 * private static String getFileName(Part part) {
	 * 
	 * for (String content : part.getHeader("content-disposition").split(";")) {
	 * if (content.trim().startsWith("filename")) { return content.substring(
	 * content.indexOf('=') + 1).trim().replace("\"", ""); } } return null; }
	 */

}