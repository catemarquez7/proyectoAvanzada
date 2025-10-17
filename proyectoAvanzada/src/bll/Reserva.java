package bll;

import java.time.LocalDateTime;

public class Reserva {

	// atributos
	protected int id;
	protected Usuario usuario;
	protected Cliente cliente;
	protected Paquete paquete;
	protected String estado;
	protected LocalDateTime fecha_checkin;
	protected LocalDateTime fecha_checkout;
	protected String tarjeta_resguardo;
	protected double monto_final;

	// constructores

	public Reserva(int id, Usuario usuario, Paquete paquete, String estado, LocalDateTime fecha_checkin,
			LocalDateTime fecha_checkout, String tarjeta_resguardo, double monto_final) {
		this.id = id;
		this.usuario = usuario;
		this.paquete = paquete;
		this.estado = estado;
		this.fecha_checkin = fecha_checkin;
		this.fecha_checkout = fecha_checkout;
		this.tarjeta_resguardo = tarjeta_resguardo;
		this.monto_final = monto_final;
	}

	public Reserva( Usuario usuario, Paquete paquete) {
		this.usuario = usuario;
		this.paquete = paquete;
		this.estado = "pendiente";
	}

	public Reserva() {
	}

	// getters y setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Paquete getPaquete() {
		return paquete;
	}

	public void setPaquete(Paquete paquete) {
		this.paquete = paquete;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDateTime getFecha_checkin() {
		return fecha_checkin;
	}

	public void setFecha_checkin(LocalDateTime fecha_checkin) {
		this.fecha_checkin = fecha_checkin;
	}

	public LocalDateTime getFecha_checkout() {
		return fecha_checkout;
	}

	public void setFecha_checkout(LocalDateTime fecha_checkout) {
		this.fecha_checkout = fecha_checkout;
	}

	public String getTarjeta_resguardo() {
		return tarjeta_resguardo;
	}

	public void setTarjeta_resguardo(String tarjeta_resguardo) {
		this.tarjeta_resguardo = tarjeta_resguardo;
	}

	public double getMonto_final() {
		return monto_final;
	}

	public void setMonto_final(double monto_final) {
		this.monto_final = monto_final;
	}

	// toString
	@Override
	public String toString() {
		return "Reserva [id=" + id + ", cliente=" + usuario.getNombre() + " " + usuario.getApellido() + ", paquete="
				+ paquete.getHotel().getNombre() + ", estado=" + estado + ", habitacion="
				+ paquete.getHabitacion().getNumero() + "]";
	}
}