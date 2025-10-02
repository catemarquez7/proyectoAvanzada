package bll;

public class Reserva {

	//atributos
	protected int id;
	protected Cliente cliente;
	protected Paquete paquete;
	
	//constructores
	public Reserva(int id, Cliente cliente, Paquete paquete) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.paquete = paquete;
	}
	
	
	public Reserva() {
		
	}


	//getters y setters
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Paquete getPaquete() {
		return paquete;
	}


	public void setPaquete(Paquete paquete) {
		this.paquete = paquete;
	}


	//toString
	@Override
	public String toString() {
		return "Reserva [id=" + id + ", cliente=" + cliente + ", paquete=" + paquete + "]";
	}
	
	
	
	
	
}
