package bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dll.DtoEncargado;
import repository.Validaciones;

public class Encargado extends Usuario {

	// atributos
	protected int id_hotel;

	// constructores

	public Encargado(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni, String direccion,
			int id, String user, String pass, String pregunta, String respuesta, LocalDate fecha_creacion,
			String tipo_usuario, String estado, int id_hotel) {
		super(nombre, apellido, fecha_nac, mail, dni, direccion, id, user, pass, pregunta, respuesta, fecha_creacion,
				tipo_usuario, estado);
		this.id_hotel = id_hotel;
	}

	public Encargado() {

	}

	// getters y setters
	public int getId_hotel() {
		return id_hotel;
	}

	public void setId_hotel(int id_hotel) {
		this.id_hotel = id_hotel;
	}

	// toString
	public String toString() {
		return "Encargado [idHotel=" + id_hotel + ", nombre=" + nombre + ", apellido=" + apellido + "]";
	}

	// metodos

	// Check-in
	public static void realizarCheckin(int id_hotel, javax.swing.JLabel lblMensaje) {

	    List<Reserva> reservas = DtoEncargado.verReservas(id_hotel);

	    if (reservas.isEmpty()) {
	        if (lblMensaje != null) {
	            lblMensaje.setForeground(java.awt.Color.RED);
	            lblMensaje.setText("No hay reservas pendientes en su hotel");
	        } else {
	            JOptionPane.showMessageDialog(null, "No hay reservas pendientes en su hotel.", "INFO",
	                JOptionPane.INFORMATION_MESSAGE);
	        }
	        return;
	    }

	    String[] opciones = new String[reservas.size()];
	    for (int i = 0; i < reservas.size(); i++) {
	        Reserva r = reservas.get(i);
	        opciones[i] = "ID: " + r.getId() + " | " + r.getCliente().getNombre() + " " + r.getCliente().getApellido()
	            + " | Estado: " + r.getEstado();
	    }

	    String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione la reserva para check-in:",
	        "CHECK-IN", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

	    if (seleccion == null) return;

	    int idReserva = Integer.parseInt(seleccion.split(" ")[1].replace("ID:", ""));

	    int dni = Validaciones.ValidarNum("Ingrese DNI del cliente:");
	    String tarjeta = Validaciones.ValidarContras("Ingrese número de tarjeta de resguardo:");

	    DtoEncargado.realizarCheckin(idReserva, String.valueOf(dni), tarjeta, id_hotel, lblMensaje);
	}// fin

	// Check-out
	public static void realizarCheckout(int id_hotel, javax.swing.JLabel lblMensaje) {
	    List<Reserva> reservas = DtoEncargado.verReservas(id_hotel);

	    // Filtrar solo reservas activas
	    List<Reserva> activas = new ArrayList<>();
	    for (Reserva r : reservas) {
	        if ("activa".equalsIgnoreCase(r.getEstado())) {
	            activas.add(r);
	        }
	    }

	    if (activas.isEmpty()) {
	        if (lblMensaje != null) {
	            lblMensaje.setForeground(java.awt.Color.RED);
	            lblMensaje.setText("No hay reservas activas para check-out");
	        } else {
	            JOptionPane.showMessageDialog(null, "No hay reservas activas para check-out.", "INFO",
	                JOptionPane.INFORMATION_MESSAGE);
	        }
	        return;
	    }

	    String[] opciones = new String[activas.size()];
	    for (int i = 0; i < activas.size(); i++) {
	        Reserva r = activas.get(i);
	        opciones[i] = "ID: " + r.getId() + " | " + r.getCliente().getNombre() + " " + r.getCliente().getApellido();
	    }

	    String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione la reserva para check-out:",
	        "CHECK-OUT", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

	    if (seleccion == null)
	        return;

	    int idReserva = Integer.parseInt(seleccion.split(" ")[1].replace("ID:", ""));
	    DtoEncargado.realizarCheckout(idReserva, id_hotel, lblMensaje);
	}// fin

