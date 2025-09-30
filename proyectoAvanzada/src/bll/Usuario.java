package bll;

import java.time.LocalDate;

public class Usuario extends Persona{

	//atributos
	protected int id;
	protected String user;
	protected String pass;
	protected String pregunta;
	protected String respuesta;
	protected LocalDate fecha_creacion;
	
	
	//constructores
	public Usuario(String nombre, String apellido, int edad, LocalDate fecha_nac, String mail, int dni, int direccion,
			int nacionalidad, int id, String user, String pass, String pregunta, String respuesta,
			LocalDate fecha_creacion) {
		super(nombre, apellido, edad, fecha_nac, mail, dni, direccion, nacionalidad);
		this.id = id;
		this.user = user;
		this.pass = pass;
		this.pregunta = pregunta;
		this.respuesta = respuesta;
		this.fecha_creacion = fecha_creacion;
	}
	
	public Usuario(int id, String user, String pass) {
		this.id = id;
		this.user = user;
		this.pass = pass;
	}
	
	public Usuario() {
		
	}

	
	//getters y setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public LocalDate getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(LocalDate fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	
	//toString
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", user=" + user + ", pass=" + pass + ", pregunta=" + pregunta + ", respuesta="
				+ respuesta + ", fecha_creacion=" + fecha_creacion + "]";
	}
	

	//metodos
	
	
	
	
	
}
