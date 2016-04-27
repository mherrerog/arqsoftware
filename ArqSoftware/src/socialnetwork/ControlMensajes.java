package socialnetwork;

import java.sql.SQLException;
import java.util.ArrayList;

import datos.Mensaje;
import datos.Usuario;
import gateway.MensajeDAO;
import gateway.UsuarioDAO;

public class ControlMensajes {
	
	public static String usersSeguidoresMutuos(int myId) throws SQLException{
		String result = "";
		
		ArrayList<Usuario> users = UsuarioDAO.selectSeguirMutuo(myId);
		result = Usuario.toJSON(users);
						
		return result;
	}

	public static String listMensajesUser(int myId) throws SQLException{
		String result = "";
		
		ArrayList<Mensaje> users = MensajeDAO.selectByUsuario(myId);
		result = Mensaje.toJSON(users);
						
		return result;
	}
}
