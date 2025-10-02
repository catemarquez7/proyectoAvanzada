package bll;

import java.time.LocalDate;

public class Empleado extends Usuario{

	//atributos
	protected Hotel sucursal;

	
	//constructores
	
	public Empleado(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni, String direccion, int id,
			String user, String pass, String pregunta, String respuesta, LocalDate fecha_creacion, Hotel sucursal) {
		super(nombre, apellido, fecha_nac, mail, dni, direccion, id, user, pass, pregunta, respuesta, fecha_creacion);
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
