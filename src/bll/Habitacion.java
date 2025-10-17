package bll;

public class Habitacion {

	//atributos
	protected Hotel hotel;
	protected int id;
	protected int numero;
	protected String estado;
	protected String tipo;
	protected double precio_noche;
	protected int cant_camas;
	
	//constructores
	public Habitacion(Hotel hotel, int id, int numero, String estado, String tipo, double precio_noche,
			int cant_camas) {
		super();
		this.hotel = hotel;
		this.id = id;
		this.numero = numero;
		this.estado = estado;
		this.tipo = tipo;
		this.precio_noche = precio_noche;
		this.cant_camas = cant_camas;
	}

	public Habitacion() {
		
	}
	
	
	//getters y setters
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getPrecio_noche() {
		return precio_noche;
	}

	public void setPrecio_noche(double precio_noche) {
		this.precio_noche = precio_noche;
	}

	public int getCant_camas() {
		return cant_camas;
	}

	public void setCant_camas(int cant_camas) {
		this.cant_camas = cant_camas;
	}

	
	//toString
	@Override
	public String toString() {
		return "Habitacion [hotel=" + hotel + ", id=" + id + ", numero=" + numero + ", estado=" + estado + ", tipo="
				+ tipo + ", precio_noche=" + precio_noche + ", cant_camas=" + cant_camas + "]";
	}
	

	
	
}
