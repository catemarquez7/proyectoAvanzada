package bll;

import java.time.LocalDate;

public class Paquete {

	//atributos
	protected int id;
	protected LocalDate inicioDate;
	protected LocalDate finDate;
	protected double precio;
	protected Hotel hotel;
	protected Habitacion habitacion;
	protected Actividad actividad;
	protected double precioOriginal;
	protected Promocion promocion;
	protected int cupo_actual;
	protected int cupo_maximo;

	
	
	//constructores
	public Paquete(int id, LocalDate inicioDate, LocalDate finDate, double precio, Hotel hotel, Habitacion habitacion,
			Actividad actividad, double precioOriginal, Promocion promocion, int cupo_actual, int cupo_maximo) {
		super();
		this.id = id;
		this.inicioDate = inicioDate;
		this.finDate = finDate;
		this.precio = precio;
		this.hotel = hotel;
		this.habitacion = habitacion;
		this.actividad = actividad;
		this.precioOriginal = precioOriginal;
		this.promocion = promocion;
		this.cupo_actual = cupo_actual;
		this.cupo_maximo = cupo_maximo;
	}

	public Paquete() {
		
	}
	

	

	//getters y setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public LocalDate getInicioDate() {
		return inicioDate;
	}

	public void setInicioDate(LocalDate date) {
		this.inicioDate = date;
	}

	public LocalDate getFinDate() {
		return finDate;
	}

	public void setFinDate(LocalDate date) {
		this.finDate = date;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	
	public double getPrecioOriginal() {
	    return precioOriginal;
	}

	public void setPrecioOriginal(double precioOriginal) {
	    this.precioOriginal = precioOriginal;
	}

	public Promocion getPromocion() {
	    return promocion;
	}

	public void setPromocion(Promocion promocion) {
	    this.promocion = promocion;
	}
	

	public int getCupo_actual() {
		return cupo_actual;
	}

	public void setCupo_actual(int cupo_actual) {
		this.cupo_actual = cupo_actual;
	}

	public int getCupo_maximo() {
		return cupo_maximo;
	}

	public void setCupo_maximo(int cupo_maximo) {
		this.cupo_maximo = cupo_maximo;
	}

	@Override
	public String toString() {
		return "Paquete [id=" + id + ", inicioDate=" + inicioDate + ", finDate=" + finDate + ", precio=" + precio
				+ ", hotel=" + hotel + ", habitacion=" + habitacion + ", actividad=" + actividad + ", precioOriginal="
				+ precioOriginal + ", promocion=" + promocion + ", cupo_actual=" + cupo_actual + ", cupo_maximo="
				+ cupo_maximo + "]";
	}

	
	
}
