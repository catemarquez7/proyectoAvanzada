package bll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dll.DtoAdministrador;
import dll.DtoUsuario;
import repository.SiNoOpcion;

public class Administrador extends Usuario {

	// constructores

	public Administrador(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni, String direccion,
			int id, String user, String pass, String pregunta, String respuesta, LocalDate fecha_creacion,
			String tipo_usuario, String estado) {
		super(nombre, apellido, fecha_nac, mail, dni, direccion, id, user, pass, pregunta, respuesta, fecha_creacion,
				tipo_usuario, estado);
	}

	public Administrador() {

	}

	// toString
	@Override
	public String toString() {
		return "Administrador [id=" + id + ", user=" + user + ", pass=" + pass + ", pregunta=" + pregunta
				+ ", respuesta=" + respuesta + ", fecha_creacion=" + fecha_creacion + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", fecha_nac=" + fecha_nac + ", mail=" + mail + ", dni=" + dni
				+ ", direccion=" + direccion + ", getId()=" + getId() + ", getUser()=" + getUser() + ", getPass()="
				+ getPass() + ", getPregunta()=" + getPregunta() + ", getRespuesta()=" + getRespuesta()
				+ ", getFecha_creacion()=" + getFecha_creacion() + ", toString()=" + super.toString() + ", getNombre()="
				+ getNombre() + ", getApellido()=" + getApellido() + ", getFecha_nac()=" + getFecha_nac()
				+ ", getMail()=" + getMail() + ", getDni()=" + getDni() + ", getDireccion()=" + getDireccion()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

	// metodos

	// Ver hoteles
	public static void verHoteles() {
		List<Hotel> hoteles = DtoAdministrador.verHoteles();

		if (hoteles.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay hoteles registrados.", "INFO", 1);
			return;
		}

		String texto = "=== HOTELES REGISTRADOS ===\n\n";
		for (Hotel h : hoteles) {
			texto += "ID: " + h.getId() + "\n";
			texto += "Nombre: " + h.getNombre() + "\n";
			texto += "Provincia: " + h.getProvincia() + "\n";
			texto += "Dirección: " + h.getDireccion() + "\n";
			texto += "Cantidad de habitaciones: " + h.getCant_habitaciones() + "\n";
			texto += "Calificación promedio: " + h.getCalificacion_promedio() + "\n";
			texto += "------------------------\n";
		}

		JOptionPane.showMessageDialog(null, texto, "HOTELES", JOptionPane.INFORMATION_MESSAGE);
	}

	// Modificar hotel
	public static void modificarHotel() {
		List<Hotel> hoteles = DtoAdministrador.verHoteles();

		if (hoteles.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay hoteles registrados.", "INFO", 1);
			return;
		}

		// Dropdown con hoteles
		String[] opcionesHoteles = new String[hoteles.size()];
		for (int i = 0; i < hoteles.size(); i++) {
			Hotel h = hoteles.get(i);
			opcionesHoteles[i] = h.getId() + " - " + h.getNombre() + " (" + h.getProvincia() + ")";
		}

		String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione el hotel a modificar:",
				"MODIFICAR HOTEL", JOptionPane.QUESTION_MESSAGE, null, opcionesHoteles, opcionesHoteles[0]);

		if (seleccion == null)
			return;

		int idHotel = Integer.parseInt(seleccion.split(" - ")[0]);

		String nuevoNombre = repository.Validaciones.ValidarContras("Ingrese el nuevo nombre del hotel:");

		DtoAdministrador.modificarHotel(idHotel, nuevoNombre);
	}

	// Eliminar hotel
		public static void eliminarHotel() {
			List<Hotel> hoteles = DtoAdministrador.verHoteles();

			if (hoteles.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No hay hoteles registrados.", "INFO", 1);
				return;
			}

			// Dropdown con hoteles
			String[] opcionesHoteles = new String[hoteles.size()];
			for (int i = 0; i < hoteles.size(); i++) {
				Hotel h = hoteles.get(i);
				opcionesHoteles[i] = h.getId() + " - " + h.getNombre() + " (" + h.getProvincia() + ")";
			}

			String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione el hotel a eliminar:",
					"ELIMINAR HOTEL", JOptionPane.QUESTION_MESSAGE, null, opcionesHoteles, opcionesHoteles[0]);

			if (seleccion == null)
				return;

			int idHotel = Integer.parseInt(seleccion.split(" - ")[0]);
			
