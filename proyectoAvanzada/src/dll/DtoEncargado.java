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
import bll.Encargado;
import bll.Habitacion;
import bll.Hotel;
import bll.Paquete;
import bll.Reserva;

public class DtoEncargado {

	private static Connection conx = Conexion.getInstance().getConnection();

	// cargar encargado
	public static Encargado cargarEncargado(int id_usuario) {
		try {
			PreparedStatement stmt = (PreparedStatement) conx
					.prepareStatement("SELECT e.*, u.nombre, u.apellido, u.fecha_nac, u.mail, u.dni, u.direccion, "
							+ "u.user, u.pass, u.pregunta, u.respuesta, u.fecha_creacion, u.tipo_usuario, u.estado "
							+ "FROM encargado e " + "JOIN usuario u ON e.id_usuario = u.id "
							+ "WHERE e.id_usuario = ?");

			stmt.setInt(1, id_usuario);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Encargado encargado = new Encargado(rs.getString("nombre"), rs.getString("apellido"),
						rs.getDate("fecha_nac").toLocalDate(), rs.getString("mail"), rs.getInt("dni"),
						rs.getString("direccion"), rs.getInt("id_usuario"), rs.getString("user"), rs.getString("pass"),
						rs.getString("pregunta"), rs.getString("respuesta"), rs.getDate("fecha_creacion").toLocalDate(),
						rs.getString("tipo_usuario"), rs.getString("estado"), rs.getInt("id_hotel"));
				return encargado;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}// fin

	// check-in
	public static boolean realizarCheckin(int id_reserva, String dni_cl, String tarjeta, int id_he) {

		try {

			PreparedStatement stmtVerif = (PreparedStatement) conx
					.prepareStatement("SELECT r.*, u.dni, p.id_hotel, p.id_habitacion FROM reserva r "
							+ "JOIN usuario u ON r.id_usuario = u.id " + "JOIN paquete p ON r.id_paquete = p.id "
							+ "WHERE r.id = ? AND r.estado = 'pendiente' AND p.id_hotel = ?");

			stmtVerif.setInt(1, id_reserva);
			stmtVerif.setInt(2, id_he);

			ResultSet rs = stmtVerif.executeQuery();

			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "Reserva no encontrada, ya procesada o no pertenece a su hotel",
						"ERROR", 0);
				return false;
			}

			int dni = rs.getInt("dni");
			int id_habitacion = rs.getInt("id_habitacion");

			// Verifica DNI
			if (dni != Integer.parseInt(dni_cl)) {
				JOptionPane.showMessageDialog(null, "El DNI no coincide con la reserva", "ERROR", 0);
				return false;
			}

			// Cambio a ocupada
			PreparedStatement stmtHabEstado = (PreparedStatement) conx
					.prepareStatement("UPDATE habitacion SET estado = 'ocupada' WHERE id = ? AND id_hotel = ?");

			stmtHabEstado.setInt(1, id_habitacion);
			stmtHabEstado.setInt(2, id_he);
			stmtHabEstado.executeUpdate();

			// Actualizar reserva
			PreparedStatement stmtReserva = (PreparedStatement) conx.prepareStatement(
					"UPDATE reserva SET estado = 'activa', fecha_checkin = ?, tarjeta_resguardo = ? WHERE id = ?");

			stmtReserva.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			stmtReserva.setString(2, tarjeta);
			stmtReserva.setInt(3, id_reserva);

			int filas = stmtReserva.executeUpdate();

			if (filas > 0) {
				// Número de habitación
				PreparedStatement stmtNumHab = (PreparedStatement) conx
						.prepareStatement("SELECT numero FROM habitacion WHERE id = ?");

				stmtNumHab.setInt(1, id_habitacion);

				ResultSet rsNum = stmtNumHab.executeQuery();

				rsNum.next();
				int numHabitacion = rsNum.getInt("numero");

				JOptionPane.showMessageDialog(null,
						"CHECK-IN REALIZADO\n" + "Habitación asignada: " + numHabitacion + "\n"
								+ "Tarjeta de resguardo: " + tarjeta + "\n" + "Llave activada - Bienvenido/a!",
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
	public static boolean realizarCheckout(int id_reserva, int id_he) {

		try {

			PreparedStatement stmt = (PreparedStatement) conx
					.prepareStatement("SELECT r.*, p.fecha_inicio, p.fecha_fin, p.precio, p.id_habitacion, p.id_hotel "
							+ "FROM reserva r " + "JOIN paquete p ON r.id_paquete = p.id "
							+ "WHERE r.id = ? AND r.estado = 'activa' AND p.id_hotel = ?");

			stmt.setInt(1, id_reserva);
			stmt.setInt(2, id_he);

			ResultSet rs = stmt.executeQuery();

			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "Reserva no encontrada, no está activa o no pertenece a su hotel",
						"ERROR", 0);
				return false;
			}

			int id_habitacion = rs.getInt("id_habitacion");
			double precioPaquete = rs.getDouble("precio");

			Timestamp fechaCheckin = rs.getTimestamp("fecha_checkin");

			// Dias de estadía
			LocalDateTime checkinTime = fechaCheckin.toLocalDateTime();
			LocalDateTime checkoutTime = LocalDateTime.now();

			long diasEstadia = ChronoUnit.DAYS.between(checkinTime, checkoutTime);
			if (diasEstadia == 0)
				diasEstadia = 1;

			double montoFinal = precioPaquete;

			// Cambiar estado
			PreparedStatement stmtHab = (PreparedStatement) conx
					.prepareStatement("UPDATE habitacion SET estado = 'disponible' WHERE id = ? AND id_hotel = ?");

			stmtHab.setInt(1, id_habitacion);
			stmtHab.setInt(2, id_he);
			stmtHab.executeUpdate();

			// Actualizar reserva con check-out
			PreparedStatement stmtReserva = (PreparedStatement) conx.prepareStatement(
					"UPDATE reserva SET estado = 'finalizada', fecha_checkout = ?, monto_final = ? WHERE id = ?");

			stmtReserva.setTimestamp(1, Timestamp.valueOf(checkoutTime));
			stmtReserva.setDouble(2, montoFinal);
			stmtReserva.setInt(3, id_reserva);

			int filas = stmtReserva.executeUpdate();

			if (filas > 0) {
				JOptionPane.showMessageDialog(null,
						"CHECK-OUT REALIZADO\n\n" + "Días de estadía: " + diasEstadia + "\n" + "Monto total: $"
								+ String.format("%.2f", montoFinal) + "\n\n" + "Llave desactivada\n"
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
	public static List<Reserva> verReservas(int id_hotel) {

		List<Reserva> reservas = new ArrayList<>();

		try {
			PreparedStatement stmt = (PreparedStatement) conx
					.prepareStatement("SELECT r.*, u.nombre, u.apellido, u.dni, p.id as id_paq, "
							+ "p.fecha_inicio, p.fecha_fin, p.precio, h.numero as num_hab " + "FROM reserva r "
							+ "JOIN usuario u ON r.id_usuario = u.id " + "JOIN paquete p ON r.id_paquete = p.id "
							+ "JOIN habitacion h ON p.id_habitacion = h.id " + "WHERE p.id_hotel = ? "
							+ "ORDER BY r.estado, r.id DESC");

			stmt.setInt(1, id_hotel);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
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
		}
		return reservas;
	}// fin

	// ver habitaciones
	public static List<Habitacion> verHabitaciones(int id_hotel) {
		List<Habitacion> habitaciones = new ArrayList<>();
		try {
			PreparedStatement stmt = (PreparedStatement) conx
					.prepareStatement("SELECT * FROM habitacion WHERE id_hotel = ? ORDER BY numero");
			stmt.setInt(1, id_hotel);
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
	}//fin

}// fin clase
