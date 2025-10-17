package bll;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import dll.DtoCliente;
import dll.DtoEncargado;
import repository.Actividades_categoria;
import repository.SiNoOpcion;

public class Cliente extends Usuario{

	//atributos
	protected LinkedList<Reserva> reservas = new LinkedList<Reserva>();
	
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
	public static void verPaquetes(Usuario usuario, Cliente cliente) {
		
        List<Paquete> paquetes = DtoCliente.verPaquetes(usuario);

        if (paquetes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay paquetes disponibles.", "INFO", 1);
            return;
        }

        String texto = "=== EXPLORAR PAQUETES RECOMENDADOS ===\n\n";

        for (Paquete p : paquetes) {
            texto += "Hotel: " + p.getHotel().getNombre()
                   + " | Actividad: " + p.getActividad().getNombre()
                   + " | Provincia: " + p.getHotel().getProvincia()
                   + "\nInicio: " + p.getInicioDate()
                   + " | Fin: " + p.getFinDate()
                   + " | Precio: $" + String.format("%.2f", p.getPrecio())
                   + "\n";

            texto += "------------------------\n";
        }
        
        JOptionPane.showMessageDialog(null, texto, "PAQUETES", JOptionPane.INFORMATION_MESSAGE);
        
        SiNoOpcion opcionEnum = (SiNoOpcion)JOptionPane.showInputDialog(null, "Desea reservar algun paquete?", "SELECCION", 0, null, repository.SiNoOpcion.values(), repository.SiNoOpcion.values());		
		
		String opcion = opcionEnum.toString();
		
		switch (opcion) {
		case "Si": 
			JOptionPane.showMessageDialog(null, Cliente.reservarPaquete(usuario, paquetes, cliente)==true?"Excelente!\nSu paquete ha sido reservado con exito":"No se pudo reservar.");
			break;
		case "No": 
			break;
		}
        
        
	
	}
	
	
	
	//Reservar_paquetes
	public static boolean reservarPaquete(Usuario usuario, List<Paquete> paquetes, Cliente cliente ) {
		
		String[] opciones = new String[paquetes.size()];
	    for (int i = 0; i < paquetes.size(); i++) {
	        Paquete p = paquetes.get(i);
	        opciones[i] = p.getHotel().getNombre()
	                   + " | " + p.getActividad().getNombre()
	                   + " | Precio: $" + String.format("%.2f", p.getPrecio());
	    }

	    String seleccion = (String) JOptionPane.showInputDialog(
	        null, "Seleccione un paquete para reservar:", "Reservar Paquete", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

	    if (seleccion == null) {
	        return false; 
	    }

	    Paquete paqueteSeleccionado = null;
	    for (Paquete p : paquetes) {
	        String texto = p.getHotel().getNombre()
	                     + " | " + p.getActividad().getNombre()
	                     + " | Precio: $" + String.format("%.2f", p.getPrecio());
	        if (texto.equals(seleccion)) {
	            paqueteSeleccionado = p;
	            break;
	        }
	    }

	    return DtoCliente.reservarPaquete(usuario, paqueteSeleccionado, cliente);
	}
	
	//Ver_reservas
	
	public static void verReservas(LinkedList<Reserva> reservas) {
		 if (reservas.isEmpty()) {
			 JOptionPane	.showMessageDialog(null, "Usted no ha realizado ninguna reserva todavia.");
			 } else {
				
			

		    String texto = "=== MIS RESERVAS ===\n\n";

		    for (Reserva r : reservas) {
		        texto += "ID Reserva: " + r.getId()
		               + " | Hotel: " + r.getPaquete().getHotel().getNombre()
		               + "\nInicio: " + r.getPaquete().getInicioDate()
		               + " | Fin: " + r.getPaquete().getFinDate()
		               + " | Precio: $" + String.format("%.2f", r.getPaquete().getPrecio())
		               + "\n\n";

		        texto += "------------------------\n\n";
		    }
		    
			 JOptionPane	.showMessageDialog(null, texto);
			 }

	}
	

	
	
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
