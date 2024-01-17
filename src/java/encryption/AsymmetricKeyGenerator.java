/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexa
 */
public class AsymmetricKeyGenerator {

    private static final Logger LOGGER
            = Logger.getLogger("AsymmetricKeyGenerator");

    public static void generate() {
        try {
            // Especificamos el algoritmo de encriptación
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            // Especificamos el tamaño en bits de las claves
            keyPairGenerator.initialize(2048);
            // Se genera el par de claves
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKeyAndMore = keyPair.getPublic();
            byte[] publicKeyBytes = publicKeyAndMore.getEncoded();

            String absolutePath = System.getProperty("user.home") + "\\AppData\\Local\\OurShop";
            try {
                new File(absolutePath).mkdir();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to create public key directory");
            }
            try (FileOutputStream publicKeyFile = new FileOutputStream(absolutePath + "\\publicKey.der")) {
                publicKeyFile.write(publicKeyBytes);
            }

            PrivateKey privateKey = keyPair.getPrivate();
            byte[] privateKeyBytes = privateKey.getEncoded();
            try (FileOutputStream privateKeyFile = new FileOutputStream(absolutePath + "\\privateKey.der")) {
                privateKeyFile.write(privateKeyBytes);
            }

            LOGGER.info("Key pair generated");
        } catch (IOException | NoSuchAlgorithmException e) {
            LOGGER.info(e.getMessage());
        }
    }
}