	// Ver reservas
	public static void verReservas(int id_hotel) {
		List<Reserva> reservas = DtoEncargado.verReservas(id_hotel);

		if (reservas.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay reservas registradas para su hotel", "INFO", 1);
			return;
		}

		String texto = "=== RESERVAS DE MI HOTEL ===\n\n";

		for (Reserva r : reservas) {
			texto += "ID: " + r.getId() + " | Cliente: " + r.getCliente().getNombre() + " "
					+ r.getCliente().getApellido() + " | DNI: " + r.getCliente().getDni() + "\nHabitación: "
					+ r.getPaquete().getHabitacion().getNumero() + " | Estado: " + r.getEstado() + " | Precio: $"
					+ String.format("%.2f", r.getPaquete().getPrecio()) + "\n";

			if (r.getTarjeta_resguardo() != null) {
				texto += "Tarjeta: " + r.getTarjeta_resguardo() + "\n";
			}

			texto += "------------------------\n";
		}

		JOptionPane.showMessageDialog(null, texto, "RESERVAS", JOptionPane.INFORMATION_MESSAGE);
	}// fin

	// Ver habitaciones
	public static void verHabitaciones(int id_hotel) {
		List<Habitacion> habitaciones = DtoEncargado.verHabitaciones(id_hotel);

		if (habitaciones.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay habitaciones en su hotel", "INFO", 1);
			return;
		}

		String texto = "=== HABITACIONES DE MI HOTEL ===\n\n";

		for (Habitacion h : habitaciones) {
			texto += "ID: " + h.getId() + " | Número: " + h.getNumero() + "\nTipo: " + h.getTipo() + " | Camas: "
					+ h.getCant_camas() + "\nPrecio: $" + String.format("%.2f", h.getPrecio_noche()) + " | Estado: "
					+ h.getEstado() + "\n------------------------\n";
		}

		JOptionPane.showMessageDialog(null, texto, "HABITACIONES", JOptionPane.INFORMATION_MESSAGE);
	}// fin

	// Menu vistas
	public static void Vistas(int id_hotel) {
		int opcion;

		do {

			opcion = JOptionPane.showOptionDialog(null, "Seleccione: ", "BIENVENIDO", 0, 0, null,
					repository.Vistas_enc.values(), repository.Vistas_enc.values());

			switch (opcion) {
			case 0:
				// Ver actividades
				Encargado.verActividades(id_hotel, null);
				break;
			case 1:
				// Ver paquetes
				Encargado.verPaquetesDelHotel(id_hotel, null);
				break;
			case 2:
				// Atrás
				JOptionPane.showMessageDialog(null, "Redirigiendo.. ", "ADIOS!", 0);
				break;

			}// switch2

		} while (opcion != 2);
	}// fin

	// Ver datos de clientes
	public static void verDatosClientes(int id_hotel) {
		List<Cliente> clientes = DtoEncargado.verClientesPorHotel(id_hotel);

		if (clientes.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay clientes con reservas en su hotel", "INFO", 1);
			return;
		}

		String[] opcionesClientes = new String[clientes.size()];
		for (int i = 0; i < clientes.size(); i++) {
			Cliente c = clientes.get(i);
			opcionesClientes[i] = c.getId() + " - " + c.getNombre() + " " + c.getApellido() + " (DNI: " + c.getDni()
					+ ")";
		}

		String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione un cliente para ver sus datos:",
				"VER DATOS DE CLIENTES", JOptionPane.QUESTION_MESSAGE, null, opcionesClientes, opcionesClientes[0]);

		if (seleccion == null)
			return;

		int idCliente = Integer.parseInt(seleccion.split(" - ")[0]);
		Cliente clienteSeleccionado = clientes.stream().filter(c -> c.getId() == idCliente).findFirst().orElse(null);

		if (clienteSeleccionado == null)
			return;

		List<Reserva> reservas = DtoEncargado.verReservasDeCliente(idCliente, id_hotel);

		String texto = "=== DATOS DEL CLIENTE ===\n\n" + "Nombre: " + clienteSeleccionado.getNombre() + " "
				+ clienteSeleccionado.getApellido() + "\n" + "DNI: " + clienteSeleccionado.getDni() + "\n" + "Mail: "
				+ clienteSeleccionado.getMail() + "\n" + "Dirección: " + clienteSeleccionado.getDireccion() + "\n"
				+ "Usuario: " + clienteSeleccionado.getUser() + "\n" + "Estado: " + clienteSeleccionado.getEstado()
				+ "\n\n" + "=== RESERVAS EN NUESTRO HOTEL ===\n\n";

