package datos;

import java.util.Date;

public class Comentario {
	
	private int id;
	private Usuario autor;
	private Publicacion publicacion;
	private Date fecha;
	private String texto;
	
	/**
	 * Metodo constructor
	 */
	public Comentario(int id, Usuario autor, Publicacion publicacion, Date fecha, String texto) {
		this.id = id;
		this.autor = autor;
		this.publicacion = publicacion;
		this.fecha = fecha;
		this.texto = texto;
	}

	//Setters & Getters
	public int getId() {
		return id;
	}

	public Usuario getAutor() {
		return autor;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getTexto() {
		return texto;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	

}
