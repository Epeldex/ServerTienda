package ejb;

import ejb.local.ProductManagerEJBLocal;
import entities.Product;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB class for managing Product entity CRUD operations.
 *
 * @author Alexander Epelde
 */
@Stateless
public class ProductManagerEJB implements ProductManagerEJBLocal {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("our_shop");

    /**
     * Entity manager object for handling persistence operations.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Updates product information in the underlying application storage.
     *
     * @param product The Product object containing updated data.
     * @throws UpdateException If there is any Exception during processing.
     */
    @Override
    public void updateProduct(Product product) throws UpdateException {
        LOGGER.info("ProductManager: Updating product.");
        try {
            em.createNamedQuery("updateProductByObject")
                    .setParameter("brand", product.getBrand())
                    .setParameter("model", product.getModel())
                    .setParameter("otherInfo", product.getOtherInfo())
                    .setParameter("weight", product.getWeight())
                    .setParameter("description", product.getDescription())
                    .setParameter("price", product.getPrice())
                    .setParameter("productId", product.getProduct_id())
                    .executeUpdate();
            LOGGER.info("ProductManager: Product updated.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception updating product.{0}", e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Deletes a product from the underlying application storage.
     *
     * @param productId The ID of the product to be deleted.
     * @throws DeleteException If there is any Exception during processing.
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
     * Retrieves a list of all products from the underlying application storage.
     *
     * @return A List of Product objects.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public List<Product> selectAllProducts() throws ReadException {
        LOGGER.info("ProductManager: Selecting all products.");
        try {
            return em.createNamedQuery("selectAllProducts", Product.class).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception selecting all products.{0}", e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Retrieves a product by its ID from the underlying application storage.
     *
     * @param productId The ID of the product to be retrieved.
     * @return The Product object containing product data.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public Product selectProductById(Integer productId) throws ReadException {
        LOGGER.info("ProductManager: Selecting product by ID.");
        try {
            return em.createNamedQuery("selectProductById", Product.class)
                    .setParameter("product_id", productId)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception selecting product by ID.{0}", e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Inserts a new product into the underlying application storage.
     *
     * @param product The Product object containing the new product data.
     * @throws CreateException If there is any Exception during processing.
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
}
