package encryption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides functionality to generate asymmetric key pairs (public
 * and private keys) using the RSA algorithm and save them to a specified
 * directory.
 */
public class AsymmetricKeyGenerator {

    private static final Logger LOGGER = Logger.getLogger(AsymmetricKeyGenerator.class.getName());

    /**
     * Generates an RSA key pair and saves the public and private keys to a
     * specified directory. The directory structure is assumed to be
     * "{user.home}/AppData/Local/OurShop". Logs success or failure messages
     * using a logger.
     */
    public static void generateKeyPair() {
        try {
            // Initialize KeyPairGenerator with the RSA algorithm and key size of 2048 bits
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Specify the directory where keys will be saved
            String keyDirectory = System.getProperty("user.home") + File.separator + "AppData"
                    + File.separator + "Local" + File.separator + "OurShop";

            // Attempt to create the directory if it doesn't exist
            if (createDirectory(keyDirectory)) {
                // Save the public and private keys to separate files
                saveKeyToFile(keyPair.getPublic(), keyDirectory + File.separator + "publicKey.der");
                saveKeyToFile(keyPair.getPrivate(), keyDirectory + File.separator + "privateKey.der");
                LOGGER.info("Key pair generated successfully");
            } else {
                LOGGER.log(Level.SEVERE, "Failed to create public key directory");
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "Error during key pair generation", e);
        }
    }

    /**
     * Creates a directory at the specified path if it does not already exist.
     *
     * @param directoryPath The path of the directory to create.
     * @return true if the directory already exists or was successfully created;
     * false otherwise.
     */
    private static boolean createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.exists() || directory.mkdirs();
    }

    /**
     * Saves a key to a file in binary DER format.
     *
     * @param key The key to be saved.
     * @param filePath The path of the file where the key will be saved.
     * @throws IOException If an I/O error occurs while writing the key to the
     * file.
     */
    private static void saveKeyToFile(Key key, String filePath) throws IOException {
        try (FileOutputStream keyFile = new FileOutputStream(filePath)) {
            keyFile.write(key.getEncoded());
        }
    }
}
