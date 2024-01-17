package ejb;

import ejb.local.UserManagerEJBLocal;
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
import javax.persistence.PersistenceContext;
/**
 * @author dani
 */
@Stateless
public class UserManagerEJB implements UserManagerEJBLocal {

    /**
     * Logger of the class.
     */
    private static final Logger LOGGER = Logger.getLogger("ejb");

    @PersistenceContext
    private EntityManager em;

    /**
     * Finds a user by their ID.
     *
     * @param id The ID of the user to search for.
     * @return The User object corresponding to the given ID.
     * @throws ReadException If an error occurs during the read process.
     */
    @Override
    public User findUserById(Integer id) throws ReadException {
        try {
            LOGGER.info("UserManager: Finding user by id=" + id + ".");
            return User.class.cast(
                    em.createNamedQuery("findUserById")
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding user by login:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to search for.
     * @return The User object corresponding to the given username.
     * @throws ReadException If an error occurs during the read process.
     */
    @Override
    public User findUserByUsername(String username) throws ReadException {
        try {
            LOGGER.info("UserManager: Finding user by username=" + username + ".");
            return User.class.cast(
                    em.createNamedQuery("findUserByUsername")
                            .setParameter("username", username)
                            .getSingleResult());
        } catch (Exception e) {
            LOGGER.log(
                    Level.SEVERE,
                    "UserManager: Exception finding user by id:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Finds users based on their active status.
     *
     * @param active The active status of users to search for.
     * @return A list of User objects matching the given active status.
     * @throws ReadException If an error occurs during the read process.
     */
    @Override
    public List<User> findUserByActive(Boolean active) throws ReadException {
        List<User> results;
        try {
            LOGGER.info("UserManager: Finding user by login.");
            results = em.createNamedQuery("findUserByActive")
                    .setParameter("active", active)
                    .getResultList();

            return results;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding user by login:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Finds all users in the system.
     *
     * @return A list of all User objects in the system.
     * @throws ReadException If an error occurs during the read process.
     */
    @Override
    public List<User> findAllUsers() throws ReadException {
        List<User> results;
        try {
            LOGGER.info("UserManager: Finding user by login.");
            results = em.createNamedQuery("findAllUsers")
                    .getResultList();

            return results;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding user by login:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Authenticates a user during sign-in using their username and password.
     *
     * @param username The username of the user attempting to sign in.
     * @param password The password of the user attempting to sign in.
     * @return The authenticated User object.
     * @throws ReadException If an error occurs during the authentication process.
     */
    @Override
    public User signIn(String username, String password) throws ReadException {
        try {
            LOGGER.info("UserManager: Finding user by login.");
            Query signIn = em.createNamedQuery("signIn");
            signIn.setParameter("username", username);
            signIn.setParameter("password", password);

            return User.class.cast(signIn.getSingleResult());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding user by login:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Updates the password of a user identified by their ID.
     *
     * @param id       The ID of the user.
     * @param password The new password to set for the user.
     * @throws UpdateException If an error occurs during the update process.
     */
    @Override
    public void updatePassword(Integer id, String password) throws UpdateException {
        try {
            LOGGER.info("UserManager: Finding user by login.");
            if (findUserById(id) != null) {
                em.createNamedQuery("updatePassword");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding user by login:",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Creates a new user in the system.
     *
     * @param user The User object representing the new user.
     * @throws CreateException If an error occurs during the creation process.
     *                         TODO: Review and handle exceptions more precisely.
     */
    @Override
    public void createUser(User user) throws CreateException {
        try {
            LOGGER.info("UserManager: Finding user by login.");
            em.persist(user);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding user by login:",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Updates the information of an existing user.
     *
     * @param user The updated User object.
     * @throws UpdateException If an error occurs during the update process.
     */
    @Override
    public void updateUser(User user) throws UpdateException {
        try {
            LOGGER.info("UserManager: Updating user.");
            em.merge(user);
            LOGGER.info("UserManager: User updated");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception updating user:",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Removes a user from the system based on their ID.
     *
     * @param id The ID of the user to be removed.
     * @throws DeleteException If an error occurs during the delete process.
     */
    @Override
    public void removeUser(Integer id) throws DeleteException {
        try {
            LOGGER.info("UserManager: Finding user by login.");
            if (findUserById(id) != null) {
                em.createNamedQuery("removeUser")
                        .setParameter("id", id)
                        .executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding user by login:",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
}
