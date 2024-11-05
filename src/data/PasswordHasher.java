package data;

import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
import java.security.SecureRandom; 

public class PasswordHasher {

	 public static boolean verifyPassword(String password, String storedHash, String salt) {
	        String hashToCompare = hashPasswordWithSalt(password, salt);
	        return hashToCompare.equals(storedHash);
	    }
    // Método para generar un salt aleatorio
    public static String generateSalt() {
        SecureRandom sr = new SecureRandom(); 
        byte[] salt = new byte[16]; // tamaño 16 (128 bits).
        sr.nextBytes(salt); // array con bytes aleatorios.
        
        // Convierte los bytes a  hexadecimal 
        StringBuilder hexString = new StringBuilder();
        for (byte b : salt) {
            String hex = Integer.toHexString(0xff & b); 
            if (hex.length() == 1) hexString.append('0'); 
        }
        return hexString.toString(); 
    }

    
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
                String hex = Integer.toHexString(0xff & b); 
                if (hex.length() == 1) hexString.append('0'); 
                hexString.append(hex);
            }
            return hexString.toString(); 
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

