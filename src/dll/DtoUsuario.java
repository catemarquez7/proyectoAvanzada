package dll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import bll.Usuario;
import bll.Administrador;
import bll.Cliente;
import repository.Encriptador;

public class DtoUsuario {

	private static Connection conx = Conexion.getInstance().getConnection();

	public static Usuario login(String user, String password) {
		Usuario usuario = null;
		try {
			PreparedStatement stmt = conx.prepareStatement("SELECT * FROM usuario WHERE user = ?");
			stmt.setString(1, user);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				String passBD_Encriptada = rs.getString("pass");

				if (passBD_Encriptada.equals(Encriptador.encriptar(password))) {

					String estado = rs.getString("estado");
					int id = rs.getInt("id");
					String nombre = rs.getString("nombre");
					String apellido = rs.getString("apellido");
					LocalDate fecha_nac = rs.getDate("fecha_nac").toLocalDate();
					String mail = rs.getString("mail");
					int dni = rs.getInt("dni");
					String direccion = rs.getString("direccion");
					String userDB = rs.getString("user");
					String pregunta = rs.getString("pregunta");
					String respuesta = rs.getString("respuesta");
					LocalDate fecha_creacion = rs.getDate("fecha_creacion").toLocalDate();
					String tipo_usuario = rs.getString("tipo_usuario");

					usuario = new Usuario(nombre, apellido, fecha_nac, mail, dni, direccion, id, userDB,
							passBD_Encriptada, pregunta, respuesta, fecha_creacion, tipo_usuario, estado);

					if (tipo_usuario.equals("2")) {
						return DtoEncargado.cargarEncargado(id);
					} else if (tipo_usuario.equals("3")) {
						return new Administrador(nombre, apellido, fecha_nac, mail, dni, direccion, id, userDB,
								passBD_Encriptada, pregunta, respuesta, fecha_creacion, tipo_usuario, estado);
					} else if (tipo_usuario.equals("1")) {
						return new Cliente(nombre, apellido, fecha_nac, mail, dni, direccion, id, userDB, passBD_Encriptada, pregunta, respuesta, fecha_creacion, tipo_usuario, estado, null);
					}
					return usuario;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
	}

	public static boolean usuarioBloqueado(Usuario usuario) {
		try {

			PreparedStatement stmt = conx.prepareStatement("SELECT * FROM usuario WHERE user = ? AND pass = ?");
			stmt.setString(1, usuario.getUser());
			stmt.setString(2, Encriptador.encriptar(usuario.getPass()));

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String estado = rs.getString("estado");
				if (estado.equals("bloqueado")) {
					return false;
				} else {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static String[] busquedaPregunta(String user) {
		try {
			java.sql.PreparedStatement stmt = conx.prepareStatement("SELECT pregunta, respuesta FROM usuario WHERE user = ?");
			stmt.setString(1, user);
			java.sql.ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				String pregunta = rs.getString("pregunta");
				String respuesta = rs.getString("respuesta");
				
				return new String[]{pregunta, respuesta.trim()};
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean actualizarPass(String user, String newPass) {
		try {
			PreparedStatement stmt2 = conx.prepareStatement("UPDATE usuario SET pass = ? WHERE user = ?");
			
			stmt2.setString(1, repository.Encriptador.encriptar(newPass));
			stmt2.setString(2, user);
			
			int rowsUpdated = stmt2.executeUpdate();
			return rowsUpdated > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean chequeoSuspension() {
		try {
			PreparedStatement stmt = conx.prepareStatement("SELECT estado FROM sistema WHERE id = 1");
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int estadoSuspension = rs.getInt("estado");
				if (estadoSuspension == 0) {
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}