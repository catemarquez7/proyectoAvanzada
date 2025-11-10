package bll;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.swing.JOptionPane;

import dll.DtoCliente;
import dll.DtoUsuario;
import repository.Utilidades;

public class Usuario extends Persona {

	// atributos
	protected int id;
	protected String user;
	protected String pass;
	protected String pregunta;
	protected String respuesta;
	protected LocalDate fecha_creacion;
	protected String tipo_usuario;
	protected String estado;

	// constructores

	public Usuario(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni, String direccion, int id,
			String user, String pass, String pregunta, String respuesta, LocalDate fecha_creacion, String tipo_usuario,
			String estado) {
		super(nombre, apellido, fecha_nac, mail, dni, direccion);
		this.id = id;
		this.user = user;
		this.pass = pass;
		this.pregunta = pregunta;
		this.respuesta = respuesta;
		this.fecha_creacion = fecha_creacion;
		this.tipo_usuario = tipo_usuario;
		this.estado = estado;
	}

	public Usuario(int id, String user, String pass) {
		this.id = id;
		this.user = user;
		this.pass = pass;
	}

	public Usuario(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni, String direccion,
			String user, String pass, String pregunta, String respuesta) {
		super(nombre, apellido, fecha_nac, mail, dni, direccion);
		this.user = user;
		this.pass = pass;
		this.pregunta = pregunta;
		this.respuesta = respuesta;
		this.fecha_creacion = LocalDate.now();
		this.tipo_usuario = "1";
		this.estado = "activo";
	}

	public Usuario() {

	}

