package socialnetwork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Fechas {
	
	public static String getFechaToWeb(Date d){
		Date hoy = new Date();
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
	    cal1.setTime(hoy);
	    cal2.setTime(d);
	    int yy1 = cal1.get(Calendar.YEAR);
	    int yy2 = cal2.get(Calendar.YEAR);
	    int mm1 = cal1.get(Calendar.MONTH);
	    int mm2 = cal2.get(Calendar.MONTH);
	    int dd1 = cal1.get(Calendar.DAY_OF_MONTH);
	    int dd2 = cal2.get(Calendar.DAY_OF_MONTH);
	    String hh = "" + d.getHours();
	    String min = "" + d.getMinutes();
	    
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
	 * 
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
	 * 
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
