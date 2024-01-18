package rest;

import ejb.local.CustomerManagerEJBLocal;
import ejb.local.ProductsBoughtManagerEJBLocal;
import entities.Customer;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The CustomerREST class represents a RESTful web service for managing
 * customer-related operations. It exposes endpoints for updating customer
 * information, deleting a customer, inserting a new customer, and retrieving
 * customer details.
 *
 * @author Alex Irusta
 */
@Path("customers")
public class CustomerREST {

    private static final Logger LOGGER = Logger.getLogger("CustomerREST");

    @EJB
    private CustomerManagerEJBLocal customerEjb;  // EJB for managing customer-related operations

    @EJB
    private ProductsBoughtManagerEJBLocal productBoughtEjb;  // EJB for managing products bought by customers

    /**
     * Handles the HTTP PUT request for updating customer information.
     *
     * @param customer The Customer object containing updated information.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void updateCustomerInfo(Customer customer) {
        try {
            LOGGER.info("CustomerREST service: Updating customer information.");
            customerEjb.updateCustomer(customer);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, "CustomerREST service: Exception updating customer information.", ex);
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Handles the HTTP DELETE request for deleting a customer by ID.
     *
     * @param id The ID of the customer to be deleted.
     */
    @DELETE
    @Path("{id}")
    public void deleteCustomer(@PathParam("id") Integer id) {
        try {
            LOGGER.info("CustomerREST service: Deleting customer with id=" + id);
            productBoughtEjb.deleteByCustomerId(id);
            customerEjb.deleteCustomerById(id);
        } catch (DeleteException ex) {
            LOGGER.log(Level.SEVERE, "CustomerREST service: Exception deleting customer.", ex);
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Handles the HTTP POST request for inserting a new customer.
     *
     * @param customer The Customer object containing information for the new
     * customer.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void insertCustomer(Customer customer) {
        try {
            LOGGER.info("CustomerREST service: Inserting customer.");
            customerEjb.insertCustomer(customer);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, "CustomerREST service: Exception inserting customer.", ex);
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Handles the HTTP GET request for retrieving customer details by ID.
     *
     * @param userId The ID of the customer to retrieve.
     * @return Response containing the retrieved Customer object or an error
     * message.
     */
    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_XML)
    public Customer getCustomer(@PathParam("userId") Integer userId) {
        try {
            LOGGER.info("CustomerREST service: Get customer with id=" + userId);
            return customerEjb.getCustomer(userId);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "CustomerREST service: Exception getting customer.", ex);
            throw new InternalServerErrorException(ex);
        }
    }
}
