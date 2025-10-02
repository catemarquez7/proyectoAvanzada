package bll;

import java.time.LocalDate;

public abstract class Persona {

	//atributos
	protected String nombre;
	protected String apellido;
	protected int edad;
	protected LocalDate fecha_nac;
	protected String mail;
	protected int dni;
	protected int direccion;
	protected int nacionalidad;
	
	
	//constructores
	public Persona(String nombre, String apellido, int edad, LocalDate fecha_nac, String mail, int dni, int direccion,
			int nacionalidad) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.fecha_nac = fecha_nac;
		this.mail = mail;
		this.dni = dni;
		this.direccion = direccion;
		this.nacionalidad = nacionalidad;
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
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
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
	public int getDireccion() {
		return direccion;
	}
	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}
	public int getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(int nacionalidad) {
		this.nacionalidad = nacionalidad;
	}


	//toString
	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + ", fecha_nac=" + fecha_nac
				+ ", mail=" + mail + ", dni=" + dni + ", direccion=" + direccion + ", nacionalidad=" + nacionalidad
				+ "]";
	}
	
	
	



	
}
