package dll;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;

import bll.Cliente;
import bll.Habitacion;
import bll.Hotel;
import bll.Paquete;
import bll.Reserva;

public class DtoEncargado {

	private static Connection conx = Conexion.getInstance().getConnection();

	// check-in
	public static boolean realizarCheckin(int idReserva, String dniCliente, String tarjetaResguardo) {

		try {
			// Verificar que la reserva existe y está pendiente

			PreparedStatement stmt = (PreparedStatement) conx.prepareStatement("SELECT r.*, u.dni FROM reserva r "
					+ "JOIN usuario u ON r.id_usuario = u.id " + "WHERE r.id = ? AND r.estado = 'pendiente'");

			stmt.setInt(1, idReserva);

			ResultSet rs = stmt.executeQuery();

			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "Reserva no encontrada o ya procesada", "ERROR", 0);
				return false;
			}

			int dni = rs.getInt("dni");
			int idPaquete = rs.getInt("id_paquete");

			// Verificar DNI

			if (dni != Integer.parseInt(dniCliente)) {
				JOptionPane.showMessageDialog(null, "El DNI no coincide con la reserva", "ERROR", 0);
				return false;
			}

			// Obtener id de habitación del paquete

			PreparedStatement stmtHab = (PreparedStatement) conx
					.prepareStatement("SELECT id_habitacion FROM paquete WHERE id = ?");

			stmtHab.setInt(1, idPaquete);

			ResultSet rsHab = stmtHab.executeQuery();

			if (!rsHab.next()) {
				JOptionPane.showMessageDialog(null, "Paquete sin habitación asignada", "ERROR", 0);
				return false;
			}

			int idHabitacion = rsHab.getInt("id_habitacion");

			// Cambiar estado de habitación a "ocupada"
			PreparedStatement stmtHabEstado = (PreparedStatement) conx
					.prepareStatement("UPDATE habitacion SET estado = 'ocupada' WHERE id = ?");

			stmtHabEstado.setInt(1, idHabitacion);
			stmtHabEstado.executeUpdate();

			// Actualizar reserva con check-in
			PreparedStatement stmtReserva = (PreparedStatement) conx.prepareStatement(
					"UPDATE reserva SET estado = 'activa', fecha_checkin = ?, tarjeta_resguardo = ? WHERE id = ?");

			// Timestamp es para tomar el momento preciso en el tiempo q se hace el check-in
			stmtReserva.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			stmtReserva.setString(2, tarjetaResguardo);
			stmtReserva.setInt(3, idReserva);

			int filas = stmtReserva.executeUpdate();

			if (filas > 0) {

				// Obtener número de habitación para mostrar

				PreparedStatement stmtNumHab = (PreparedStatement) conx
						.prepareStatement("SELECT numero FROM habitacion WHERE id = ?");

				stmtNumHab.setInt(1, idHabitacion);

				ResultSet rsNum = stmtNumHab.executeQuery();

				rsNum.next();

				int numHabitacion = rsNum.getInt("numero");

				JOptionPane.showMessageDialog(null,
						"CHECK-IN REALIZADO\n\n" + "Habitación asignada: " + numHabitacion + "\n"
								+ "Tarjeta de resguardo: " + tarjetaResguardo + "\n\n"
								+ "Llave activada - Bienvenido/a!",
						"CHECK-IN EXITOSO", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al realizar check-in: " + e.getMessage(), "ERROR", 0);
		}
		return false;
	}// fin

	// check-out
	public static boolean realizarCheckout(int idReserva) {

		try {

			// Obtener datos de la reserva

			PreparedStatement stmt = (PreparedStatement) conx.prepareStatement(
					"SELECT r.*, p.fecha_inicio, p.fecha_fin, p.precio, p.id_habitacion " + "FROM reserva r "
							+ "JOIN paquete p ON r.id_paquete = p.id " + "WHERE r.id = ? AND r.estado = 'activa'");

			stmt.setInt(1, idReserva);
			ResultSet rs = stmt.executeQuery();

			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "Reserva no encontrada o no está activa", "ERROR", 0);
				return false;
			}

			int idHabitacion = rs.getInt("id_habitacion");
			double precioPaquete = rs.getDouble("precio");
			java.sql.Timestamp fechaCheckin = rs.getTimestamp("fecha_checkin");

			// Calcular días de estadía

			LocalDateTime checkinTime = fechaCheckin.toLocalDateTime();
			LocalDateTime checkoutTime = LocalDateTime.now();

