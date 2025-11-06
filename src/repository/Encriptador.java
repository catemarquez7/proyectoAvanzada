package repository;

public class Encriptador {
	
	public static String encriptar(String texto) {
		StringBuilder resultado = new StringBuilder();
		for (char c : texto.toCharArray()) {
			if (Character.isLetter(c)) {
				char base = Character.isLowerCase(c) ? 'a' : 'A';
				c = (char) ((c - base + 3) % 26 + base);
			}
			resultado.append(c);
		}
		return resultado.toString();
	}

	public static String desencriptar(String texto) {
		StringBuilder resultado = new StringBuilder();
		for (char c : texto.toCharArray()) {
			if (Character.isLetter(c)) {
				char base = Character.isLowerCase(c) ? 'a' : 'A';
				c = (char) ((c - base - 3 + 26) % 26 + base);
			}
			resultado.append(c);
		}
		return resultado.toString();
	}
	
}//fin