		if (reservas.isEmpty()) {
			texto += "No tiene reservas actualmente.\n";
		} else {
			for (Reserva r : reservas) {
				texto += "ID Reserva: " + r.getId() + "\n" + "Paquete: " + r.getPaquete().getHotel().getNombre() + "\n"
						+ "Fecha inicio: " + r.getPaquete().getInicioDate() + "\n" + "Fecha fin: "
						+ r.getPaquete().getFinDate() + "\n";

				if (r.getPaquete().getHabitacion() != null) {
					texto += "Habitación: " + r.getPaquete().getHabitacion().getNumero() + "\n";
				}

				if (r.getPaquete().getActividad() != null) {
					texto += "Actividad: " + r.getPaquete().getActividad().getNombre() + "\n";
				}

				texto += "Estado: " + r.getEstado() + "\n" + "Precio: $"
						+ String.format("%.2f", r.getPaquete().getPrecio()) + "\n";

				if (r.getFecha_checkin() != null) {
					texto += "Check-in: " + r.getFecha_checkin() + "\n";
				}
				if (r.getFecha_checkout() != null) {
					texto += "Check-out: " + r.getFecha_checkout() + "\n";
				}

				texto += "------------------------\n";
			}
		}

		JOptionPane.showMessageDialog(null, texto, "DATOS DEL CLIENTE", JOptionPane.INFORMATION_MESSAGE);
	}// fin

	// Ver paquetes por hotel
	public static void verPaquetesDelHotel(int id_hotel, javax.swing.JTextArea txtArea) {
	    List<Paquete> paquetes = DtoEncargado.verPaquetesDelHotel(id_hotel);

	    if (paquetes.isEmpty()) {
	        if (txtArea != null) {
	            txtArea.setText("No hay paquetes disponibles en su hotel");
	        } else {
	            JOptionPane.showMessageDialog(null, "No hay paquetes disponibles en su hotel", "INFO", 1);
	        }
	        return;
	    }

	    String texto = "=== PAQUETES DE MI HOTEL ===\n\n";

	    for (Paquete p : paquetes) {
	        texto += "ID: " + p.getId() + "\n" + "Hotel: " + p.getHotel().getNombre() + "\n" + "Fecha inicio: "
	            + p.getInicioDate() + "\n" + "Fecha fin: " + p.getFinDate() + "\n" + "Precio: $"
	            + String.format("%.2f", p.getPrecio()) + "\n";

	        if (p.getHabitacion() != null) {
	            texto += "Habitación: Nro " + p.getHabitacion().getNumero() + " - Tipo: " + p.getHabitacion().getTipo()
	                + " - Camas: " + p.getHabitacion().getCant_camas() + " - Estado: "
	                + p.getHabitacion().getEstado() + "\n";
	        } else {
	            texto += "Habitación: Sin asignar\n";
	        }

	        if (p.getActividad() != null) {
	            texto += "Actividad: " + p.getActividad().getNombre() + " - Categoría: "
	                + p.getActividad().getCategoria() + "\n" + "Duración: " + p.getActividad().getDuracion() + " hs"
	                + " - Ubicación: " + p.getActividad().getLocacion() + "\n";
	        } else {
	            texto += "Actividad: Sin asignar\n";
	        }

	        texto += "========================\n\n";
	    }

	    if (txtArea != null) {
	        txtArea.setText(texto);
	        txtArea.setCaretPosition(0); // Scroll al inicio
	    } else {
	        JOptionPane.showMessageDialog(null, texto, "PAQUETES DEL HOTEL", JOptionPane.INFORMATION_MESSAGE);
	    }
	}// fin

	// Ver actividades
	public static void verActividades(int id_hotel, javax.swing.JTextArea txtArea) {
	    List<Actividad> actividades = DtoEncargado.verActividadesPorHotel(id_hotel);

	    if (actividades.isEmpty()) {
	        if (txtArea != null) {
	            txtArea.setText("No hay actividades disponibles para su hotel");
	        } else {
	            JOptionPane.showMessageDialog(null, "No hay actividades disponibles para su hotel", "INFO", 1);
	        }
	        return;
	    }

	    String texto = "=== ACTIVIDADES DE MI HOTEL ===\n\n";

	    for (Actividad a : actividades) {
	        texto += "ID: " + a.getId() + "\n" + "Nombre: " + a.getNombre() + "\n" + "Categoría: " + a.getCategoria()
	            + "\n" + "Ubicación: " + a.getLocacion() + "\n" + "Duración: " + a.getDuracion() + " horas\n"
	            + "Precio: $" + String.format("%.2f", a.getPrecio()) + "\n" + "Edad: " + a.getEdad_minima() + " - "
	            + a.getEdad_maxima() + " años\n" + "Vigencia: " + a.getInicioDate() + " al " + a.getFinDate()
	            + "\n";

	        if (a.getRiesgo() != null) {
	            texto += "Nivel de riesgo: " + a.getRiesgo() + "\n";
	        }

	        texto += "------------------------\n";
	    }

	    if (txtArea != null) {
	        txtArea.setText(texto);
	        txtArea.setCaretPosition(0); // Scroll al inicio
	    } else {
	        JOptionPane.showMessageDialog(null, texto, "ACTIVIDADES", JOptionPane.INFORMATION_MESSAGE);
	    }
	}// fin

	// Menu promociones
	public static void gestionarPromociones(int id_hotel) {
		int opcion;

		do {

			opcion = JOptionPane.showOptionDialog(null, "Seleccione: ", "BIENVENIDO", 0, 0, null,
					repository.Promociones.values(), repository.Promociones.values());

			switch (opcion) {
			case 0:
				// Nueva promocion
				//crearPromocion(id_hotel);
				break;
			case 1:
				// Promociones activas
				verPromociones(id_hotel);
				break;
			case 2:
				// Aplicar promocion
				//aplicarPromocionAPaquete(id_hotel);
				break;
			case 3:
				// Editar promocion
				//editarPromocion(id_hotel);
				break;
			case 4:
				// Eliminar promocion
				//eliminarPromocion(id_hotel);
				break;
			case 5:
				// Ver paquetes
				verPaquetesConPromociones(id_hotel);
				break;
			case 6:
				// Volver
				JOptionPane.showMessageDialog(null, "Redirigiendo al menú principal! ", "ADIOS!", 0);
				break;
			}// switch

		} while (opcion != 6);
	}// fin

	// Crear
	public static boolean crearPromocion(int idHotel, String nombre, String descripcion, 
	        int porcentaje, LocalDate fechaInicio, LocalDate fechaFin) {
	    try {
	        if (nombre == null || nombre.trim().isEmpty()) {
	            return false;
	        }
	        
	        if (porcentaje < 1 || porcentaje > 100) {
	            return false;
	        }
	        
	        if (fechaFin.isBefore(fechaInicio) || fechaFin.isEqual(fechaInicio)) {
	            return false;
	        }
	        
	        if (descripcion == null) {
	            descripcion = "";
	        }
	        
	        DtoEncargado.crearPromocion(nombre, descripcion, porcentaje, fechaInicio, fechaFin, idHotel);
	        return true;
	        
	    } catch (Exception e) {
	        System.err.println("Error al crear promoción: " + e.getMessage());
	        return false;
	    }
	}// fin

	// Ver promos
	private static void verPromociones(int id_hotel) {
		List<Promocion> promociones = DtoEncargado.verPromocionesDelHotel(id_hotel);

		if (promociones.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay promociones registradas para su hotel", "INFO", 1);
			return;
		}

		String texto = "=== PROMOCIONES DE MI HOTEL ===\n\n";

		for (Promocion p : promociones) {
			texto += "ID: " + p.getId() + "\n" + "Nombre: " + p.getNombre() + "\n" + "Descuento: "
					+ p.getPorcentajeDescuento() + "%\n" + "Vigencia: " + p.getFechaInicio() + " al " + p.getFechaFin()
					+ "\n" + "Estado: " + p.getEstado();

			if (p.estaVigente()) {
				texto += " ✓ VIGENTE\n";
			} else if (p.getEstado().equals("activa")) {
				LocalDate hoy = LocalDate.now();
				if (hoy.isBefore(p.getFechaInicio())) {
					texto += " (Aún no comenzó)\n";
				} else {
					texto += " (Expirada)\n";
				}
			} else {
				texto += "\n";
			}

			if (p.getDescripcion() != null && !p.getDescripcion().isEmpty()) {
				texto += "Descripción: " + p.getDescripcion() + "\n";
			}

			texto += "------------------------\n";
		}

		JOptionPane.showMessageDialog(null, texto, "PROMOCIONES", JOptionPane.INFORMATION_MESSAGE);
	}// fin

	// Sobrecarga
	public static boolean editarPromocionAtributo(int idPromocion, String atributo, 
	        String nuevoValor, int idHotel) {
	    try {
	        DtoEncargado.editarPromocion(idPromocion, atributo, nuevoValor, idHotel);
	        return true;
	    } catch (Exception e) {
	        System.err.println("Error al editar atributo: " + e.getMessage());
	        return false;
	    }
	}
	
	// Aplicar
	public static boolean aplicarPromocionAPaquete(int idHotel, int idPromocion, int idPaquete, javax.swing.JLabel lblMensaje) {
	    try {
	        
	        return DtoEncargado.aplicarPromocionAPaquete(idPaquete, idPromocion, idHotel, lblMensaje);
	        
	    } catch (Exception e) {
	        lblMensaje.setForeground(java.awt.Color.RED);
	        lblMensaje.setText("Error BLL al aplicar promoción: " + e.getMessage());
	        System.err.println("Error al aplicar promoción: " + e.getMessage());
	        return false;
	    }
	}// fin

	// Eliminar
	public static boolean eliminarPromocion(int idHotel, int idPromocion) {
	    try {

	        List<Promocion> promociones = DtoEncargado.verPromocionesDelHotel(idHotel);
	        boolean existe = false;
	        
	        for (Promocion p : promociones) {
	            if (p.getId() == idPromocion) {
	                existe = true;
	                break;
	            }
	        }
	        
	        if (!existe) {
	            return false;
	        }
	        
	        DtoEncargado.eliminarPromocion(idPromocion, idHotel);
	        return true;
	        
	    } catch (Exception e) {
	        System.err.println("Error al eliminar promoción: " + e.getMessage());
	        return false;
	    }
	}// fin

	// Ver
	private static void verPaquetesConPromociones(int id_hotel) {
		List<Paquete> paquetes = DtoEncargado.verPaquetesConPromociones(id_hotel);

		if (paquetes.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay paquetes disponibles en su hotel", "INFO", 1);
			return;
		}

		String texto = "=== PAQUETES CON INFORMACIÓN DE PROMOCIONES ===\n\n";

		for (Paquete p : paquetes) {
			texto += "ID: " + p.getId() + "\n" + "Fecha: " + p.getInicioDate() + " al " + p.getFinDate() + "\n";

			if (p.getHabitacion() != null) {
				texto += "Habitación: Nro " + p.getHabitacion().getNumero() + "\n";
			}

			if (p.getActividad() != null) {
				texto += "Actividad: " + p.getActividad().getNombre() + "\n";
			}

			if (p.getPromocion() != null) {
				texto += "PROMOCIÓN APLICADA: " + p.getPromocion().getNombre() + "\n" + "Descuento: "
						+ p.getPromocion().getPorcentajeDescuento() + "%\n" + "Precio original: $"
						+ String.format("%.2f", p.getPrecioOriginal()) + "\n" + "Precio con descuento: $"
						+ String.format("%.2f", p.getPrecio()) + "\n";

				if (p.getPromocion().estaVigente()) {
					texto += "Estado: VIGENTE\n";
				} else {
					texto += "Estado: NO VIGENTE\n";
				}
			} else {
				texto += "Sin promoción\n" + "Precio: $" + String.format("%.2f", p.getPrecio()) + "\n";
			}

			texto += "========================\n\n";
		}

		JOptionPane.showMessageDialog(null, texto, "PAQUETES Y PROMOCIONES", JOptionPane.INFORMATION_MESSAGE);
	}// fin

	// Editar
	public static boolean editarPromocion(int idHotel, int idPromocion, String nombre, 
	        String descripcion, int porcentaje) {
	    try {
	        if (nombre == null || nombre.trim().isEmpty()) {
	            return false;
	        }
	        
	        if (porcentaje < 1 || porcentaje > 100) {
	            return false;
	        }
	        
	        if (descripcion == null) {
	            descripcion = "";
	        }

	        List<Promocion> promociones = DtoEncargado.verPromocionesDelHotel(idHotel);
	        Promocion promoActual = null;
	        
	        for (Promocion p : promociones) {
	            if (p.getId() == idPromocion) {
	                promoActual = p;
	                break;
	            }
	        }
	        
	        if (promoActual == null) {
	            return false;
	        }
	        
	        boolean exito = true;
	        
	        if (!nombre.equals(promoActual.getNombre())) {
	            DtoEncargado.editarPromocion(idPromocion, "nombre", nombre, idHotel);
	        }
	        
	        if (!descripcion.equals(promoActual.getDescripcion())) {
	            DtoEncargado.editarPromocion(idPromocion, "descripcion", descripcion, idHotel);
	        }
	        
	        if (porcentaje != promoActual.getPorcentajeDescuento()) {
	            DtoEncargado.editarPromocion(idPromocion, "porcentaje", String.valueOf(porcentaje), idHotel);
	        }
	        
	        return exito;
	        
	    } catch (Exception e) {
	        System.err.println("Error al editar promoción: " + e.getMessage());
	        return false;
	    }
	}// fin

}// final clase