			 SiNoOpcion opcionEnum = (SiNoOpcion)JOptionPane.showInputDialog(null, "Esta seguro que quiere eliminar el hotel? No podrá volver atrás.", "SELECCION", 0, null, repository.SiNoOpcion.values(), repository.SiNoOpcion.values());		
				
				String opcion = opcionEnum.toString();
				
				switch (opcion) {
				case "Si": 
					DtoAdministrador.eliminarHotel(idHotel);
					break;
				case "No": 
					break;
				}
		}
	
	// Ver reservas
	public static void verReservas() {

		List<Hotel> hoteles = DtoAdministrador.verHoteles();

		if (hoteles.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay paquetes registrados.", "INFO", 1);
			return;
		}

		String[] opcionesHoteles = new String[hoteles.size()];
		for (int i = 0; i < hoteles.size(); i++) {
			Hotel h = hoteles.get(i);
			opcionesHoteles[i] = h.getId() + " - " + h.getNombre() + " (" + h.getProvincia() + ")";
		}

		String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione el hotel para ver sus paquetes:",
				"VER RESERVAS", JOptionPane.QUESTION_MESSAGE, null, opcionesHoteles, opcionesHoteles[0]);

		int idHotel = Integer.parseInt(seleccion.split(" - ")[0]);

		List<Reserva> reservas = DtoAdministrador.verReservas(idHotel);

		if (reservas.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay reservas registradas.", "INFO", 1);
			return;
		}

		String texto = "=== RESERVAS DEL SISTEMA ===\n\n";
		for (Reserva r : reservas) {
			texto += "ID Reserva: " + r.getId() + "\n";
			texto += "Cliente: " + r.getCliente().getNombre() + " " + r.getCliente().getApellido() + "\n";
			texto += "Hotel: " + r.getPaquete().getHotel().getNombre() + "\n";
			texto += "Estado: " + r.getEstado() + "\n";

			if (r.getPaquete().getHabitacion() != null) {
				texto += "Habitación: " + r.getPaquete().getHabitacion().getNumero() + "\n";
			}

			if (r.getPaquete().getActividad() != null) {
				texto += "Actividad: " + r.getPaquete().getActividad().getNombre() + "\n";
			}

			texto += "Monto: $" + r.getMonto_final() + "\n";
			texto += "------------------------\n";
		}

		JOptionPane.showMessageDialog(null, texto, "RESERVAS", JOptionPane.INFORMATION_MESSAGE);
	}

	// Modificar reserva
	public static void modificarReserva() {

		List<Hotel> hoteles = DtoAdministrador.verHoteles();
		if (hoteles.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay hoteles registrados.", "INFO", 1);
			return;
		}

		String[] opcionesHoteles = new String[hoteles.size()];
		for (int i = 0; i < hoteles.size(); i++) {
			Hotel h = hoteles.get(i);
			opcionesHoteles[i] = h.getId() + " - " + h.getNombre() + " (" + h.getProvincia() + ")";
		}

		String hotelSel = (String) JOptionPane.showInputDialog(null, "Seleccione el hotel:", "HOTEL",
				JOptionPane.QUESTION_MESSAGE, null, opcionesHoteles, opcionesHoteles[0]);

		if (hotelSel == null)
			return;
		int idHotel = Integer.parseInt(hotelSel.split(" - ")[0]);

		List<Reserva> reservas = DtoAdministrador.verReservas(idHotel);

		if (reservas.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay reservas registradas.", "INFO", 1);
			return;
		}

		// Crear dropdown con reservas
		String[] opcionesReservas = new String[reservas.size()];
		for (int i = 0; i < reservas.size(); i++) {
			Reserva r = reservas.get(i);
			opcionesReservas[i] = r.getId() + " - " + r.getCliente().getNombre() + " " + r.getCliente().getApellido()
					+ " - " + r.getPaquete().getHotel().getNombre() + " [" + r.getEstado() + "]";
		}

		String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione la reserva a modificar:",
				"MODIFICAR RESERVA", JOptionPane.QUESTION_MESSAGE, null, opcionesReservas, opcionesReservas[0]);

		if (seleccion == null)
			return;

		// Extraer ID de la reserva seleccionada
		int idReserva = Integer.parseInt(seleccion.split(" - ")[0]);
		Reserva reservaSeleccionada = reservas.stream().filter(r -> r.getId() == idReserva).findFirst().orElse(null);

		if (reservaSeleccionada == null)
			return;

		// Seleccionar usuario
		List<Usuario> usuarios = DtoAdministrador.verCuentas();
		String[] opcionesUsuarios = new String[usuarios.size()];
		for (int i = 0; i < usuarios.size(); i++) {
			Usuario u = usuarios.get(i);
			opcionesUsuarios[i] = u.getId() + " - " + u.getNombre() + " " + u.getApellido() + " (" + u.getUser() + ")";
		}

		String usuarioSel = (String) JOptionPane.showInputDialog(null, "Seleccione el cliente:", "CLIENTE",
				JOptionPane.QUESTION_MESSAGE, null, opcionesUsuarios, opcionesUsuarios[0]);

		if (usuarioSel == null)
			return;
		int idUsuario = Integer.parseInt(usuarioSel.split(" - ")[0]);

		// Seleccionar paquete
		List<Paquete> paquetes = DtoAdministrador.verPaquetes(idHotel);

		if (paquetes.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay paquetes para este hotel.", "INFO", 1);
			return;
		}

		String[] opcionesPaquetes = new String[paquetes.size()];
		for (int i = 0; i < paquetes.size(); i++) {
			Paquete p = paquetes.get(i);
			opcionesPaquetes[i] = p.getId() + " - " + p.getHotel().getNombre() + " - $" + p.getPrecio();
		}

		String paqueteSel = (String) JOptionPane.showInputDialog(null, "Seleccione el paquete:", "PAQUETE",
				JOptionPane.QUESTION_MESSAGE, null, opcionesPaquetes, opcionesPaquetes[0]);

		if (paqueteSel == null)
			return;
		int idPaquete = Integer.parseInt(paqueteSel.split(" - ")[0]);

		// Seleccionar estado
		String[] estados = { "pendiente", "activa", "finalizada", "cancelada" };
		String estadoSel = (String) JOptionPane.showInputDialog(null, "Seleccione el estado:", "ESTADO",
				JOptionPane.QUESTION_MESSAGE, null, estados, estados[0]);

		if (estadoSel == null)
			return;

		// Fechas (mantener las actuales si no se modifican)
		LocalDateTime fechaCheckin = reservaSeleccionada.getFecha_checkin();
		LocalDateTime fechaCheckout = reservaSeleccionada.getFecha_checkout();

		String tarjeta = JOptionPane.showInputDialog("Ingrese tarjeta de resguardo (dejar vacío para mantener):");
		if (tarjeta == null || tarjeta.trim().isEmpty()) {
			tarjeta = reservaSeleccionada.getTarjeta_resguardo();
		}

		String montoStr = JOptionPane.showInputDialog("Ingrese monto final (0 para mantener actual):");
		double monto = reservaSeleccionada.getMonto_final();
		if (montoStr != null && !montoStr.trim().isEmpty()) {
			try {
				double nuevoMonto = Double.parseDouble(montoStr);
				if (nuevoMonto > 0)
					monto = nuevoMonto;
			} catch (NumberFormatException e) {
				// Mantener monto actual
			}
		}

		DtoAdministrador.modificarReserva(idReserva, idUsuario, idPaquete, estadoSel, fechaCheckin, fechaCheckout,
				tarjeta, monto);
	}

	// Ver paquetes
	public static void verPaquetes() {

		List<Hotel> hoteles = DtoAdministrador.verHoteles();

		if (hoteles.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay paquetes registrados.", "INFO", 1);
			return;
		}

		String[] opcionesHoteles = new String[hoteles.size()];
		for (int i = 0; i < hoteles.size(); i++) {
			Hotel h = hoteles.get(i);
			opcionesHoteles[i] = h.getId() + " - " + h.getNombre() + " (" + h.getProvincia() + ")";
		}

		String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione el hotel para ver sus paquetes:",
				"VER PAQUETES", JOptionPane.QUESTION_MESSAGE, null, opcionesHoteles, opcionesHoteles[0]);

		int idHotel = Integer.parseInt(seleccion.split(" - ")[0]);

		List<Paquete> paquetes = DtoAdministrador.verPaquetes(idHotel);

		if (paquetes.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay paquetes registrados para este hotel.", "INFO", 1);
			return;
		}

		String texto = "=== PAQUETES DEL SISTEMA ===\n\n";
		for (Paquete p : paquetes) {
			texto += "ID: " + p.getId() + "\n";
			texto += "Hotel: " + p.getHotel().getNombre() + "\n";
			texto += "Provincia: " + p.getHotel().getProvincia() + "\n";
			texto += "Fecha inicio: " + p.getInicioDate() + "\n";
			texto += "Fecha fin: " + p.getFinDate() + "\n";
			texto += "Precio: $" + p.getPrecio() + "\n";

			if (p.getHabitacion() != null) {
				texto += "Habitación: " + p.getHabitacion().getNumero() + " (" + p.getHabitacion().getTipo() + ")\n";
			}

			if (p.getActividad() != null) {
				texto += "Actividad: " + p.getActividad().getNombre() + " - " + p.getActividad().getCategoria() + "\n";
			}

			texto += "------------------------\n";
		}

		JOptionPane.showMessageDialog(null, texto, "PAQUETES", JOptionPane.INFORMATION_MESSAGE);
	}

	// Modificar paquete
	public static void modificarPaquete() {

		List<Hotel> hoteles = DtoAdministrador.verHoteles();

		if (hoteles.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay hoteles registrados.", "INFO", 1);
			return;
		}
		String[] opcionesHoteles = new String[hoteles.size()];
		for (int i = 0; i < hoteles.size(); i++) {
			Hotel h = hoteles.get(i);
			opcionesHoteles[i] = h.getId() + " - " + h.getNombre() + " (" + h.getProvincia() + ")";
		}

		String hotelSeleccion = (String) JOptionPane.showInputDialog(null, "Seleccione el hotel del paquete:", "HOTEL",
				JOptionPane.QUESTION_MESSAGE, null, opcionesHoteles, opcionesHoteles[0]);

		if (hotelSeleccion == null)
			return;

		int idHotelFiltro = Integer.parseInt(hotelSeleccion.split(" - ")[0]);

		List<Paquete> paquetes = DtoAdministrador.verPaquetes(idHotelFiltro);
		if (paquetes.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay paquetes registrados para este hotel.", "INFO", 1);
			return;
		}

		// Crear dropdown con paquetes
		String[] opcionesPaquetes = new String[paquetes.size()];
		for (int i = 0; i < paquetes.size(); i++) {
			Paquete p = paquetes.get(i);
			opcionesPaquetes[i] = p.getId() + " - " + p.getHotel().getNombre() + " (" + p.getInicioDate() + " a "
					+ p.getFinDate() + ") - $" + p.getPrecio();
		}

		String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione el paquete a modificar:",
				"MODIFICAR PAQUETE", JOptionPane.QUESTION_MESSAGE, null, opcionesPaquetes, opcionesPaquetes[0]);

		if (seleccion == null)
			return;

		// Extraer ID del paquete seleccionado
		int idPaquete = Integer.parseInt(seleccion.split(" - ")[0]);
		Paquete paqueteSeleccionado = paquetes.stream().filter(p -> p.getId() == idPaquete).findFirst().orElse(null);

		if (paqueteSeleccionado == null)
			return;

		// Solicitar nuevos datos
		LocalDate fechaInicio = repository.Validaciones.ValidarFecha("Ingrese nueva fecha de inicio");
		LocalDate fechaFin = repository.Validaciones.ValidarFecha("Ingrese nueva fecha de fin");
		double precio = repository.Validaciones.ValidarNum("Ingrese nuevo precio del paquete:");

		// Seleccionar hotel
		String hotelNuevo = (String) JOptionPane.showInputDialog(null, "Seleccione el hotel:", "HOTEL",
				JOptionPane.QUESTION_MESSAGE, null, opcionesHoteles, opcionesHoteles[0]);

		if (hotelNuevo == null)
			return;
		int idHotel = Integer.parseInt(hotelNuevo.split(" - ")[0]);

		// Mantener habitación y actividad actuales del paquete
		Integer idHabitacion = paqueteSeleccionado.getHabitacion() != null ? paqueteSeleccionado.getHabitacion().getId()
				: null;
		Integer idActividad = paqueteSeleccionado.getActividad() != null ? paqueteSeleccionado.getActividad().getId()
				: null;

		DtoAdministrador.modificarPaquete(idPaquete, fechaInicio, fechaFin, precio, idHotel, idHabitacion, idActividad);
	}

	// Gestionar cuentas
	public static void gestionarCuentas() {
		int opcion;

		do {
			opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción:", "GESTIÓN DE CUENTAS", 0, 0, null,
					new String[] { "Ver cuentas", "Crear cuenta", "Bloquear cuenta", "Desbloquear cuenta",
							"Eliminar cuenta", "Estadísticas", "Volver" },
					"Ver cuentas");

			switch (opcion) {
			case 0: // Ver cuentas
				verCuentas();
				break;
			case 1: // Crear cuenta
				crearCuenta();
				break;
			case 2: // Bloquear
				bloquearCuenta();
				break;
			case 3: // Desbloquear
				desbloquearCuenta();
				break;
			case 4: // Eliminar
				eliminarCuenta();
				break;
			case 5: // Estadísticas
				mostrarEstadisticas();
				break;
			case 6: // Volver
				break;
			}
		} while (opcion != 6);
	}

	// Ver todas las cuentas
	private static void verCuentas() {
		List<Usuario> usuarios = DtoAdministrador.verCuentas();

		if (usuarios.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay usuarios registrados.", "INFO", 1);
			return;
		}

		String texto = "=== CUENTAS DE USUARIO ===\n\n";
		for (Usuario u : usuarios) {
			texto += "ID: " + u.getId() + "\n";
			texto += "Usuario: " + u.getUser() + "\n";
			texto += "Nombre: " + u.getNombre() + " " + u.getApellido() + "\n";
			texto += "DNI: " + u.getDni() + "\n";
			texto += "Mail: " + u.getMail() + "\n";

			String tipoTexto = "";
			switch (u.getTipo_usuario()) {
			case "1":
				tipoTexto = "Cliente";
				break;
			case "2":
				tipoTexto = "Encargado";
				break;
			case "3":
				tipoTexto = "Administrador";
				break;
			}

			texto += "Tipo: " + tipoTexto + "\n";
			texto += "Estado: " + u.getEstado() + "\n";
			texto += "------------------------\n";
		}

		JOptionPane.showMessageDialog(null, texto, "USUARIOS", JOptionPane.INFORMATION_MESSAGE);
	}

	// Crear cuenta
	public static void crearCuenta() {
		String nombre = repository.Validaciones.ValidarLetras("Ingrese nombre:");
		String apellido = repository.Validaciones.ValidarLetras("Ingrese apellido:");
		LocalDate fechaNac = repository.Validaciones.ValidarFecha("Ingrese fecha de nacimiento");

		String mail;
		do {
			mail = JOptionPane.showInputDialog("Ingrese mail:");
			if (!repository.Validaciones.ValidarMail(mail)) {
				JOptionPane.showMessageDialog(null, "Mail incorrecto", "ERROR", 0);
			}
		} while (!repository.Validaciones.ValidarMail(mail));

		int dni = repository.Validaciones.ValidarNum("Ingrese DNI:");
		String direccion = repository.Validaciones.ValidarContras("Ingrese dirección:");
		String user = repository.Validaciones.ValidarLetras("Ingrese nombre de usuario:");
		String pass = repository.Validaciones.ValidarContras("Ingrese contraseña (min 8 caracteres):");
		String pregunta = repository.Validaciones.ValidarLetras("Ingrese pregunta de seguridad:");
		String respuesta = repository.Validaciones.ValidarLetras("Ingrese respuesta de seguridad:");

		// Seleccionar tipo de usuario
		String[] tiposUsuario = { "1 - Cliente", "2 - Encargado", "3 - Administrador" };
		String tipoSel = (String) JOptionPane.showInputDialog(null, "Seleccione tipo de usuario:", "TIPO DE USUARIO",
				JOptionPane.QUESTION_MESSAGE, null, tiposUsuario, tiposUsuario[0]);

		if (tipoSel == null)
			return;

		String tipoUsuario = tipoSel.split(" - ")[0];

		Usuario nuevoUsuario = new Usuario(nombre, apellido, fechaNac, mail, dni, direccion, user, pass, pregunta,
				respuesta);
		nuevoUsuario.setTipo_usuario(tipoUsuario);
		nuevoUsuario.setFecha_creacion(LocalDate.now());
		nuevoUsuario.setEstado("activo");

		if (DtoAdministrador.crearUsuario(nuevoUsuario)) {
			// Si es encargado, asignar hotel
			if (tipoUsuario.equals("2")) {
				List<Hotel> hoteles = DtoAdministrador.verHoteles();

				if (hoteles.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No hay hoteles disponibles. Asigne un hotel manualmente.",
							"INFO", 1);
					return;
				}

				String[] opcionesHoteles = new String[hoteles.size()];
				for (int i = 0; i < hoteles.size(); i++) {
					Hotel h = hoteles.get(i);
					opcionesHoteles[i] = h.getId() + " - " + h.getNombre();
				}

				String hotelSel = (String) JOptionPane.showInputDialog(null, "Seleccione el hotel para el encargado:",
						"ASIGNAR HOTEL", JOptionPane.QUESTION_MESSAGE, null, opcionesHoteles, opcionesHoteles[0]);

				if (hotelSel != null) {
					int idHotel = Integer.parseInt(hotelSel.split(" - ")[0]);
					DtoAdministrador.crearEncargado(nuevoUsuario.getId(), idHotel);
				}
			}
		}
	}

	// Bloquear cuenta
	private static void bloquearCuenta() {
		List<Usuario> usuarios = DtoAdministrador.verCuentas();

		List<Usuario> usuariosActivos = new ArrayList<>();
		for (Usuario u : usuarios) {
			if (u.getEstado().equals("activo")) {
				usuariosActivos.add(u);
			}
		}

		if (usuariosActivos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay usuarios activos para bloquear.", "INFO", 1);
			return;
		}

		// Dropdown con usuarios activos
		String[] opcionesUsuarios = new String[usuariosActivos.size()];
		for (int i = 0; i < usuariosActivos.size(); i++) {
			Usuario u = usuariosActivos.get(i);
			String tipoTexto = "";
			switch (u.getTipo_usuario()) {
			case "1":
				tipoTexto = "Cliente";
				break;
			case "2":
				tipoTexto = "Encargado";
				break;
			case "3":
				tipoTexto = "Administrador";
				break;
			}
			opcionesUsuarios[i] = u.getId() + " - " + u.getNombre() + " " + u.getApellido() + " (" + u.getUser()
					+ ") - " + tipoTexto;
		}

		String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione el usuario a bloquear:",
				"BLOQUEAR USUARIO", JOptionPane.QUESTION_MESSAGE, null, opcionesUsuarios, opcionesUsuarios[0]);

		if (seleccion == null)
			return;

		int idUsuario = Integer.parseInt(seleccion.split(" - ")[0]);
		DtoAdministrador.bloquearCuenta(idUsuario);
	}

	// Desbloquear cuenta
	private static void desbloquearCuenta() {
		List<Usuario> usuarios = DtoAdministrador.verCuentas();

		List<Usuario> usuariosBloqueados = new ArrayList<>();
		for (Usuario u : usuarios) {
			if (u.getEstado().equals("bloqueado")) {
				usuariosBloqueados.add(u);
			}
		}

		if (usuariosBloqueados.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay usuarios bloqueados.", "INFO", 1);
			return;
		}

		// Dropdown con usuarios bloqueados
		String[] opcionesUsuarios = new String[usuariosBloqueados.size()];
		for (int i = 0; i < usuariosBloqueados.size(); i++) {
			Usuario u = usuariosBloqueados.get(i);
			String tipoTexto = "";
			switch (u.getTipo_usuario()) {
			case "1":
				tipoTexto = "Cliente";
				break;
			case "2":
				tipoTexto = "Encargado";
				break;
			case "3":
				tipoTexto = "Administrador";
				break;
			}
			opcionesUsuarios[i] = u.getId() + " - " + u.getNombre() + " " + u.getApellido() + " (" + u.getUser()
					+ ") - " + tipoTexto;
		}

		String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione el usuario a desbloquear:",
				"DESBLOQUEAR USUARIO", JOptionPane.QUESTION_MESSAGE, null, opcionesUsuarios, opcionesUsuarios[0]);

		if (seleccion == null)
			return;

		int idUsuario = Integer.parseInt(seleccion.split(" - ")[0]);
		DtoAdministrador.desbloquearCuenta(idUsuario);
	}
	
	static void suspenderSistema() {
		if (DtoUsuario.chequeoSuspension()) {
			
			 SiNoOpcion opcionEnum = (SiNoOpcion)JOptionPane.showInputDialog(null, "Desea reactivar el sistema en suspensión?", "SELECCION", 0, null, repository.SiNoOpcion.values(), repository.SiNoOpcion.values());		
				
				String opcion = opcionEnum.toString();
				
				switch (opcion) {
				case "Si":
					DtoAdministrador.activarSistema();
				break;

				case "No": 
				break;
				}
		} else {
		
		 SiNoOpcion opcionEnum = (SiNoOpcion)JOptionPane.showInputDialog(null, "Desea poner todo el sistema en suspensión? El programa quedará fuera de uso", "SELECCION", 0, null, repository.SiNoOpcion.values(), repository.SiNoOpcion.values());		
			
			String opcion = opcionEnum.toString();
			
			switch (opcion) {
			case "Si":
				DtoAdministrador.suspenderSistema();
			break;

			case "No": 
			break;
			}
				
	}
	

	}

	// Eliminar cuenta
	private static void eliminarCuenta() {
		List<Usuario> usuarios = DtoAdministrador.verCuentas();

		if (usuarios.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay usuarios registrados.", "INFO", 1);
			return;
		}

		// Dropdown con usuarios
		String[] opcionesUsuarios = new String[usuarios.size()];
		for (int i = 0; i < usuarios.size(); i++) {
			Usuario u = usuarios.get(i);
			String tipoTexto = "";
			switch (u.getTipo_usuario()) {
			case "1":
				tipoTexto = "Cliente";
				break;
			case "2":
				tipoTexto = "Encargado";
				break;
			case "3":
				tipoTexto = "Administrador";
				break;
			}
			opcionesUsuarios[i] = u.getId() + " - " + u.getNombre() + " " + u.getApellido() + " (" + u.getUser()
					+ ") - " + tipoTexto + " [" + u.getEstado() + "]";
		}

		String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione el usuario a eliminar:",
				"ELIMINAR USUARIO", JOptionPane.WARNING_MESSAGE, null, opcionesUsuarios, opcionesUsuarios[0]);

		if (seleccion == null)
			return;

		int idUsuario = Integer.parseInt(seleccion.split(" - ")[0]);

		int confirmar = JOptionPane.showConfirmDialog(null,
				"¿Está seguro de eliminar esta cuenta?\nEsta acción no se puede deshacer.", "CONFIRMAR",
				JOptionPane.YES_NO_OPTION);

		if (confirmar == JOptionPane.YES_OPTION) {
			DtoAdministrador.eliminarCuenta(idUsuario);
		}
	}

	// Mostrar estadísticas
	private static void mostrarEstadisticas() {
		String stats = DtoAdministrador.obtenerEstadisticas();
		JOptionPane.showMessageDialog(null, stats, "ESTADÍSTICAS DEL SISTEMA", JOptionPane.INFORMATION_MESSAGE);
	}

	// =============== CREAR HOTEL ===============
	public static void crearHotel() {
	    String nombre = repository.Validaciones.ValidarContras("Ingrese nombre del hotel:");
	    String provincia = repository.Validaciones.ValidarLetras("Ingrese provincia:");
	    String direccion = repository.Validaciones.ValidarContras("Ingrese dirección:");
	    int cantHabitaciones = repository.Validaciones.ValidarNum("Ingrese cantidad de habitaciones:");
	    int cupoMaximo = repository.Validaciones.ValidarNum("Ingrese cupo máximo del hotel:");
	    
	    DtoAdministrador.crearHotel(nombre, provincia, direccion, cantHabitaciones, cupoMaximo);
	}

	// =============== CREAR ACTIVIDAD ===============
	public static void crearActividad() {
	    // Seleccionar hotel
	    List<Hotel> hoteles = DtoAdministrador.verHoteles();
	    
	    if (hoteles.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No hay hoteles registrados. Cree un hotel primero.", "INFO", 1);
	        return;
	    }
	    
	    String[] opcionesHoteles = new String[hoteles.size()];
	    for (int i = 0; i < hoteles.size(); i++) {
	        Hotel h = hoteles.get(i);
	        opcionesHoteles[i] = h.getId() + " - " + h.getNombre() + " (" + h.getProvincia() + ")";
	    }
	    
	    String hotelSel = (String) JOptionPane.showInputDialog(null,
	        "Seleccione el hotel para la actividad:",
	        "HOTEL",
	        JOptionPane.QUESTION_MESSAGE,
	        null,
	        opcionesHoteles,
	        opcionesHoteles[0]);
	    
	    if (hotelSel == null) return;
	    int idHotel = Integer.parseInt(hotelSel.split(" - ")[0]);
	    
	    // Ingresar datos de la actividad
	    String nombre = repository.Validaciones.ValidarContras("Ingrese nombre de la actividad:");
	    
	    // Dropdown de categoría
	    repository.Actividades_categoria categoriaEnum = (repository.Actividades_categoria) JOptionPane.showInputDialog(
	        null,
	        "Seleccione la categoría:",
	        "CATEGORÍA",
	        JOptionPane.QUESTION_MESSAGE,
	        null,
	        repository.Actividades_categoria.values(),
	        repository.Actividades_categoria.values()[0]);
	    
	    if (categoriaEnum == null) return;
	    String categoria = categoriaEnum.toString();
	    
	    String locacion = repository.Validaciones.ValidarContras("Ingrese locación:");
	    int edadMinima = repository.Validaciones.ValidarNum("Ingrese edad mínima:");
	    int edadMaxima = repository.Validaciones.ValidarNum("Ingrese edad máxima:");
	    double precio = repository.Validaciones.ValidarNum("Ingrese precio:");
	    double duracion = repository.Validaciones.ValidarNum("Ingrese duración (en horas):");
	    LocalDate fechaInicio = repository.Validaciones.ValidarFecha("Ingrese fecha de inicio");
	    LocalDate fechaFin = repository.Validaciones.ValidarFecha("Ingrese fecha de fin");
	    
	    String riesgo = repository.Validaciones.ValidarContras("¿Tiene riesgo? (Si/No):");
	    
	    DtoAdministrador.crearActividad(nombre, categoria, locacion, edadMinima, edadMaxima,
	        precio, duracion, fechaInicio, fechaFin, idHotel, riesgo);
	}

	// =============== CREAR PAQUETE ===============
	public static void crearPaquete() {
	    // Seleccionar hotel
	    List<Hotel> hoteles = DtoAdministrador.verHoteles();
	    
	    if (hoteles.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No hay hoteles registrados.", "INFO", 1);
	        return;
	    }
	    
	    String[] opcionesHoteles = new String[hoteles.size()];
	    for (int i = 0; i < hoteles.size(); i++) {
	        Hotel h = hoteles.get(i);
	        opcionesHoteles[i] = h.getId() + " - " + h.getNombre() + " (" + h.getProvincia() + ")";
	    }
	    
	    String hotelSel = (String) JOptionPane.showInputDialog(null,
	        "Seleccione el hotel:",
	        "HOTEL",
	        JOptionPane.QUESTION_MESSAGE,
	        null,
	        opcionesHoteles,
	        opcionesHoteles[0]);
	    
	    if (hotelSel == null) return;
	    int idHotel = Integer.parseInt(hotelSel.split(" - ")[0]);
	    
	    // Seleccionar actividad del hotel seleccionado
	    List<Actividad> actividades = DtoAdministrador.obtenerActividadesPorHotel(idHotel);
	    
	    if (actividades.isEmpty()) {
	        JOptionPane.showMessageDialog(null, 
	            "Este hotel no tiene actividades. Cree una actividad primero.", "INFO", 1);
	        return;
	    }
	    
	    String[] opcionesActividades = new String[actividades.size()];
	    for (int i = 0; i < actividades.size(); i++) {
	        Actividad a = actividades.get(i);
	        opcionesActividades[i] = a.getId() + " - " + a.getNombre() + 
	            " (" + a.getCategoria() + ") - $" + a.getPrecio();
	    }
	    
	    String actSel = (String) JOptionPane.showInputDialog(null,
	        "Seleccione la actividad:",
	        "ACTIVIDAD",
	        JOptionPane.QUESTION_MESSAGE,
	        null,
	        opcionesActividades,
	        opcionesActividades[0]);
	    
	    if (actSel == null) return;
	    int idActividad = Integer.parseInt(actSel.split(" - ")[0]);
	    
	    // Ingresar datos del paquete
	    LocalDate fechaInicio = repository.Validaciones.ValidarFecha("Ingrese fecha de inicio del paquete");
	    LocalDate fechaFin = repository.Validaciones.ValidarFecha("Ingrese fecha de fin del paquete");
	    double precio = repository.Validaciones.ValidarNum("Ingrese precio del paquete:");
	    int cupoMaximo = repository.Validaciones.ValidarNum("Ingrese cupo máximo de personas:");
	    
	    DtoAdministrador.crearPaquete(fechaInicio, fechaFin, precio, idHotel, idActividad, cupoMaximo);
	}
	
}// fin
