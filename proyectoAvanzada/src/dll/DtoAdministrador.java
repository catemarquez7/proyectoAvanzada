package dll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import bll.Actividad;
import bll.Cliente;
import bll.Habitacion;
import bll.Hotel;
import bll.Paquete;
import bll.Reserva;
import bll.Usuario;

public class DtoAdministrador {

    private static Connection conx = Conexion.getInstance().getConnection();

    // =============== VER HOTELES ===============
    public static List<Hotel> verHoteles() {
        List<Hotel> hoteles = new ArrayList<>();
        
        try {
            PreparedStatement stmt = conx.prepareStatement("SELECT * FROM hotel");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String provincia = rs.getString("provincia");
                String direccion = rs.getString("direccion");
                int cantHabitaciones = rs.getInt("cant_habitaciones");
                double calificacion = rs.getDouble("calificacion_promedio");
                
                Hotel hotel = new Hotel(id, nombre, provincia, direccion, cantHabitaciones, calificacion);
                hoteles.add(hotel);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar hoteles: " + e.getMessage(), "ERROR", 0);
        }
        
        return hoteles;
    }

    // =============== MODIFICAR HOTEL ===============
    public static boolean modificarHotel(int idHotel, String nuevoNombre) {
        try {
            PreparedStatement stmt = conx.prepareStatement(
                "UPDATE hotel SET nombre = ? WHERE id = ?");
            stmt.setString(1, nuevoNombre);
            stmt.setInt(2, idHotel);
            
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Hotel modificado exitosamente", "ÉXITO", 1);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el hotel con ID: " + idHotel, "ERROR", 0);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al modificar hotel: " + e.getMessage(), "ERROR", 0);
        }
        return false;
    }

    // =============== VER RESERVAS ===============
    public static List<Reserva> verReservas() {
        List<Reserva> reservas = new ArrayList<>();
        
        try {
            PreparedStatement stmt = conx.prepareStatement(
                "SELECT r.*, u.*, p.*, h.*, hab.*, act.* " +
                "FROM reserva r " +
                "INNER JOIN usuario u ON r.id_usuario = u.id " +
                "INNER JOIN paquete p ON r.id_paquete = p.id " +
                "INNER JOIN hotel h ON p.id_hotel = h.id " +
                "LEFT JOIN habitacion hab ON p.id_habitacion = hab.id " +
                "LEFT JOIN actividad act ON p.id_actividad = act.id"
            );
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                // Datos de la reserva
                int idReserva = rs.getInt("r.id");
                String estadoReserva = rs.getString("r.estado");
                double montoFinal = rs.getDouble("r.monto_final");
                String tarjeta = rs.getString("r.tarjeta_resguardo");
                
                LocalDateTime checkin = null;
                LocalDateTime checkout = null;
                if (rs.getTimestamp("r.fecha_checkin") != null) {
                    checkin = rs.getTimestamp("r.fecha_checkin").toLocalDateTime();
                }
                if (rs.getTimestamp("r.fecha_checkout") != null) {
                    checkout = rs.getTimestamp("r.fecha_checkout").toLocalDateTime();
                }
                
                // Datos del cliente
                int idUsuario = rs.getInt("u.id");
                String nombre = rs.getString("u.nombre");
                String apellido = rs.getString("u.apellido");
                LocalDate fechaNac = rs.getDate("u.fecha_nac").toLocalDate();
                String mail = rs.getString("u.mail");
                int dni = rs.getInt("u.dni");
                String direccion = rs.getString("u.direccion");
                String user = rs.getString("u.user");
                String pass = rs.getString("u.pass");
                String pregunta = rs.getString("u.pregunta");
                String respuesta = rs.getString("u.respuesta");
                LocalDate fechaCreacion = rs.getDate("u.fecha_creacion").toLocalDate();
                String tipoUsuario = rs.getString("u.tipo_usuario");
                String estadoUsuario = rs.getString("u.estado");
                
                Cliente cliente = new Cliente(nombre, apellido, fechaNac, mail, dni, direccion,
                    idUsuario, user, pass, pregunta, respuesta, fechaCreacion, tipoUsuario,
                    estadoUsuario, new LinkedList<>());
                
                // Datos del hotel
                int idHotel = rs.getInt("h.id");
                String nombreHotel = rs.getString("h.nombre");
                String provincia = rs.getString("h.provincia");
                String direccionHotel = rs.getString("h.direccion");
                int cantHabitaciones = rs.getInt("h.cant_habitaciones");
                double calificacion = rs.getDouble("h.calificacion_promedio");
                
                Hotel hotel = new Hotel(idHotel, nombreHotel, provincia, direccionHotel, 
                    cantHabitaciones, calificacion);
                
                // Datos de la habitación
                Habitacion habitacion = null;
                if (rs.getObject("hab.id") != null) {
                    int idHab = rs.getInt("hab.id");
                    int numeroHab = rs.getInt("hab.numero");
                    String estadoHab = rs.getString("hab.estado");
                    String tipoHab = rs.getString("hab.tipo");
                    double precioHab = rs.getDouble("hab.precio");
                    int cantCamas = rs.getInt("hab.cant_camas");
                    
                    habitacion = new Habitacion(hotel, idHab, numeroHab, estadoHab, 
                        tipoHab, precioHab, cantCamas);
                }
                
                // Datos de la actividad
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
                    
                    actividad = new Actividad(idAct, nombreAct, categoria, edadMin, edadMax,
                        duracion, precioAct, locacion, inicioAct, finAct, hotel, riesgo);
                }
                
                // Datos del paquete
                int idPaquete = rs.getInt("p.id");
                LocalDate inicioPaq = rs.getDate("p.fecha_inicio").toLocalDate();
                LocalDate finPaq = rs.getDate("p.fecha_fin").toLocalDate();
                double precioPaq = rs.getDouble("p.precio");
                
                Paquete paquete = new Paquete(idPaquete, inicioPaq, finPaq, precioPaq, 
                    hotel, habitacion, actividad);
                
                // Crear reserva
                Reserva reserva = new Reserva(idReserva, cliente, paquete, estadoReserva,
                    checkin, checkout, tarjeta, montoFinal);
                
                reservas.add(reserva);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar reservas: " + e.getMessage(), "ERROR", 0);
        }
        
        return reservas;
    }

    // =============== VER PAQUETES ===============
    public static List<Paquete> verPaquetes() {
        List<Paquete> paquetes = new ArrayList<>();
        
        try {
            PreparedStatement stmt = conx.prepareStatement(
                "SELECT p.*, h.*, hab.*, act.* " +
                "FROM paquete p " +
                "INNER JOIN hotel h ON p.id_hotel = h.id " +
                "LEFT JOIN habitacion hab ON p.id_habitacion = hab.id " +
                "LEFT JOIN actividad act ON p.id_actividad = act.id"
            );
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                // Datos del paquete
                int idPaquete = rs.getInt("p.id");
                LocalDate inicioPaq = rs.getDate("p.fecha_inicio").toLocalDate();
                LocalDate finPaq = rs.getDate("p.fecha_fin").toLocalDate();
                double precioPaq = rs.getDouble("p.precio");
                
                // Datos del hotel
                int idHotel = rs.getInt("h.id");
                String nombreHotel = rs.getString("h.nombre");
                String provincia = rs.getString("h.provincia");
                String direccionHotel = rs.getString("h.direccion");
                int cantHabitaciones = rs.getInt("h.cant_habitaciones");
                double calificacion = rs.getDouble("h.calificacion_promedio");
                
                Hotel hotel = new Hotel(idHotel, nombreHotel, provincia, direccionHotel, 
                    cantHabitaciones, calificacion);
                
                // Datos de la habitación
                Habitacion habitacion = null;
                if (rs.getObject("hab.id") != null) {
                    int idHab = rs.getInt("hab.id");
                    int numeroHab = rs.getInt("hab.numero");
                    String estadoHab = rs.getString("hab.estado");
                    String tipoHab = rs.getString("hab.tipo");
                    double precioHab = rs.getDouble("hab.precio");
                    int cantCamas = rs.getInt("hab.cant_camas");
                    
                    habitacion = new Habitacion(hotel, idHab, numeroHab, estadoHab, 
                        tipoHab, precioHab, cantCamas);
                }
                
                // Datos de la actividad
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
                    
                    actividad = new Actividad(idAct, nombreAct, categoria, edadMin, edadMax,
                        duracion, precioAct, locacion, inicioAct, finAct, hotel, riesgo);
                }
                
                // Crear paquete
                Paquete paquete = new Paquete(idPaquete, inicioPaq, finPaq, precioPaq, 
                    hotel, habitacion, actividad);
                
                paquetes.add(paquete);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar paquetes: " + e.getMessage(), "ERROR", 0);
        }
        
        return paquetes;
    }


    
    // Ver todas las cuentas de usuario
    public static List<Usuario> verCuentas() {
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            PreparedStatement stmt = conx.prepareStatement("SELECT * FROM usuario");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                LocalDate fechaNac = rs.getDate("fecha_nac").toLocalDate();
                String mail = rs.getString("mail");
                int dni = rs.getInt("dni");
                String direccion = rs.getString("direccion");
                String user = rs.getString("user");
                String pass = rs.getString("pass");
                String pregunta = rs.getString("pregunta");
                String respuesta = rs.getString("respuesta");
                LocalDate fechaCreacion = rs.getDate("fecha_creacion").toLocalDate();
                String tipoUsuario = rs.getString("tipo_usuario");
                String estado = rs.getString("estado");
                
                Usuario usuario = new Usuario(nombre, apellido, fechaNac, mail, dni, direccion, 
                                              id, user, pass, pregunta, respuesta, fechaCreacion, 
                                              tipoUsuario, estado);
                usuarios.add(usuario);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar cuentas: " + e.getMessage(), "ERROR", 0);
        }
        
        return usuarios;
    }

    // Bloquear cuenta de usuario
    public static boolean bloquearCuenta(int idUsuario) {
        try {
            PreparedStatement stmt = conx.prepareStatement(
                "UPDATE usuario SET estado = 'bloqueado' WHERE id = ?");
            stmt.setInt(1, idUsuario);
            
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Cuenta bloqueada exitosamente", "ÉXITO", 1);
                return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al bloquear cuenta: " + e.getMessage(), "ERROR", 0);
        }
        return false;
    }

    // Desbloquear cuenta de usuario
    public static boolean desbloquearCuenta(int idUsuario) {
        try {
            PreparedStatement stmt = conx.prepareStatement(
                "UPDATE usuario SET estado = 'activo' WHERE id = ?");
            stmt.setInt(1, idUsuario);
            
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Cuenta desbloqueada exitosamente", "ÉXITO", 1);
                return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al desbloquear cuenta: " + e.getMessage(), "ERROR", 0);
        }
        return false;
    }

    // Eliminar cuenta de usuario
    public static boolean eliminarCuenta(int idUsuario) {
        try {
            PreparedStatement stmt = conx.prepareStatement(
                "DELETE FROM usuario WHERE id = ?");
            stmt.setInt(1, idUsuario);
            
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Cuenta eliminada exitosamente", "ÉXITO", 1);
                return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar cuenta: " + e.getMessage(), "ERROR", 0);
        }
        return false;
    }

    // Obtener estadísticas del sistema
    public static String obtenerEstadisticas() {
        StringBuilder stats = new StringBuilder();
        
        try {
            // Total de usuarios
            PreparedStatement stmt1 = conx.prepareStatement("SELECT COUNT(*) as total FROM usuario");
            ResultSet rs1 = stmt1.executeQuery();
            if (rs1.next()) {
                stats.append("Total de usuarios: ").append(rs1.getInt("total")).append("\n");
            }
            
            // Total de clientes
            PreparedStatement stmt2 = conx.prepareStatement("SELECT COUNT(*) as total FROM usuario WHERE tipo_usuario = '1'");
            ResultSet rs2 = stmt2.executeQuery();
            if (rs2.next()) {
                stats.append("Clientes: ").append(rs2.getInt("total")).append("\n");
            }
            
            // Total de encargados
            PreparedStatement stmt3 = conx.prepareStatement("SELECT COUNT(*) as total FROM usuario WHERE tipo_usuario = '2'");
            ResultSet rs3 = stmt3.executeQuery();
            if (rs3.next()) {
                stats.append("Encargados: ").append(rs3.getInt("total")).append("\n");
            }
            
            // Total de administradores
            PreparedStatement stmt4 = conx.prepareStatement("SELECT COUNT(*) as total FROM usuario WHERE tipo_usuario = '3'");
            ResultSet rs4 = stmt4.executeQuery();
            if (rs4.next()) {
                stats.append("Administradores: ").append(rs4.getInt("total")).append("\n");
            }
            
            // Total de reservas
            PreparedStatement stmt5 = conx.prepareStatement("SELECT COUNT(*) as total FROM reserva");
            ResultSet rs5 = stmt5.executeQuery();
            if (rs5.next()) {
                stats.append("Total de reservas: ").append(rs5.getInt("total")).append("\n");
            }
            
            // Total de hoteles
            PreparedStatement stmt6 = conx.prepareStatement("SELECT COUNT(*) as total FROM hotel");
            ResultSet rs6 = stmt6.executeQuery();
            if (rs6.next()) {
                stats.append("Total de hoteles: ").append(rs6.getInt("total")).append("\n");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener estadísticas: " + e.getMessage();
        }
        
        return stats.toString();
    }

}