package rest;

import ejb.local.CustomerManagerEJBLocal;
import ejb.local.ProductsBoughtManagerEJBLocal;
import ejb.local.UserManagerEJBLocal;
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
 * information, deleting a customer, inserting a new user, and retrieving
 * customer details.
 *
 * @author Alex Irusta
 *
 */
@Path("customers")
public class CustomerREST {

    private static final Logger LOGGER = Logger.getLogger("ejb");

    @EJB
    private CustomerManagerEJBLocal customerEjb;

    @EJB
    private UserManagerEJBLocal userEjb;

    @EJB
    private ProductsBoughtManagerEJBLocal productBoughtEjb;

    /**
     * Handles the HTTP PUT request for updating customer information.
     *
     * @param customer The Customer object containing updated information.
     * @return Response indicating the success or failure of the operation.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void updatePersonalInfo(Customer customer) {
        try {
            LOGGER.info("CustomerRESTful service: Updating customer information.");
            customerEjb.updateCustomer(customer);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, "CustomerRESTful service: Exception updating customer information.", ex);
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Handles the HTTP DELETE request for deleting a customer by ID.
     *
     * @param id The ID of the customer to be deleted.
     * @return Response indicating the success or failure of the operation.
     */
    @DELETE
    @Path("{id}")
    public void deleteCustomer(@PathParam("id") Integer id) {
        try {
            LOGGER.info("CustomerRESTful service: Deleting customer by id=" + id);
            productBoughtEjb.deleteByCustomerId(id);
            customerEjb.deleteCustomerById(id);
        } catch (DeleteException ex) {
            LOGGER.log(Level.SEVERE, "CustomerRESTful service: Exception deleting customer.", ex);
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Handles the HTTP POST request for inserting a new user.
     *
     * @param user The User object containing information for the new user.
     * @return Response indicating the success or failure of the operation.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void insertCustomer(Customer customer) {
        try {
            LOGGER.info("CustomerRESTful service: Inserting user.");
            customerEjb.insertUser(customer);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, "CustomerRESTful service: Exception inserting user.", ex);
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Handles the HTTP GET request for retrieving customer details by ID.
     *
     * @param userId The ID of the customer to retrieve.
     * @return Response containing the retrieved Customer object or an error
     *         message.
     */
    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_XML)
    public Customer getCustomer(@PathParam("userId") Integer userId) {
        try {
            LOGGER.info("CustomerRESTful service: Get customer by id=" + userId);
            return customerEjb.getCustomer(userId);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "CustomerRESTful service: Exception getting customer.", ex);
            throw new InternalServerErrorException(ex);
        }
    }
}
