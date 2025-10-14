package dll;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;

import bll.Paquete;
import bll.Preferencias;
import bll.Reserva;

public class DtoCliente {

	private static Connection conx = Conexion.getInstance().getConnection();

	
	
		//Ver paquetes
		public static List<Paquete> verPaquetes(int id) {
			
			List<Paquete> paquetes = new ArrayList<>();

				
					//fijarse el id del usuario, con eso ir a las preferencias. despues de ahi comparar si coincide con los atributos de actividad
					// y unirlo con paquete. usamos duracion (en horas), rieso (si/no) y categoria que ya tienen las actividades
					//agregar esos paquetes al array, que son especificamente seleccionados para el usuario
			
			return paquetes;
			
		}
	
	
		
		
		
		
		
		//Reservar paquetes
		
					//aca va a seleccionar un paquete de un menu desplegable, pregunta de confimacion, datos de tarjeta?? pago?? 
					//y despues se agrega desde el dto el paquete a las reservas del cliente con el id 
		
		
		
		
		
		
		
		
		
		
		//Ingresar preferencias
		public static boolean ingresarPreferencias(Preferencias preferencias) {
			
			try {
				
				java.sql.PreparedStatement statement = conx.prepareStatement(
						"INSERT INTO preferencias(riesgo, duracion, id_usuario, categoria) VALUES (?, ?, ?, ?)");
				
				statement.setString(1, preferencias.getRiesgo());
				statement.setDouble(2, preferencias.getDuracion());
				statement.setInt(3, preferencias.getCliente());
				statement.setString(4, preferencias.getCategoria());

				int filas = statement.executeUpdate();
				if (filas > 0) {
					System.out.println("Preferencias agregadas correctamente.");
					return true;
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al registrar sus preferencias: " + e.getMessage(), "ERROR", 0);
				return false;	
				}			
			return false;
		}
				
		
		
		
		
		
		
		
		//Cancelar paquete
		
					//aca directamente se elimina de las reservas del cliente segun el id, desde el panel de reservas ya hechas
		
}
