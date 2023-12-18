package rest;

import ejb.local.ProductsBoughtManagerEJBLocal;
import entities.Product;
import exceptions.ReadException;
import exceptions.UpdateException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    private ProductsBoughtManagerEJBLocal productsBoughtManager;

    /**
     * Handles the HTTP POST request for purchasing a product.
     *
     * @param product The Product object to be purchased.
     * @param amount The quantity of the product to be purchased.
     * @param customerId The ID of the customer making the purchase.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("{amount}/{customerId}")
    public void purchaseProduct(Product product,
            @PathParam("amount") Integer amount,
            @PathParam("customerId") Integer customerId) {
        try {
            productsBoughtManager.purchaseProduct(product, amount, customerId);
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
    @Path("{customerId}/{productId}/{amount}")
    public void updateAmount(@PathParam("customerId") Integer customerId,
            @PathParam("productId") Integer productId,
            @PathParam("amount") Integer amount) {
        try {
            productsBoughtManager.updateAmount(customerId, productId, amount);
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
    public List<Product> getProductsBought(@PathParam("customerId") String customerId) {
        try {
            return productsBoughtManager.getProductsBought(customerId);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error getting products bought", e);
            throw new InternalServerErrorException(e);
        }
    }
}
