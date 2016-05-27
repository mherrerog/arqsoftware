package datos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import gateway.PublicacionDAO;
import gateway.UsuarioDAO;
import socialnetwork.Fechas;

public class Publicacion {
	
	private final String PATH = "/confluence/";

	private int id;
	private int autor;
	private String texto;
	private Date fecha;
	private String imagen;
	private String video;
	private String ruta;
	private String deporte;
	private final String PERFIL = "https://image.freepik.com/iconos-gratis/perfil-macho-sombra-de-usuario_318-40244.png";

	
	/**
	 * Metodo constructor
	 * @param id identificador de la publicacion
	 * @param autor identificador del autor
	 * @param texto contenido textual de la publicacion
	 * @param fecha momento de la publicacion
	 * @param imagen enlace una imagen
	 * @param video enlace a un video de YouTube
	 * @param ruta nombre del archivo GPX
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
	
	/**
	 * Metodo constructor
	 * @param autor identificador del autor
	 * @param texto contenido textual de la publicacion
	 * @param fecha momento de la publicacion
	 * @param imagen enlace una imagen
	 * @param video enlace a un video de YouTube
	 * @param ruta nombre del archivo GPX
	 */
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

	//Setters & Getters
	/**
	 * Devuelve el identificador de la publicacion
	 * @return identificador de la publicacion
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Devuelve el autor de la publicacion
	 * @return identificador del autor
	 */
	public int getAutor() {
		return autor;
	}

	/**
	 * Devuelve el texto de la publicacion
	 * @return texto de la publicacion
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * Devuelve la fecha de la publicacion
	 * @return fecha de la publicacion
	 */
	public Date getFecha() {
		return fecha;
	}
	
	/**
	 * Devuelve la fecha de la publicacion
	 * @return fecha en formato aaaammdd
	 */
	public String getFechaString() {
		return Fechas.getFechaString(this.fecha);
	}
	
	/**
	 * Devuelve la hora de la publicacion
	 * @return hora en formato hhmm
	 */
	public String getHoraString() {
		return Fechas.getHoraString(this.fecha);
	}

	/**
	 * Devuelve el deporte de la publicacion
	 * @return deporte de la publicacion
	 */
	public String getDeporte() {
		return deporte;
	}
	
	/**
	 * Devuelve la url de la imagen de la publicacion
	 * @return imagen de la publicacion
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * Devuelve la url del video de la publicacion
	 * @return video de la publicacion
	 */
	public String getVideo() {
		return video;
	}

	/**
	 * Devuelve nombre completo del fichero de ruta
	 *  de la publicacion
	 * @return nombre del fichero GPX
	 */
	public String getRuta() {
		return ruta;
	}
	
	/**
	 * Pasa a formato JSON la publicacion
	 * @return publicacion en formato JSON
	 */
	public String toJSON(){
		Usuario u = null;
		int likes = 0;
		try {
			u = UsuarioDAO.selectById(autor);
			
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
		if (u.getLogo() != null){
			obj.put("logo", u.getLogo());
		}else{
			obj.put("logo", PERFIL);
		}
		obj.put("equipo", u.getEquipo());
		
		// Likes
		obj.put("likes", likes);
		
		// Atributos de publicacion
		obj.put("Id", id);
		obj.put("texto", texto);
		obj.put("fecha", f);
		obj.put("img", imagen);
		obj.put("video", video);
		if (ruta != null){
			if (ruta.contains(".gpx")){
				obj.put("ruta", PATH + ruta);
			} else {
				obj.put("ruta", "");
			}
		} else {
			obj.put("ruta", "");
		}
		obj.put("deporte", deporte);
		obj.put("idUsuario", autor);
		
		return obj.toString();
	}
	
	/**
	 * Pasa a formato JSON un vector de publicaciones
	 * @param vector publicaciones a parsear
	 * @return vector en formato JSON de publicaciones
	 */
	public static String toJSON(ArrayList<Publicacion> vector) {
		
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
