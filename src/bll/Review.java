package bll;

public class Review {

	//atributos
	protected int id;
	protected Hotel hotel;
	protected Cliente cliente;
	protected double puntaje;
	protected String descripcion;
	protected Reserva reserva;
	
	
	//constructores
	public Review(Integer id, Hotel hotel, Cliente cliente, double puntaje, String descripcion, Reserva reserva) {
		this.id = id;
		this.hotel = hotel;
		this.cliente = cliente;
		this.puntaje = puntaje;
		this.descripcion = descripcion;
		this.reserva = reserva;
	}

	public Review(){
		
	}

	
	//getters y setters
	
	public Hotel getHotel() {
		return hotel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	

	public double getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(double puntaje) {
		this.puntaje = puntaje;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	
	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	//toString
	@Override
	public String toString() {
		return "Review [hotel=" + hotel + ", cliente=" + cliente + ", puntuacion=" + puntaje + ", descripcion="
				+ descripcion + "]";
	}
	
	
	
}
