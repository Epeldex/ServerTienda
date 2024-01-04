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
 *
 */
@Path("productsBought")
public class ProductsBoughtREST {

    private static final Logger LOGGER = Logger.getLogger("ourshop.ejb");

    @EJB
    private ProductsBoughtManagerEJBLocal productsBoughtEjb;

    @EJB
    private CustomerManagerEJBLocal customerEjb;

    /**
     * Handles the HTTP POST request for purchasing a product.
     *
     * @param productBought
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void purchaseProduct(Customer customer) {
        try {
            LOGGER.info("Purchasing product");
            customerEjb.updateBalance(customer.getBalance(), customer.getId());
            for (ProductsBought pb : customer.getProductsBought()) {
                productsBoughtEjb.purchaseProduct(pb);
            }
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Error purchasing product", e);
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Handles the HTTP PUT request for updating the amount of a purchased
     * product.
     *
     * @param customerId The ID of the customer who purchased the product.
     * @param productId The ID of the product to be updated.
     * @param amount The new quantity of the purchased product.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void updateAmount(ProductsBought productBought) {
        try {
            productsBoughtEjb.updateAmount(productBought.getAmount(), productBought.getId().getCustomerId(), productBought.getId().getProductId());
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Error updating amount", e);
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Handles the HTTP GET request for retrieving a list of products bought by
     * a customer.
     *
     * @param customerId The ID of the customer for whom to retrieve the
     * purchased products.
     * @return Response containing the list of products bought or an error
     * message.
     */
    @GET
    @Path("{customerId}")
    @Produces(MediaType.APPLICATION_XML)
    public List<ProductsBought> getProductsBought(@PathParam("customerId") Integer customerId) {
        try {
            return productsBoughtEjb.getProductsBought(customerId);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error getting products bought", e);
            e.printStackTrace();
            throw new InternalServerErrorException(e);
        }
    }
}
