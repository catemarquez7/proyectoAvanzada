package bll;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;


import dll.DtoAdministrador;
import dll.DtoCliente;
import repository.Actividades_categoria;
import repository.SiNoOpcion;

public class Cliente extends Usuario{

	//atributos
	protected LinkedList<Reserva> reservas = new LinkedList<Reserva>();
	protected LinkedList<Reserva> reservasPasadas = new LinkedList<Reserva>();

	
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
	
	
	public LinkedList<Reserva> getReservasPasadas() {
		return reservasPasadas;
	}


	public void setReservasPasadas(LinkedList<Reserva> reservasPasadas) {
		this.reservasPasadas = reservasPasadas;
	}


	//toString
	@Override
	public String toString() {
		return "Cliente [reservas=" + reservas + ", user=" + user + ", nombre=" + nombre + "]";
	}
	
	
	//metodos
	//Ver paquetes recomendados
	public static void verPaquetesReco(Usuario usuario, Cliente cliente) {
		
        List<Paquete> paquetes = DtoCliente.verPaquetesReco(usuario, cliente);

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
			JOptionPane.showMessageDialog(null, Cliente.reservarPaqueteReco(usuario, paquetes, cliente)==true?"Excelente!\nSu paquete ha sido reservado con exito":"No se pudo reservar.");
			break;
		case "No": 
			break;
		}
        
        
	
	}
	
	
	//Reservar paquetes recomendados
	public static boolean reservarPaqueteReco(Usuario usuario, List<Paquete> paquetes, Cliente cliente ) {
		
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
	
	//Reservar paquetes general
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
	
	
	//Ver paquetes general
	public static void verPaquetes(Usuario usuario, Cliente cliente) {
		
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
	
	
	//Ver_reservas
	public static void verReservas(LinkedList<Reserva> reservas) {
		 if (reservas.isEmpty()) {
			 JOptionPane	.showMessageDialog(null, "Usted no ha realizado ninguna reserva todavia.");
			 } else {
			
		    String texto = "=== MIS RESERVAS ===\n\n";

		    for (Reserva r : reservas) {
		        texto += "RESERVA: "
		        		+ "\nHotel: " + r.getPaquete().getHotel().getNombre()
		        		   + " | Paquete: " + r.getPaquete().getActividad().getNombre()
		               + "\nInicio: " + r.getPaquete().getInicioDate()
		               + " | Fin: " + r.getPaquete().getFinDate()
		               + " | Precio: $" + String.format("%.2f", r.getPaquete().getPrecio())
		               + "\n\n";

		        texto += "------------------------\n\n";
		    }
		    
			 JOptionPane	.showMessageDialog(null, texto);
			 }

	}
	
		
	//Menu_preferencias
	public static void preferencias(Usuario usuario) {
				
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
		
	
	
	//Ingreso preferencias
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
	
	
	//Reservas menu general
	public static void reservas(Usuario usuario, Cliente cliente) {
		int opcion ;
		do {
			opcion = JOptionPane.showOptionDialog(null, "Seleccione: ", "BIENVENIDO " + usuario.getNombre(), 0, 0, null,
					repository.Reservas_cl.values(), repository.Reservas_cl.values());
			
			switch (opcion) {
			case 0://ver reservas
				Cliente.verReservas(cliente.getReservas());
				break;
			case 1://cancelar reservas
				Cliente.cancelarReserva(usuario, cliente);
				break;
			case 2://historial reservas pasadas
				Cliente.verHistorial(cliente.getReservasPasadas());
				break;
			case 3://volver
				break;
			}
			
			
		} while (opcion != 3);
		
	}
	
	//Reservas menu general
		public static void reviews(Usuario usuario, Cliente cliente) {
			int opcion ;
			do {
				opcion = JOptionPane.showOptionDialog(null, "Seleccione: ", "BIENVENIDO " + usuario.getNombre(), 0, 0, null,
						repository.Review_cl.values(), repository.Review_cl.values());
				
				switch (opcion) {
				case 0://escribir reseña
					
					break;
				case 1://ver reseñas
					
					break;
				case 2://eliminar reseña
					
					break;
				case 3://volver
					break;
				}
				
				
			} while (opcion != 3);
			
		}
	
	
	//Cancelar reservas
	public static void cancelarReserva(Usuario usuario, Cliente cliente) {
		List<Reserva> reservas = cliente.getReservas();
		String[] opciones = new String[reservas.size()];
	    for (int i = 0; i < reservas.size(); i++) {
	        Reserva r = reservas.get(i);
	        opciones[i] = r.getPaquete().getHotel().getNombre()
	                   + " | " + r.getPaquete().getActividad().getNombre();
	    }

	    String seleccion = (String) JOptionPane.showInputDialog(
	        null, "Seleccione el paquete que desea cancelar:", "Cancelar Paquete", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

	    
	    Reserva reservaSeleccionada = null;
	    for (Reserva r : reservas) {
	        String texto = r.getPaquete().getHotel().getNombre()
	                   + " | " + r.getPaquete().getActividad().getNombre();
	        if (texto.equals(seleccion)) {
	        	reservaSeleccionada = r;
	            break;
	        }
	    }
	    
	    SiNoOpcion opcionEnum = (SiNoOpcion)JOptionPane.showInputDialog(null, "Está seguro de querer continuar?", "SELECCION", 0, null, repository.SiNoOpcion.values(), repository.SiNoOpcion.values());		
		
	  		String opcion = opcionEnum.toString();
	  		
	  		switch (opcion) {
	  		case "Si": 
	  			JOptionPane.showMessageDialog(null, DtoCliente.cancelarReserva(usuario, cliente, reservaSeleccionada)==true?"Su paquete ha sido cancelado con exito.":"No se pudo cancelar.");
	  			break;
	  		case "No": 
	  			break;
	  		}
	    
	}
	
	
	//Ver historial
	public static void verHistorial(LinkedList<Reserva> reservasPasadas) {
		 if (reservasPasadas.isEmpty()) {
			 JOptionPane	.showMessageDialog(null, "Usted no cuenta con reservas ya finalizadas.");
			 } else {
			
		    String texto = "=== MIS HISTORIAL ===\n\n";

		    for (Reserva r : reservasPasadas) {
		        texto += "RESERVA: "
		        		+ "\nHotel: " + r.getPaquete().getHotel().getNombre()
		        		   + " | Paquete: " + r.getPaquete().getActividad().getNombre()
		               + "\nInicio: " + r.getPaquete().getInicioDate()
		               + " | Fin: " + r.getPaquete().getFinDate()
		               + " | Precio: $" + String.format("%.2f", r.getPaquete().getPrecio())
		               + "\n\n";

		        texto += "------------------------\n\n";
		    }
		    
			 JOptionPane	.showMessageDialog(null, texto);
			 }	
				
	}
	
	
	
	
	
	
	
		

}//fin