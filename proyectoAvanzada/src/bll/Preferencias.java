package bll;

public class Preferencias {

	//atributos
	protected String clima;
	protected String geografia;
	protected String riesgo;
	protected String rango_horario;
	protected Cliente cliente;
	
	
	//constructor
	public Preferencias(String clima, String geografia, String riesgo, String rango_horario, Cliente cliente) {
		super();
		this.clima = clima;
		this.geografia = geografia;
		this.riesgo = riesgo;
		this.rango_horario = rango_horario;
		this.cliente = cliente;
	}
	
	
	//getters y setters
	public String getClima() {
		return clima;
	}
	public void setClima(String clima) {
		this.clima = clima;
	}
	public String getGeografia() {
		return geografia;
	}
	public void setGeografia(String geografia) {
		this.geografia = geografia;
	}
	public String getRiesgo() {
		return riesgo;
	}
	public void setRiesgo(String riesgo) {
		this.riesgo = riesgo;
	}
	public String getRango_horario() {
		return rango_horario;
	}
	public void setRango_horario(String rango_horario) {
		this.rango_horario = rango_horario;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	//toString
	@Override
	public String toString() {
		return "Preferencias [clima=" + clima + ", geografia=" + geografia + ", riesgo=" + riesgo + ", rango_horario="
				+ rango_horario + ", cliente=" + cliente + "]";
	}
	
	
	
}
