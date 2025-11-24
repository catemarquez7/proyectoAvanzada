package bll;

public class Preferencias {

	//atributos
	protected String categoria;
	protected String riesgo;
	protected double duracion;
	protected int id_cliente;
	
	
	//constructor
	public Preferencias(String categoria, String riesgo, double duracion, int id_cliente) {
		super();
		this.categoria = categoria;
		this.riesgo = riesgo;
		this.duracion = duracion;
		this.id_cliente = id_cliente;
	}
	
	public Preferencias() {};
	
	
	//getters y setters
	public String getRiesgo() {
		return riesgo;
	}
	public void setRiesgo(String riesgo) {
		this.riesgo = riesgo;
	}
	
	public int getCliente() {
		return id_cliente;
	}
	public void setCliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}


	//toString
	@Override
	public String toString() {
		return "Preferencias [categoria=" + categoria + ", riesgo=" + riesgo + ", duracion=" + duracion + ", id_cliente="
				+ id_cliente + "]";
	}


	
	
	
	
}
