package datos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;
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
	// private String deporte;
	
	private final String PERFIL = "https://image.freepik.com/iconos-gratis/perfil-macho-sombra-de-usuario_318-40244.png";

	/**
	 * Metodo constructor
	 * @param id identificador del usuario
	 * @param nombre nombre completo del usuario
	 * @param fecha nacimiento del usuario
	 * @param sexo naturaleza del usuario
	 * @param email correo del usuario
	 * @param nick apodo del usaurio
	 * @param password clave de acceso del usuario
	 * @param equipo si equipo > 0 el usuario representa un equipo 
	 * @param logo direccion de la imagen de usuario
	 * @param fondo direccion del fondo de usuario
	 * @param descripcion texto sobre el usuario
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
	 * Metodo constructor
	 * @param id identificador del usuario
	 */
	public Usuario(int id){
		this.id = id;
	}
	
	/**
	 * Metodo constructor
	 * @param nombre nombre completo del usuario
	 * @param fecha nacimiento del usuario
	 * @param sexo naturaleza del usuario
	 * @param email correo del usuario
	 * @param nick apodo del usaurio
	 * @param password clave de acceso del usuario
	 * @param equipo si equipo > 0 el usuario representa un equipo 
	 * @param logo direccion de la imagen de usuario
	 * @param fondo direccion del fondo de usuario
	 * @param descripcion texto sobre el usuario
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
	 * Pasa a formato JSON la publicacion
	 * @return publicacion en formato JSON
	 * @throws SQLException error al ejecutar una sentencia
	 */
	public String toJSON(int idUsuario) throws SQLException{
		
		//Debug
		System.out.println("This: " + this.id + " <> Parametro: " + idUsuario);
		
		int[] seguir = UsuarioDAO.seguidor_seguido(id);
		
		JSONObject obj = new JSONObject();
		
		// Atributos de publicacion
		obj.put("Id", id);
		obj.put("nombre", nombre);
		obj.put("nick", nick);
		obj.put("equipo", equipo);
		if (logo != null){
			obj.put("logo", logo);
		}else{
			obj.put("logo", PERFIL);
		}
		obj.put("fondo", fondo);
		obj.put("fecha", fecha);
		obj.put("sexo", sexo);
		//obj.put("deporte", deporte);
		obj.put("descripcion", descripcion);
		// Seguidores y seguidos
		obj.put("seguidores", seguir[0]);
		obj.put("seguidos", seguir[1]);
		if (idUsuario != id){
			if (UsuarioDAO.yoSigo(this.id, idUsuario)){
				// Lo siguo
				obj.put("le_sigue", "true");
			} else {
				// No lo sigo
				obj.put("le_sigue", "false");
			}
			if (UsuarioDAO.meSigue(this.id, idUsuario)){
				// Me sigue
				obj.put("me_sigue", "true");
			} else {
				// No me sigue
				obj.put("me_sigue", "false");
			}
		} else {
			// Yo mismo
			obj.put("le_sigue", "false");
			obj.put("me_sigue", "false");
		}
		
		return obj.toString();
	}
	
	/**
	 * Pasa a formato JSON un vector de publicaciones
	 * @param vector publicaciones a parsear
	 * @param idUsuario identificador del usuario
	 * @return vector en formato JSON de publicaciones
	 * @throws SQLException error al ejecutar una sentencia
	 */
	public static String toJSON(ArrayList<Usuario> vector, int idUsuario) throws SQLException{
		
		String rs = "{\"usuarios\": [\n";
		for (Usuario d: vector){
			
			rs += d.toJSON(idUsuario) + ",\n";
		}
		
		// Consulta vacia?
		if (!vector.isEmpty()){
			int end = rs.lastIndexOf(',');
			rs = rs.substring(0, end);	// Elimina la última coma puesta
		}
		
		return rs + "\n]}";
	}
	
	// Getters & Setters
	/**
	 * Devuelve el nombre del usuario
	 * @return nombre del usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve el identificador del usuario
	 * @return identificador del usuario
	 */
	public int getId() {
		return id;
	}

	/**
	 * Devuelve el sexo del usuario
	 * @return sexo del usuario
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * Devuelve el email del usuario
	 * @return email del usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Devuelve el nick del usuario
	 * @return nick del usuario
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Devuelve la clave del usuario
	 * @return clave del usuario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Devuelve el nacimiento del usuario
	 * @return nacimiento del usuario
	 */
	public Date getFecha() {
		return fecha;
	}
	
	/**
	 * Devuelve la descripcion del usuario
	 * @return descripcion del usuario
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * Devuelve el nacimiento del usuario
	 * @return nacimiento del usuario en formato aaaammdd
	 */
	public String getFechaString() {
		return Fechas.getFechaString(this.fecha);
	}
	
	/**
	 * Devuelve un entero indicando si el usuario es un equipo
	 * @return devuele un entero positivo si el
	 * usuario es un equipo
	 */
	public int getEquipo() {
		if (equipo > 0) return 1;
		else return 0;
	}
	
	/**
	 * Devuelve el logo del usuario
	 * @return url del logo
	 */
	public String getLogo() {
		return logo;
	}
	
	/**
	 * Devuelve el fondo del usuario
	 * @return url del fondo
	 */
	public String getFondo() {
		return fondo;
	}
	
	

}
