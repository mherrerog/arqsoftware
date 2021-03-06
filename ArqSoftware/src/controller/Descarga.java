package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * Clase que tiene como objetivo descargar el archivo .gpx subido por los usuarios 
 * y almacenarlo en el directorio de almacenamiento de rutas.
 * <p>
 * @author Grupo 1 - Arquitectura Software. Universidad de Zaragoza.
 *
 */
public class Descarga {

	// Directorio donde se almacenarán las rutas
	private static final String RUTA = "/var/lib/openshift/56e1800089f5cf548100012e/jbossews/confluence/";

	public static String downloadMap(HttpServletRequest request)
			throws ServletException, IOException {
		
		// Debug
		System.out.println("Procesando descarga...");

		// creates the save directory if it does not exists
		File fileSaveDir = new File(RUTA);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		Part p = request.getPart("myGPX");
		String fileName = extractFileName(p);
		
		String newName = lookNames();
		
		if (fileName.contains(".gpx")){
			// Archivo gpx
			p.write(RUTA + newName);
			return newName;
		}
		
		return null;
		
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

	/**
	 * Devuelve el nuevo nombre del fichero
	 */
	private static String lookNames() {
		int max = 0;
		// Abrir directorio predeterminado
		File dir = new File(RUTA);
		File [] ficheros = dir.listFiles();
		if (ficheros.length == 0){
			return "0.gpx";
		}
		for (File f: ficheros){
			int aux = 0;
			
			try {
				String fName = f.getName();
				System.out.println(f.getName());
				if (fName.contains("gpx")){
					String [] n = f.getName().split("gpx");
					aux = Integer.parseInt(n[0].substring(0, n[0].length() - 1));
				}
			} catch (Exception e){
				e.printStackTrace();
			}
			if (aux > max) max = aux;
		}
		max++;
		return max + ".gpx";
	}
}