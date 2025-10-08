package bll;

import java.time.LocalDate;
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
	public static void realizarCheckin(int id_hotel) {
        int idReserva = Validaciones.ValidarNum("Ingrese ID de la reserva:");
        int dni = Validaciones.ValidarNum("Ingrese DNI del cliente:");
        String tarjeta = Validaciones.ValidarContras("Ingrese número de tarjeta de resguardo:");
        
        DtoEncargado.realizarCheckin(idReserva, String.valueOf(dni), tarjeta, id_hotel);
    }//fin

	// Check-out
	public static void realizarCheckout(int id_hotel) {
        int idReserva = Validaciones.ValidarNum("Ingrese ID de la reserva para check-out:");
        DtoEncargado.realizarCheckout(idReserva, id_hotel);
    }//fin

	// Ver reservas
	public static void verReservas(int id_hotel) {
        List<Reserva> reservas = DtoEncargado.verReservas(id_hotel);

        if (reservas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay reservas registradas para su hotel", "INFO", 1);
            return;
        }

        String texto = "=== RESERVAS DE MI HOTEL ===\n\n";

        for (Reserva r : reservas) {
            texto += "ID: " + r.getId()
                  + " | Cliente: " + r.getCliente().getNombre() + " " + r.getCliente().getApellido()
                  + " | DNI: " + r.getCliente().getDni()
                  + "\nHabitación: " + r.getPaquete().getHabitacion().getNumero()
                  + " | Estado: " + r.getEstado()
                  + " | Precio: $" + String.format("%.2f", r.getPaquete().getPrecio())
                  + "\n";

            if (r.getTarjeta_resguardo() != null) {
                texto += "Tarjeta: " + r.getTarjeta_resguardo() + "\n";
            }

            texto += "------------------------\n";
        }

        JOptionPane.showMessageDialog(null, texto, "RESERVAS", JOptionPane.INFORMATION_MESSAGE);
    }//fin 
	
	//Ver habitaciones
	public static void verHabitaciones(int id_hotel) {
	    List<Habitacion> habitaciones = DtoEncargado.verHabitaciones(id_hotel);

	    if (habitaciones.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No hay habitaciones en su hotel", "INFO", 1);
	        return;
	    }

	    String texto = "=== HABITACIONES DE MI HOTEL ===\n\n";

	    for (Habitacion h : habitaciones) {
	        texto += "ID: " + h.getId()
	              + " | Número: " + h.getNumero()
	              + "\nTipo: " + h.getTipo()
	              + " | Camas: " + h.getCant_camas()
	              + "\nPrecio: $" + String.format("%.2f", h.getPrecio_noche())
	              + " | Estado: " + h.getEstado()
	              + "\n------------------------\n";
	    }

	    JOptionPane.showMessageDialog(null, texto, "HABITACIONES", JOptionPane.INFORMATION_MESSAGE);
	}//fin

}//final clase
