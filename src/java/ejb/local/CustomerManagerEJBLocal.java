package ejb.local;

import entities.Customer;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import javax.ejb.Local;

/**
 * Local interface for an EJB (Enterprise JavaBeans) session bean responsible
 * for managing customer data. This interface defines methods for updating
 * personal information, deleting a user, inserting a new user, retrieving
 * customer information, and updating customer balance.
 *
 * @author Alex Irusta
 */
@Local
public interface CustomerManagerEJBLocal {

    /**
     * Updates the personal information of a customer identified by their user
     * ID.
     *
     * @param customer The Customer object containing the updated personal
     * information.
     * @throws UpdateException If an error occurs during the update process.
     */
    public void updateCustomer(Customer customer) throws UpdateException;

    /**
     * Deletes a Customer identified by their ID.
     *
     * @param id The ID of the customer to be deleted.
     * @throws DeleteException If an error occurs during the delete process.
     */
    public void deleteCustomerById(Integer id) throws DeleteException;

    /**
     * Obtains a Customer by email.
     *
     * @param email The email of the customer that requests it.
     * @throws UpdateException If an error occurs during the process.
     */
    public void resetPassword(String email, String password) throws UpdateException;

    /**
     * Inserts a new user (customer) into the system.
     *
     * @param customer The Customer object representing the new customer.
     * @throws CreateException If an error occurs during the creation process.
     */
    public void insertCustomer(Customer customer) throws CreateException;

    /**
     * Retrieves customer information for a user identified by their user ID.
     *
     * @param userId The ID of the user for whom customer information is
     * requested.
     * @return The Customer object containing the requested customer
     * information.
     * @throws ReadException If an error occurs during the read process.
     */
    public Customer getCustomer(Integer userId) throws ReadException;

    /**
     * Updates the balance of a customer identified by their ID.
     *
     * @param balance The new balance to be set for the customer.
     * @param customerId The ID of the customer for whom the balance is updated.
     * @throws UpdateException If an error occurs during the update process.
     */
    public void updateBalance(Double balance, Integer customerId) throws UpdateException;
    
    public Customer findCustomerByMail(String mail) throws ReadException;
}
