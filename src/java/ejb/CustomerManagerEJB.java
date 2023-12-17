package ejb;

import entities.Customer;
import entities.User;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stateless EJB (Enterprise JavaBeans) class responsible for managing customer
 * data. This class provides methods for updating personal information, deleting
 * a user, inserting a new user, and retrieving customer information.
 *
 * @author Alex Irusta
 */
@Stateless
public class CustomerManagerEJB implements CustomerManagerEJBLocal {

    // Logger for class methods.
    private static final Logger LOGGER = Logger.getLogger("ejb");

    // Entity manager for handling persistence operations.
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Updates the personal information of a customer identified by their user
     * ID.
     *
     * @param customer The Customer object containing updated information.
     * @throws UpdateException If an error occurs during the update process.
     */
    @Override
    public void updatePersonalInfoById(Customer customer) throws UpdateException {
        try {
            LOGGER.info("CustomerManager: Updating customer.");

            // Create and execute a named query to update personal information.
            Query query = entityManager.createNamedQuery("updatePersonalInfoById");
            query.setParameter("fullName", customer.getFullName());
            query.setParameter("email", customer.getEmail());
            // Set other parameters...
            query.executeUpdate();

            LOGGER.info("CustomerManager: Customer updated.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "CustomerManager: Exception updating customer.", e);
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Deletes a user (customer) identified by their ID.
     *
     * @param id The ID of the user to be deleted.
     * @throws DeleteException If an error occurs during the delete process.
     */
    @Override
    public void deleteUserById(String id) throws DeleteException {
        try {
            LOGGER.info("CustomerManager: Deleting customer.");

            // Create and execute a named query to delete a user by ID.
            Query query = entityManager.createNamedQuery("deleteUserById");
            query.setParameter("userId", id);

            // Get the number of deleted rows after executing the query.
            int deletedRows = query.executeUpdate();

            // Log a warning if the customer was not found for deletion.
            if (deletedRows == 0) {
                LOGGER.warning("CustomerManager: Customer not found for deletion.");
            } else {
                LOGGER.info("CustomerManager: Customer deleted.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "CustomerManager: Exception deleting customer.", e);
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Inserts a new user (customer) into the system.
     *
     * @param user The User object representing the new user.
     * @throws CreateException If an error occurs during the creation process.
     */
    @Override
    public void insertUser(User user) throws CreateException {
        try {
            LOGGER.info("CustomerManager: Inserting user.");

            // Persist the user entity using the entity manager.
            entityManager.persist(user);

            LOGGER.info("CustomerManager: User inserted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "CustomerManager: Exception inserting user.", e);
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Retrieves customer information for a user identified by their user ID.
     *
     * @param userId The ID of the user for whom customer information is
     * requested.
     * @return The Customer object containing customer information.
     * @throws ReadException If an error occurs during the read process.
     */
    @Override
    public Customer getCustomer(Integer userId) throws ReadException {
        try {
            // Execute a named query to get a customer by user ID.
            Query query = entityManager.createNamedQuery("getCustomer")
                    .setParameter("userId", userId);

            // Return the single result as a Customer object.
            return (Customer) query.getSingleResult();
        } catch (Exception e) {
            throw new ReadException("Error getting customer");
        }
    }
}
