package bll;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import dll.DtoUsuario;

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

	public static Usuario login() {
		String user, pass;

		user = JOptionPane.showInputDialog("Ingrese su nombre de usuario:");
		pass = JOptionPane.showInputDialog("Ingrese su contraseña:");

		Usuario usuarioEncontrado = DtoUsuario.login(user, pass);

		if (usuarioEncontrado != null) {
			JOptionPane.showMessageDialog(null, "¡Bienvenido/a " + usuarioEncontrado.getNombre() + "!", "LOGIN EXITOSO",
					JOptionPane.INFORMATION_MESSAGE);
			return usuarioEncontrado;
		} else {
			JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	public static boolean registrarse() {

		String nombre, apellido, direccion, mail, user, pass, pregunta, respuesta;
		int dni;
		LocalDate fecha_nac;

		nombre = repository.Validaciones.ValidarLetras("Ingrese su nombre: ");
		apellido = repository.Validaciones.ValidarLetras("Ingrese su apellido: ");
		fecha_nac = repository.Validaciones.ValidarFecha("Ingrese su fecha de nacimiento: ");
		do {
			mail = JOptionPane.showInputDialog("Ingrese mail");
			if (repository.Validaciones.ValidarMail(mail) == false) {
				JOptionPane.showMessageDialog(null, "Mail incorrecto");
			}
		} while (repository.Validaciones.ValidarMail(mail) == false);
		dni = Usuario.ingresoDni();
		direccion = repository.Validaciones.ValidarContras("Ingrese su domicilio: ");
		user = repository.Validaciones.ValidarLetras("Ingrese su nombre de usuario: ");
		pass = Usuario.ingresoContra();
		pregunta = repository.Validaciones.ValidarLetras("Ingrese su pregunta para recuperación: ");
		respuesta = repository.Validaciones
				.ValidarLetras("Ingrese su respuesta para recuperación:\nLa respuesta no puede ser numérica");

		Usuario nuevo = new Usuario(nombre, apellido, fecha_nac, mail, dni, direccion, user, pass, pregunta, respuesta);
		return DtoUsuario.agregarUsuario(nuevo);

	}// fin

	public static int ingresoDni() {

		int dni;
		String documento;

		do {
			dni = repository.Validaciones.ValidarNum("Ingrese su número de documento: ");
			documento = Integer.toString(dni);

			if (documento.length() < 8) {
				JOptionPane.showMessageDialog(null, "Su número de documento debe tener al menos 8 dígitos.", "ERROR!",
						0);
			}
		} while (documento.length() < 8);

		return dni;

	}// fin

	public static String ingresoContra() {

		String cont, cont2;
		boolean flag = false;

		do {

			cont = repository.Validaciones.ValidarContras("Ingrese su contraseña:\n(Debe tener 8 dígitos)");

			if (cont.length() >= 8) {
				cont2 = repository.Validaciones.ValidarContras("Ingrese su contraseña nuevamente: ");

				if (cont.equals(cont2)) {
					flag = true;
				} else {
					JOptionPane.showMessageDialog(null, "Las contraseñas no son idénticas, vuelva a intentarlo",
							"ERROR!", 0);
					flag = false;
				}
			} else {
				JOptionPane.showMessageDialog(null, "Su contraseña debe tener 8 dígitos", "ERROR!", 0);
				flag = false;
			}

		} while (flag == false);

		return cont;

	}// fin

	public static void redirigir(Usuario usuario) {

		String tipoUser = usuario.getTipo_usuario();

		switch (tipoUser) {
		case "1": // Cliente
			menuCliente(usuario);
			break;
		case "2": // Encargado
			menuEncargado(usuario);
			break;
		case "3": // Administrador
			menuAdmin(usuario);
			break;
		default:
			JOptionPane.showMessageDialog(null, "Tipo de usuario no reconocido", "ERROR", 0);
		}

	}// fin

	public static void menuCliente(Usuario usuario) {
        int opcion;
        
        do {
        	opcion = JOptionPane.showOptionDialog(null, "Seleccione: ", "BIENVENIDO" + usuario.getNombre(), 0, 0, null, repository.Acciones_cl.values(), repository.Acciones_cl.values());
            
            switch(opcion) {
            	case 0:
            		//Reservas
            		break;
                case 1:
                	//Realizar_reseñas
                    break;
                case 2:
                    //Preferencias
                    break;
                case 3:
                    //Historial
                    break;
                case 4:
                    //Atras
                	JOptionPane.showMessageDialog(null, "Redirigiendo al menú principal! ", "ADIOS!", 0);
                    break;
            }
            
        } while(opcion != 4);
    }//fin
	
	
	public static void menuEncargado(Usuario usuario) {
        int opcion;
        
        do {
        	opcion = JOptionPane.showOptionDialog(null, "Seleccione: ", "BIENVENIDO" + usuario.getNombre(), 0, 0, null, repository.Acciones_enc.values(), repository.Acciones_enc.values());
            
            switch(opcion) {
            	case 0:
            		//Reservas
            		Encargado.verReservas();
            		break;
                case 1:
                	//Habitaciones
            		Encargado.verHabitaciones();
                    break;
                case 2:
                    //Actividades
                    break;
                case 3:
                    //Check_in
                	Encargado.realizarCheckin();
                    break;
                case 4:
                    //Check_out
                	Encargado.realizarCheckout();
                    break;
                case 5:
            		//Datos_cliente
            		break;
                case 6:
                	//Historial
                    break;
                case 7:
                    //Promociones
                    break;
                case 8:
                    //Atras
                	JOptionPane.showMessageDialog(null, "Redirigiendo al menú principal! ", "ADIOS!", 0);
                    break;
            }
            
        } while(opcion != 8);
    }//fin
	
	
	public static void menuAdmin(Usuario usuario) {
        int opcion;
        //Ver_hoteles, Gestionar_cuentas, Atrás;
        
        do {
        	opcion = JOptionPane.showOptionDialog(null, "Seleccione: ", "BIENVENIDO" + usuario.getNombre(), 0, 0, null, repository.Acciones_adm.values(), repository.Acciones_adm.values());
            
            switch(opcion) {
            	case 0:
            		//Reservas
            		break;
                case 1:
                	//Habitaciones
                    break;
                case 2:
                    //Atras
                	JOptionPane.showMessageDialog(null, "Redirigiendo al menú principal! ", "ADIOS!", 0);
                    break;
            }
            
        } while(opcion != 2);
    }//fin

}//FIN USUARIO
