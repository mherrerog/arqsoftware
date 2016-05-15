package socialnetwork;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import datos.Mensaje;
import datos.Usuario;
import gateway.MensajeDAO;
import gateway.UsuarioDAO;

public class ControlMensajes {
	
	public static String usersSeguidoresMutuos(int myId) throws SQLException{
		String result = "";
		
		ArrayList<Usuario> users = UsuarioDAO.selectSeguirMutuo(myId);
		result = Usuario.toJSON(users, myId);
						
		return result;
	}

	public static String listMensajesUser(int myId) throws SQLException{
		String result = "";
		
		ArrayList<Mensaje> users = MensajeDAO.selectByUsuario(myId);
		result = Mensaje.toJSON(users);
						
		return result;
	}
	
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
