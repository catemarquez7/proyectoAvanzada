package bll;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import dll.DtoEncargado;

public class Encargado extends Usuario {

	// atributos
	protected Hotel sucursal;

	// constructores

	public Encargado(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni, String direccion,
			int id, String user, String pass, String pregunta, String respuesta, LocalDate fecha_creacion,
			String tipo_usuario, String estado, Hotel sucursal) {
		super(nombre, apellido, fecha_nac, mail, dni, direccion, id, user, pass, pregunta, respuesta, fecha_creacion,
				tipo_usuario, estado);
		this.sucursal = sucursal;
	}

	public Encargado() {

	}

	// getters y setters
	public Hotel getSucursal() {
		return sucursal;
	}

	public void setSucursal(Hotel sucursal) {
		this.sucursal = sucursal;
	}

	// toString
	@Override
	public String toString() {
		return "Encargado [sucursal=" + sucursal + "]";
	}

	// metodos

	// Check-in
	public static void realizarCheckin() {
		try {
			String idReservaStr = JOptionPane.showInputDialog("Ingrese ID de la reserva:");
			if (idReservaStr == null)
				return;
			int idReserva = Integer.parseInt(idReservaStr);

			String dni = JOptionPane.showInputDialog("Ingrese DNI del cliente:");
			if (dni == null)
				return;

			String tarjeta = JOptionPane.showInputDialog("Ingrese número de tarjeta de resguardo:");
			if (tarjeta == null)
				return;

			DtoEncargado.realizarCheckin(idReserva, dni, tarjeta);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Datos inválidos", "ERROR", 0);
		}
	}// fin

	// Check-out
	public static void realizarCheckout() {
		try {
			String idReservaStr = JOptionPane.showInputDialog("Ingrese ID de la reserva para check-out:");
			if (idReservaStr == null)
				return;
			int idReserva = Integer.parseInt(idReservaStr);

			DtoEncargado.realizarCheckout(idReserva);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "ID inválido", "ERROR", 0);
		}
	}// fin

	// Ver reservas
	public static void verReservas() {
		List<Reserva> reservas = DtoEncargado.verTodasReservas();

		if (reservas.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay reservas registradas", "INFO", 1);
			return;
		}

		StringBuilder sb = new StringBuilder("=== LISTA DE RESERVAS ===\n\n");

		for (Reserva r : reservas) {
			sb.append("ID: ").append(r.getId()).append(" | Cliente: ").append(r.getCliente().getNombre()).append(" ")
					.append(r.getCliente().getApellido()).append(" | DNI: ").append(r.getCliente().getDni())
					.append("\nHabitación: ").append(r.getPaquete().getHabitacion().getNumero()).append(" | Estado: ")
					.append(r.getEstado()).append(" | Precio: $")
					.append(String.format("%.2f", r.getPaquete().getPrecio())).append("\n");

			if (r.getTarjeta_resguardo() != null) {
				sb.append("Tarjeta: ").append(r.getTarjeta_resguardo()).append("\n");
			}

			sb.append("------------------------\n");
		}

		JOptionPane.showMessageDialog(null, sb.toString(), "RESERVAS", JOptionPane.INFORMATION_MESSAGE);
	}
	
	//Ver habitaciones
	public static void verHabitaciones() {
		try {
			String idHotelStr = JOptionPane.showInputDialog("Ingrese ID del hotel:");
			if (idHotelStr == null)
				return;
			int idHotel = Integer.parseInt(idHotelStr);

			List<Habitacion> habitaciones = DtoEncargado.verHabitaciones(idHotel);

			if (habitaciones.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No hay habitaciones en este hotel", "INFO", 1);
				return;
			}

			StringBuilder sb = new StringBuilder("=== HABITACIONES DEL HOTEL ===\n\n");

			for (Habitacion h : habitaciones) {
				sb.append("ID: ").append(h.getId()).append(" | Número: ").append(h.getNumero()).append("\nTipo: ")
						.append(h.getTipo()).append(" | Camas: ").append(h.getCant_camas()).append("\nPrecio: $")
						.append(String.format("%.2f", h.getPrecio_noche())).append(" | Estado: ").append(h.getEstado())
						.append("\n------------------------\n");
			}

			JOptionPane.showMessageDialog(null, sb.toString(), "HABITACIONES", 1);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "ID inválido", "ERROR", 0);
		}
	}//fin

}
