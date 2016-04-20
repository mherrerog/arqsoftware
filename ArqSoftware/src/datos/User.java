package datos;

public class User {
	
	private String nombre;
	private String nick;
	
	public User(String nombre, String nick){
		this.nombre = nombre.toUpperCase();
		this.nick = nick;
	}
	
	@Override
	public String toString(){
		return "" + nombre + " <> " + nick;
		
	}
	
	//Getters
	public String getNombre(){ return nombre; }
	
	public String getNick(){ return nick; }

}
