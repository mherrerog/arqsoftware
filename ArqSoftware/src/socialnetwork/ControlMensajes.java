package socialnetwork;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import datos.Mensaje;
import datos.Usuario;
import gateway.MensajeDAO;
import gateway.UsuarioDAO;

/**
 * Clase correspondiente a la capa de logica de aplicacion, concretamente esta clase
 * tiene el objetivo de soportar la logica asociada a los mensajes.
 * 
 * @author Grupo 1 - Arquitectura Software. Universidad de Zaragoza.
 *
 */
public class ControlMensajes {
	
	/**
	 * Metodo encargado de enviar una peticion a la base de datos para obtener 
	 * un listado con aquellos usuarios que se siguen mutuamente y devuelve el resultado
	 * a la capa de presentacion para que se muestren por pantalla.
	 * <p>
	 * Para que estos resultados puedan ser impresos en pantalla, los datos estan en 
	 * formato JSON.
	 * 
	 * @param myId	Entero correspondiente al identificador del usuario.
	 * @return	Un string que contiene una lista de aquellos usuarios que se siguen al
	 * usuario identificado por myId y que este les sigue.
	 * @throws SQLException	Excepcion que puede resultar si ocurre algun problema durante la 
	 * consulta a la base de datos.
	 */
	public static String usersSeguidoresMutuos(int myId) throws SQLException{
		String result = "";
		
		ArrayList<Usuario> users = UsuarioDAO.selectSeguirMutuo(myId);
		result = Usuario.toJSON(users, myId);
						
		return result;
	}

	/**
	 * Metodo encargado de enviar una peticion a la base de datos para obtener 
	 * un listado con los mensajes que posee el usuario cuyo identificador es myId
	 * y devuelve el resultado a la capa de presentacion para que se muestren por pantalla.
	 * <p>
	 * Para que estos resultados puedan ser impresos en pantalla, los datos estan en 
	 * formato JSON.
	 * 
	 * @param myId	Entero correspondiente al identificador del usuario.
	 * @return	Un string que contiene una lista de mensajes que posee el usuario identificado 
	 * por myId.
	 * @throws SQLException	Excepcion que puede resultar si ocurre algun problema durante la 
	 * consulta a la base de datos.
	 */
	public static String listMensajesUser(int myId) throws SQLException{
		String result = "";
		
		ArrayList<Mensaje> users = MensajeDAO.selectByUsuario(myId);
		result = Mensaje.toJSON(users);
						
		return result;
	}
	
	/**
	 * Metodo encargado de enviar una peticion a la base de datos para insertar 
	 * un mensaje que es enviado por el usuario identificado por userId al usuario
	 * identificado por idReceptor.
	 * 
	 * @param userId	Entero correspondiente al identificador del usuario emisor del mensaje.
	 * @param idReceptor	Entero correspondiente al identificador del usuario receptor del mensaje.
	 * @param cuerpo	Cadena de caracteres correspondiente al cuerpo del mensaje.
	 */
	public static void envioMensaje(int userId, int idReceptor, String cuerpo) {
		Date d = new Date();
		String fecha = Fechas.getFechaString(d);
		String hora = Fechas.getHoraString(d);
		
		Mensaje ms = new Mensaje(userId, idReceptor, fecha, hora, cuerpo, 0);
		
		try {
			MensajeDAO.insert(ms);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
