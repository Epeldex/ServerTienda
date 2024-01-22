package rest;

import ejb.local.ProductManagerEJBLocal;
import ejb.local.ProductsBoughtManagerEJBLocal;
import entities.Product;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * This class provides CRUD (Create, Read, Update, Delete) operations for
 * Product entities using XML as the data format. It integrates with the
 * {@link ProductManagerEJBLocal} EJB for handling business logic.
 *
 *
 * @author Alexander Epelde
 */
@Path("products")
public class ProductREST {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("ProductREST");

    /**
     * EJB for managing Product entity CRUD operations.
     */
    @EJB
    private ProductManagerEJBLocal productEjb;
    
    /**
     * EJB for managing ProductsBought entity CRUD operations.
     */
    @EJB
    private ProductsBoughtManagerEJBLocal productsBoughtEjb;

    /**
     * Creates a new Product using XML data.
     *
     * @param product The {@link Product} object containing the product data.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Product product) {
        try {
            LOGGER.log(Level.INFO, "ProductRESTful service: create {0}.", product);
            productEjb.insertProduct(product);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, "ProductRESTful service: Exception creating product, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Updates an existing Product using XML data.
     *
     * @param product The {@link Product} object containing the updated product
     * data.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(Product product) {
        try {
            LOGGER.log(Level.INFO, "ProductRESTful service: update {0}.", product);
            productEjb.updateProduct(product);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, "ProductRESTful service: Exception updating product, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Deletes a Product by its ID.
     *
     * @param id The ID of the Product to be deleted.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "ProductRESTful service: delete Product by id={0}.", id);
            productsBoughtEjb.deleteByProductId(id);
            productEjb.deleteProduct(id);
        } catch (DeleteException ex) {
            LOGGER.log(Level.SEVERE, "ProductRESTful service: Exception deleting product by id, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Retrieves a Product by its ID.
     *
     * @param id The ID of the Product to be retrieved.
     * @return The retrieved {@link Product} object.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Product find(@PathParam("id") Integer id) {
        Product product = null;
        try {
            LOGGER.log(Level.INFO, "ProductRESTful service: find Product by id={0}.", id);
            product = productEjb.selectProductById(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "ProductRESTful service: Exception reading product by id, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return product;
    }

    /**
     * Retrieves all Products.
     *
     * @return A List of {@link Product} objects representing all products.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findAll() {
        List<Product> products = null;
        try {
            LOGGER.log(Level.INFO, "ProductRESTful service: find all products.");
            products = productEjb.selectAllProducts();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "ProductRESTful service: Exception reading all products, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return products;
    }
}
