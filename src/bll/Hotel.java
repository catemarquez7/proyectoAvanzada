package bll;

public class Hotel {

	//atributos
	protected int id;
	protected String nombre;
	protected String provincia;
	protected String direccion;
	protected int cant_habitaciones;
	protected double calificacion_promedio;
	
	//constructores
	public Hotel(int id, String nombre, String provincia, String direccion, int cant_habitaciones,
			double calificacion_promedio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.provincia = provincia;
		this.direccion = direccion;
		this.cant_habitaciones = cant_habitaciones;
		this.calificacion_promedio = calificacion_promedio;
	}

	public Hotel(){
		
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

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getCant_habitaciones() {
		return cant_habitaciones;
	}

	public void setCant_habitaciones(int cant_habitaciones) {
		this.cant_habitaciones = cant_habitaciones;
	}

	public double getCalificacion_promedio() {
		return calificacion_promedio;
	}

	public void setCalificacion_promedio(double calificacion_promedio) {
		this.calificacion_promedio = calificacion_promedio;
	}

	
	//toString
	@Override
	public String toString() {
		return "Hotel [id=" + id + ", nombre=" + nombre + ", provincia=" + provincia + ", direccion=" + direccion
				+ ", cant_habitaciones=" + cant_habitaciones + ", calificacion_promedio=" + calificacion_promedio + "]";
	}
	
	
	
	
	
	
}
