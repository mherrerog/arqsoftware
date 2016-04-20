package datos;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.json.JSONObject;

import gateway.PublicacionDAO;
import gateway.UsuarioDAO;
import socialnetwork.Fechas;

public class Publicacion implements Comparable{

	private int id;
	private int autor;
	private String texto;
	private Date fecha;
	private ArrayList<Usuario> compartido;
	private ArrayList<Usuario> megusta;
	private ArrayList<Comentario> comentarios;
	private String imagen;
	private String video;
	private String ruta;
	private String deporte;
	
	/**
	 * @param id
	 * @param autor
	 * @param texto
	 * @param fecha
	 * @param imagen
	 * @param video
	 * @param ruta
	 */
	public Publicacion(int id, int autor, String texto, Date fecha, String imagen, String video, 
			String ruta, String deporte) {
		this.id = id;
		this.autor = autor;
		this.texto = texto;
		this.fecha = fecha;
		this.imagen = imagen;
		this.video = video;
		this.ruta = ruta;
		this.deporte = deporte;
	}
	
	public Publicacion(int autor, String texto, Date fecha, String imagen, String video, 
			String ruta, String deporte) {
		this.autor = autor;
		this.texto = texto;
		this.fecha = fecha;
		this.imagen = imagen;
		this.video = video;
		this.ruta = ruta;
		this.deporte = deporte;
	}


	/**
	 * Metodo constructor
	 */
	public Publicacion(int id, int autor, String texto, Date fecha, ArrayList<Usuario> compartido, 
			ArrayList<Usuario> megusta, ArrayList<Comentario> comentarios) {
		this.id = id;
		this.autor = autor;
		this.texto = texto;
		this.fecha = fecha;
		this.deporte = deporte;
		this.compartido = compartido;
		this.megusta = megusta;
		this.comentarios = comentarios;
	}
	
	public static ArrayList<Publicacion> unirPublicaciones(ArrayList<Publicacion> a,
			ArrayList<Publicacion> b){
		ArrayList<Publicacion> union = a;
		for (Publicacion sig: b){
			union.add(sig);
		}
		Collections.sort(union);	//Ordena la lista de forma ascendente
		return union;
		
	}

	//Setters & Getters
	public int getId() {
		return id;
	}
	
	public int getAutor() {
		return autor;
	}

	public String getTexto() {
		return texto;
	}

	public Date getFecha() {
		return fecha;
	}
	
	public String getFechaString() {
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
	
	public String getHoraString() {
		// Hora
		String hh = "" + fecha.getHours();
		while (hh.length() < 2){
			hh = "0" + hh;
		}
		// Minutos
		String min = "" + fecha.getMinutes();
		while (min.length() < 2){
			min = "0" + min;
		}
		return hh + min;
	}

	public String getDeporte() {
		return deporte;
	}

	public ArrayList<Usuario> getCompartido() {
		return compartido;
	}

	public ArrayList<Usuario> getMegusta() {
		return megusta;
	}

	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}
	
	public String getImagen() {
		return imagen;
	}

	public String getVideo() {
		return video;
	}

	public String getRuta() {
		return ruta;
	}

	/**
	 * Pre: o tipo Publicacion
	 * Compara las fechas de dos publicaciones
	 */
	public int compareTo(Object o) {
		Publicacion otra = (Publicacion) o;
		return otra.fecha.compareTo(fecha);
	}

	@Override
	public String toString() {
		return "Publicacion [id=" + id + ", autor=" + autor + ", texto=" + texto + ", fecha=" + fecha + ", deporte="
				+ deporte + ", compartido=" + compartido + ", megusta=" + megusta + ", comentarios=" + comentarios
				+ ", imagen=" + imagen + ", video=" + video + ", ruta=" + ruta + "]";
	}
	
	/**
	 * 
	 */
	public String toJSON(){
		Usuario u = null;
		int likes = 0;
		try {
			u = UsuarioDAO.selectById(autor);
			System.out.println(u);
			
			likes = PublicacionDAO.selectLikes(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String f = Fechas.getFechaToWeb(fecha);
		
		JSONObject obj = new JSONObject();
		
		// Atributos de usuario
		obj.put("nombre", u.getNombre());
		obj.put("nick", u.getNick());
		obj.put("logo", u.getLogo());
		obj.put("equipo", u.getEquipo());
		
		// Likes
		obj.put("likes", likes);
		
		// Atributos de publicacion
		obj.put("Id", id);
		obj.put("texto", texto);
		obj.put("fecha", f);
		obj.put("img", imagen);
		obj.put("video", video);
		obj.put("ruta", ruta);
		obj.put("deporte", deporte);
		obj.put("idUsuario", autor);
		
		return obj.toString();
	}
	
	/**
	 * Devuelve un String en formato JSON con el contenido de la publicacion
	 */
	public static String toJSON(ArrayList<Publicacion> vector){
		
		String rs = "{\"publicaciones\": [\n";
		for (Publicacion d: vector){
			
			rs += d.toJSON() + ",\n";
		}
		
		// Consulta vacia?
		if (!vector.isEmpty()){
			int end = rs.lastIndexOf(',');
			rs = rs.substring(0, end);	// Elimina la última coma puesta
		}
		
		return rs + "\n]}";
	}
	
}
