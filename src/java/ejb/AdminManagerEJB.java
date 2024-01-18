package ejb;

import ejb.local.AdminManagerEJBLocal;
import encryption.EncriptionManager;
import encryption.EncriptionManagerFactory;
import java.time.LocalDate;
import java.util.Base64;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entities.Admin;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;

/**
 * EJB implementation for managing administrator entities. Implements the
 * {@link AdminManagerEJBLocal} interface.
 */
@Stateless
public class AdminManagerEJB implements AdminManagerEJBLocal {

    // Use a class-specific logger
    private static final Logger LOGGER = Logger.getLogger(AdminManagerEJB.class.getName());

    @PersistenceContext
    private EntityManager em;

    // Use EncriptionManager interface for better flexibility
    private EncriptionManager encriptionManager = EncriptionManagerFactory.getInstance();

    /**
     * Deprecated method. Updates the last access date of a specific admin.
     *
     * @param id Id of the admin whose last access date is to be updated.
     * @param date New date for the last access of the admin.
     * @throws UpdateException If there is any exception during the update
     * process.
     */
    @Deprecated
    @Override
    public void updateLastAccess(Integer id, LocalDate date) throws UpdateException {
        try {
            LOGGER.info("Updating the last access of the admin; id=" + id + ".");
            // Use the appropriate NamedQuery for updating last access
            Query updateLastAccess = em.createNamedQuery("updateLastAccess");
            updateLastAccess.setParameter("id", id);
            updateLastAccess.setParameter("date", date);
            updateLastAccess.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Exception attempting the update:", e.getMessage());
            throw new UpdateException("Failed to update last access date.", e);
        }
    }

    /**
     * Signs in an admin using the provided username and password.
     *
     * @param username The username of the admin.
     * @param password The password of the admin.
     * @return The Admin object representing the signed-in admin.
     * @throws ReadException If there is any exception during the sign-in
     * process.
     */
    @Override
    public Admin signIn(String username, String password) throws ReadException {
        try {
            LOGGER.info("Administrator username=" + username + " signing in.");
            Query signIn = em.createNamedQuery("signIn");
            signIn.setParameter("username", username);
            signIn.setParameter("password", Base64.getEncoder().encodeToString(encriptionManager.decryptMessage(password)));

            // Cast directly to Admin since it's expected to be a single result
            Admin signedInAdmin = (Admin) signIn.getSingleResult();
            return encryptPassword(signedInAdmin);
        } catch (Exception e) {
            LOGGER.severe("Exception signing in:" + e.getMessage());
            throw new ReadException("Failed to sign in.", e);
        }
    }

    /**
     * Creates a new admin and stores it in the underlying application storage.
     *
     * @param admin The Admin object containing the admin data to be created.
     * @throws CreateException If there is any exception during the creation
     * process.
     */
    @Override
    public void createAdmin(Admin admin) throws CreateException {
        try {
            LOGGER.info("Creating new administrator; id=" + admin.getId() + ".");
            admin.setPassword(Base64.getEncoder().encodeToString(encriptionManager.decryptMessage(admin.getPassword())));
            em.persist(admin);
        } catch (Exception e) {
            LOGGER.severe("Exception creating admin." + e.getMessage());
            throw new CreateException("Failed to create admin.", e);
        }
    }

    /**
     * Updates an admin's data in the underlying application storage.
     *
     * @param admin The Admin object containing the updated admin data.
     * @throws UpdateException If there is any exception during the update
     * process.
     */
    @Override
    public void updateAdmin(Admin admin) throws UpdateException {
        try {
            LOGGER.info("Updating admin; id=" + admin.getId() + ".");
            admin.setPassword(Base64.getEncoder().encodeToString(encriptionManager.decryptMessage(admin.getPassword())));
            em.merge(admin);
        } catch (Exception e) {
            LOGGER.severe("Exception updating admin:" + e.getMessage());
            throw new UpdateException("Failed to update admin.", e);
        }
    }

    /**
     * Deletes an admin's data from the underlying application storage based on
     * the given ID.
     *
     * @param id The ID of the admin whose data is to be deleted.
     * @throws DeleteException If there is any exception during the deletion
     * process.
     */
    @Override
    public void removeAdmin(Integer id) throws DeleteException {
        try {
            LOGGER.info("Removing admin; id=" + id + ".");
            em.createNamedQuery("removeAdmin").setParameter("id", id).executeUpdate();
        } catch (Exception e) {
            LOGGER.severe("Exception removing admin:" + e.getMessage());
            throw new DeleteException("Failed to remove admin.", e);
        }
    }

    /**
     * Encrypts the password of the given admin.
     *
     * @param admin The admin object to encrypt the password for.
     * @return A new Admin object with the encrypted password.
     * @throws Exception If there is any exception during the encryption
     * process.
     */
    private Admin encryptPassword(Admin admin) throws Exception {
        // Use a cloned object to avoid modifying the original Admin
        Admin clonedAdmin = (Admin) admin.clone();
        clonedAdmin.setPassword(Base64.getEncoder().encodeToString(encriptionManager.encryptMessage(admin.getPassword())));
        return clonedAdmin;
    }
}
