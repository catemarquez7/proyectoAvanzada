package bll;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import dll.DtoCliente;
import dll.DtoEncargado;
import repository.Actividades_categoria;
import repository.SiNoOpcion;

public class Cliente extends Usuario{

	//atributos
	protected LinkedList<Reserva> reservas;
	
	//constructores
	
	public Cliente(String nombre, String apellido, LocalDate fecha_nac, String mail, int dni, String direccion, int id,
			String user, String pass, String pregunta, String respuesta, LocalDate fecha_creacion, String tipo_usuario,
			String estado, LinkedList<Reserva> reservas) {
		super(nombre, apellido, fecha_nac, mail, dni, direccion, id, user, pass, pregunta, respuesta, fecha_creacion,
				tipo_usuario, estado);
		this.reservas = reservas;
	}

	
	public Cliente(){
		
	}
	
	//getters y setters
	public LinkedList<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(LinkedList<Reserva> reservas) {
		this.reservas = reservas;
	}

	
	
	//toString
	@Override
	public String toString() {
		return "Cliente [reservas=" + reservas + ", user=" + user + ", nombre=" + nombre + "]";
	}
	
	
	
	
	//metodos
	//Ver_paquetes
	public static void verPaquetes(Usuario usuario) {
        List<Paquete> paquetes = DtoCliente.verPaquetes(usuario.getId());

        if (paquetes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay paquetes disponibles.", "INFO", 1);
            return;
        }

        String texto = "=== PAQUETES DISPONIBLES PARA USTED ===\n\n";

        for (Paquete p : paquetes) {
            texto += "ID: " + p.getId()
                  + " | Hotel: " + p.getHotel()
                  + " | Ubicacion: " + p.getHotel().getProvincia()
                  + " | Actividad: " + p.getActividad().getNombre()
                  + "\nFecha inicio: " + p.getActividad().getInicioDate()
                  + " | Fecha fin: " + p.getActividad().getFinDate()
                  + "\nPrecio total: " + p.getPrecio()
                  + "\n";

            texto += "------------------------\n";
        }

        JOptionPane.showMessageDialog(null, texto, "PAQUETES", JOptionPane.INFORMATION_MESSAGE);
    }
	
	
	//Reservar_paquetes
	
	
	//Cancelar_reserva

		
	//Menu_preferencias
	public static void preferencias(Usuario usuario) {
				
		double duracion;
		String categoria="", riesgo= "";
		String opcion="";
		
		
		if (DtoCliente.preferenciasExistentes(usuario)) {
			
			JOptionPane.showMessageDialog(null, 	DtoCliente.mostrarPreferencias(usuario));
			
			SiNoOpcion opcionEnum = (SiNoOpcion)JOptionPane.showInputDialog(null, "Desea modificar sus preferencias?", "SELECCION", 0, null, repository.SiNoOpcion.values(), repository.SiNoOpcion.values());		
			
			opcion = opcionEnum.toString();
			
			switch (opcion) {
			case "Si": 
				JOptionPane.showMessageDialog(null, Cliente.ingresarPreferencias(usuario)==true?"Preferencias agregadas/modificadas correctamente!":"No se pudo agregar.");
				break;
			case "No": 
				break;
			}
			
			
		} else {

			JOptionPane.showMessageDialog(null, Cliente.ingresarPreferencias(usuario)==true?"Preferencias agregadas/modificadas correctamente!":"No se pudo agregar.");

		}
		
		
	}
		
	
	
	//Ingresar_preferencias
		public static boolean ingresarPreferencias(Usuario usuario) {
			double duracion;
			String categoria="", riesgo= "";
			duracion = repository.Validaciones.ValidarNum("Ingrese la duracion ideal de la actividad, en horas:");
			
			Actividades_categoria categoriaEnum = (Actividades_categoria) JOptionPane.showInputDialog(null, "Seleccione la categoria de su interes", "SELECCION", 0, null, repository.Actividades_categoria.values(), repository.Actividades_categoria.values());
			
			SiNoOpcion riesgoEnum = (SiNoOpcion)JOptionPane.showInputDialog(null, "Desea un alto nivel de riesgo?", "SELECCION", 0, null, repository.SiNoOpcion.values(), repository.SiNoOpcion.values());
			
			
			categoria = categoriaEnum.toString();
			riesgo = riesgoEnum.toString();
			
			Preferencias preferencias = new Preferencias(categoria, riesgo, duracion, usuario.getId());
			
			return DtoCliente.ingresarPreferencias(preferencias);
		}
		

}
