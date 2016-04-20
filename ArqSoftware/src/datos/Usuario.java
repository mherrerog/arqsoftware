package datos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import gateway.PublicacionDAO;
import gateway.UsuarioDAO;
import socialnetwork.Fechas;

public class Usuario {

	private String nombre;
	private int id;
	private String sexo;
	private String email;
	private String nick;
	private String password;
	private Date fecha;
	private int equipo;
	private String logo;
	private String fondo;
	private String descripcion;
	

	/**
	 * Metodo constructor
	 */
	public Usuario(int id, String nombre, Date fecha, String sexo, String email, 
			String nick, String password, int equipo, String logo, String fondo, String descripcion){
		this.id = id;
		this.nombre = nombre;
		this.sexo = sexo;
		this.fecha = fecha;
		this.email = email;
		this.nick = nick;
		this.password = password;
		this.equipo = equipo;
		this.logo = logo;
		this.fondo = fondo;
		this.descripcion = descripcion;
	}
	
	/**
	 * Metodo constructor sin id
	 */
	public Usuario(String nombre, Date fecha, String sexo, String email, 
			String nick, String password, int equipo, String logo, String fondo, String descripcion){
		this.id = 0;
		this.nombre = nombre;
		this.sexo = sexo;
		this.fecha = fecha;
		this.email = email;
		this.nick = nick;
		this.password = password;
		this.equipo = equipo;
		this.logo = logo;
		this.fondo = fondo;
		this.descripcion = descripcion;
	}
	
	
	/**
	 * 
	 */
	public String toJSON(){
		
		JSONObject obj = new JSONObject();
		
		// Atributos de publicacion
		obj.put("Id", id);
		obj.put("nombre", nombre);
		obj.put("nick", nick);
		obj.put("equipo", equipo);
		obj.put("logo", logo);
		obj.put("fondo", fondo);
		obj.put("fecha", fecha);
		obj.put("sexo", sexo);
		//obj.put("deporte", deporte);
		//obj.put("descripcion", descripcion);
		
		return obj.toString();
	}
	
	/**
	 * Devuelve un String en formato JSON con el contenido de la publicacion
	 */
	public static String toJSON(ArrayList<Usuario> vector){
		
		String rs = "{\"usuarios\": [\n";
		for (Usuario d: vector){
			
			rs += d.toJSON() + ",\n";
		}
		
		// Consulta vacia?
		if (!vector.isEmpty()){
			int end = rs.lastIndexOf(',');
			rs = rs.substring(0, end);	// Elimina la última coma puesta
		}
		
		return rs + "\n]}";
	}
	
	// Getters & Setters
	public String getNombre() {
		return nombre;
	}

	public int getId() {
		return id;
	}

	public String getSexo() {
		return sexo;
	}

	public String getEmail() {
		return email;
	}

	public String getNick() {
		return nick;
	}

	public String getPassword() {
		return password;
	}

	public Date getFecha() {
		return fecha;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public String getFechaString() {
		// Año
		String yy = "" + fecha.getYear();
		while (yy.length() < 4){
			yy = "0" + yy;
		}
		// Meses
		String mm = "" + fecha.getMonth();
		while (mm.length() < 2){
			mm = "0" + mm;
		}
		// Dia
		String dd = "" + fecha.getDay();
		while (dd.length() < 2){
			dd = "0" + dd;
		}
		return yy + mm + dd;
	}
	
	/**
	 * Devuele un entero positivo si el
	 * usuario es un equipo
	 */
	public int getEquipo() {
		if (equipo > 0) return 1;
		else return 0;
	}
	
	public String getLogo() {
		return logo;
	}
	
	public String getFondo() {
		return fondo;
	}


	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", id=" + id + ", sexo=" + sexo + ", email=" + email + ", nick=" + nick
				+ ", password=" + password + ", fecha=" + fecha + ", equipo=" + equipo + ", logo=" + logo + ", fondo="
				+ fondo + "]";
	}
	
	

}
