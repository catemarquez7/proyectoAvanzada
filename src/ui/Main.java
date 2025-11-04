package ui;

import javax.swing.JOptionPane;

import bll.Usuario;
import dll.Conexion;
import dll.DtoUsuario;

public class Main {

	public static void main(String[] args) {

		Conexion.getInstance();
		int eleccion;
		Usuario inicio = new Usuario();

		JOptionPane.showMessageDialog(null, "Bienvenido al Sistema de House Hunter!", "BIENVENIDO!", 0);
		
		do {

			eleccion = JOptionPane.showOptionDialog(null, "Seleccione: ", "INICIO DE SESION", 0, 0, null,
					repository.Inicio.values(), repository.Inicio.values());

			switch (eleccion) {
			case 0: // iniciar sesion

				inicio = Usuario.login();

				if (inicio != null) {

					Usuario.redirigir(inicio);

				}
				break;
			case 1: // registrarse

				JOptionPane.showMessageDialog(null,
						Usuario.registrarse() == true ? "Agregado correctamente" : "No se pudo agregar");

				break;
			case 2: // recuperar contraseña

				JOptionPane.showMessageDialog(null,
						DtoUsuario.recuperarPass() == true ? "Su contraseña fue modificada con exito!" : "Su respuesta fue incorrecta. Por favor vuelva a intentarlo");	
				
				break;
				
			case 3: //salir
				
				JOptionPane.showMessageDialog(null, "Hasta luego! ", "ADIOS!", 0);

				
				break;
			}// fin switch

		} while (eleccion != 3);
	}
}// fin main
