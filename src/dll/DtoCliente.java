package dll;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import bll.Actividad;
import bll.Cliente;
import bll.Habitacion;
import bll.Hotel;
import bll.Paquete;
import bll.Preferencias;
import bll.Reserva;
import bll.Review;
import bll.Usuario;

public class DtoCliente {

	private static Connection conx = Conexion.getInstance().getConnection();

	
	
		//Ver paquetes recomendados
		public static List<Paquete> verPaquetesReco(Usuario usuario, Cliente cliente) {
			
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
		                "SELECT * FROM paquete WHERE id_actividad = ? AND cupo_actual < cupo_maximo"
		            );
		            stmtPaq.setInt(1, actividad.getId());

		            ResultSet rsPaq = stmtPaq.executeQuery();

		            while (rsPaq.next()) {
		                Paquete paquete = new Paquete();
		                paquete.setId(rsPaq.getInt("id"));
		                paquete.setPrecio(rsPaq.getDouble("precio"));
		                paquete.setInicioDate(rsPaq.getDate("fecha_inicio").toLocalDate());
		                paquete.setFinDate(rsPaq.getDate("fecha_fin").toLocalDate());
		                paquete.setActividad(actividad);
		                paquete.setCupo_actual(rsPaq.getInt("cupo_actual"));
		                paquete.setCupo_maximo(rsPaq.getInt("cupo_maximo"));


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
		
		
		// Ver paquetes
		public static List<Paquete> verPaquetes(int idHotel) {
			List<Paquete> paquetes = new ArrayList<>();

			try {
				PreparedStatement stmt = (PreparedStatement) conx.prepareStatement(
						"SELECT p.*, h.*, hab.*, act.* " + "FROM paquete p " + "INNER JOIN hotel h ON p.id_hotel = h.id "
								+ "LEFT JOIN habitacion hab ON p.id_habitacion = hab.id "
								+ "LEFT JOIN actividad act ON p.id_actividad = act.id " + "WHERE p.id_hotel = ? AND p.cupo_actual < p.cupo_maximo");

				stmt.setInt(1, idHotel);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					// Datos del paquete
					int idPaquete = rs.getInt("p.id");
					LocalDate inicioPaq = rs.getDate("p.fecha_inicio").toLocalDate();
					LocalDate finPaq = rs.getDate("p.fecha_fin").toLocalDate();
					double precioPaq = rs.getDouble("p.precio");
					int cupo_actual = rs.getInt("p.cupo_actual");
					int cupo_maximo = rs.getInt("p.cupo_maximo");


					// Datos del hotel
					int idHotelRes = rs.getInt("h.id");
					String nombreHotel = rs.getString("h.nombre");
					String provincia = rs.getString("h.provincia");
					String direccionHotel = rs.getString("h.direccion");
					int cantHabitaciones = rs.getInt("h.cant_habitaciones");
					double calificacion = rs.getDouble("h.calificacion_promedio");

					Hotel hotel = new Hotel(idHotelRes, nombreHotel, provincia, direccionHotel, cantHabitaciones,
							calificacion);

					// Datos de la habitación
					Habitacion habitacion = null;
					if (rs.getObject("hab.id") != null) {
						int idHab = rs.getInt("hab.id");
						int numeroHab = rs.getInt("hab.numero");
						String estadoHab = rs.getString("hab.estado");
						String tipoHab = rs.getString("hab.tipo");
						double precioHab = rs.getDouble("hab.precio");
						int cantCamas = rs.getInt("hab.cant_camas");

						habitacion = new Habitacion(hotel, idHab, numeroHab, estadoHab, tipoHab, precioHab, cantCamas);
					}

					// Datos de la activida
					Actividad actividad = null;
					if (rs.getObject("act.id") != null) {
						int idAct = rs.getInt("act.id");
						String nombreAct = rs.getString("act.nombre");
						String categoria = rs.getString("act.categoria");
						int edadMin = rs.getInt("act.edad_minima");
						int edadMax = rs.getInt("act.edad_maxima");
						double duracion = rs.getDouble("act.duracion");
						double precioAct = rs.getDouble("act.precio");
						String locacion = rs.getString("act.locacion");
						LocalDate inicioAct = rs.getDate("act.fecha_inicio").toLocalDate();
						LocalDate finAct = rs.getDate("act.fecha_fin").toLocalDate();
						String riesgo = rs.getString("act.riesgo");

						actividad = new Actividad(idAct, nombreAct, categoria, edadMin, edadMax, duracion, precioAct,
								locacion, inicioAct, finAct, hotel, riesgo);
					}

					// Crear paq
					Paquete paquete = new Paquete(idPaquete, inicioPaq, finPaq, precioPaq, hotel, habitacion, actividad, precioPaq, null, cupo_actual, cupo_maximo);

					paquetes.add(paquete);
				}

			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al cargar paquetes: " + e.getMessage(), "ERROR", 0);
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
	                
	                PreparedStatement stmtUpdate = (PreparedStatement) conx.prepareStatement(
	                	    "UPDATE paquete SET cupo_actual = cupo_actual - 1 WHERE id = ?"
	                	);
	                	stmtUpdate.setInt(1, paquete.getId());
	                	stmtUpdate.executeUpdate();

	                Reserva reserva = new Reserva();
	                reserva.setId(idReserva);
	                reserva.setUsuario(usuario);
	                reserva.setPaquete(paquete);
	                reserva.setEstado("pendiente");
	                
	                cliente.getReservas().add(reserva);
       
	                return true;
	            } else {
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
			        "WHERE r.id_usuario = ? AND estado = ?"
			    );

			    stmt.setInt(1, usuario.getId()); 
			    stmt.setString(2, "pendiente");

			    ResultSet rs = stmt.executeQuery();

			    while (rs.next()) {
			        // Crear actividad
			        Actividad actividad = new Actividad();
			        actividad.setId(rs.getInt("id_actividad"));
			        actividad.setNombre(rs.getString("actividad_nombre"));
			        actividad.setCategoria(rs.getString("categoria"));
			        actividad.setRiesgo(rs.getString("riesgo"));

			        // Crear hotel
			        Hotel hotel = new Hotel();
			        hotel.setId(rs.getInt("id_hotel"));
			        hotel.setNombre(rs.getString("hotel_nombre"));
			        hotel.setDireccion(rs.getString("direccion"));
			        hotel.setProvincia(rs.getString("provincia"));

			        // Crear paquete
			        Paquete paquete = new Paquete();
			        paquete.setId(rs.getInt("id_paquete"));
			        paquete.setInicioDate(rs.getDate("fecha_inicio").toLocalDate());
			        paquete.setFinDate(rs.getDate("fecha_fin").toLocalDate());
			        paquete.setPrecio(rs.getDouble("precio"));
			        paquete.setHotel(hotel);
			        paquete.setActividad(actividad);

			        // Crear reserva
			        Reserva reserva = new Reserva();
			        reserva.setId(rs.getInt("id_reserva"));
			        reserva.setCliente(cliente);
			        reserva.setUsuario(usuario);
			        reserva.setPaquete(paquete);
			        reserva.setEstado(rs.getString("estado"));

			        // Agregar a la lista del cliente
			        cliente.getReservas().add(reserva);
			    }

			    // Segundo PreparedStatement para reservas finalizadas
			    PreparedStatement stmt2 = (PreparedStatement) conx.prepareStatement(
			        "SELECT r.id AS id_reserva, r.estado, r.id_paquete, " +
			        "p.fecha_inicio, p.fecha_fin, p.precio, p.id_hotel, p.id_actividad, " +
			        "h.nombre AS hotel_nombre, h.direccion, h.provincia, " +
			        "a.nombre AS actividad_nombre, a.categoria, a.riesgo " +
			        "FROM reserva r " +
			        "JOIN paquete p ON r.id_paquete = p.id " +
			        "JOIN hotel h ON p.id_hotel = h.id " +
			        "JOIN actividad a ON p.id_actividad = a.id " +
			        "WHERE r.id_usuario = ? AND estado = ?"
			    );

			    stmt2.setInt(1, usuario.getId());
			    stmt2.setString(2, "finalizada");

			    ResultSet rs2 = stmt2.executeQuery();
			    while (rs2.next()) {
			        Actividad actividad2 = new Actividad();
			        actividad2.setId(rs2.getInt("id_actividad"));
			        actividad2.setNombre(rs2.getString("actividad_nombre"));
			        actividad2.setCategoria(rs2.getString("categoria"));
			        actividad2.setRiesgo(rs2.getString("riesgo"));

			        Hotel hotel2 = new Hotel();
			        hotel2.setId(rs2.getInt("id_hotel"));
			        hotel2.setNombre(rs2.getString("hotel_nombre"));
			        hotel2.setDireccion(rs2.getString("direccion"));
			        hotel2.setProvincia(rs2.getString("provincia"));

			        Paquete paquete2 = new Paquete();
			        paquete2.setId(rs2.getInt("id_paquete"));
			        paquete2.setInicioDate(rs2.getDate("fecha_inicio").toLocalDate());
			        paquete2.setFinDate(rs2.getDate("fecha_fin").toLocalDate());
			        paquete2.setPrecio(rs2.getDouble("precio"));
			        paquete2.setHotel(hotel2);
			        paquete2.setActividad(actividad2);

			        Reserva r = new Reserva();
			        r.setId(rs2.getInt("id_reserva"));
			        r.setCliente(cliente);
			        r.setUsuario(usuario);
			        r.setPaquete(paquete2);
			        r.setEstado(rs2.getString("estado"));

			        cliente.getReservasPasadas().add(r);
			    }

			    rs.close();
			    stmt.close();
			    rs2.close();
			    stmt2.close();

		    } catch (SQLException e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Error al cargar las reservas: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		    }
			
			
			
		}
		
		//Cargar reseñas existentes
		public static void cargarReviewsExistentes(Usuario usuario, Cliente cliente) {
				try {
					PreparedStatement stmt = (PreparedStatement) conx.prepareStatement(
							   "SELECT " +
									    // Review
									    "r.id AS id_review, r.descripcion AS descripcion_review, r.puntaje AS puntaje_review, r.id_usuario AS id_usuario_review, r.id_hotel AS id_hotel_review, r.id_reserva AS id_reserva_review, " +
									    // Reserva
									    "re.id_paquete AS id_paquete_reserva, re.estado AS estado_reserva, " +
									    // Paquete
									    "p.id AS id_paquete, p.fecha_inicio AS inicio_paquete, p.fecha_fin AS fin_paquete, p.precio AS precio_paquete, p.id_hotel AS id_hotel_paquete, p.id_habitacion AS id_habitacion_paquete, p.id_actividad AS id_actividad_paquete, " +
									    // Actividad
									    "a.id AS id_actividad, a.nombre AS nombre_actividad, a.categoria AS categoria_actividad, a.locacion AS locacion_actividad, a.edad_minima, a.edad_maxima, a.precio AS precio_actividad, a.duracion, a.fecha_inicio AS inicio_actividad, a.fecha_fin AS fin_actividad, a.id_hotel AS id_hotel_actividad, a.riesgo, " +
									    // Hotel
									    "h.nombre AS nombre_hotel, h.provincia AS provincia_hotel, h.direccion AS direccion_hotel, h.cant_habitaciones, h.calificacion_promedio " +
									    "FROM review r " +
									    "INNER JOIN reserva re ON r.id_reserva = re.id " +
									    "INNER JOIN paquete p ON re.id_paquete = p.id " +
									    "INNER JOIN actividad a ON p.id_actividad = a.id " +
									    "INNER JOIN hotel h ON p.id_hotel = h.id " +
									    "WHERE r.id_usuario = ?"
						);

						stmt.setInt(1, usuario.getId());
						ResultSet rs = stmt.executeQuery();


						while (rs.next()) {
							Hotel hotel = new Hotel();
							hotel.setNombre(rs.getString("nombre_hotel"));
							hotel.setProvincia(rs.getString("provincia_hotel"));
							hotel.setDireccion(rs.getString("direccion_hotel"));
							
							   Actividad actividad = new Actividad();
							    actividad.setId(rs.getInt("id_actividad"));
							    actividad.setNombre(rs.getString("nombre_actividad"));
							    actividad.setCategoria(rs.getString("categoria_actividad"));
							    actividad.setLocacion(rs.getString("locacion_actividad"));
							    actividad.setEdad_minima(rs.getInt("edad_minima"));
							    actividad.setEdad_maxima(rs.getInt("edad_maxima"));
							    actividad.setPrecio(rs.getDouble("precio_actividad"));
							    actividad.setDuracion(rs.getInt("duracion"));
							    actividad.setInicioDate(rs.getDate("inicio_actividad").toLocalDate());
							    actividad.setFinDate(rs.getDate("fin_actividad").toLocalDate());
							    actividad.setHotel(hotel);
							    actividad.setRiesgo(rs.getString("riesgo"));
							
							Paquete paquete = new Paquete();
							paquete.setId(rs.getInt("id_paquete"));
							paquete.setInicioDate(rs.getDate("inicio_paquete").toLocalDate());
							paquete.setFinDate(rs.getDate("fin_paquete").toLocalDate());
							paquete.setPrecio(rs.getDouble("precio_paquete"));
							paquete.setHotel(hotel);
							paquete.setActividad(actividad);
							  
							
							Reserva reserva = new Reserva();
							reserva.setEstado(rs.getString("estado_reserva"));
							reserva.setCliente(cliente);
							reserva.setId(rs.getInt("id_reserva_review"));
							reserva.setPaquete(paquete);
							
							
						    Review review = new Review();
						    review.setDescripcion(rs.getString("descripcion_review"));
						    review.setPuntaje(rs.getInt("puntaje_review"));
						    review.setCliente(cliente);
						    review.setReserva(reserva);
						    review.setHotel(hotel);
						    review.setId(rs.getInt("id_review"));
     
					        	 cliente.getReviews().add(review);
					        	 
						    }

						    
				} catch (Exception e) {
			        e.printStackTrace();
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

				    String texto = "Duracion: " + duracion
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
		
		
		//Cancelar reserva
		public static boolean cancelarReserva(Usuario usuario, Cliente cliente, Reserva reserva) {
		    try {
		        PreparedStatement stmt = (PreparedStatement) conx.prepareStatement("DELETE FROM reserva WHERE id = ?");
		        stmt.setInt(1, reserva.getId());
		        int filas = stmt.executeUpdate();
		        boolean eliminado = (filas > 0);
		        
		        if (eliminado) {
		            PreparedStatement stmtUpdate = (PreparedStatement) conx.prepareStatement(
		                "UPDATE paquete SET cupo_actual = cupo_actual + 1 WHERE id = ?"
		            );
		            stmtUpdate.setInt(1, reserva.getPaquete().getId());
		            stmtUpdate.executeUpdate();
		            
		            cliente.getReservas().clear();
		            cliente.getReservasPasadas().clear();
		            DtoCliente.cargarReservasExistentes(usuario, cliente);
		        }
		        
		        return eliminado;
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		    return false;
		}
		
		//Escribir reseña
		public static boolean escribirReview(Usuario usuario, Cliente cliente, Reserva reservaSeleccionada, String descripcion, double puntaje) {
			try {
				PreparedStatement stmt = (PreparedStatement) conx.prepareStatement(
		                "INSERT INTO review (descripcion, puntaje, id_usuario, id_hotel, id_reserva) VALUES (?, ?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS
					 );
				
				stmt.setString(1, descripcion);
				stmt.setDouble(2, puntaje);
				stmt.setInt(3, usuario.getId());
				stmt.setInt(4, reservaSeleccionada.getPaquete().getHotel().getId());
				stmt.setInt(5, reservaSeleccionada.getId());

				
		        int filas = stmt.executeUpdate();

		        if (filas > 0) {
		        	  ResultSet generatedKeys = stmt.getGeneratedKeys();
		        	    int idReview = 0;
		        	    if (generatedKeys.next()) {
		        	        idReview = generatedKeys.getInt(1);
		        	    }
		        	
		        	 Review review = new Review();
		        	 review.setCliente(cliente);
		        	 review.setDescripcion(descripcion);
		        	 review.setHotel(reservaSeleccionada.getPaquete().getHotel());
		        	 review.setPuntaje(puntaje);
		        	 review.setReserva(reservaSeleccionada);
		        	 review.setId(idReview);		        	 
		        	 cliente.getReviews().add(review);
		        	
		        		return true;
		        		
		        } else {
		        		return false;
		        }
		        
		        
				
			} catch (Exception e) {
		        e.printStackTrace();
			}
			
			return false;
		}
		
		//Chequear review existente
		public static boolean reviewExistente(Usuario usuario, Reserva reservaSeleccionada) {
			try {
				PreparedStatement stmtCheck = (PreparedStatement) conx.prepareStatement(
					    "SELECT * FROM review WHERE id_usuario = ? AND id_reserva = ?"
					);
					stmtCheck.setInt(1, usuario.getId());
					stmtCheck.setInt(2, reservaSeleccionada.getId());

					ResultSet rs = stmtCheck.executeQuery();

					if (rs.next()) {
						return true;
					} else {
						return false;
					}
			} catch (Exception e) {
		        e.printStackTrace();
			}
			
			return false;
		}
		
		//Borrar reseña
		public static boolean borrarReview(Usuario usuario, Cliente cliente, Review reviewSeleccionada) {
			try {
				  PreparedStatement stmt = (PreparedStatement) conx.prepareStatement("DELETE FROM review WHERE id = ?");
			        stmt.setInt(1, reviewSeleccionada.getId());
			        int filas = stmt.executeUpdate();
			        boolean eliminado = (filas > 0);
			        
			        cliente.getReviews().clear();
			        DtoCliente.cargarReviewsExistentes(usuario, cliente);
			        
			        return eliminado;
				
			} catch (Exception e) {
	            e.printStackTrace();
			}
			
			return false;
		}
	
		
		
		
		
}//fin
