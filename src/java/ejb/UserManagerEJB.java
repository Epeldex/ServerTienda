package ejb;

import ejb.local.UserManagerEJBLocal;
import encryption.EncriptionManager;
import encryption.EncriptionManagerFactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.User;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.ArrayList;
import java.util.Base64;
import javax.persistence.PersistenceContext;

@Stateless
public class UserManagerEJB implements UserManagerEJBLocal {

    /**
     * Logger of the class.
     */
    private static final Logger LOGGER = Logger.getLogger("ejb");

    @PersistenceContext
    private EntityManager em;

    private EncriptionManager encriptionManager = EncriptionManagerFactory.getInstance();

    /**
     * Finds a user by ID.
     *
     * @param id The ID of the user to be found.
     * @return The user object containing user data.
     * @throws ReadException If there is any exception during the process.
     */
    @Override
    public User findUserById(Integer id) throws ReadException {
        try {
            LOGGER.info("UserManager: Finding user by id=" + id + ".");
            // Using named query to find a user by ID
            return encryptPassword((User) em.createNamedQuery("findUserById")
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding user by id:", e);
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Finds a user by username.
     *
     * @param username The username of the user to be found.
     * @return The user object containing user data.
     * @throws ReadException If there is any exception during the process.
     */
    @Override
    public User findUserByUsername(String username) throws ReadException {
        try {
            LOGGER.info("UserManager: Finding user by username = " + username + ".");
            // Using named query to find a user by username
            return encryptPassword((User) em.createNamedQuery("findUserByUsername")
                    .setParameter("username", username)
                    .getSingleResult());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding user by username:", e);
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Finds users by active state.
     *
     * @param active The state of the user as a boolean.
     * @return The list of user objects containing user data.
     * @throws ReadException If there is any exception during the process.
     */
    @Override
    public List<User> findUserByActive(Boolean active) throws ReadException {
        List<User> results;
        try {
            LOGGER.info("UserManager: Finding user by active state.");
            // Using named query to find users by active state
            results = em.createNamedQuery("findUserByActive")
                    .setParameter("active", active)
                    .getResultList();
            // Encrypting passwords for all users in the result list
            List<User> newList = new ArrayList<>();
            for (User user : results) {
                newList.add(encryptPassword(user));
            }
            return newList;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding user by active state:", e);
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Finds all users in the application data storage.
     *
     * @return A list of user objects.
     * @throws ReadException If there is any exception during the process.
     */
    @Override
    public List<User> findAllUsers() throws ReadException {
        List<User> results;
        try {
            LOGGER.info("UserManager: Finding all users.");
            // Using named query to find all users
            results = em.createNamedQuery("findAllUsers").getResultList();
            // Encrypting passwords for all users in the result list
            List<User> newList = new ArrayList<>();
            for (User user : results) {
                newList.add(encryptPassword(user));
            }
            return newList;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding all users:", e);
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Checks if there's any user with the provided credentials.
     *
     * @param username The user object's username.
     * @param password The user object's password.
     * @return The user object containing the user data.
     * @throws ReadException If there is any exception during the process.
     */
    @Override
    public User signIn(String username, String password) throws ReadException {
        try {
            LOGGER.info("UserManager: Signing in user.");
            // Using named query to sign in user with given credentials
            Query signIn = em.createNamedQuery("signIn");
            signIn.setParameter("username", username);
            signIn.setParameter("password", Base64.getEncoder().encodeToString(encriptionManager.decryptMessage(password)));

            return encryptPassword((User) signIn.getSingleResult());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception signing in user:", e);
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * @deprecated This method is deprecated and should not be used.
     * @param id The user's ID whose password is to be changed.
     * @param password The new password of the user.
     * @throws UpdateException If there is any exception during the process.
     */
    @Deprecated
    @Override
    public void updatePassword(Integer id, String password) throws UpdateException {
        try {
            LOGGER.info("UserManager: Updating user password.");
            // Checking if the user with the specified ID exists
            if (findUserById(id) != null) {
                // TODO: This code does not actually update the password, consider removing or completing
                em.createNamedQuery("updatePassword");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception updating user password:", e);
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Creates a user and stores it in the underlying application storage.
     *
     * @param user The user object containing the user data.
     * @throws CreateException If there is any exception during the process.
     */
    @Override
    public void createUser(User user) throws CreateException {
        try {
            LOGGER.info("UserManager: Creating user.");
            // Decrypting and encoding the user's password before persisting
            user.setPassword(Base64.getEncoder().encodeToString(encriptionManager.decryptMessage(user.getPassword())));
            em.persist(user);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception creating user:", e);
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Updates a user's data in the underlying application storage.
     *
     * @param user The user object containing the user data.
     * @throws UpdateException If there is any exception during the process.
     */
    @Override
    public void updateUser(User user) throws UpdateException {
        try {
            LOGGER.info("UserManager: Updating user.");
            // Decrypting and encoding the user's password before merging
            user.setPassword(Base64.getEncoder().encodeToString(encriptionManager.decryptMessage(user.getPassword())));
            em.merge(user);
            LOGGER.info("UserManager: User updated");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception updating user:", e);
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Removes a user's data from the underlying application storage.
     *
     * @param id The user's ID who has to be removed.
     * @throws DeleteException If there is any exception during the process.
     */
    @Override
    public void removeUser(Integer id) throws DeleteException {
        try {
            LOGGER.info("UserManager: Removing user.");
            // Checking if the user with the specified ID exists before removing
            if (findUserById(id) != null) {
                em.createNamedQuery("removeUser")
                        .setParameter("id", id)
                        .executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception removing user:", e);
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Encrypts the password of the given user.
     *
     * @param user The user object whose password is to be encrypted.
     * @return A new user object with the encrypted password.
     * @throws Exception If there is any exception during the encryption
     * process.
     */
    private User encryptPassword(User user) throws Exception {
        User u = (User) user.clone();
        u.setPassword(Base64.getEncoder().encodeToString(encriptionManager.encryptMessage(u.getPassword())));
        return u;
    }
}
