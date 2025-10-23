package bll;

import java.time.LocalDate;

public class Promocion {

	// Atributos
	private int id;
	private String nombre;
	private String descripcion;
	private double porcentajeDescuento;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String estado;
	private int idHotel;

	// Constructores
	public Promocion() {
	}

	public Promocion(int id, String nombre, String descripcion, double porcentajeDescuento, LocalDate fechaInicio,
			LocalDate fechaFin, String estado, int idHotel) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.porcentajeDescuento = porcentajeDescuento;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
		this.idHotel = idHotel;
	}

	public Promocion(String nombre, String descripcion, double porcentajeDescuento, LocalDate fechaInicio,
			LocalDate fechaFin, int idHotel) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.porcentajeDescuento = porcentajeDescuento;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = "activa";
		this.idHotel = idHotel;
	}

	// Getters y Setters
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(double porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public boolean estaVigente() {
		LocalDate hoy = LocalDate.now();
		return estado.equals("activa") && !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
	}

	public double calcularPrecioConDescuento(double precioOriginal) {
		return precioOriginal - (precioOriginal * porcentajeDescuento / 100);
	}

	@Override
	public String toString() {
		return "Promocion [id=" + id + ", nombre=" + nombre + ", descuento=" + porcentajeDescuento + "%, fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", estado=" + estado + "]";
	}
}