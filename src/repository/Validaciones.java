package repository;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public interface Validaciones {

//validacion de mail (caracteres especiales)
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean ValidarMail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.matches();
	}//fin

//validacion de ingreso de numeros (-0, enter, letras)

	public static int ValidarNum(String mensaje) {
		String data="";
		boolean flag;
		int numero = 0;

		do {
			flag = true;
			try {
			do {
				data = (String) JOptionPane.showInputDialog(null, mensaje, "INGRESO", 0);
				if (data.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No puede haber espacios en blanco,\nvuelva a intentarlo",
							"ERROR!", 0);
				}
			} while (data.isEmpty());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Cancelado");
				break;
				// COMPLETAR !!!!
			}
			for (int i = 0; i < data.length(); i++) {
				if (!Character.isDigit(data.charAt(i))) {
					JOptionPane.showMessageDialog(null,
							"No se pueden ingresar caracteres alfanuméricos,\nvuelva a intentarlo", "ERROR!", 0);
					flag = false;
					break;
				}
			}
			if (flag) {
				numero = Integer.parseInt(data);
				if (numero <= 0) {
					JOptionPane.showMessageDialog(null,
							"No se pueden ingresar números menores a 0,\nvuelva a intentarlo", "ERROR!", 0);
					flag = false;
				}
			}
		} while (!flag);

		return numero;
	}// fin

// validacion de ingreso de palabras (enter, nros)

	public static String ValidarLetras(String mensaje) {

		String data;
		boolean flag;

		do {
			flag = true;
			do {
				data = (String) JOptionPane.showInputDialog(null, mensaje, "INGRESO", 0);
				if (data.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No puede haber espacios en blanco,\nvuelva a intentarlo",
							"ERROR!", 0);
				}
			} while (data.isEmpty());
			for (int i = 0; i < data.length(); i++) {
				if (Character.isDigit(data.charAt(i))) {
					JOptionPane.showMessageDialog(null, "No se pueden ingresar números,\nvuelva a intentarlo", "ERROR!", 0);
					flag = false;
					break;
				}
			}
		} while (!flag);

		return data;
	}// fin

// validacion de ingreso de palabras (enter)

	public static String ValidarContras(String mensaje) {

		String data;

		do {
			data = (String) JOptionPane.showInputDialog(null, mensaje, "INGRESO", 0);
			if (data.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No puede haber espacios en blanco,\nvuelva a intentarlo", "ERROR!", 0);
			}
		} while (data.isEmpty());

		return data;

	}

// validacion de fechas (localdate)
	
	public static LocalDate ValidarFecha(String mensaje) {
		
	    LocalDate fecha = null;
	    boolean valido = false;
	    
	    while (!valido) {
	        try {
	            String dia = JOptionPane.showInputDialog(mensaje + "\nDía (1-31):");
	            String mes = JOptionPane.showInputDialog(mensaje + "\nMes (1-12):");
	            String anio = JOptionPane.showInputDialog(mensaje + "\nAño:");
	            
	            fecha = LocalDate.of(
	                Integer.parseInt(anio),
	                Integer.parseInt(mes),
	                Integer.parseInt(dia)
	            );
	            valido = true;
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Fecha inválida, intente nuevamente", "ERROR", 0);
	        }
	    }
	    
	    return fecha;
	}
	
}// fin interface