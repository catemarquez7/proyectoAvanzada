package dll;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;

import bll.Paquete;
import bll.Preferencias;
import bll.Reserva;
import bll.Usuario;

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
		        PreparedStatement check = (PreparedStatement) conx.prepareStatement(
		            "SELECT 1 FROM preferencias WHERE id_usuario = ?"
		        );
		        check.setInt(1, preferencias.getCliente());
		        ResultSet rs = check.executeQuery();

		        if (rs.next()) {
		            PreparedStatement update = (PreparedStatement) conx.prepareStatement(
		                "UPDATE preferencias SET riesgo = ?, duracion = ?, categoria = ? WHERE id_usuario = ?"
		            );
		            update.setString(1, preferencias.getRiesgo());
		            update.setDouble(2, preferencias.getDuracion());
		            update.setString(3, preferencias.getCategoria());
		            update.setInt(4, preferencias.getCliente());
		            update.executeUpdate();
		            System.out.println("Preferencias actualizadas correctamente.");
		            return true;
		        } else {
		            PreparedStatement insert = (PreparedStatement) conx.prepareStatement(
		                "INSERT INTO preferencias (riesgo, duracion, id_usuario, categoria) VALUES (?, ?, ?, ?)"
		            );
		            insert.setString(1, preferencias.getRiesgo());
		            insert.setDouble(2, preferencias.getDuracion());
		            insert.setInt(3, preferencias.getCliente());
		            insert.setString(4, preferencias.getCategoria());
		            insert.executeUpdate();
		            System.out.println("Preferencias insertadas correctamente.");
		            return true;
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(null,
		            "Error al registrar/modificar preferencias: " + e.getMessage(), "ERROR", 0);
		        return false;
		    }
		}

		
		
		//Chequeo de preferencias existentes, para que haya una sola por usuario
		public static boolean preferenciasExistentes(Usuario usuario) {
			
			try {
				java.sql.PreparedStatement stmtCheck = conx.prepareStatement(
					    "SELECT * FROM preferencias WHERE id_usuario = ?");
				stmtCheck.setInt(1, usuario.getId());
				ResultSet rs = stmtCheck.executeQuery();
				if (rs.next()) {
					System.out.println("El usuario ya cuenta con preferencias.");
					return true;
				} else {
					System.out.println("El usuario debe registrar sus preferencias.");
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al registrar sus preferencias: " + e.getMessage(), "ERROR", 0);
				return false;
			}
		}
		
		//Ver preferencias
		public static String mostrarPreferencias(Usuario usuario) {
			
			try {
				java.sql.PreparedStatement statement = conx.prepareStatement(
					    "SELECT * FROM preferencias WHERE id_usuario = ?");
				statement.setInt(1, usuario.getId());
				ResultSet rs = statement.executeQuery();
				
				if (rs.next()) {
				    double duracion = rs.getDouble("duracion");
				    String categoria = rs.getString("categoria");
				    String riesgo = rs.getString("riesgo");

				    String texto = "=== MIS PREFERENCIAS ===\n\nDuracion: " + duracion
				                 + "\nCategoria: " + categoria
				                 + "\nRiesgo: " + riesgo;

				    return texto;
				} else {
				    return "No hay preferencias registradas para este usuario.";
				}
				
			} catch (Exception e) {
				String texto = "Error al mostrar sus preferencias: " + e.getMessage();
				return texto;
			}
			
		}
		
		
		//Cancelar paquete
		
					//aca directamente se elimina de las reservas del cliente segun el id, desde el panel de reservas ya hechas
		
}
