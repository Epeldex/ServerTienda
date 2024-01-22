package encryption;

import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.ws.rs.InternalServerErrorException;

/**
 * Implementation of the EncriptionManager interface providing methods for
 * message encryption, decryption, message hashing, and key management using
 * both asymmetric and symmetric encryption techniques.
 *
 */
public class EncriptionManagerImpl implements EncriptionManager {

    private static final Logger LOGGER = Logger.getLogger(EncriptionManagerImpl.class.getName());

    private static PrivateKey privateKey;
    private static PublicKey publicKey;
    private static SecretKey symmetricKey;

    /**
     * Constructor for EncriptionManagerImpl. Initializes asymmetric and
     * symmetric keys.
     *
     * @throws InternalServerErrorException if an error occurs during key setup.
     */
    public EncriptionManagerImpl() throws InternalServerErrorException {
        setupAsymmetricKeys();
        setupSymmetricKey();
    }

    /**
     * Encrypts the given message using the symmetric key.
     *
     * @param message The message to be encrypted.
     * @return The encrypted message.
     * @throws InternalServerErrorException if an error occurs during
     * encryption.
     */
    @Override
    public byte[] encryptMessage(String message) throws InternalServerErrorException {
        try {
            LOGGER.info("Encrypting message");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, symmetricKey);
            return cipher.doFinal(Base64.getDecoder().decode(message));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error encrypting message", e);
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Decrypts the given message using the symmetric key.
     *
     * @param message The message to be decrypted.
     * @return The decrypted message.
     * @throws InternalServerErrorException if an error occurs during
     * decryption.
     */
    @Override
    public byte[] decryptMessage(String message) throws InternalServerErrorException {
        try {
            LOGGER.info("Decrypting message");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, symmetricKey);
            return cipher.doFinal(Base64.getDecoder().decode(message));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error decrypting message", e);
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Hashes the given message using MD5.
     *
     * @param message The message to be hashed.
     * @return The hashed message.
     * @throws InternalServerErrorException if an error occurs during hashing.
     */
    @Override
    public String hashMessage(String message) throws InternalServerErrorException {
        try {
            LOGGER.info("Hashing message");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md5.digest(message.getBytes());
            return bytesToHexString(hashBytes);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error hashing message", e);
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Encrypts the symmetric key using the private key.
     *
     * @return The encrypted symmetric key.
     * @throws InternalServerErrorException if an error occurs during
     * encryption.
     */
    @Override
    public byte[] getSymmetricKey() throws InternalServerErrorException {
        try {
            LOGGER.info("Encrypting symmetric key");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(symmetricKey.getEncoded());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error encrypting symmetric key", e);
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Decrypts the symmetric key using the public key.
     *
     * @param key The encrypted symmetric key.
     * @return The decrypted symmetric key.
     * @throws InternalServerErrorException if an error occurs during
     * decryption.
     */
    @Override
    public byte[] decryptSymmetricKey(String key) throws InternalServerErrorException {
        try {
            LOGGER.info("Decrypting symmetric key");
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(Base64.getDecoder().decode(key));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error decrypting symmetric key", e);
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Sets up the asymmetric keys by generating them if they do not exist.
     *
     * @throws InternalServerErrorException if an error occurs during key
     * generation.
     */
    private void setupAsymmetricKeys() throws InternalServerErrorException {
        try {
            if (!new File(System.getProperty("user.home") + "\\AppData\\Local\\OurShop").exists()) {
                AsymmetricKeyGenerator.generateKeyPair();
            }

            privateKey = getPrivateKey();
            publicKey = getPublicKey();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error setting up asymmetric keys", e);
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Sets up the symmetric key by generating it if it does not exist.
     *
     * @throws InternalServerErrorException if an error occurs during key
     * generation.
     */
    private void setupSymmetricKey() throws InternalServerErrorException {
        try {
            if (symmetricKey == null) {
                symmetricKey = generateAESKey();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error setting up symmetric key", e);
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Retrieves the private key from the file system.
     *
     * @return The private key.
     * @throws InternalServerErrorException if an error occurs during key
     * retrieval.
     */
    private PrivateKey getPrivateKey() throws InternalServerErrorException {
        try {
            String privateKeyPath = System.getProperty("user.home") + "\\AppData\\Local\\OurShop\\privateKey.der";
            byte[] privateKeyBytes = readKeyBytes(privateKeyPath);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            return KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting private key", e);
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Retrieves the public key from the file system.
     *
     * @return The public key.
     * @throws InternalServerErrorException if an error occurs during key
     * retrieval.
     */
    private PublicKey getPublicKey() throws InternalServerErrorException {
        try {
            String publicKeyPath = System.getProperty("user.home") + "\\AppData\\Local\\OurShop\\publicKey.der";
            byte[] publicKeyBytes = readKeyBytes(publicKeyPath);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            return KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting public key", e);
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Generates a new AES key.
     *
     * @return The generated AES key.
     * @throws NoSuchAlgorithmException if an error occurs during key
     * generation.
     */
    private static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            return keyGenerator.generateKey();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating AES key", e);
            throw new NoSuchAlgorithmException(e);
        }
    }

    /**
     * Reads the key bytes from the specified file path.
     *
     * @param keyPath The path to the key file.
     * @return The key bytes.
     * @throws Exception if an error occurs during key file reading.
     */
    private static byte[] readKeyBytes(String keyPath) throws Exception {
        try (FileInputStream fis = new FileInputStream(keyPath)) {
            byte[] keyBytes = new byte[fis.available()];
            fis.read(keyBytes);
            return keyBytes;
        }
    }

    /**
     * Converts byte array to a hexadecimal string representation.
     *
     * @param bytes The byte array to be converted.
     * @return The hexadecimal string.
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02X", b));
        }
        return hexStringBuilder.toString();
    }
}
