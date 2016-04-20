package Pruebas;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import datos.Publicacion;
import gateway.PublicacionDAO;
import gateway.UsuarioDAO;
import socialnetwork.Fechas;

public class Tests {
	
	public static void main (String [] args){
		selectUsuarioId();
	}
	
	private static void selectUsuarioId(){
		try {
			System.out.println(UsuarioDAO.selectById(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void horaActual(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
	}
	
	private static void nuevaPub(){
		Publicacion n;
		int autor;
		String texto;
		Date fecha;
		String img;
		String video;
		String ruta;
		String deporte;
		
		autor = 1;
		texto = "Pruebaa";
		fecha = toDate("19950402", "1222");
		img = null;
		video = null;
		ruta = null;
		deporte = "futbol";
		
		n = new Publicacion(autor, texto, fecha, img, video, ruta, deporte);
		
		System.out.println(n);
		
		System.out.println("Fecha: " + n.getFechaString() + " Hora: " + n.getHoraString());
		
		try {
			PublicacionDAO.insert(n);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Formato fecha: aaaammdd Formato hora: hhmm
	 */
	private static Date toDate(String fecha, String hora) {
		Date nueva = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmyyyyMMdd");
		try {
			nueva = sdf.parse(hora+fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nueva;
	}

}
