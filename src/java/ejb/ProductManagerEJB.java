package ejb;

import ejb.local.ProductManagerEJBLocal;
import entities.Product;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stateless EJB for managing products in the data store. Implements the
 * ProductManagerEJBLocal interface. Uses an EntityManager to interact with the
 * persistence context.
 *
 * @author Alexander Epelde
 */
@Stateless
public class ProductManagerEJB implements ProductManagerEJBLocal {

    /**
     * Logger for logging information related to product management.
     */
    private static final Logger LOGGER = Logger.getLogger("our_shop");

    /**
     * Entity Manager for interacting with the persistence context.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Updates the information of a product in the data store.
     *
     * @param product The Product entity object containing updated product data.
     * @throws UpdateException Thrown when an error occurs during product
     * update.
     */
    @Override
    public void updateProduct(Product product) throws UpdateException {
        LOGGER.info("ProductManager: Updating product.");
        try {
            em.merge(product);
            LOGGER.info("ProductManager: Product updated.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception updating product.{0}", e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Deletes a product from the data store based on the product ID.
     *
     * @param productId The ID of the product to be deleted.
     * @throws DeleteException Thrown when an error occurs during product
     * deletion.
     */
    @Override
    public void deleteProduct(Integer productId) throws DeleteException {
        LOGGER.info("ProductManager: Deleting product.");
        try {
            em.createNamedQuery("deleteProduct").setParameter("productId", productId).executeUpdate();
            LOGGER.info("ProductManager: Product deleted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception deleting product.{0}", e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of all products from the data store.
     *
     * @return A List of Product entity objects.
     * @throws ReadException Thrown when an error occurs during product
     * retrieval.
     */
    @Override
    public List<Product> selectAllProducts() throws ReadException {
        LOGGER.info("ProductManager: Selecting all products.");
        try {
            return em.createNamedQuery("selectAllProducts").getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception selecting all products.{0}", e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Retrieves a product from the data store based on the product ID.
     *
     * @param productId The ID of the product to be retrieved.
     * @return A Product entity object containing product data.
     * @throws ReadException Thrown when an error occurs during product
     * retrieval.
     */
    @Override
    public Product selectProductById(Integer productId) throws ReadException {
        LOGGER.info("ProductManager: Selecting product by ID.");
        try {
            return (Product) em.createNamedQuery("selectProductById")
                    .setParameter("product_id", productId)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception selecting product by ID.{0}", e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Inserts a new product into the data store.
     *
     * @param product The Product entity object containing new product data.
     * @throws CreateException Thrown when an error occurs during product
     * insertion.
     */
    @Override
    public void insertProduct(Product product) throws CreateException {
        LOGGER.info("ProductManager: Inserting product.");
        try {
            em.persist(product);
            LOGGER.info("ProductManager: Product inserted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception inserting product.{0}", e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of product IDs associated with a given tag ID.
     *
     * @param tagId The ID of the tag to filter products.
     * @return A List of Integer representing product IDs.
     * @throws ReadException Thrown when an error occurs during product ID
     * retrieval.
     */
    @Override
    public List<Integer> selectProductWithTagId(Integer tagId) throws ReadException {
        LOGGER.info("ProductManager: Selecting product IDs with tag ID.");
        try {
            return em.createNamedQuery("selectProductWithTagId")
                    .setParameter("tag_id", tagId)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception selecting product IDs with tag ID.{0}", e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of product IDs associated with a given supplier ID.
     *
     * @param supplierId The ID of the supplier to filter products.
     * @return A List of Integer representing product IDs.
     * @throws ReadException Thrown when an error occurs during product ID
     * retrieval.
     */
    @Override
    public List<Integer> selectProductWithSupplierId(Integer supplierId) throws ReadException {
        LOGGER.info("ProductManager: Selecting product IDs with supplier ID.");
        try {
            return em.createNamedQuery("selectProductWithSupplierId")
                    .setParameter("supplier_id", supplierId)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception selecting product IDs with supplier ID.{0}", e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Deletes products associated with a given tag ID from the data store.
     *
     * @param tagId The ID of the tag to filter products for deletion.
     * @throws DeleteException Thrown when an error occurs during product
     * deletion by tag ID.
     */
    @Override
    public void deleteProductByTagId(Integer tagId) throws DeleteException {
        LOGGER.info("ProductManager: Deleting products by tag ID.");
        try {
            em.createNamedQuery("deleteProductByTagId").setParameter("tag_id", tagId).executeUpdate();
            LOGGER.info("ProductManager: Products deleted by tag ID.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception deleting products by tag ID.{0}", e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Deletes products associated with a given supplier ID from the data store.
     *
     * @param supplierId The ID of the supplier to filter products for deletion.
     * @throws DeleteException Thrown when an error occurs during product
     * deletion by supplier ID.
     */
    @Override
    public void deleteProductBySupplierId(Integer supplierId) throws DeleteException {
        LOGGER.info("ProductManager: Deleting products by supplier ID.");
        try {
            em.createNamedQuery("deleteProductBySupplierId").setParameter("supplier_id", supplierId).executeUpdate();
            LOGGER.info("ProductManager: Products deleted by supplier ID.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception deleting products by supplier ID.{0}", e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
}
