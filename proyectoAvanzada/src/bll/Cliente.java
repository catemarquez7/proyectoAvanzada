package bll;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import dll.DtoCliente;
import dll.DtoEncargado;

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
	//Ver paquetes
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
    }//fin 
	
	
	//Reservar paquetes
		
		
		
	//Ingresar preferencias
	public static boolean ingresarPreferencias(Usuario usuario) {
				
		int categoriaNum, riesgoNum;
		double duracion;
		String categoria="", riesgo= "";
		
		duracion = repository.Validaciones.ValidarNum("Ingrese la duracion ideal de la actividad, en horas:");
		
		categoriaNum = (int)JOptionPane.showInputDialog(null, "Seleccione la categoria de su interes", "SELECCION", 0, null, repository.Actividades_categoria.values(), repository.Actividades_categoria.values());
		
		riesgoNum = (int)JOptionPane.showInputDialog(null, "Desea un alto nivel de riesgo?", "SELECCION", 0, null, repository.SiNoOpcion.values(), repository.SiNoOpcion.values());
		
		switch (categoriaNum) {
		case 0:
			categoria = "cultural";
			break;
		case 1:
			categoria = "entretenimiento";
			break;
		case 2:
			categoria = "deportivo";
			break;
		case 3:
			categoria = "aventura";
			break;
		case 4:
			categoria = "recreativo";
			break;
		case 5:
			categoria = "naturaleza";
			break;
		case 6:
			categoria = "gastronómico";
			break;
		
		} 
		
		// HACER QUE PUEDA ELEGIR MAS DE UNA CATEGORIA !!!!!
		// ver si hacemos otro panel para modificarlas o si directamente llene esto otra vez y listo

		
		switch (riesgoNum) {
		case 0:
			riesgo = "Si";
			break;
		case 1:
			riesgo = "No";
			break;

		}
			
		Preferencias preferencias = new Preferencias(categoria, riesgo, duracion, usuario.getId());
		return DtoCliente.ingresarPreferencias(preferencias);
	}
		
		
	//Modificar preferencias
		
		
		
	//Cancelar paquete
		
	
	
	
	
	
	
	
}
