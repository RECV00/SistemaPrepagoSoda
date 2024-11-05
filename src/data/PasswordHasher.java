package data;

import java.security.MessageDigest; // Importa la clase para hacer hashing.
import java.security.NoSuchAlgorithmException; // Importa la excepción que se lanza si el algoritmo de hashing no es compatible.
import java.security.SecureRandom; // Importa la clase para generar un salt aleatorio.

public class PasswordHasher {

	 public static boolean verifyPassword(String password, String storedHash, String salt) {
	        String hashToCompare = hashPasswordWithSalt(password, salt);
	        return hashToCompare.equals(storedHash);
	    }
    // Método para generar un salt aleatorio
    public static String generateSalt() {
        SecureRandom sr = new SecureRandom(); // Crea un objeto de SecureRandom para generar bytes aleatorios.
        byte[] salt = new byte[16]; // Crea un array de bytes de tamaño 16 (128 bits).
        sr.nextBytes(salt); // Llena el array con bytes aleatorios.
        
        // Convierte los bytes a una representación hexadecimal para poder almacenarlos como texto.
        StringBuilder hexString = new StringBuilder();
        for (byte b : salt) {
            String hex = Integer.toHexString(0xff & b); // Convierte el byte a un valor hexadecimal.
            if (hex.length() == 1) hexString.append('0'); // Agrega un '0' si el valor tiene un solo dígito.
            hexString.append(hex);
        }
        return hexString.toString(); // Devuelve el salt en forma de cadena de texto.
    }

    // Método para hacer hashing de la contraseña con un salt
    public static String hashPasswordWithSalt(String password, String salt) {
        try {
            // Crea una instancia de MessageDigest para usar SHA-256.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // Concatena la contraseña con el salt.
            String passwordWithSalt = password + salt;
            
            // Genera el hash en bytes.
            byte[] hash = md.digest(passwordWithSalt.getBytes());
            
            // Convierte el hash a una representación hexadecimal.
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b); // Convierte el byte a valor hexadecimal.
                if (hex.length() == 1) hexString.append('0'); // Agrega un '0' si el valor tiene un solo dígito.
                hexString.append(hex);
            }
            return hexString.toString(); // Devuelve el hash en forma de cadena de texto.
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // Lanza una excepción si el algoritmo no es compatible.
        }
    }
}

