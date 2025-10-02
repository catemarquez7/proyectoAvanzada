package dll;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import bll.Usuario;
import repository.Encriptador;

public class DtoUsuario {

	private static Connection conx = Conexion.getInstance().getConnection();
	
	public static boolean agregarUsuario(Usuario usuario) {
	    try {
	        PreparedStatement statement = conx.prepareStatement(
	            "INSERT INTO usuario (nombre, apellido, fecha_nac, mail, dni, direccion, user, pass, pregunta, respuesta, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
	        );
	        
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

	        int filas = statement.executeUpdate();
	        if (filas > 0) {
	            System.out.println("Usuario agregado correctamente.");
	            JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente", "ÉXITO", 1);
	            return true;
	        }
	        
	    } catch (MySQLIntegrityConstraintViolationException e) {
	        // Verifica qué constraint falló
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
	}
	
	
}//fin
