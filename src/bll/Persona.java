package bll;

import java.time.LocalDate;

public abstract class Persona {

	//atributos
	protected String nombre;
	protected String apellido;
	protected LocalDate fecha_nac;
	protected String mail;
	protected int dni;
	protected String direccion;
	
	
	//constructores
	public Persona(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni, String direccion) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.fecha_nac = fecha_nac;
		this.mail = mail;
		this.dni = dni;
		this.direccion = direccion;
	}
	
	public Persona() {
		
	}
	
	
	//getters y setters
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public LocalDate getFecha_nac() {
		return fecha_nac;
	}
	public void setFecha_nac(LocalDate fecha_nac) {
		this.fecha_nac = fecha_nac;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	//toString
	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", fecha_nac=" + fecha_nac
				+ ", mail=" + mail + ", dni=" + dni + ", direccion=" + direccion + "]";
	}
	
	
	



	
}