	// getters y setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public LocalDate getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(LocalDate fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public String getTipo_usuario() {
		return tipo_usuario;
	}

	public void setTipo_usuario(String tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	// toString
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", user=" + user + ", pass=" + pass + ", pregunta=" + pregunta + ", respuesta="
				+ respuesta + ", fecha_creacion=" + fecha_creacion + ", tipo_usuario=" + tipo_usuario + ", estado="
				+ estado + "]";
	}

	// metodos

	public static Usuario login(String user, String pass) { 

	    Usuario usuarioEncontrado = DtoUsuario.login(user, pass);

	    if (usuarioEncontrado != null) {
	        
	        if (usuarioEncontrado.getEstado().equals("bloqueado")) {
	              JOptionPane.showMessageDialog(null,
	                      "Su cuenta ha sido bloqueada. Contacte al administrador.",
	                      "CUENTA BLOQUEADA",
	                      JOptionPane.ERROR_MESSAGE);
	              return null;
	        } else {
	            
	            return usuarioEncontrado;

	        }
	        
	    } else {
	        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "ERROR",
	                JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	}

	public static boolean registrarse(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni,
			String direccion, String user, String pass, String pregunta, String respuesta) {

		if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || mail.trim().isEmpty() || direccion.trim().isEmpty()
				|| user.trim().isEmpty() || pass.isEmpty() || pregunta.trim().isEmpty() || respuesta.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error de Validación",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!repository.Utilidades.contieneSoloLetras(nombre) || !repository.Utilidades.contieneSoloLetras(apellido)) {
			JOptionPane.showMessageDialog(null, "El nombre y apellido solo deben contener letras.", "Error de Formato",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		long edad = ChronoUnit.YEARS.between(fecha_nac, LocalDate.now());
		if (edad < 18) {
			JOptionPane.showMessageDialog(null, "Debe ser mayor de 18 años para registrarse.", "Error de Edad",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (String.valueOf(dni).length() < 8) {
			JOptionPane.showMessageDialog(null, "El DNI debe tener al menos 8 dígitos.", "Error de DNI",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (pass.length() < 8) {
			JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres.", "Error de Seguridad",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!Utilidades.ValidarMail(mail)) {
			JOptionPane.showMessageDialog(null, "El formato del correo electrónico no es válido.", "Error de Formato",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			Usuario nuevo = new Usuario(nombre, apellido, fecha_nac, mail, dni, direccion, user, pass, pregunta,
					respuesta);

			return DtoUsuario.agregarUsuario(nuevo);

		} catch (Exception e) {
			e.printStackTrace();

			JOptionPane.showMessageDialog(null, "Error interno al procesar el registro.", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}//fin


	public static void redirigir(Usuario usuario) {

	    String tipoUser = usuario.getTipo_usuario();

	    switch (tipoUser) {
	        case "1": // Cliente
	            if (DtoUsuario.chequeoSuspension()) {
	                JOptionPane.showMessageDialog(null, "Sistema en mantenimiento, intentelo de nuevo en unas horas");
	            } else {
	                // REEMPLAZADO: menuCliente(usuario, cliente);
	                try {
	                    // Abrir la ventana de Cliente
	                	
	                	Cliente cliente = new Cliente(usuario.getNombre(), usuario.getApellido(), usuario.fecha_nac, usuario.getMail(), usuario.getDni(), usuario.getDireccion(), usuario.getId(), usuario.getUser(), usuario.getPass(), usuario.getPregunta(), usuario.getRespuesta(), usuario.fecha_creacion, usuario.getTipo_usuario(), usuario.getEstado(), null);
	                	
	                	 ui.Cliente frameCliente = new ui.Cliente(usuario, cliente);
	                     frameCliente.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    JOptionPane.showMessageDialog(null, "Error al cargar la vista de Cliente.", "ERROR", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	            break;
	        case "2": // Encargado
	            if (DtoUsuario.chequeoSuspension()) {
	                JOptionPane.showMessageDialog(null, "Sistema en mantenimiento, intentelo de nuevo en unas horas");
	            } else {
	                // REEMPLAZADO: menuEncargado(usuario);
	                try {
	                    // Abrir la ventana de Encargado (Requiere castear a bll.Encargado)
	                    ui.Encargado frameEncargado = new ui.Encargado();
	                    frameEncargado.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    JOptionPane.showMessageDialog(null, "Error al cargar la vista de Encargado.", "ERROR", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	            break;
	        case "3": // Administrador
	            // REEMPLAZADO: menuAdmin(usuario);
	            try {
	                // Abrir la ventana de Administrador (Requiere castear a bll.Administrador)
	                ui.Administrador frameAdmin = new ui.Administrador();
	                frameAdmin.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(null, "Error al cargar la vista de Administrador.", "ERROR", JOptionPane.ERROR_MESSAGE);
	            }
	            break;
	        default:
	            JOptionPane.showMessageDialog(null, "Tipo de usuario no reconocido", "ERROR", 0);
	    }

	}//fin

	public static void menuCliente(Usuario usuario, Cliente cliente) {
		int opcion;
		DtoCliente.cargarReservasExistentes(usuario, cliente);
		DtoCliente.cargarReviewsExistentes(usuario, cliente);

		do {
			opcion = JOptionPane.showOptionDialog(null, "Seleccione: ", "BIENVENIDO " + usuario.getNombre(), 0, 0, null,
					repository.Acciones_cl.values(), repository.Acciones_cl.values());

			switch (opcion) {
			case 0:// paquetes_recomendados
				Cliente.verPaquetesReco(usuario, cliente);
				break;
			case 1:// paquetes_todos
				Cliente.verPaquetes(usuario, cliente);
				break;
			case 2: // reservas_activas
				Cliente.reservas(usuario, cliente);
				;
				break;
			case 3: // realizar_reseñas
				Cliente.reviews(usuario, cliente);
				break;
			case 4: // ver _preferencias
				Cliente.preferencias(usuario);
				break;
			case 5:// atras
				JOptionPane.showMessageDialog(null, "Redirigiendo al menú principal! ", "ADIOS!", 0);
				break;
			}

		} while (opcion != 5);
	}// fin

	public static void menuEncargado(Usuario usuario) {
		int opcion;
		Encargado encargado = (Encargado) usuario;

		do {
			opcion = JOptionPane.showOptionDialog(null, "Seleccione: ", "BIENVENIDO " + usuario.getNombre(), 0, 0, null,
					repository.Acciones_enc.values(), repository.Acciones_enc.values());

			switch (opcion) {
			case 0:
				// Reservas
				Encargado.verReservas(encargado.getId_hotel());
				break;
			case 1:
				// Habitaciones
				Encargado.verHabitaciones(encargado.getId_hotel());
				break;
			case 2:
				// Check_in
				Encargado.realizarCheckin(encargado.getId_hotel());
				break;
			case 3:
				// Check_out
				Encargado.realizarCheckout(encargado.getId_hotel());
				break;
			case 4:
				// Datos_cliente
				Encargado.verDatosClientes(encargado.getId_hotel());
				break;
			case 5:
				// Vistas
				Encargado.Vistas(encargado.getId_hotel());
				break;
			case 6:
				// Promociones
				Encargado.gestionarPromociones(encargado.getId_hotel());
				break;
			case 7:
				// Atras
				JOptionPane.showMessageDialog(null, "Redirigiendo al menú principal! ", "ADIOS!", 0);
				break;
			}// switch1

		} while (opcion != 7);
	}// fin

	public static void menuAdmin(Usuario usuario) {
		int opcion;

		do {
			opcion = JOptionPane.showOptionDialog(null, "Seleccione: ", "BIENVENIDO " + usuario.getNombre(), 0, 0, null,
					repository.Acciones_adm.values(), repository.Acciones_adm.values());

			switch (opcion) {
			case 0:
				// Ver_hoteles
				Administrador.verHoteles();
				break;
			case 1:
				// Modificar_hotel
				Administrador.modificarHotel();
				break;
			case 2:
				// Eliminar_hotel
				Administrador.eliminarHotel();
				break;
			case 3:
				// Crear_hotel
				Administrador.crearHotel();
				break;
			case 4:
				// Ver_reservas
				Administrador.verReservas();
				break;
			case 5:
				// Modificar_reserva
				Administrador.modificarReserva();
				break;
			case 6:
				// Ver_paquetes
				Administrador.verPaquetes();
				break;
			case 7:
				// Modificar_paquete
				Administrador.modificarPaquete();
				break;
			case 8:
				// Crear_paquete
				Administrador.crearPaquete();
				break;
			case 9:
				// Crear_actividad
				Administrador.crearActividad();
				break;
			case 10:
				// Gestionar_cuentas
				Administrador.gestionarCuentas();
				break;
			case 11:
				// Mantenimiento (Suspender_sistema)
				Administrador.suspenderSistema();
				break;
			case 12:
				// Cerrar_Sesión
				JOptionPane.showMessageDialog(null, "Redirigiendo al menú principal! ", "ADIOS!", 0);
				break;
			}

		} while (opcion != 12);
	}// fin

}// FIN USUARIO
