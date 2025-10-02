package ui;

import javax.swing.JOptionPane;

import bll.Usuario;
import dll.Conexion;

public class Main {

	public static void main(String[] args) {
		
		Conexion.getInstance();
		int eleccion;
		
		JOptionPane.showMessageDialog(null, "Bienvenido al Sistema de House Hunter!", "BIENVENIDO!", 0);
		
		do {	
			
			eleccion = JOptionPane.showOptionDialog(null, "Seleccione: ", "INICIO DE SESION", 0, 0, null, repository.Inicio.values(), repository.Inicio.values());
			
		switch (eleccion) {
		case 0: //iniciar sesion
			
			break;
		case 1: //registrarse
			
			JOptionPane.showMessageDialog(null, Usuario.registrarse()==true?"Agregado correctamente":"No se pudo agregar");
			
			break;
		case 2: //salir
			JOptionPane.showMessageDialog(null, "Hasta luego! ", "ADIOS!", 0);
			break;
			
		}//fin switch
		
		} while (eleccion != 2);
	}
}//fin main
