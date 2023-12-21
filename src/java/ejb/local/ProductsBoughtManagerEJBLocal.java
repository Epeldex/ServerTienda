package ejb.local;

import entities.Customer;
import entities.Product;
import entities.ProductsBought;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;

/**
 * Local interface for an EJB (Enterprise JavaBeans) session bean responsible
 * for managing products bought by customers. This interface defines methods for
 * purchasing products, updating product amounts, and retrieving products bought
 * by a customer.
 *
 * @author Alex Irusta
 */
public interface ProductsBoughtManagerEJBLocal {

    /**
     * Purchases a product for a customer by updating the product amount and
     * associated customer's balance.
     *
     * @param product The product to be purchased.
     * @param amount The quantity of the product to be purchased.
     * @param customer_id The ID of the customer making the purchase.
     * @throws UpdateException If an error occurs during the update process.
     */
    public void purchaseProduct(Product product, Integer amount, Integer customer_id) throws UpdateException;

    /**
     * Updates the amount of a purchased product for a customer.
     *
     * @param customerId The ID of the customer.
     * @param product_id The ID of the product.
     * @param amount The quantity to update the product amount.
     * @throws UpdateException If an error occurs during the update process.
     */
    public void updateAmount(Integer customerId, Integer product_id, Integer amount) throws UpdateException;

    /**
     * Retrieves a list of products bought by a customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of products bought by the customer.
     * @throws ReadException If an error occurs during the read process.
     */
    public List<ProductsBought> getProductsBought(Integer customerId) throws ReadException;
}
