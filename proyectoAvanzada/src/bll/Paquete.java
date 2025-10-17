package bll;

import java.sql.Date;
import java.time.LocalDate;

public class Paquete {

	//atributos
	protected int id;
	protected String nombre;
	protected Date inicioDate;
	protected Date finDate;
	protected double precio;
	protected Hotel hotel;
	protected Habitacion habitacion;
	protected Actividad actividad;
	
	
	//constructores
	public Paquete(String nombre, int id, Date inicioDate, Date finDate, double precio, Hotel hotel, Habitacion habitacion,
			Actividad actividad) {
		this.nombre = nombre;
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
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getInicioDate() {
		return inicioDate;
	}

	public void setInicioDate(Date date) {
		this.inicioDate = date;
	}

	public Date getFinDate() {
		return finDate;
	}

	public void setFinDate(Date date) {
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

	
	@Override
	public String toString() {
		return "Paquete [id=" + id + ", inicioDate=" + inicioDate + ", finDate=" + finDate + ", precio=" + precio
				+ ", hotel=" + hotel + ", habitacion=" + habitacion + ", actividad=" + actividad + "]";
	}


	
	
	
}
