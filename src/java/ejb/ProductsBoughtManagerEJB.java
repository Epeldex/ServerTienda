package ejb;

import ejb.local.ProductsBoughtManagerEJBLocal;
import entities.Customer;
import entities.Product;
import entities.ProductsBought;
import entities.ProductsBoughtId;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EJB (Enterprise JavaBeans) session bean responsible for managing products
 * bought by customers. This bean provides methods for purchasing products,
 * updating product amounts, and retrieving products bought by a customer.
 *
 * @author Alex Irusta
 */
@Stateless
public class ProductsBoughtManagerEJB implements ProductsBoughtManagerEJBLocal {

    private static final Logger LOGGER = Logger.getLogger("ejb");

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Purchases a product for a customer by creating a new ProductsBought
     * entity and updating the customer's balance.
     *
     * @param product The product to be purchased.
     * @param amount The quantity of the product to be purchased.
     * @param customerId The ID of the customer making the purchase.
     * @throws UpdateException If an error occurs during the update process.
     */
    @Override
    public void purchaseProduct(Product product, Integer amount, Integer customerId) throws UpdateException {
        try {
            // Find the associated Customer
            Customer customer = (Customer) entityManager.createNamedQuery("getCustomer")
                    .setParameter("id", customerId).getSingleResult();

            // Create a new ProductsBought instance with the composite key
            ProductsBought productsBought = new ProductsBought();
            productsBought.setAmount(amount);
            productsBought.setBoughtTimestamp(new Date());

            // Persist the ProductsBought entity
            entityManager.persist(productsBought);

            // Update the customer's balance using the named query
            Query updateQuery = entityManager.createNamedQuery("purchaseProduct")
                    .setParameter("price", product.getPrice())
                    .setParameter("customerId", customerId);
            updateQuery.executeUpdate();

        } catch (Exception e) {
            throw new UpdateException("Error purchasing product");
        }
    }

    /**
     * Updates the amount of a purchased product for a customer.
     *
     * @param customerId The ID of the customer.
     * @param productId The ID of the product.
     * @param amount The quantity to update the product amount.
     * @throws UpdateException If an error occurs during the update process.
     */
    @Override
    public void updateAmount(Integer customerId, Integer productId, Integer amount) throws UpdateException {
        try {
            // Find the ProductsBought entity
            ProductsBought productsBought = entityManager.find(ProductsBought.class, productId);

            // Update the amount
            productsBought.setAmount(productsBought.getAmount() + amount);

            // No persist or merge needed
            // Return the associated Product
            Query updateQuery = entityManager.createNamedQuery("updateAmount")
                    .setParameter("productId", productsBought.getId())
                    .setParameter("amount", productsBought.getAmount())
                    .setParameter("customerId", productsBought.getCustomer().getId());
            updateQuery.executeUpdate();

        } catch (Exception e) {
            throw new UpdateException("Error updating amount");
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
        try {
            LOGGER.log(Level.INFO, "Getting products bought");
            // Execute the named query to get products bought by a customer
            return entityManager.createNamedQuery("getProductsBought")
                    .setParameter("customerId", customerId).getResultList();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting products bought", e);
            throw new ReadException("Error gettv ing products bought");
        }
    }
}
