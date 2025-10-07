package bll;

import java.time.LocalDate;

public class Encargado extends Usuario{

	//atributos
	protected Hotel sucursal;

	
	//constructores
	
	public Encargado(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni, String direccion,
			int id, String user, String pass, String pregunta, String respuesta, LocalDate fecha_creacion,
			String tipo_usuario, String estado, Hotel sucursal) {
		super(nombre, apellido, fecha_nac, mail, dni, direccion, id, user, pass, pregunta, respuesta, fecha_creacion,
				tipo_usuario, estado);
		this.sucursal = sucursal;
	}
	
	public Encargado(){
	
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
		return "Encargado [sucursal=" + sucursal + "]";
	}

	
	//metodos
	
	
	
	
	
	
	
	
}
