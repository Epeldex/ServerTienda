package ejb.local;

import exceptions.CreateException;
import exceptions.UpdateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import entities.Product;

import javax.ejb.Local;
import java.util.List;

/**
 * EJB Local Interface for managing Product entity CRUD operations.
 *
 * @author Alexander Epelde
 */
@Local
public interface ProductManagerEJBLocal {

    /**
     * Updates a product's information in the underlying application storage.
     *
     * @param product The {@link Product} object containing the details of the
     * product to update.
     * @throws UpdateException If there is any exception during processing.
     */
    public void updateProduct(Product product) throws UpdateException;

    /**
     * Deletes a product from the underlying application storage.
     *
     * @param productId The ID of the product to be deleted.
     * @throws DeleteException If there is any exception during processing.
     */
    public void deleteProduct(Integer productId) throws DeleteException;

    /**
     * Retrieves a list of all products from the application data storage.
     *
     * @return A List of {@link Product} objects.
     * @throws ReadException If there is any exception during processing.
     */
    public List<Product> selectAllProducts() throws ReadException;

    /**
     * Retrieves a product by its ID from the application data storage.
     *
     * @param productId The ID of the product to be retrieved.
     * @return The {@link Product} object containing product data.
     * @throws ReadException If there is any exception during processing.
     */
    public Product selectProductById(Integer productId) throws ReadException;

    /**
     * Inserts a new product into the underlying application storage.
     *
     * @param product The {@link Product} object containing the details of the
     * new product.
     * @throws CreateException If there is any exception during processing.
     */
    public void insertProduct(Product product) throws CreateException;

}
