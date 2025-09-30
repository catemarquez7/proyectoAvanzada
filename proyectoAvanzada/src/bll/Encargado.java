package bll;

import java.time.LocalDate;

public class Encargado extends Empleado{

	//constructores
	public Encargado(String nombre, String apellido, int edad, LocalDate fecha_nac, String mail, int dni, int direccion,
			int nacionalidad, int id, String user, String pass, String pregunta, String respuesta,
			LocalDate fecha_creacion, Hotel sucursal) {
		super(nombre, apellido, edad, fecha_nac, mail, dni, direccion, nacionalidad, id, user, pass, pregunta,
				respuesta, fecha_creacion, sucursal);
	}

	public Encargado(){
		
	}

	
	//toString
	@Override
	public String toString() {
		return "Encargado [sucursal=" + sucursal + ", user=" + user + ", nombre=" + nombre + "]";
	}
	
	//metodos
	
	
	
	
	
	
	
	
}
