package bll;

import java.time.LocalDate;

public class Actividad {

	//atributos
	protected int id;
	protected String nombre;
	protected String categoria;
	protected int edad_minima;
	protected int edad_maxima;
	protected double duracion;
	protected double precio;
	protected String locacion;
	protected LocalDate inicioDate;
	protected LocalDate finDate;
	protected Hotel hotel;
	protected String riesgo;
	
	//constructores
	public Actividad(int id, String nombre, String categoria, int edad_minima, int edad_maxima, double duracion,
			double precio, String locacion, LocalDate inicioDate, LocalDate finDate, Hotel hotel, String riesgo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.categoria = categoria;
		this.edad_minima = edad_minima;
		this.edad_maxima = edad_maxima;
		this.duracion = duracion;
		this.precio = precio;
		this.locacion = locacion;
		this.inicioDate = inicioDate;
		this.finDate = finDate;
		this.hotel = hotel;
		this.riesgo = riesgo;
	}
	
	public Actividad() {
		
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getEdad_minima() {
		return edad_minima;
	}

	public void setEdad_minima(int edad_minima) {
		this.edad_minima = edad_minima;
	}

	public int getEdad_maxima() {
		return edad_maxima;
	}

	public void setEdad_maxima(int edad_maxima) {
		this.edad_maxima = edad_maxima;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getLocacion() {
		return locacion;
	}

	public void setLocacion(String locacion) {
		this.locacion = locacion;
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

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	
	public String getRiesgo() {
		return riesgo;
	}

	public void setRiesgo(String riesgo) {
		this.riesgo = riesgo;
	}

	//toString
	@Override
	public String toString() {
		return "Actividad [id=" + id + ", nombre=" + nombre + ", categoria=" + categoria + ", edad_minima="
				+ edad_minima + ", edad_maxima=" + edad_maxima + ", duracion=" + duracion + ", precio=" + precio
				+ ", locacion=" + locacion + ", inicioDate=" + inicioDate + ", finDate=" + finDate + ", hotel=" + hotel + ", riesto=" + riesgo
				+ "]";
	}
	
	
}
