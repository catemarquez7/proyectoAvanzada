package bll;

public class Review {

	//atributos
	protected Hotel hotel;
	protected Cliente cliente;
	protected int puntuacion;
	protected String descripcion;
	
	
	//constructores
	public Review(Hotel hotel, Cliente cliente, int puntuacion, String descripcion) {
		super();
		this.hotel = hotel;
		this.cliente = cliente;
		this.puntuacion = puntuacion;
		this.descripcion = descripcion;
	}
	
	public Review(){
		
	}

	
	//getters y setters
	public Hotel getHotel() {
		return hotel;
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

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	//toString
	@Override
	public String toString() {
		return "Review [hotel=" + hotel + ", cliente=" + cliente + ", puntuacion=" + puntuacion + ", descripcion="
				+ descripcion + "]";
	}
	
	
	
}
