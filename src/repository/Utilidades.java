package repository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilidades {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean ValidarMail(String emailStr) {
		if (emailStr == null || emailStr.trim().isEmpty()) {
			return false;
		}
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.matches();
	}

	public static boolean contieneSoloLetras(String data) {
	    if (data == null || data.trim().isEmpty()) {
	        return false;
	    }
	    for (int i = 0; i < data.length(); i++) {
	        if (!Character.isLetter(data.charAt(i))) {
	            return false;
	        }
	    }
	    return true;
	}

	public static boolean contieneSoloNumeros(String data) {
	    if (data == null || data.trim().isEmpty()) {
	        return false;
	    }
	    for (int i = 0; i < data.length(); i++) {
	        if (!Character.isDigit(data.charAt(i))) {
	            return false;
	        }
	    }
	    return true;
	}
	
}
