package elorESClient.modelo.util;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * Utilidad de encriptación RSA para el cliente
 * Permite encriptar contraseñas con la clave pública del servidor
 */
public class CryptoClient {

    private static final String ALGORITHM = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    
    private static PublicKey serverPublicKey;

    /**
     * Establece la clave pública del servidor (recibida al conectar)
     * @param publicKeyBase64 Clave pública en formato Base64
     */
    public static void setServerPublicKey(String publicKeyBase64) {
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            serverPublicKey = keyFactory.generatePublic(keySpec);
            
            System.out.println("[CRYPTO CLIENT] Clave pública del servidor configurada");
        } catch (Exception e) {
            System.err.println("[CRYPTO CLIENT ERROR] Error al configurar clave pública: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Encripta un texto con la clave pública del servidor
     * @param texto Texto a encriptar (ej: contraseña)
     * @return Texto encriptado en Base64, o null si hay error
     */
    public static String encriptar(String texto) {
        if (serverPublicKey == null) {
            System.err.println("[CRYPTO CLIENT ERROR] No se ha configurado la clave pública del servidor");
            return null;
        }

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, serverPublicKey);
            byte[] encryptedBytes = cipher.doFinal(texto.getBytes());
            
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.err.println("[CRYPTO CLIENT ERROR] Error al encriptar: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verifica si la clave pública está configurada
     * @return true si está configurada, false si no
     */
    public static boolean isKeyConfigured() {
        return serverPublicKey != null;
    }
}