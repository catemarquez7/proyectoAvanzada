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
	
	
	//constructores
	public Paquete(int id, LocalDate inicioDate, LocalDate finDate, double precio, Hotel hotel, Habitacion habitacion,
			Actividad actividad) {
		super();
		this.id = id;
		this.inicioDate = inicioDate;
		this.finDate = finDate;
		this.precio = precio;
		this.hotel = hotel;
		this.habitacion = habitacion;
		this.actividad = actividad;
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

	public void setInicioDate(LocalDate inicioDate) {
		this.inicioDate = inicioDate;
	}

	public LocalDate getFinDate() {
		return finDate;
	}

	public void setFinDate(LocalDate finDate) {
		this.finDate = finDate;
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

	
	//toString
	@Override
	public String toString() {
		return "Paquete [id=" + id + ", inicioDate=" + inicioDate + ", finDate=" + finDate + ", precio=" + precio
				+ ", hotel=" + hotel + ", habitacion=" + habitacion + ", actividad=" + actividad + "]";
	}
	
	
	
}
