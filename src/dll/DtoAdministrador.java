package dll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// import javax.swing.JOptionPane; // Ya no es necesario para mensajes de éxito

import bll.Actividad;
import bll.Cliente;
import bll.Habitacion;
import bll.Hotel;
import bll.Paquete;
import bll.Reserva;
import bll.Usuario;

public class DtoAdministrador {

    private static Connection conx = Conexion.getInstance().getConnection();

    // ========================================================================
    //                               HOTELES
    // ========================================================================

    // Ver hoteles
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
                // Asegúrate de que tu constructor de Hotel coincida con los datos que recuperas
                // Si tienes cupo_maximo en la clase Hotel, añádelo aquí.
                int cupoMax = rs.getInt("cupo_maximo"); 

                Hotel hotel = new Hotel(id, nombre, provincia, direccion, cantHabitaciones, calificacion);
                // Si tienes setter para cupo: hotel.setCupoMaximo(cupoMax);
                hotel.setCupo_maximo(cupoMax); // Asumiendo que existe el setter
                
                hoteles.add(hotel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hoteles;
    }

    // Crear Hotel
    public static boolean crearHotel(String nombre, String provincia, String direccion, int cantHabitaciones, int cupoMaximo) {
        try {
            PreparedStatement stmt = conx.prepareStatement(
                "INSERT INTO hotel (nombre, provincia, direccion, cant_habitaciones, calificacion_promedio, cupo_actual, cupo_maximo) " +
                "VALUES (?, ?, ?, ?, 0.0, 0, ?)");
            
            stmt.setString(1, nombre);
            stmt.setString(2, provincia);
            stmt.setString(3, direccion);
            stmt.setInt(4, cantHabitaciones);
            stmt.setInt(5, cupoMaximo);
            
            int filas = stmt.executeUpdate();
            return filas > 0; // Retorna true si se insertó, la UI mostrará el mensaje verde.
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Modificar Hotel (Método antiguo, por nombre)
    public static boolean modificarHotel(int idHotel, String nuevoNombre) {
        try {
            PreparedStatement stmt = conx.prepareStatement("UPDATE hotel SET nombre = ? WHERE id = ?");
            stmt.setString(1, nuevoNombre);
            stmt.setInt(2, idHotel);

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // *** NUEVO MÉTODO: Modificar campo específico del hotel (Para el desplegable) ***
    public static boolean modificarHotelCampo(int idHotel, String campoIU, Object nuevoValor) {
        String columnaDb = "";
        
        // Mapeamos lo que dice el Combo ("Nombre") al nombre de la columna en BD ("nombre")
        switch (campoIU) {
            case "Nombre": columnaDb = "nombre"; break;
            case "Provincia": columnaDb = "provincia"; break;
            case "Dirección": columnaDb = "direccion"; break;
            case "Cant. Habitaciones": columnaDb = "cant_habitaciones"; break;
            case "Cupo Máximo": columnaDb = "cupo_maximo"; break;
            default: return false;
        }

        try {
            String sql = "UPDATE hotel SET " + columnaDb + " = ? WHERE id = ?";
            PreparedStatement stmt = conx.prepareStatement(sql);

            if (nuevoValor instanceof Integer) {
                stmt.setInt(1, (Integer) nuevoValor);
            } else if (nuevoValor instanceof Double) {
                stmt.setDouble(1, (Double) nuevoValor);
            } else {
                stmt.setString(1, String.valueOf(nuevoValor));
            }

            stmt.setInt(2, idHotel);

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar hotel
    public static boolean eliminarHotel(int idHotel) {
        try {
            PreparedStatement stmt = conx.prepareStatement("DELETE FROM hotel WHERE id = ?");
            stmt.setInt(1, idHotel);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========================================================================
    //                               PAQUETES
    // ========================================================================

    public static List<Paquete> verPaquetes(int idHotel) {
        List<Paquete> paquetes = new ArrayList<>();
        try {
            PreparedStatement stmt = conx.prepareStatement(
                    "SELECT p.*, h.*, hab.*, act.* " + "FROM paquete p " + "INNER JOIN hotel h ON p.id_hotel = h.id "
                            + "LEFT JOIN habitacion hab ON p.id_habitacion = hab.id "
                            + "LEFT JOIN actividad act ON p.id_actividad = act.id " + "WHERE p.id_hotel = ?");

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
                Hotel hotel = new Hotel(idHotelRes, nombreHotel, provincia, direccionHotel, cantHabitaciones, calificacion);

                // Habitacion
                Habitacion habitacion = null;
                if (rs.getObject("hab.id") != null) {
                    habitacion = new Habitacion(hotel, rs.getInt("hab.id"), rs.getInt("hab.numero"), 
                            rs.getString("hab.estado"), rs.getString("hab.tipo"), 
                            rs.getDouble("hab.precio"), rs.getInt("hab.cant_camas"));
                }

                // Actividad
                Actividad actividad = null;
                if (rs.getObject("act.id") != null) {
                    actividad = new Actividad(rs.getInt("act.id"), rs.getString("act.nombre"), rs.getString("act.categoria"),
                            rs.getInt("act.edad_minima"), rs.getInt("act.edad_maxima"), rs.getDouble("act.duracion"),
                            rs.getDouble("act.precio"), rs.getString("act.locacion"), 
                            rs.getDate("act.fecha_inicio").toLocalDate(), rs.getDate("act.fecha_fin").toLocalDate(), 
                            hotel, rs.getString("act.riesgo"));
                }

                Paquete paquete = new Paquete(idPaquete, inicioPaq, finPaq, precioPaq, hotel, habitacion, actividad, precioPaq, null, cupo_actual, cupo_maximo);
                paquetes.add(paquete);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paquetes;
    }

    public static boolean crearPaquete(LocalDate fechaInicio, LocalDate fechaFin, double precio, 
                                     int idHotel, int idActividad, int cupoMaximo) {
        try {
            PreparedStatement stmt = conx.prepareStatement(
                "INSERT INTO paquete (fecha_inicio, fecha_fin, precio, id_hotel, id_habitacion, id_actividad, " +
                "precio_original, id_promocion, cupo_actual, cupo_maximo) " +
                "VALUES (?, ?, ?, ?, NULL, ?, ?, NULL, 0, ?)");
            
            stmt.setDate(1, java.sql.Date.valueOf(fechaInicio));
            stmt.setDate(2, java.sql.Date.valueOf(fechaFin));
            stmt.setDouble(3, precio);
            stmt.setInt(4, idHotel);
            stmt.setInt(5, idActividad);
            stmt.setDouble(6, precio); 
            stmt.setInt(7, cupoMaximo);
            
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean modificarPaquete(int idPaquete, LocalDate fechaInicio, LocalDate fechaFin, double precio,
            int idHotel, Integer idHabitacion, Integer idActividad) {
        try {
            PreparedStatement stmt = conx.prepareStatement("UPDATE paquete SET fecha_inicio = ?, fecha_fin = ?, precio = ?, "
                            + "id_hotel = ?, id_habitacion = ?, id_actividad = ? WHERE id = ?");

            stmt.setDate(1, java.sql.Date.valueOf(fechaInicio));
            stmt.setDate(2, java.sql.Date.valueOf(fechaFin));
            stmt.setDouble(3, precio);
            stmt.setInt(4, idHotel);

            if (idHabitacion != null) stmt.setInt(5, idHabitacion);
            else stmt.setNull(5, java.sql.Types.INTEGER);

            if (idActividad != null) stmt.setInt(6, idActividad);
            else stmt.setNull(6, java.sql.Types.INTEGER);

            stmt.setInt(7, idPaquete);

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========================================================================
    //                             ACTIVIDADES
    // ========================================================================

    public static List<Actividad> obtenerActividadesPorHotel(int idHotel) {
        List<Actividad> actividades = new ArrayList<>();
        try {
            PreparedStatement stmt = conx.prepareStatement("SELECT a.*, h.nombre as nombre_hotel FROM actividad a "
                    + "INNER JOIN hotel h ON a.id_hotel = h.id WHERE a.id_hotel = ?");
            stmt.setInt(1, idHotel);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Hotel hotel = new Hotel();
                hotel.setId(idHotel);
                hotel.setNombre(rs.getString("nombre_hotel"));

                Actividad act = new Actividad(rs.getInt("id"), rs.getString("nombre"), rs.getString("categoria"),
                        rs.getInt("edad_minima"), rs.getInt("edad_maxima"), rs.getDouble("duracion"),
                        rs.getDouble("precio"), rs.getString("locacion"), rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_fin").toLocalDate(), hotel, rs.getString("riesgo"));
                actividades.add(act);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return actividades;
    }

    public static boolean crearActividad(String nombre, String categoria, String locacion, 
                                       int edadMinima, int edadMaxima, double precio, 
                                       double duracion, LocalDate fechaInicio, LocalDate fechaFin, 
                                       int idHotel, String riesgo) {
        try {
            PreparedStatement stmt = conx.prepareStatement(
                "INSERT INTO actividad (nombre, categoria, locacion, edad_minima, edad_maxima, " +
                "precio, duracion, fecha_inicio, fecha_fin, id_hotel, riesgo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            stmt.setString(1, nombre);
            stmt.setString(2, categoria);
            stmt.setString(3, locacion);
            stmt.setInt(4, edadMinima);
            stmt.setInt(5, edadMaxima);
            stmt.setDouble(6, precio);
            stmt.setDouble(7, duracion);
            stmt.setDate(8, java.sql.Date.valueOf(fechaInicio));
            stmt.setDate(9, java.sql.Date.valueOf(fechaFin));
            stmt.setInt(10, idHotel);
            stmt.setString(11, riesgo);
            
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========================================================================
    //                               RESERVAS
    // ========================================================================

    public static List<Reserva> verReservas(int idHotel) {
        List<Reserva> reservas = new ArrayList<>();
        try {
            PreparedStatement stmt = conx.prepareStatement("SELECT r.*, u.*, p.*, h.*, hab.*, act.* "
                    + "FROM reserva r " + "INNER JOIN usuario u ON r.id_usuario = u.id "
                    + "INNER JOIN paquete p ON r.id_paquete = p.id " + "INNER JOIN hotel h ON p.id_hotel = h.id "
                    + "LEFT JOIN habitacion hab ON p.id_habitacion = hab.id "
                    + "LEFT JOIN actividad act ON p.id_actividad = act.id " + "WHERE h.id = ?");

            stmt.setInt(1, idHotel);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // ... (Lógica de extracción de Reserva, Usuario, Hotel, Paquete, etc.) ...
                // Para simplificar el copiado, mantengo la lógica exacta que tenías
                int idReserva = rs.getInt("r.id");
                String estadoReserva = rs.getString("r.estado");
                double montoFinal = rs.getDouble("r.monto_final");
                String tarjeta = rs.getString("r.tarjeta_resguardo");
                LocalDateTime checkin = rs.getTimestamp("r.fecha_checkin") != null ? rs.getTimestamp("r.fecha_checkin").toLocalDateTime() : null;
                LocalDateTime checkout = rs.getTimestamp("r.fecha_checkout") != null ? rs.getTimestamp("r.fecha_checkout").toLocalDateTime() : null;

                // User
                int idUsuario = rs.getInt("u.id");
                String nombre = rs.getString("u.nombre");
                String apellido = rs.getString("u.apellido");
                // ... resto de campos de usuario
                Cliente cliente = new Cliente(nombre, apellido, rs.getDate("u.fecha_nac").toLocalDate(), 
                        rs.getString("u.mail"), rs.getInt("u.dni"), rs.getString("u.direccion"), 
                        idUsuario, rs.getString("u.user"), rs.getString("u.pass"), rs.getString("u.pregunta"), 
                        rs.getString("u.respuesta"), rs.getDate("u.fecha_creacion").toLocalDate(), 
                        rs.getString("u.tipo_usuario"), rs.getString("u.estado"), new LinkedList<>());

                // Hotel y Paquete
                Hotel hotel = new Hotel(rs.getInt("h.id"), rs.getString("h.nombre"), rs.getString("h.provincia"), 
                        rs.getString("h.direccion"), rs.getInt("h.cant_habitaciones"), rs.getDouble("h.calificacion_promedio"));
                
                Paquete paquete = new Paquete(rs.getInt("p.id"), rs.getDate("p.fecha_inicio").toLocalDate(), 
                        rs.getDate("p.fecha_fin").toLocalDate(), rs.getDouble("p.precio"), hotel, null, null, 
                        rs.getDouble("p.precio"), null, rs.getInt("p.cupo_actual"), rs.getInt("p.cupo_maximo"));

                Reserva reserva = new Reserva(idReserva, cliente, paquete, estadoReserva, checkin, checkout, tarjeta, montoFinal);
                reservas.add(reserva);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservas;
    }

    public static boolean modificarReserva(int idReserva, int idUsuario, int idPaquete, String estado,
            LocalDateTime fechaCheckin, LocalDateTime fechaCheckout, String tarjeta, double montoFinal) {
        try {
            PreparedStatement stmt = conx.prepareStatement("UPDATE reserva SET id_usuario = ?, id_paquete = ?, estado = ?, "
                            + "fecha_checkin = ?, fecha_checkout = ?, tarjeta_resguardo = ?, monto_final = ? "
                            + "WHERE id = ?");

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idPaquete);
            stmt.setString(3, estado);

            if (fechaCheckin != null) stmt.setTimestamp(4, java.sql.Timestamp.valueOf(fechaCheckin));
            else stmt.setNull(4, java.sql.Types.TIMESTAMP);

            if (fechaCheckout != null) stmt.setTimestamp(5, java.sql.Timestamp.valueOf(fechaCheckout));
            else stmt.setNull(5, java.sql.Types.TIMESTAMP);

            stmt.setString(6, tarjeta);
            stmt.setDouble(7, montoFinal);
            stmt.setInt(8, idReserva);

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========================================================================
    //                               USUARIOS
    // ========================================================================

    public static List<Usuario> verCuentas() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            PreparedStatement stmt = conx.prepareStatement("SELECT * FROM usuario");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getString("nombre"), rs.getString("apellido"), 
                        rs.getDate("fecha_nac").toLocalDate(), rs.getString("mail"), rs.getInt("dni"), 
                        rs.getString("direccion"), rs.getInt("id"), rs.getString("user"), rs.getString("pass"),
                        rs.getString("pregunta"), rs.getString("respuesta"), rs.getDate("fecha_creacion").toLocalDate(), 
                        rs.getString("tipo_usuario"), rs.getString("estado"));
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public static boolean crearUsuario(Usuario usuario) {
        try {
            PreparedStatement statement = conx.prepareStatement(
                    "INSERT INTO usuario (nombre, apellido, fecha_nac, mail, dni, direccion, user, pass, "
                            + "pregunta, respuesta, fecha_creacion, tipo_usuario, estado) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    java.sql.Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setDate(3, java.sql.Date.valueOf(usuario.getFecha_nac()));
            statement.setString(4, usuario.getMail());
            statement.setInt(5, usuario.getDni());
            statement.setString(6, usuario.getDireccion());
            statement.setString(7, usuario.getUser());
            statement.setString(8, repository.Encriptador.encriptar(usuario.getPass()));
            statement.setString(9, usuario.getPregunta());
            statement.setString(10, usuario.getRespuesta());
            statement.setDate(11, java.sql.Date.valueOf(usuario.getFecha_creacion()));
            statement.setString(12, usuario.getTipo_usuario());
            statement.setString(13, usuario.getEstado());

            int filas = statement.executeUpdate();
            if (filas > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean crearEncargado(int idUsuario, int idHotel) {
        try {
            PreparedStatement statement = conx.prepareStatement("INSERT INTO encargado (id_usuario, id_hotel) VALUES (?, ?)");
            statement.setInt(1, idUsuario);
            statement.setInt(2, idHotel);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean bloquearCuenta(int idUsuario) {
        try {
            PreparedStatement stmt = conx.prepareStatement("UPDATE usuario SET estado = 'bloqueado' WHERE id = ?");
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean desbloquearCuenta(int idUsuario) {
        try {
            PreparedStatement stmt = conx.prepareStatement("UPDATE usuario SET estado = 'activo' WHERE id = ?");
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean eliminarCuenta(int idUsuario) {
        try {
            PreparedStatement stmt = conx.prepareStatement("DELETE FROM usuario WHERE id = ?");
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========================================================================
    //                               SISTEMA
    // ========================================================================

    public static String obtenerEstadisticas() {
        StringBuilder stats = new StringBuilder();
        try {
            String[] queries = {
                "SELECT COUNT(*) as total FROM usuario", "Total de usuarios: ",
                "SELECT COUNT(*) as total FROM usuario WHERE tipo_usuario = '1'", "Clientes: ",
                "SELECT COUNT(*) as total FROM usuario WHERE tipo_usuario = '2'", "Encargados: ",
                "SELECT COUNT(*) as total FROM usuario WHERE tipo_usuario = '3'", "Administradores: ",
                "SELECT COUNT(*) as total FROM reserva", "Total de reservas: ",
                "SELECT COUNT(*) as total FROM hotel", "Total de hoteles: "
            };

            for (int i = 0; i < queries.length; i += 2) {
                PreparedStatement stmt = conx.prepareStatement(queries[i]);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    stats.append(queries[i+1]).append(rs.getInt("total")).append("\n");
                }
            }
        } catch (Exception e) {
            return "Error al obtener estadísticas: " + e.getMessage();
        }
        return stats.toString();
    }

    public static void suspenderSistema() {
        try {
            // Asumo que tienes una tabla sistema con id=1 para la config global
            PreparedStatement stmt = conx.prepareStatement("UPDATE sistema SET estado = 0 WHERE id = 1");
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void activarSistema() {
        try {
            PreparedStatement stmt = conx.prepareStatement("UPDATE sistema SET estado = 1 WHERE id = 1");
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Otros métodos de lectura como obtenerHabitacionesPorHotel se mantienen igual
    public static List<Habitacion> obtenerHabitacionesPorHotel(int idHotel) {
        List<Habitacion> habitaciones = new ArrayList<>();
        try {
            PreparedStatement stmt = conx.prepareStatement("SELECT h.*, hot.nombre as nombre_hotel FROM habitacion h "
                    + "INNER JOIN hotel hot ON h.id_hotel = hot.id WHERE h.id_hotel = ?");
            stmt.setInt(1, idHotel);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Hotel hotel = new Hotel();
                hotel.setId(idHotel);
                hotel.setNombre(rs.getString("nombre_hotel"));
                habitaciones.add(new Habitacion(hotel, rs.getInt("id"), rs.getInt("numero"), rs.getString("estado"),
                        rs.getString("tipo"), rs.getDouble("precio"), rs.getInt("cant_camas")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return habitaciones;
    }
}