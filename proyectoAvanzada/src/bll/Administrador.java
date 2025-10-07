package bll;

import java.time.LocalDate;

public class Administrador extends Usuario{
	
	
	//constructores
	
	public Administrador(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni, String direccion,
			int id, String user, String pass, String pregunta, String respuesta, LocalDate fecha_creacion,
			String tipo_usuario, String estado) {
		super(nombre, apellido, fecha_nac, mail, dni, direccion, id, user, pass, pregunta, respuesta, fecha_creacion,
				tipo_usuario, estado);
	}

	public Administrador() {
		
	}

	//toString
	@Override
	public String toString() {
		return "Administrador [id=" + id + ", user=" + user + ", pass=" + pass + ", pregunta=" + pregunta
				+ ", respuesta=" + respuesta + ", fecha_creacion=" + fecha_creacion + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", fecha_nac=" + fecha_nac + ", mail=" + mail + ", dni=" + dni
				+ ", direccion=" + direccion + ", getId()=" + getId() + ", getUser()=" + getUser() + ", getPass()="
				+ getPass() + ", getPregunta()=" + getPregunta() + ", getRespuesta()=" + getRespuesta()
				+ ", getFecha_creacion()=" + getFecha_creacion() + ", toString()=" + super.toString() + ", getNombre()="
				+ getNombre() + ", getApellido()=" + getApellido() + ", getFecha_nac()=" + getFecha_nac()
				+ ", getMail()=" + getMail() + ", getDni()=" + getDni() + ", getDireccion()=" + getDireccion()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

	

	
	
	//metodos
	
	
	
	
}
