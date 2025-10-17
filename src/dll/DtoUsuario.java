package dll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import bll.Usuario;
import bll.Administrador;
import repository.Encriptador;

public class DtoUsuario {

    private static Connection conx = Conexion.getInstance().getConnection();

    // Log in
    public static Usuario login(String user, String password) {
        Usuario usuario = null;
        try {
            PreparedStatement stmt = conx.prepareStatement("SELECT * FROM usuario WHERE user = ? AND pass = ?");
            stmt.setString(1, user);
            stmt.setString(2, Encriptador.encriptar(password));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String estado = rs.getString("estado");

                if (estado.equals("bloqueado")) {
                    JOptionPane.showMessageDialog(null,
                            "Su cuenta ha sido bloqueada. Contacte al administrador.",
                            "CUENTA BLOQUEADA",
                            JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                LocalDate fecha_nac = rs.getDate("fecha_nac").toLocalDate();
                String mail = rs.getString("mail");
                int dni = rs.getInt("dni");
                String direccion = rs.getString("direccion");
                String userDB = rs.getString("user");
                String pass = rs.getString("pass");
                String pregunta = rs.getString("pregunta");
                String respuesta = rs.getString("respuesta");
                LocalDate fecha_creacion = rs.getDate("fecha_creacion").toLocalDate();
                String tipo_usuario = rs.getString("tipo_usuario");


                usuario = new Usuario(nombre, apellido, fecha_nac, mail, dni, direccion, id,
                        userDB, pass, pregunta, respuesta, fecha_creacion, tipo_usuario, estado);


                if (tipo_usuario.equals("2")) {
                    return DtoEncargado.cargarEncargado(id);
                }

                else if (tipo_usuario.equals("3")) {
                    return new Administrador(nombre, apellido, fecha_nac, mail, dni, direccion, id,
                            userDB, pass, pregunta, respuesta, fecha_creacion, tipo_usuario, estado);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }// fin login

    // Sign in
    public static boolean agregarUsuario(Usuario usuario) {
        try {
            PreparedStatement statement = conx.prepareStatement(
                    "INSERT INTO usuario (nombre, apellido, fecha_nac, mail, dni, direccion, user, pass, pregunta, respuesta, fecha_creacion, tipo_usuario, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setDate(3, java.sql.Date.valueOf(usuario.getFecha_nac()));
            statement.setString(4, usuario.getMail());
            statement.setInt(5, usuario.getDni());
            statement.setString(6, usuario.getDireccion());
            statement.setString(7, usuario.getUser());
            statement.setString(8, Encriptador.encriptar(usuario.getPass()));
            statement.setString(9, usuario.getPregunta());
            statement.setString(10, usuario.getRespuesta());
            statement.setDate(11, java.sql.Date.valueOf(usuario.getFecha_creacion()));
            statement.setString(12, usuario.getTipo_usuario());
            statement.setString(13, usuario.getEstado());

            int filas = statement.executeUpdate();
            if (filas > 0) {
                System.out.println("Usuario agregado correctamente.");
                JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente", "ÉXITO", 1);
                return true;
            }

        } catch (MySQLIntegrityConstraintViolationException e) {

            String mensaje = e.getMessage();
            if (mensaje.contains("mail")) {
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese mail", "ERROR", 0);
            } else if (mensaje.contains("user")) {
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nombre de usuario", "ERROR", 0);
            } else {
                JOptionPane.showMessageDialog(null, "Datos duplicados en el sistema", "ERROR", 0);
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage(), "ERROR", 0);
            return false;
        }

        return false;
    }// fin sign in

}// fin clase
