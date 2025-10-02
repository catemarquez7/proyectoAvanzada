package bll;

import java.time.LocalDate;

public class Encargado extends Empleado{

	//constructores
	
	public Encargado(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni, String direccion,
			int id, String user, String pass, String pregunta, String respuesta, LocalDate fecha_creacion,
			Hotel sucursal) {
		super(nombre, apellido, fecha_nac, mail, dni, direccion, id, user, pass, pregunta, respuesta, fecha_creacion,
				sucursal);
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
