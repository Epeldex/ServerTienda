package rest;

import ejb.ProductsBoughtManagerEJBLocal;
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
@Path("/productsBought")
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
     * @return Response indicating the success or failure of the operation.
     */
    @POST
    @Path("/purchaseProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response purchaseProduct(Product product, Integer amount, Integer customerId) {
        try {
            productsBoughtManager.purchaseProduct(product, amount, customerId);
            return Response.ok().build();
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Error purchasing product", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Handles the HTTP PUT request for updating the amount of a purchased
     * product.
     *
     * @param customerId The ID of the customer who purchased the product.
     * @param productId The ID of the product to be updated.
     * @param amount The new quantity of the purchased product.
     * @return Response indicating the success or failure of the operation.
     */
    @PUT
    @Path("/updateAmount")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAmount(Integer customerId, Integer productId, Integer amount) {
        try {
            productsBoughtManager.updateAmount(customerId, productId, amount);
            return Response.ok().build();
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Error updating amount", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
    @Path("/getProductsBought/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsBought(@PathParam("customerId") String customerId) {
        try {
            List<Product> productsBought = productsBoughtManager.getProductsBought(customerId);
            return Response.ok(productsBought).build();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error getting products bought", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
