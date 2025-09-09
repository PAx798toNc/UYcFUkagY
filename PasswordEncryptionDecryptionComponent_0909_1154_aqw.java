// 代码生成时间: 2025-09-09 11:54:01
import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PasswordEncryptionDecryptionComponent {

    private static final String ALGORITHM = "AES";
    private static final String ENCRYPTION_SCHEME = "AES/ECB/PKCS5Padding";
    private static final String CHARSET = "UTF-8";

    /**
     * Encrypt the provided plain text using AES encryption
     * @param plainText the text to encrypt
     * @param key the secret key for encryption
     * @return the encrypted text in Base64 encoding
     */
    public String encrypt(String plainText, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(CHARSET));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            // Handle encryption error
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Decrypt the provided encrypted text using AES encryption
     * @param encryptedText the text to decrypt
     * @param key the secret key for decryption
     * @return the decrypted text
     */
    public String decrypt(String encryptedText, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedValue);
            return new String(decryptedBytes, CHARSET);
        } catch (Exception e) {
            // Handle decryption error
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generate a secret key for AES encryption
     * @return a new secret key
     */
    public SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(128, new SecureRandom());
            return keyGenerator.generateKey();
        } catch (Exception e) {
            // Handle key generation error
            e.printStackTrace();
            return null;
        }
    }
}
