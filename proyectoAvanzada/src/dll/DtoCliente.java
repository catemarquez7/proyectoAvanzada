package dll;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import bll.Actividad;
import bll.Cliente;
import bll.Hotel;
import bll.Paquete;
import bll.Preferencias;
import bll.Reserva;
import bll.Usuario;

public class DtoCliente {

	private static Connection conx = Conexion.getInstance().getConnection();

	
	
		//Ver paquetes
		public static List<Paquete> verPaquetes(Usuario usuario) {
			
		    List<Paquete> paquetes = new ArrayList<>();
			
			try {
		        PreparedStatement stmtPref = (PreparedStatement) conx.prepareStatement(
		            "SELECT categoria, riesgo FROM preferencias WHERE id_usuario = ?"
		        );
		        stmtPref.setInt(1, usuario.getId());

		        ResultSet rsPref = stmtPref.executeQuery();

		        String categoria = null;
		        String riesgo = null;

		        if (rsPref.next()) {
		            categoria = rsPref.getString("categoria");
		            riesgo = rsPref.getString("riesgo");
		        }

		        PreparedStatement stmtAct = (PreparedStatement) conx.prepareStatement(
		            "SELECT id, nombre, categoria, riesgo FROM actividad " +
		            "WHERE categoria = ? AND riesgo = ?"
		        );
		        stmtAct.setString(1, categoria);
		        stmtAct.setString(2, riesgo);

		        ResultSet rsAct = stmtAct.executeQuery();

		        List<Actividad> actividades = new ArrayList<>();

		        while (rsAct.next()) {
		            Actividad act = new Actividad();
		            act.setId(rsAct.getInt("id"));
		            act.setNombre(rsAct.getString("nombre"));
		            act.setCategoria(rsAct.getString("categoria"));
		            act.setRiesgo(rsAct.getString("riesgo"));
		            actividades.add(act);
		        }

		        for (Actividad actividad : actividades) {
		            PreparedStatement stmtPaq = (PreparedStatement) conx.prepareStatement(
		                "SELECT * FROM paquete WHERE id_actividad = ?"
		            );
		            stmtPaq.setInt(1, actividad.getId());

		            ResultSet rsPaq = stmtPaq.executeQuery();

		            while (rsPaq.next()) {
		                Paquete paquete = new Paquete();
		                paquete.setId(rsPaq.getInt("id"));
		                paquete.setPrecio(rsPaq.getDouble("precio"));
		                paquete.setInicioDate(rsPaq.getDate("fecha_inicio"));
		                paquete.setFinDate(
		                    rsPaq.getDate("fecha_fin"));

		                paquete.setActividad(actividad);

		                int idHotel = rsPaq.getInt("id_hotel");

		                PreparedStatement stmtHotel = (PreparedStatement) conx.prepareStatement(
		                    "SELECT * FROM hotel WHERE id = ?"
		                );
		                stmtHotel.setInt(1, idHotel);

		                ResultSet rsHotel = stmtHotel.executeQuery();

		                if (rsHotel.next()) {
		                    Hotel hotel = new Hotel();
		                    hotel.setId(rsHotel.getInt("id"));
		                    hotel.setNombre(rsHotel.getString("nombre"));
		                    hotel.setDireccion(rsHotel.getString("direccion"));
		                    hotel.setProvincia(rsHotel.getString("provincia"));
		                    paquete.setHotel(hotel);
		                }

		                paquetes.add(paquete);
		            }
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

			return paquetes;
			
		}
	
	
		
		
		
		
		
		//Reservar paquetes
		public static boolean reservarPaquete(Usuario usuario, Paquete paquete, Cliente cliente) {
			try {
	            PreparedStatement stmt = (PreparedStatement) conx.prepareStatement(
	                "INSERT INTO reserva (id_usuario, id_paquete, estado) VALUES (?, ?, ?)",
	                Statement.RETURN_GENERATED_KEYS
	            );

	            stmt.setInt(1, usuario.getId());
	            stmt.setInt(2, paquete.getId());
	            stmt.setString(3, "pendiente");

	            int filas = stmt.executeUpdate();

	            if (filas > 0) {
	                ResultSet rs = stmt.getGeneratedKeys();
	                int idReserva = 0;
	                if (rs.next()) {
	                    idReserva = rs.getInt(1);
	                }

	                Reserva reserva = new Reserva();
	                reserva.setId(idReserva);
	                reserva.setUsuario(usuario);
	                reserva.setPaquete(paquete);
	                reserva.setEstado("pendiente");

	                cliente.getReservas().add(reserva);

	                JOptionPane.showMessageDialog(null, 
	                        "Reserva creada con éxito:\nID: " + idReserva
	                        + "Paquete: " + paquete.getHotel().getNombre()
	                        + " | " + paquete.getActividad().getNombre()
	                        + "\nPrecio: $" + String.format("%.2f", paquete.getPrecio())
	                    );	                
	                return true;
	            } else {
	                JOptionPane.showMessageDialog(null, "Error al crear la reserva.");
	                return false;
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
		}
		
		
		//Cargar_reservas
		public static void cargarReservasExistentes(Usuario usuario, Cliente cliente) {
			try {
		        PreparedStatement stmt = (PreparedStatement) conx.prepareStatement(
		            "SELECT r.id AS id_reserva, r.estado, r.id_paquete, " +
		            "p.fecha_inicio, p.fecha_fin, p.precio, p.id_hotel, p.id_actividad, " +
		            "h.nombre AS hotel_nombre, h.direccion, h.provincia, " +
		            "a.nombre AS actividad_nombre, a.categoria, a.riesgo " +
		            "FROM reserva r " +
		            "JOIN paquete p ON r.id_paquete = p.id " +
		            "JOIN hotel h ON p.id_hotel = h.id " +
		            "JOIN actividad a ON p.id_actividad = a.id " +
		            "WHERE r.id_usuario = ?"
		        );

		        stmt.setInt(1, usuario.getId()); 

		        ResultSet rs = stmt.executeQuery();

		        while (rs.next()) {
		            // Crear actividad
		            bll.Actividad actividad = new bll.Actividad();
		            actividad.setId(rs.getInt("id_actividad"));
		            actividad.setNombre(rs.getString("actividad_nombre"));
		            actividad.setCategoria(rs.getString("categoria"));
		            actividad.setRiesgo(rs.getString("riesgo"));

		            // Crear hotel
		            bll.Hotel hotel = new bll.Hotel();
		            hotel.setId(rs.getInt("id_hotel"));
		            hotel.setNombre(rs.getString("hotel_nombre"));
		            hotel.setDireccion(rs.getString("direccion"));
		            hotel.setProvincia(rs.getString("provincia"));

		            // Crear paquete
		            bll.Paquete paquete = new bll.Paquete();
		            paquete.setId(rs.getInt("id_paquete"));
		            paquete.setInicioDate(rs.getDate("fecha_inicio"));
		            paquete.setFinDate(rs.getDate("fecha_fin"));
		            paquete.setPrecio(rs.getDouble("precio"));
		            paquete.setHotel(hotel);
		            paquete.setActividad(actividad);

		            // Crear reserva
		            bll.Reserva reserva = new bll.Reserva();
		            reserva.setId(rs.getInt("id_reserva"));
		            reserva.setCliente(cliente);
		            reserva.setUsuario(usuario); // <- asignamos el usuario
		            reserva.setPaquete(paquete);
		            reserva.setEstado(rs.getString("estado"));

		            // Agregar a la lista del cliente
		            cliente.getReservas().add(reserva);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Error al cargar las reservas: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		    }
			
			
			
		}
		
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
