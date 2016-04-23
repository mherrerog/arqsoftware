package socialnetwork;

import java.sql.SQLException;
import java.util.ArrayList;

import datos.Usuario;
import gateway.UsuarioDAO;

public class ControlMensajes {
	
	public static String usersSeguidoresMutuos(int myId) throws SQLException{
		String result = "";
		
		ArrayList<Usuario> users = UsuarioDAO.selectSeguirMutuo(myId);
		result = Usuario.toJSON(users);
						
		return result;
	}

}
