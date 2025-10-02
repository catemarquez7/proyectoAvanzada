package bll;

import java.time.LocalDate;
import java.util.LinkedList;

public class Cliente extends Usuario{

	//atributos
	protected LinkedList<Reserva> reservas;

	
	//constructores
	
	public Cliente(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni, String direccion, int id,
			String user, String pass, String pregunta, String respuesta, LocalDate fecha_creacion,
			LinkedList<Reserva> reservas) {
		super(nombre, apellido, fecha_nac, mail, dni, direccion, id, user, pass, pregunta, respuesta, fecha_creacion);
		this.reservas = reservas;
	}
	
	public Cliente(){
		
	}

	//getters y setters
	public LinkedList<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(LinkedList<Reserva> reservas) {
		this.reservas = reservas;
	}

	
	
	//toString
	@Override
	public String toString() {
		return "Cliente [reservas=" + reservas + ", user=" + user + ", nombre=" + nombre + "]";
	}
	
	
	
	
	//metodos
	
	
	
	
	
	
	
	
}
