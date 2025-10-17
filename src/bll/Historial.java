package bll;

import java.util.LinkedList;

public class Historial {

	//atributos
	protected LinkedList<Reserva> historial;
	protected Cliente cliente;
	
	
	//constructor
	public Historial(LinkedList<Reserva> historial, Cliente cliente) {
		super();
		this.historial = historial;
		this.cliente = cliente;
	}


	//getters y setters
	public LinkedList<Reserva> getHistorial() {
		return historial;
	}


	public void setHistorial(LinkedList<Reserva> historial) {
		this.historial = historial;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	//toString
	@Override
	public String toString() {
		return "Historial [historial=" + historial + ", cliente=" + cliente + "]";
	}
	
	
}
