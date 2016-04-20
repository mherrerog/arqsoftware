package Pruebas;

import org.json.JSONObject;

public class JSON {
	
	public static void main (String [] args){
		JSONObject uno = new JSONObject();
		
		JSONObject dos = new JSONObject();
		
		uno.put("Num", 0);
		uno.put("Otro", "3");
		
		dos.put("JSON", uno);
		dos.put("TT", "@");
		
		System.out.println(dos);
	}

}
