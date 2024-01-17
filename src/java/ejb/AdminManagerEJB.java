
package ejb;

import ejb.local.AdminManagerEJBLocal;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Admin;
import entities.User;
import entities.UserType;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import javax.persistence.PersistenceContext;

/**
 * @author dani
 */

@Stateless
public class AdminManagerEJB implements AdminManagerEJBLocal {

    private static final Logger LOGGER = Logger.getLogger("ejb");
    @PersistenceContext
    private EntityManager em;

    /**
     * Updates the last access time of an administrator identified by their ID.
     *
     * @param id   The ID of the administrator.
     * @param date The new last access time.
     * @throws UpdateException If an error occurs during the update process.
     */
    @Override
    public void updateLastAccess(Integer id, LocalDate date) throws UpdateException {
        try {
            LOGGER.info("AdminManager: Updating the last access of the admin; id=" + id + ".");
            Query updateLastAccess = em.createNamedQuery("signIn");
            updateLastAccess.setParameter("id", id);
            updateLastAccess.setParameter("date", date);

            updateLastAccess.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "AdminManager: Exception attempting the update:",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Authenticates an administrator using their username and password.
     *
     * @param username The administrator's username.
     * @param password The administrator's password.
     * @return The authenticated Admin object.
     * @throws ReadException If an error occurs during the authentication process.
     */
    @Override
    public Admin signIn(String username, String password) throws ReadException {
        try {
            LOGGER.info("AdminManager: Administrator username=" + username + " signing in.");
            Query signIn = em.createNamedQuery("signIn");
            signIn.setParameter("username", username);
            signIn.setParameter("password", password);

            return Admin.class.cast(signIn.getSingleResult());
        } catch (Exception e) {
            LOGGER.log(
                    Level.SEVERE, "AdminManager: Exception signing in:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Creates a new administrator in the system.
     *
     * @param admin The Admin object representing the new administrator.
     * @throws CreateException If an error occurs during the creation process.
     *                         TODO: Review and handle exceptions more precisely.
     */
    @Override
    public void createAdmin(Admin admin) throws CreateException {
        try {
            LOGGER.info("AdminManager: Creating new administrator; id=" + admin.getId() + ".");
            em.persist(admin);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "AdminManager: Exception creating admin.",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Updates an existing administrator's information, including password
     * encryption.
     *
     * @param admin The updated Admin object.
     * @throws UpdateException If an error occurs during the update process.
     */
    @Override
    public void updateAdmin(Admin admin) throws UpdateException {
        try {
            LOGGER.info("AdminManager: Updating admin; id=" + admin.getId() + ".");
            if (em.contains(admin)) {
                /*
                 * Crypting admin password
                 */
                em.merge(admin);
            }
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "AdminManager: Exception finding user by login:",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Removes an administrator from the system if the user type is ADMIN.
     *
     * @param id The ID of the administrator to be removed.
     * @throws DeleteException If an error occurs during the delete process.
     */
    @Override
    public void removeAdmin(Integer id) throws DeleteException {
        try {
            User user;
            LOGGER.info("UserManager: Finding user by login.");
            user = new UserManagerEJB().findUserById(id);

            if (user.getUserType().equals(UserType.ADMIN)) {
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
