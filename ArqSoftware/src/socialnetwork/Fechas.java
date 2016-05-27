package socialnetwork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Clase que tiene el objetivo de controlar los formatos de las fechas para 
 * insertarlas a la base de datos o para mostrarlas en la web.
 * 
 * @author Grupo 1 - Arquitectura Software. Universidad de Zaragoza.
 *
 */
public class Fechas {
	
	/**
	 * Devuelve la fecha en formato para publicarse, compara la fecha actual
	 * con la fecha en la que se realizo la publicacion, el comentario, el
	 * mensaje... y devuelve un string con el formato: hoy a las hh:mm, ayer
	 * a las hh:mm o hace x dias a las hh:mm.
	 * <p>
	 * Ademas este metodo tienen en cuenta que la hora hay que cambiarla al 
	 * uso horario de Madrid - Europa. 
	 *
	 * @param  d	Fecha en la que se realizo la publicacion, el comentario...
	 * @return      Un string con la fecha en formato para publicarse
	 */
	public static String getFechaToWeb(Date d){
		TimeZone timeZone = TimeZone.getTimeZone("Europe/Madrid");
		
		Date hoy = new Date();
		
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeZone(timeZone);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeZone(timeZone);
		
		cal1.setTime(hoy);
	    cal2.setTime(d);
	    
	    int yy1 = cal1.get(Calendar.YEAR);
	    int yy2 = cal2.get(Calendar.YEAR);
	    int mm1 = cal1.get(Calendar.MONTH);
	    int mm2 = cal2.get(Calendar.MONTH);
	    int dd1 = cal1.get(Calendar.DAY_OF_MONTH);
	    int dd2 = cal2.get(Calendar.DAY_OF_MONTH);
	    String hh = "" + cal2.get(Calendar.HOUR_OF_DAY);
	    String min = "" + cal2.get(Calendar.MINUTE);
	    
	    
	    // Hora
		while (hh.length() < 2){
			hh = "0" + hh;
		}
		// Minutos
		while (min.length() < 2){
			min = "0" + min;
		}
	    
	    if (yy1 == yy2 && mm1 == mm2){
	    	if(dd1 == dd2){
	    		// Hoy
	    		return "Hoy a las " + hh +":" + min;
	    	}
	    	else if(dd1== (dd2 + 1)){
	    		// Ayer
	    		return "Ayer a las " + hh +":" + min;
	    	}
	    	else if ((dd1 - dd2) < 15){
	    		return "Hace "+ (dd1 - dd2) + " días a las " + hh +":" + min;
	    	}
	    }
	    else {
	    	return dd2 + "/" + mm2 + "/" + yy2 + " a las " + hh +":" + min;
	    }
	    
		return "";
	}
	
	/**
	 * Metodo que dados dos cadenas de caracteres que representan la fecha 
	 * y la hora, devuelve el objeto Date correspondiente a la fecha y la 
	 * hora dadas por parametro.
	 * 
	 * @param fecha	string que representa una fecha.
	 * @param hora	string que representa una hora.
	 * @return	devuelve el objeto Date correspondiente a la fecha y la hora.
	 */
	public static Date getFechaFromWeb(String fecha, String hora){
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
	
	/**
	 * Metodo que dado una cadena de caracteres que representa una fecha, 
	 * devuelve el objeto Date correspondiente a la fecha dada por parametro.
	 * 
	 * @param fecha	string que representa una fecha.
	 * @return	devuelve el objeto Date correspondiente a la fecha.
	 */
	public static Date getFechaFromWeb(String fecha){
		Date nueva = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		try {
			nueva = sdf.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nueva;
	    
	}
	
	/**
	 * Metodo que dado un objeto Date que representa una fecha valida, 
	 * devuelve la cadena de caracteres correspondiente a la fecha representada 
	 * por el objeto Date.
	 * 
	 * @param fecha	objeto Date que representa una fecha valida
	 * @return	devuelve la fecha en una cadena de caracteres correspondiente 
	 * al objeto Date.
	 */
	public static String getFechaString(Date fecha) {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(fecha);
		// Año
		String yy = "" + cal.get(Calendar.YEAR);
		while (yy.length() < 4){
			yy = "0" + yy;
		}
		// Meses
		String mm = "" + (cal.get(Calendar.MONTH) + 1);	// Mes 0 -> Enero
														// En los nuestros datos: Enero -> mes 1
		while (mm.length() < 2){
			mm = "0" + mm;
		}
		// Dia
		String dd = "" + cal.get(Calendar.DAY_OF_MONTH);
		while (dd.length() < 2){
			dd = "0" + dd;
		}
		return yy + mm + dd;
	}
	
	/**
	 * Metodo que dado un objeto Date que representa una fecha valida, 
	 * devuelve la cadena de caracteres correspondiente a la hora representada 
	 * por el objeto Date.
	 * 
	 * @param fecha	objeto Date que representa una fecha valida
	 * @return	devuelve la hora en una cadena de caracteres correspondiente 
	 * al objeto Date.
	 */
	public static String getHoraString(Date fecha){
		Calendar cal = Calendar.getInstance();
	    cal.setTime(fecha);
	    String hh = "" + cal.get(Calendar.HOUR_OF_DAY);
	    while (hh.length() < 2){
			hh = "0" + hh;
		}
	    String mm = "" + cal.get(Calendar.MINUTE);
	    while (mm.length() < 2){
			mm = "0" + mm;
		}
	    return hh + mm;
	}
	
	
}
