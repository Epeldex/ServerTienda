package rest;

import ejb.local.CustomerManagerEJBLocal;
import ejb.local.ProductsBoughtManagerEJBLocal;
import entities.Customer;
import entities.ProductsBought;
import exceptions.ReadException;
import exceptions.UpdateException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ProductsBoughtREST class represents a RESTful web service for managing
 * operations related to purchased products. It exposes endpoints for purchasing
 * a product, updating the amount of a purchased product, and retrieving a list
 * of products bought by a customer.
 *
 * @author Alex Irusta
 */
@Path("productsBought")
public class ProductsBoughtREST {

    private static final Logger LOGGER = Logger.getLogger("ProductsBoughtREST");

    @EJB
    private ProductsBoughtManagerEJBLocal productsBoughtEjb;  // EJB for managing products bought operations

    @EJB
    private CustomerManagerEJBLocal customerEjb;  // EJB for managing customer-related operations

    /**
     * Handles the HTTP POST request for purchasing a product.
     *
     * @param customer The Customer object representing the customer purchasing
     * the product.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void purchaseProduct(Customer customer) {
        try {
            LOGGER.info("ProductsBoughtREST service: Purchasing product");
            customerEjb.updateBalance(customer.getBalance(), customer.getId());
            for (ProductsBought pb : customer.getProductsBought()) {
                productsBoughtEjb.purchaseProduct(pb);
            }
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "ProductsBoughtREST service: Error purchasing product", e);
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Handles the HTTP PUT request for updating the amount of a purchased
     * product.
     *
     * @param productBought The ProductsBought object containing information
     * about the purchased product.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateAmount(ProductsBought productBought) {
        try {
            productsBoughtEjb.updateAmount(productBought.getAmount(), productBought.getId().getCustomerId(), productBought.getId().getProductId());
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "ProductsBoughtREST service: Error updating amount", e);
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Handles the HTTP GET request for retrieving a list of products bought by
     * a customer.
     *
     * @param customerId The ID of the customer for whom to retrieve the
     * purchased products.
     * @return List of ProductsBought representing the products bought by the
     * customer.
     */
    @GET
    @Path("{customerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ProductsBought> getProductsBought(@PathParam("customerId") Integer customerId) {
        try {
            LOGGER.info("ProductsBoughtREST service: Get products bought by customer with id=" + customerId);
            return productsBoughtEjb.getProductsBought(customerId);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "ProductsBoughtREST service: Error getting products bought", e);
            e.printStackTrace();
            throw new InternalServerErrorException(e);
        }
    }
}
