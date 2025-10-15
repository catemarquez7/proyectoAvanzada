package bll;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import dll.DtoAdministrador;

public class Administrador extends Usuario{
	
	
	//constructores
	
	public Administrador(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni, String direccion,
			int id, String user, String pass, String pregunta, String respuesta, LocalDate fecha_creacion,
			String tipo_usuario, String estado) {
		super(nombre, apellido, fecha_nac, mail, dni, direccion, id, user, pass, pregunta, respuesta, fecha_creacion,
				tipo_usuario, estado);
	}

	public Administrador() {
		
	}

	//toString
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

	

	
	

	//metodos

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

	    verHoteles();
	    
	    int idHotel = repository.Validaciones.ValidarNum("Ingrese el ID del hotel que desea modificar:");
	    String nuevoNombre = repository.Validaciones.ValidarContras("Ingrese el nuevo nombre del hotel:");
	    
	    if (DtoAdministrador.modificarHotel(idHotel, nuevoNombre)) {
	        JOptionPane.showMessageDialog(null, "Hotel modificado correctamente!", "ÉXITO", 1);
	    }
	}

	// Ver reservas
	public static void verReservas() {
	    List<Reserva> reservas = DtoAdministrador.verReservas();
	    
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

	// Ver paquetes
	public static void verPaquetes() {
	    List<Paquete> paquetes = DtoAdministrador.verPaquetes();
	    
	    if (paquetes.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No hay paquetes registrados.", "INFO", 1);
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
	            texto += "Habitación: " + p.getHabitacion().getNumero() + 
	                     " (" + p.getHabitacion().getTipo() + ")\n";
	        }
	        
	        if (p.getActividad() != null) {
	            texto += "Actividad: " + p.getActividad().getNombre() + 
	                     " - " + p.getActividad().getCategoria() + "\n";
	        }
	        
	        texto += "------------------------\n";
	    }
	    
	    JOptionPane.showMessageDialog(null, texto, "PAQUETES", JOptionPane.INFORMATION_MESSAGE);
	}

	// Gestionar cuentas
	public static void gestionarCuentas() {
	    int opcion;
	    
	    do {
	        opcion = JOptionPane.showOptionDialog(null, 
	            "Seleccione una opción:", 
	            "GESTIÓN DE CUENTAS", 
	            0, 0, null, 
	            new String[]{"Ver cuentas", "Bloquear cuenta", "Desbloquear cuenta", "Eliminar cuenta", "Estadísticas", "Volver"}, 
	            "Ver cuentas");
	        
	        switch(opcion) {
	            case 0: // Ver cuentas
	                verCuentas();
	                break;
	            case 1: // Bloquear
	                bloquearCuenta();
	                break;
	            case 2: // Desbloquear
	                desbloquearCuenta();
	                break;
	            case 3: // Eliminar
	                eliminarCuenta();
	                break;
	            case 4: // Estadísticas
	                mostrarEstadisticas();
	                break;
	            case 5: // Volver
	                break;
	        }
	    } while(opcion != 5);
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
	        switch(u.getTipo_usuario()) {
	            case "1": tipoTexto = "Cliente"; break;
	            case "2": tipoTexto = "Encargado"; break;
	            case "3": tipoTexto = "Administrador"; break;
	        }
	        
	        texto += "Tipo: " + tipoTexto + "\n";
	        texto += "Estado: " + u.getEstado() + "\n";
	        texto += "------------------------\n";
	    }
	    
	    JOptionPane.showMessageDialog(null, texto, "USUARIOS", JOptionPane.INFORMATION_MESSAGE);
	}

	// Bloquear cuenta
	private static void bloquearCuenta() {
	    verCuentas();
	    int idUsuario = repository.Validaciones.ValidarNum("Ingrese el ID del usuario a bloquear:");
	    DtoAdministrador.bloquearCuenta(idUsuario);
	}

	// Desbloquear cuenta
	private static void desbloquearCuenta() {
	    verCuentas();
	    int idUsuario = repository.Validaciones.ValidarNum("Ingrese el ID del usuario a desbloquear:");
	    DtoAdministrador.desbloquearCuenta(idUsuario);
	}

	// Eliminar cuenta
	private static void eliminarCuenta() {
	    verCuentas();
	    int idUsuario = repository.Validaciones.ValidarNum("Ingrese el ID del usuario a eliminar:");
	    
	    int confirmar = JOptionPane.showConfirmDialog(null, 
	        "¿Está seguro de eliminar esta cuenta?\nEsta acción no se puede deshacer.", 
	        "CONFIRMAR", 
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
	
	
	
}
