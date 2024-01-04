package ejb;

import ejb.local.ProductsBoughtManagerEJBLocal;
import entities.Customer;
import entities.ProductsBought;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EJB class for managing products bought by customers. This class implements
 * the local interface ProductsBoughtManagerEJBLocal. It handles purchasing
 * products, updating product amounts, and retrieving products bought by a
 * customer.
 *
 * @author Alex Irusta
 */
@Stateless
public class ProductsBoughtManagerEJB implements ProductsBoughtManagerEJBLocal {

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
     * Purchases a product for a customer by updating the product amount and
     * associated customer's balance.
     *
     * @param productBought The product to be purchased.
     * @throws UpdateException If an error occurs during the update process.
     */
    @Override
    public void purchaseProduct(ProductsBought productBought) throws UpdateException {
        LOGGER.info("ProductsBoughtManager: Purchasing product.");
        try {
            if (em.find(ProductsBought.class, productBought.getId()) != null) {
                em.merge(productBought);
            } else {
                em.persist(productBought);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductsBoughtManager: Exception purchasing product.{0}", e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Updates the amount of a purchased product for a customer.
     *
     * @param customerId The ID of the customer.
     * @param product_id The ID of the product.
     * @param amount The quantity to update the product amount.
     * @throws UpdateException If an error occurs during the update process.
     */
    @Override
    public void updateAmount(Integer customerId, Integer product_id, Integer amount) throws UpdateException {
        LOGGER.info("ProductsBoughtManager: Updating product amount.");
        try {
            em.createNamedQuery("updateAmount")
                    .setParameter("amount", amount)
                    .setParameter("customerId", customerId)
                    .setParameter("productId", product_id)
                    .executeUpdate();
            LOGGER.info("ProductsBoughtManager: Product amount updated.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductsBoughtManager: Exception updating product amount.{0}", e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of products bought by a customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of products bought by the customer.
     * @throws ReadException If an error occurs during the read process.
     */
    @Override
    public List<ProductsBought> getProductsBought(Integer customerId) throws ReadException {
        LOGGER.info("ProductsBoughtManager: Retrieving products bought by customer.");
        try {
            Query query = em.createNamedQuery("getProductsBought")
                    .setParameter("customerId", customerId);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductsBoughtManager: Exception retrieving products bought by customer.{0}", e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Deletes products bought by a customer based on the specified customer ID.
     *
     * @param customer_id The ID of the customer to delete products for.
     * @throws DeleteException If an error occurs during the delete process.
     */
    @Override
    public void deleteByCustomerId(Integer customer_id) throws DeleteException {
        LOGGER.info("ProductsBoughtManager: Deleting products bought by customer.");
        try {
            em.createNamedQuery("deleteByCustomerId")
                    .setParameter("customer_id", customer_id)
                    .executeUpdate();
            LOGGER.info("ProductsBoughtManager: Products bought by customer deleted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductsBoughtManager: Exception deleting products bought by customer.{0}", e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Deletes products bought by customers based on the specified product ID.
     *
     * @param product_id The ID of the product to delete.
     * @throws DeleteException If an error occurs during the delete process.
     */
    @Override
    public void deleteByProductId(Integer product_id) throws DeleteException {
        LOGGER.info("ProductsBoughtManager: Deleting products bought by product ID.");
        try {
            em.createNamedQuery("deleteByProductId")
                    .setParameter("product_id", product_id)
                    .executeUpdate();
            LOGGER.info("ProductsBoughtManager: Products bought by product ID deleted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ProductsBoughtManager: Exception deleting products bought by product ID.{0}", e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
}