			// ChronoUnit toma el conjunto de dias
			long diasEstadia = ChronoUnit.DAYS.between(checkinTime, checkoutTime);

			if (diasEstadia == 0)
				diasEstadia = 1;

			// Calcular monto final
			double montoFinal = precioPaquete;

			// Cambiar estado de habitación a "disponible"
			PreparedStatement stmtHab = (PreparedStatement) conx
					.prepareStatement("UPDATE habitacion SET estado = 'disponible' WHERE id = ?");

			stmtHab.setInt(1, idHabitacion);
			stmtHab.executeUpdate();

			// Actualizar reserva con check-out
			PreparedStatement stmtReserva = (PreparedStatement) conx.prepareStatement(
					"UPDATE reserva SET estado = 'finalizada', fecha_checkout = ?, monto_final = ? WHERE id = ?");
			stmtReserva.setTimestamp(1, Timestamp.valueOf(checkoutTime));
			stmtReserva.setDouble(2, montoFinal);
			stmtReserva.setInt(3, idReserva);

			int filas = stmtReserva.executeUpdate();

			if (filas > 0) {
				JOptionPane.showMessageDialog(null,
						"CHECK-OUT REALIZADO\n\n" + "Días de estadía: " + diasEstadia + "\n" + "Monto total: $"
								+ String.format("%.2f", montoFinal) + "\n\n" + "🔑 Llave desactivada\n"
								+ "¡Gracias por su visita!",
						"CHECK-OUT EXITOSO", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al realizar check-out: " + e.getMessage(), "ERROR", 0);
		}
		return false;
	}// fin

	// ver reservas

	public static List<Reserva> verTodasReservas() {

		List<Reserva> reservas = new ArrayList<>();

		try {

			PreparedStatement stmt = (PreparedStatement) conx
					.prepareStatement("SELECT r.*, u.nombre, u.apellido, u.dni, p.id as id_paq, "
							+ "p.fecha_inicio, p.fecha_fin, p.precio, h.numero as num_hab " + "FROM reserva r "
							+ "JOIN usuario u ON r.id_usuario = u.id " + "JOIN paquete p ON r.id_paquete = p.id "
							+ "LEFT JOIN habitacion h ON p.id_habitacion = h.id " + "ORDER BY r.estado, r.id DESC");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Crear objetos simplificados para la lista

				Cliente cliente = new Cliente();

				cliente.setId(rs.getInt("id_usuario"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setApellido(rs.getString("apellido"));
				cliente.setDni(rs.getInt("dni"));

				Paquete paquete = new Paquete();

				paquete.setId(rs.getInt("id_paq"));
				paquete.setPrecio(rs.getDouble("precio"));

				Habitacion hab = new Habitacion();
				hab.setNumero(rs.getInt("num_hab"));
				paquete.setHabitacion(hab);

				Hotel hotel = new Hotel();
				paquete.setHotel(hotel);

				Reserva reserva = new Reserva(rs.getInt("id"), cliente, paquete, rs.getString("estado"),
						rs.getTimestamp("fecha_checkin") != null ? rs.getTimestamp("fecha_checkin").toLocalDateTime()
								: null,
						rs.getTimestamp("fecha_checkout") != null ? rs.getTimestamp("fecha_checkout").toLocalDateTime()
								: null,
						rs.getString("tarjeta_resguardo"), rs.getDouble("monto_final"));

				reservas.add(reserva);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al obtener reservas: " + e.getMessage(), "ERROR", 0);
		}
		return reservas;
	}// fin

	// listar habitaciones

	public static List<Habitacion> verHabitaciones(int idHotel) {

		List<Habitacion> habitaciones = new ArrayList<>();

		try {
			PreparedStatement stmt = (PreparedStatement) conx
					.prepareStatement("SELECT * FROM habitacion WHERE id_hotel = ? ORDER BY numero");

			stmt.setInt(1, idHotel);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Habitacion hab = new Habitacion();
				hab.setId(rs.getInt("id"));
				hab.setNumero(rs.getInt("numero"));
				hab.setEstado(rs.getString("estado"));
				hab.setTipo(rs.getString("tipo"));
				hab.setPrecio_noche(rs.getDouble("precio"));
				hab.setCant_camas(rs.getInt("cant_camas"));

				habitaciones.add(hab);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return habitaciones;
	}// fin

	

}// fin clase
