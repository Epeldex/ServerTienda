package rest;

import ejb.local.ProductManagerEJBLocal;
import ejb.local.ProductsBoughtManagerEJBLocal;
import ejb.local.SupplierManagerEJBLocal;
import ejb.local.TagManagerEJBLocal;
import entities.Supplier;
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
 * Supplier entities using XML as the data format. It integrates with the
 * {@link SupplierManagerEJBLocal} EJB for handling business logic.
 *
 * @author Alexander Epelde
 */
@Path("suppliers")
public class SupplierREST {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("our_shop");

    /**
     * EJB for managing Supplier entity CRUD operations.
     */
    @EJB
    private SupplierManagerEJBLocal supplierEjb;

    @EJB
    private ProductManagerEJBLocal productEjb;

    @EJB
    private ProductsBoughtManagerEJBLocal productBoughtEjb;

    /**
     * Creates a new Supplier using XML data.
     *
     * @param supplier The {@link Supplier} object containing the supplier data.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void create(Supplier supplier) {
        try {
            LOGGER.log(Level.INFO, "SupplierRESTful service: create {0}.", supplier);
            supplierEjb.insertSupplier(supplier);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, "SupplierRESTful service: Exception creating supplier, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Updates an existing Supplier using XML data.
     *
     * @param supplier The {@link Supplier} object containing the updated
     * supplier data.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void update(Supplier supplier) {
        try {
            LOGGER.log(Level.INFO, "SupplierRESTful service: update {0}.", supplier);
            supplierEjb.updateSupplier(supplier);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, "SupplierRESTful service: Exception updating supplier, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Deletes a Supplier by its ID.
     *
     * @param id The ID of the Supplier to be deleted.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "SupplierRESTful service: delete Supplier by id={0}.", id);
            // Find all the product of that supplier
            for (Integer product_id : productEjb.selectProductWithSupplierId(id)) {
                productBoughtEjb.deleteByProductId(product_id); // Delete all the products bought of that product
            }
            productEjb.deleteProductBySupplierId(id); // Delete the products with of said Supplier
            supplierEjb.deleteSupplier(id);// Delete the tag itself 
        } catch (DeleteException | ReadException ex) {
            LOGGER.log(Level.SEVERE, "SupplierRESTful service: Exception deleting supplier by id, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Retrieves a Supplier by its ID.
     *
     * @param id The ID of the Supplier to be retrieved.
     * @return The retrieved {@link Supplier} object.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Supplier find(@PathParam("id") Integer id) {
        Supplier supplier = null;
        try {
            LOGGER.log(Level.INFO, "SupplierRESTful service: find Supplier by id={0}.", id);
            supplier = supplierEjb.selectSupplierById(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "SupplierRESTful service: Exception reading supplier by id, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return supplier;
    }

    /**
     * Retrieves all Suppliers.
     *
     * @return A List of {@link Supplier} objects representing all suppliers.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Supplier> findAll() {
        List<Supplier> suppliers = null;
        try {
            LOGGER.log(Level.INFO, "SupplierRESTful service: find all suppliers.");
            suppliers = supplierEjb.selectAllSuppliers();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "SupplierRESTful service: Exception reading all suppliers, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return suppliers;
    }
}
