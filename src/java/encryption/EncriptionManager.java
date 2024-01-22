package encryption;

import javax.ws.rs.InternalServerErrorException;

/**
 * The EncriptionManager interface provides methods for message encryption,
 * decryption, message hashing, and key management using both asymmetric and
 * symmetric encryption techniques.
 *
 */
public interface EncriptionManager {

    /**
     * Encrypts the given message using a symmetric key.
     *
     * @param message The message to be encrypted.
     * @return The encrypted message.
     * @throws InternalServerErrorException if an error occurs during
     * encryption.
     */
    public byte[] encryptMessage(String message) throws InternalServerErrorException;

    /**
     * Decrypts the given message using a symmetric key.
     *
     * @param message The message to be decrypted.
     * @return The decrypted message.
     * @throws InternalServerErrorException if an error occurs during
     * decryption.
     */
    public byte[] decryptMessage(String message) throws InternalServerErrorException;

    /**
     * Hashes the given message using MD5.
     *
     * @param message The message to be hashed.
     * @return The hashed message.
     * @throws InternalServerErrorException if an error occurs during hashing.
     */
    public String hashMessage(String message) throws InternalServerErrorException;

    /**
     * Encrypts the symmetric key using an asymmetric private key.
     *
     * @return The encrypted symmetric key.
     * @throws InternalServerErrorException if an error occurs during
     * encryption.
     */
    public byte[] getSymmetricKey() throws InternalServerErrorException;

    /**
     * Decrypts the symmetric key using an asymmetric public key.
     *
     * @param message The encrypted symmetric key.
     * @return The decrypted symmetric key.
     * @throws InternalServerErrorException if an error occurs during
     * decryption.
     */
    public byte[] decryptSymmetricKey(String message) throws InternalServerErrorException;

}
