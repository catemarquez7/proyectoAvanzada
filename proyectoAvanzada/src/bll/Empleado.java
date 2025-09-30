package bll;

import java.time.LocalDate;

public class Empleado extends Usuario{

	//atributos
	protected Hotel sucursal;

	
	//constructores
	public Empleado(String nombre, String apellido, int edad, LocalDate fecha_nac, String mail, int dni, int direccion,
			int nacionalidad, int id, String user, String pass, String pregunta, String respuesta,
			LocalDate fecha_creacion, Hotel sucursal) {
		super(nombre, apellido, edad, fecha_nac, mail, dni, direccion, nacionalidad, id, user, pass, pregunta,
				respuesta, fecha_creacion);
		this.sucursal = sucursal;
	}
	
	public Empleado(){
		
	}
	
	
	//getters y setters
	public Hotel getSucursal() {
		return sucursal;
	}

	public void setSucursal(Hotel sucursal) {
		this.sucursal = sucursal;
	}

	
	//toString
	@Override
	public String toString() {
		return "Empleado [sucursal=" + sucursal + ", user=" + user + ", nombre=" + nombre + "]";
	}
	
	
	
	
}
