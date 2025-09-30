package bll;

import java.time.LocalDate;

public class Administrador extends Usuario{
	
	
	//constructores
	public Administrador(String nombre, String apellido, int edad, LocalDate fecha_nac, String mail, int dni,
			int direccion, int nacionalidad, int id, String user, String pass, String pregunta, String respuesta,
			LocalDate fecha_creacion) {
		super(nombre, apellido, edad, fecha_nac, mail, dni, direccion, nacionalidad, id, user, pass, pregunta,
				respuesta, fecha_creacion);
	}

	public Administrador() {
		
	}
	
	
	//toString
	@Override
	public String toString() {
		return "Administrador [id=" + id + ", user=" + user + ", pass=" + pass + ", pregunta=" + pregunta
				+ ", respuesta=" + respuesta + ", fecha_creacion=" + fecha_creacion + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", edad=" + edad + ", fecha_nac=" + fecha_nac + ", mail=" + mail + ", dni="
				+ dni + ", direccion=" + direccion + ", nacionalidad=" + nacionalidad + "]";
	}

	
	
	//metodos
	
	
	
	
}
