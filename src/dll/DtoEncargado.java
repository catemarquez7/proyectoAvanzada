package dll;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;

import bll.Actividad;
import bll.Cliente;
import bll.Encargado;
import bll.Habitacion;
import bll.Hotel;
import bll.Paquete;
import bll.Promocion;
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
	public static boolean realizarCheckin(int id_reserva, String dni_cl, String tarjeta, int id_he, javax.swing.JLabel lblMensaje) {
	    try {
	        PreparedStatement stmtVerif = (PreparedStatement) conx
	            .prepareStatement("SELECT r.*, u.dni, p.id_hotel, p.id_habitacion FROM reserva r "
	                + "JOIN usuario u ON r.id_usuario = u.id " + "JOIN paquete p ON r.id_paquete = p.id "
	                + "WHERE r.id = ? AND r.estado = 'pendiente' AND p.id_hotel = ?");

	        stmtVerif.setInt(1, id_reserva);
	        stmtVerif.setInt(2, id_he);

	        ResultSet rs = stmtVerif.executeQuery();

	        if (!rs.next()) {
	            if (lblMensaje != null) {
	                lblMensaje.setForeground(java.awt.Color.RED);
	                lblMensaje.setText("Reserva no encontrada, ya procesada o no pertenece a su hotel");
	            } else {
	                JOptionPane.showMessageDialog(null, "Reserva no encontrada, ya procesada o no pertenece a su hotel", "ERROR", 0);
	            }
	            return false;
	        }

	        int dni = rs.getInt("dni");
	        int id_habitacion = rs.getInt("id_habitacion");

	        // Verifica DNI
	        if (dni != Integer.parseInt(dni_cl)) {
	            if (lblMensaje != null) {
	                lblMensaje.setForeground(java.awt.Color.RED);
	                lblMensaje.setText("El DNI no coincide con la reserva");
	            } else {
	                JOptionPane.showMessageDialog(null, "El DNI no coincide con la reserva", "ERROR", 0);
	            }
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

	            if (lblMensaje != null) {
	                lblMensaje.setForeground(new java.awt.Color(0, 128, 0));
	                lblMensaje.setText("CHECK-IN EXITOSO - Habitación: " + numHabitacion + " - Llave activada");
	            } else {
	                JOptionPane.showMessageDialog(null,
	                    "CHECK-IN REALIZADO\n" + "Habitación asignada: " + numHabitacion + "\n"
	                        + "Tarjeta de resguardo: " + tarjeta + "\n" + "Llave activada - Bienvenido/a!",
	                    "CHECK-IN EXITOSO", JOptionPane.INFORMATION_MESSAGE);
	            }
	            return true;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        if (lblMensaje != null) {
	            lblMensaje.setForeground(java.awt.Color.RED);
	            lblMensaje.setText("Error al realizar check-in: " + e.getMessage());
	        } else {
	            JOptionPane.showMessageDialog(null, "Error al realizar check-in: " + e.getMessage(), "ERROR", 0);
	        }
	    }
	    return false;
	}// fin

	// check-out
	public static boolean realizarCheckout(int id_reserva, int id_he, javax.swing.JLabel lblMensaje) {
		try {
			PreparedStatement stmt = (PreparedStatement) conx
					.prepareStatement("SELECT r.*, p.fecha_inicio, p.fecha_fin, p.precio, p.id_habitacion, p.id_hotel "
							+ "FROM reserva r " + "JOIN paquete p ON r.id_paquete = p.id "
							+ "WHERE r.id = ? AND r.estado = 'activa' AND p.id_hotel = ?");

			stmt.setInt(1, id_reserva);
			stmt.setInt(2, id_he);

			ResultSet rs = stmt.executeQuery();

			if (!rs.next()) {
				if (lblMensaje != null) {
					lblMensaje.setForeground(java.awt.Color.RED);
					lblMensaje.setText("Reserva no encontrada, no está activa o no pertenece a su hotel");
				} else {
					JOptionPane.showMessageDialog(null,
							"Reserva no encontrada, no está activa o no pertenece a su hotel", "ERROR", 0);
				}
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
				if (lblMensaje != null) {
					lblMensaje.setForeground(new java.awt.Color(0, 128, 0));
					lblMensaje.setText("CHECK-OUT EXITOSO - Días: " + diasEstadia + " - Monto: $"
							+ String.format("%.2f", montoFinal) + " - Llave desactivada");
				} else {
					JOptionPane.showMessageDialog(null,
							"CHECK-OUT REALIZADO\n\n" + "Días de estadía: " + diasEstadia + "\n" + "Monto total: $"
									+ String.format("%.2f", montoFinal) + "\n\n" + "Llave desactivada\n"
									+ "¡Gracias por su visita!",
							"CHECK-OUT EXITOSO", JOptionPane.INFORMATION_MESSAGE);
				}
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (lblMensaje != null) {
				lblMensaje.setForeground(java.awt.Color.RED);
				lblMensaje.setText("Error al realizar check-out: " + e.getMessage());
			} else {
				JOptionPane.showMessageDialog(null, "Error al realizar check-out: " + e.getMessage(), "ERROR", 0);
			}
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
	}// fin

	// ver clientes
	public static List<Cliente> verClientesPorHotel(int id_hotel) {
		List<Cliente> clientes = new ArrayList<>();

		try {
			PreparedStatement stmt = (PreparedStatement) conx.prepareStatement(
					"SELECT DISTINCT u.* " + "FROM usuario u " + "INNER JOIN reserva r ON u.id = r.id_usuario "
							+ "INNER JOIN paquete p ON r.id_paquete = p.id "
							+ "WHERE p.id_hotel = ? AND u.tipo_usuario = '1' " + "ORDER BY u.nombre, u.apellido");
			stmt.setInt(1, id_hotel);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setApellido(rs.getString("apellido"));
				cliente.setDni(rs.getInt("dni"));
				cliente.setMail(rs.getString("mail"));
				cliente.setFecha_nac(rs.getDate("fecha_nac").toLocalDate());
				cliente.setDireccion(rs.getString("direccion"));
				cliente.setUser(rs.getString("user"));
				cliente.setFecha_creacion(rs.getDate("fecha_creacion").toLocalDate());
				cliente.setEstado(rs.getString("estado"));
				cliente.setTipo_usuario(rs.getString("tipo_usuario"));

				clientes.add(cliente);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e.getMessage(), "ERROR", 0);
		}

		return clientes;
	}// fin

	// ver reservas de cliente
	public static List<Reserva> verReservasDeCliente(int id_cliente, int id_hotel) {
		List<Reserva> reservas = new ArrayList<>();

		try {
			PreparedStatement stmt = (PreparedStatement) conx
					.prepareStatement("SELECT r.*, p.id as id_paq, p.fecha_inicio, p.fecha_fin, p.precio, "
							+ "h.nombre as hotel_nombre, hab.numero as hab_numero, " + "act.nombre as act_nombre "
							+ "FROM reserva r " + "INNER JOIN paquete p ON r.id_paquete = p.id "
							+ "INNER JOIN hotel h ON p.id_hotel = h.id "
							+ "LEFT JOIN habitacion hab ON p.id_habitacion = hab.id "
							+ "LEFT JOIN actividad act ON p.id_actividad = act.id "
							+ "WHERE r.id_usuario = ? AND p.id_hotel = ? " + "ORDER BY r.id DESC");
			stmt.setInt(1, id_cliente);
			stmt.setInt(2, id_hotel);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(id_cliente);

				Paquete paquete = new Paquete();
				paquete.setId(rs.getInt("id_paq"));
				paquete.setInicioDate(rs.getDate("fecha_inicio").toLocalDate());
				paquete.setFinDate(rs.getDate("fecha_fin").toLocalDate());
				paquete.setPrecio(rs.getDouble("precio"));

				Hotel hotel = new Hotel();
				hotel.setNombre(rs.getString("hotel_nombre"));
				paquete.setHotel(hotel);

				if (rs.getObject("hab_numero") != null) {
					Habitacion hab = new Habitacion();
					hab.setNumero(rs.getInt("hab_numero"));
					paquete.setHabitacion(hab);
				}

				if (rs.getString("act_nombre") != null) {
					Actividad act = new Actividad();
					act.setNombre(rs.getString("act_nombre"));
					paquete.setActividad(act);
				}

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

	// ver paquetes
	public static List<Paquete> verPaquetesDelHotel(int id_hotel) {
		List<Paquete> paquetes = new ArrayList<>();

		try {
			PreparedStatement stmt = (PreparedStatement) conx.prepareStatement(
					"SELECT p.*, h.*, hab.*, act.* " + "FROM paquete p " + "INNER JOIN hotel h ON p.id_hotel = h.id "
							+ "LEFT JOIN habitacion hab ON p.id_habitacion = hab.id "
							+ "LEFT JOIN actividad act ON p.id_actividad = act.id " + "WHERE p.id_hotel = ? "
							+ "ORDER BY p.fecha_inicio");
			stmt.setInt(1, id_hotel);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Hotel hotel = new Hotel();
				hotel.setId(rs.getInt("h.id"));
				hotel.setNombre(rs.getString("h.nombre"));
				hotel.setProvincia(rs.getString("h.provincia"));
				hotel.setDireccion(rs.getString("h.direccion"));

				Habitacion habitacion = null;
				if (rs.getObject("hab.id") != null) {
					habitacion = new Habitacion();
					habitacion.setId(rs.getInt("hab.id"));
					habitacion.setNumero(rs.getInt("hab.numero"));
					habitacion.setTipo(rs.getString("hab.tipo"));
					habitacion.setPrecio_noche(rs.getDouble("hab.precio"));
					habitacion.setCant_camas(rs.getInt("hab.cant_camas"));
					habitacion.setEstado(rs.getString("hab.estado"));
				}

				Actividad actividad = null;
				if (rs.getObject("act.id") != null) {
					actividad = new Actividad();
					actividad.setId(rs.getInt("act.id"));
					actividad.setNombre(rs.getString("act.nombre"));
					actividad.setCategoria(rs.getString("act.categoria"));
					actividad.setPrecio(rs.getDouble("act.precio"));
					actividad.setDuracion(rs.getDouble("act.duracion"));
					actividad.setLocacion(rs.getString("act.locacion"));
				}

				Paquete paquete = new Paquete();
				paquete.setId(rs.getInt("p.id"));
				paquete.setInicioDate(rs.getDate("p.fecha_inicio").toLocalDate());
				paquete.setFinDate(rs.getDate("p.fecha_fin").toLocalDate());
				paquete.setPrecio(rs.getDouble("p.precio"));
				paquete.setHotel(hotel);
				paquete.setHabitacion(habitacion);
				paquete.setActividad(actividad);

				paquetes.add(paquete);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar paquetes: " + e.getMessage(), "ERROR", 0);
		}

		return paquetes;
	}// fin

	// ver actividades
	public static List<Actividad> verActividadesPorHotel(int id_hotel) {
		List<Actividad> actividades = new ArrayList<>();
		try {
			PreparedStatement stmt = (PreparedStatement) conx.prepareStatement("SELECT a.*, h.nombre as hotel_nombre "
					+ "FROM actividad a " + "INNER JOIN hotel h ON a.id_hotel = h.id " + "WHERE a.id_hotel = ? "
					+ "ORDER BY a.fecha_inicio, a.nombre");
			stmt.setInt(1, id_hotel);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Hotel hotel = new Hotel();
				hotel.setId(id_hotel);
				hotel.setNombre(rs.getString("hotel_nombre"));

				Actividad act = new Actividad();
				act.setId(rs.getInt("id"));
				act.setNombre(rs.getString("nombre"));
				act.setCategoria(rs.getString("categoria"));
				act.setLocacion(rs.getString("locacion"));
				act.setEdad_minima(rs.getInt("edad_minima"));
				act.setEdad_maxima(rs.getInt("edad_maxima"));
				act.setPrecio(rs.getDouble("precio"));
				act.setDuracion(rs.getDouble("duracion"));
				act.setInicioDate(rs.getDate("fecha_inicio").toLocalDate());
				act.setFinDate(rs.getDate("fecha_fin").toLocalDate());
				act.setHotel(hotel);

				actividades.add(act);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actividades;
	}// fin

	// crear promoción
	public static boolean crearPromocion(String nombre, String descripcion, double porcentaje, 
	        LocalDate fechaInicio, LocalDate fechaFin, int id_hotel) {
	    try {
	        // Validaciones básicas
	        if (porcentaje <= 0 || porcentaje > 100) {
	            System.err.println("El porcentaje debe estar entre 1 y 100");
	            return false;
	        }

	        if (fechaFin.isBefore(fechaInicio)) {
	            System.err.println("La fecha de fin debe ser posterior a la fecha de inicio");
	            return false;
	        }

	        PreparedStatement stmt = (PreparedStatement) conx.prepareStatement(
	            "INSERT INTO promocion (nombre, descripcion, porcentaje_descuento, fecha_inicio, fecha_fin, estado, id_hotel) "
	            + "VALUES (?, ?, ?, ?, ?, 'activa', ?)");

	        stmt.setString(1, nombre);
	        stmt.setString(2, descripcion);
	        stmt.setDouble(3, porcentaje);
	        stmt.setDate(4, java.sql.Date.valueOf(fechaInicio));
	        stmt.setDate(5, java.sql.Date.valueOf(fechaFin));
	        stmt.setInt(6, id_hotel);

	        int filas = stmt.executeUpdate();
	        return filas > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println("Error al crear promoción: " + e.getMessage());
	        return false;
	    }
	}// fin

	// aplicar promoción a paquete
	public static boolean aplicarPromocionAPaquete(int id_paquete, int id_promocion, int id_hotel, javax.swing.JLabel lblMensaje) {
		try {
			// Verificar que el paquete y la promoción existen y obtener el porcentaje
			PreparedStatement stmtVerif = (PreparedStatement) conx.prepareStatement(
				"SELECT p.precio, p.precio_original, pr.porcentaje_descuento, pr.nombre as promo_nombre FROM paquete p "
				+ "LEFT JOIN promocion pr ON pr.id = ? "
				+ "WHERE p.id = ? AND p.id_hotel = ?");

			stmtVerif.setInt(1, id_promocion);
			stmtVerif.setInt(2, id_paquete);
			stmtVerif.setInt(3, id_hotel);

			ResultSet rs = stmtVerif.executeQuery();

			if (!rs.next()) {
				lblMensaje.setForeground(java.awt.Color.RED);
				lblMensaje.setText("Paquete o promoción no encontrados o no pertenecen al hotel.");
				return false;
			}

			// Si precio_original es 0, usamos el precio actual como base (asumiendo que es el precio sin descuento)
			double precioActual = rs.getDouble("precio");
			double precioOriginal = rs.getDouble("precio_original");
			if (precioOriginal <= 0) {
				precioOriginal = precioActual;
			}
			
			double porcentajeDescuento = rs.getDouble("porcentaje_descuento");
	        String promoNombre = rs.getString("promo_nombre");
	        
	        // Calcular nuevo precio
			double nuevoPrecio = precioOriginal - (precioOriginal * porcentajeDescuento / 100);

			// Aplicar la promoción
			PreparedStatement stmtUpdate = (PreparedStatement) conx.prepareStatement(
				"UPDATE paquete SET id_promocion = ?, precio = ?, precio_original = ? WHERE id = ?");

			stmtUpdate.setInt(1, id_promocion);
			stmtUpdate.setDouble(2, nuevoPrecio);
			stmtUpdate.setDouble(3, precioOriginal); // Guardamos el original
			stmtUpdate.setInt(4, id_paquete);

			int filas = stmtUpdate.executeUpdate();
			
			if (filas > 0) {
				lblMensaje.setForeground(new java.awt.Color(0, 150, 0));
				lblMensaje.setText("✓ Promoción '" + promoNombre + "' aplicada. Nuevo precio: $" + String.format("%.2f", nuevoPrecio));
			}
			
			return filas > 0;

		} catch (Exception e) {
			e.printStackTrace();
			lblMensaje.setForeground(java.awt.Color.RED);
			lblMensaje.setText("Error al aplicar promoción: " + e.getMessage());
			return false;
		}
	}// fin

	// Ver promociones del hotel
	public static List<Promocion> verPromocionesDelHotel(int id_hotel) {
		List<Promocion> promociones = new ArrayList<>();

		try {
			PreparedStatement stmt = (PreparedStatement) conx
					.prepareStatement("SELECT * FROM promocion WHERE id_hotel = ? ORDER BY fecha_inicio DESC");

			stmt.setInt(1, id_hotel);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Promocion promo = new Promocion(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"),
						rs.getDouble("porcentaje_descuento"), rs.getDate("fecha_inicio").toLocalDate(),
						rs.getDate("fecha_fin").toLocalDate(), rs.getString("estado"), rs.getInt("id_hotel"));
				promociones.add(promo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return promociones;
	}// fin

	// Eliminar promoción
	public static boolean eliminarPromocion(int id_promocion, int id_hotel) {
	    try {
	        // Primero restaurar precios de paquetes que tenían esta promoción
	        PreparedStatement stmtPaquetes = (PreparedStatement) conx.prepareStatement(
	            "UPDATE paquete SET precio = precio_original, id_promocion = NULL "
	            + "WHERE id_promocion = ? AND id_hotel = ?");

	        stmtPaquetes.setInt(1, id_promocion);
	        stmtPaquetes.setInt(2, id_hotel);
	        // Eliminar la promoción
	        PreparedStatement stmtPromo = (PreparedStatement) conx.prepareStatement(
	            "DELETE FROM promocion WHERE id = ? AND id_hotel = ?");

	        stmtPromo.setInt(1, id_promocion);
	        stmtPromo.setInt(2, id_hotel);

	        int filas = stmtPromo.executeUpdate();

	        if (filas > 0) {
	            
	            return true;
	        } else {
	            
	            return false;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println("Error al eliminar promoción: " + e.getMessage());
	        return false;
	    }
	}// fin

	// ver paquetes con promos
	public static List<Paquete> verPaquetesConPromociones(int id_hotel) {
		List<Paquete> paquetes = new ArrayList<>();

		try {
			PreparedStatement stmt = (PreparedStatement) conx.prepareStatement(
					"SELECT p.*, h.nombre as hotel_nombre, hab.numero as hab_numero, hab.tipo as hab_tipo, "
							+ "act.nombre as act_nombre, pr.id as promo_id, pr.nombre as promo_nombre, "
							+ "pr.porcentaje_descuento, pr.fecha_inicio as promo_inicio, pr.fecha_fin as promo_fin, pr.estado as promo_estado "
							+ "FROM paquete p " + "INNER JOIN hotel h ON p.id_hotel = h.id "
							+ "LEFT JOIN habitacion hab ON p.id_habitacion = hab.id "
							+ "LEFT JOIN actividad act ON p.id_actividad = act.id "
							+ "LEFT JOIN promocion pr ON p.id_promocion = pr.id " + "WHERE p.id_hotel = ? "
							+ "ORDER BY p.fecha_inicio");

			stmt.setInt(1, id_hotel);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Hotel hotel = new Hotel();
				hotel.setNombre(rs.getString("hotel_nombre"));

				Habitacion hab = null;
				if (rs.getObject("hab_numero") != null) {
					hab = new Habitacion();
					hab.setNumero(rs.getInt("hab_numero"));
					hab.setTipo(rs.getString("hab_tipo"));
				}

				Actividad act = null;
				if (rs.getString("act_nombre") != null) {
					act = new Actividad();
					act.setNombre(rs.getString("act_nombre"));
				}

				Promocion promo = null;
				if (rs.getObject("promo_id") != null) {
					promo = new Promocion(rs.getInt("promo_id"), rs.getString("promo_nombre"), null,
							rs.getDouble("porcentaje_descuento"), rs.getDate("promo_inicio").toLocalDate(),
							rs.getDate("promo_fin").toLocalDate(), rs.getString("promo_estado"), id_hotel);
				}

				Paquete paquete = new Paquete();
				paquete.setId(rs.getInt("id"));
				paquete.setInicioDate(rs.getDate("fecha_inicio").toLocalDate());
				paquete.setFinDate(rs.getDate("fecha_fin").toLocalDate());
				paquete.setPrecio(rs.getDouble("precio"));
				paquete.setPrecioOriginal(rs.getDouble("precio_original"));
				paquete.setHotel(hotel);
				paquete.setHabitacion(hab);
				paquete.setActividad(act);
				paquete.setPromocion(promo);

				paquetes.add(paquete);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return paquetes;
	}// fin

	// Editar
	public static boolean editarPromocion(int id_promocion, String campo, String nuevoValor, int id_hotel) {
	    try {
	        String sql = "";
	        PreparedStatement stmt = null;

	        switch (campo) {
	            case "nombre":
	                sql = "UPDATE promocion SET nombre = ? WHERE id = ? AND id_hotel = ?";
	                stmt = (PreparedStatement) conx.prepareStatement(sql);
	                stmt.setString(1, nuevoValor);
	                break;

	            case "descripcion":
	                sql = "UPDATE promocion SET descripcion = ? WHERE id = ? AND id_hotel = ?";
	                stmt = (PreparedStatement) conx.prepareStatement(sql);
	                stmt.setString(1, nuevoValor);
	                break;

	            case "porcentaje":
	                sql = "UPDATE promocion SET porcentaje_descuento = ? WHERE id = ? AND id_hotel = ?";
	                stmt = (PreparedStatement) conx.prepareStatement(sql);
	                stmt.setDouble(1, Double.parseDouble(nuevoValor));
	                break;

	            case "fecha_inicio":
	                sql = "UPDATE promocion SET fecha_inicio = ? WHERE id = ? AND id_hotel = ?";
	                stmt = (PreparedStatement) conx.prepareStatement(sql);
	                stmt.setDate(1, java.sql.Date.valueOf(nuevoValor));
	                break;

	            case "fecha_fin":
	                sql = "UPDATE promocion SET fecha_fin = ? WHERE id = ? AND id_hotel = ?";
	                stmt = (PreparedStatement) conx.prepareStatement(sql);
	                stmt.setDate(1, java.sql.Date.valueOf(nuevoValor));
	                break;

	            case "estado":
	                sql = "UPDATE promocion SET estado = ? WHERE id = ? AND id_hotel = ?";
	                stmt = (PreparedStatement) conx.prepareStatement(sql);
	                stmt.setString(1, nuevoValor);
	                break;

	            default:
	                System.err.println("Campo inválido: " + campo);
	                return false;
	        }

	        stmt.setInt(2, id_promocion);
	        stmt.setInt(3, id_hotel);

	        int filas = stmt.executeUpdate();

	        if (filas > 0) {
	            // Si se cambió el porcentaje, actualizar precios de paquetes
	            if (campo.equals("porcentaje")) {
	                actualizarPreciosPaquetesConPromocion(id_promocion, Double.parseDouble(nuevoValor));
	            }
	            
	            
	            return true;
	        } else {
	            System.err.println("No se pudo actualizar la promoción");
	            return false;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}// fin

	// Actualizar
	private static void actualizarPreciosPaquetesConPromocion(int id_promocion, double nuevoPorcentaje) {
	    try {
	        PreparedStatement stmt = (PreparedStatement) conx.prepareStatement(
	            "UPDATE paquete SET precio = precio_original - (precio_original * ? / 100) "
	            + "WHERE id_promocion = ?");

	        stmt.setDouble(1, nuevoPorcentaje);
	        stmt.setInt(2, id_promocion);

	        int filasActualizadas = stmt.executeUpdate();

	        if (filasActualizadas > 0) {
	            
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}// fin

}// fin clase
