/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.ws.rs.InternalServerErrorException;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import org.apache.xml.security.utils.Base64;

/**
 *
 * @author alexa
 */
public class EncriptionManagerImplementation implements EncriptionManager {

    private static final Logger LOGGER = Logger.getLogger("EncriptionManager");

    private static boolean initializedFlag = false;

    private static PrivateKey privateKey;
    private static PublicKey publicKey;
    private static SecretKey symmetricKey;

    public EncriptionManagerImplementation() throws InternalServerErrorException {
        setupAsymmetricKeys();
        setupSymmetricKey();
    }

    private void setupAsymmetricKeys() throws InternalServerErrorException {
        if (!new File(System.getProperty("user.home") + "\\AppData\\Local\\OurShop").exists()) {
            AsymmetricKeyGenerator.generate();
        }

        privateKey = getPrivateKey();
        publicKey = getPublicKey();
    }

    private void setupSymmetricKey() throws InternalServerErrorException {
        try {
            if (symmetricKey == null) {
                symmetricKey = generateAESKey();
            }

        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
    }

    private PrivateKey getPrivateKey() throws InternalServerErrorException {
        try {
            String privateKeyPath = System.getProperty("user.home") + "\\AppData\\Local\\OurShop\\privateKey.der";
            FileInputStream fis = new FileInputStream(privateKeyPath);
            byte[] privateKeyBytes = new byte[fis.available()];
            fis.read(privateKeyBytes);
            fis.close();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            return keyFactory.generatePrivate(privateKeySpec);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error setting up private key decryption");
            throw new InternalServerErrorException(e);
        }
    }

    private static PublicKey getPublicKey() throws InternalServerErrorException {
        try {
            String publicKeyPath = System.getProperty("user.home") + "\\AppData\\Local\\OurShop\\publicKey.der";
            FileInputStream fis = new FileInputStream(publicKeyPath);
            byte[] publicKeyBytes = new byte[fis.available()];
            fis.read(publicKeyBytes);
            fis.close();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error setting up public key encryption");
            throw new InternalServerErrorException(e);
        }

    }

    @Override
    public byte[] encryptMessage(String message) throws InternalServerErrorException {
        try {
            Cipher cipher = Cipher.getInstance("AES"); // No Bouncy Castle provider
            cipher.init(Cipher.ENCRYPT_MODE, symmetricKey);
            return cipher.doFinal(Base64.decode(message));
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public byte[] decryptMessage(String message) throws InternalServerErrorException {
        try {
            LOGGER.info("Decrypting " + message);
            Cipher cipher = Cipher.getInstance("AES"); // No Bouncy Castle provider
            cipher.init(Cipher.DECRYPT_MODE, symmetricKey);
            return cipher.doFinal(Base64.decode(message));

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error decrypting message", e.getMessage());
            throw new InternalServerErrorException(e);
        }

    }

    @Override
    public String hashMessage(String message) throws InternalServerErrorException {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md5.digest(message.getBytes());

            // Convert the byte array to a hexadecimal representation
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = String.format("%02X", b);
                hexStringBuilder.append(hex);
            }
            return hexStringBuilder.toString();
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public byte[] getSymmetricKey() throws InternalServerErrorException {
        try {
            LOGGER.info("Encrypting symmetric key");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(symmetricKey.getEncoded());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error encrypting message", e.getMessage());
            throw new InternalServerErrorException();
        }
    }

    private static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // You can change the key size (128, 192, or 256)
        return keyGenerator.generateKey();
    }

    @Override
    public byte[] decryptSymmeticKey(String key) throws InternalServerErrorException {
        try {
            System.out.println(key.getBytes().length);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(key.getBytes());
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
    }

}
