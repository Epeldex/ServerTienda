package ejb;

import ejb.local.SupplierManagerEJBLocal;
import entities.Supplier;
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
import javax.persistence.Query;

/**
 * EJB class for managing {@link Supplier} entity CRUD operations.
 *
 * @author Alexander Epelde
 */
@Stateless
public class SupplierManagerEJB implements SupplierManagerEJBLocal {

    /**
     * Logger for class methods.
     */
    private static final Logger LOGGER = Logger.getLogger("our_shop");

    /**
     * Persistence context for managing entities.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Updates a Supplier entity.
     *
     * @param supplier The {@link Supplier} object to be updated.
     * @throws UpdateException If an exception occurs during the update
     * operation.
     */
    @Override
    public void updateSupplier(Supplier supplier) throws UpdateException {
        LOGGER.info("SupplierManager: Updating supplier.");
        try {
            em.createNamedQuery("updateSupplier")
                    .setParameter("name", supplier.getName())
                    .setParameter("phone", supplier.getPhone())
                    .setParameter("country", supplier.getCountry())
                    .setParameter("zip", supplier.getZip())
                    .setParameter("supplierId", supplier.getSupplier_id())
                    .executeUpdate();
            LOGGER.info("SupplierManager: Supplier updated.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "SupplierManager: Exception updating supplier.{0}", e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Deletes a Supplier entity based on the supplied ID.
     *
     * @param supplierId The ID of the {@link Supplier} to be deleted.
     * @throws DeleteException If an exception occurs during the delete
     * operation.
     */
    @Override
    public void deleteSupplier(Integer supplierId) throws DeleteException {
        LOGGER.info("SupplierManager: Deleting supplier.");
        try {
            // Executes a named query to delete {@link Supplier} entity data.
            em.createNamedQuery("deleteSupplier")
                    .setParameter("supplierId", supplierId)
                    .executeUpdate();
            LOGGER.info("SupplierManager: Supplier deleted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "SupplierManager: Exception deleting supplier.", e);
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of all {@link Supplier} entities.
     *
     * @return A List of {@link Supplier} objects.
     * @throws ReadException If an exception occurs during the read operation.
     */
    @Override
    public List<Supplier> selectAllSuppliers() throws ReadException {
        List<Supplier> suppliers = null;
        try {
            LOGGER.info("SupplierManager: Selecting all suppliers.");
            Query query = em.createNamedQuery("selectAllSuppliers");
            suppliers = query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "SupplierManager: Exception selecting all suppliers.{0}", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return suppliers;
    }

    /**
     * Retrieves a {@link Supplier} entity based on the supplied ID.
     *
     * @param supplierId The ID of the {@link Supplier} to be retrieved.
     * @return The {@link Supplier} object with the specified ID.
     * @throws ReadException If an exception occurs during the read operation.
     */
    @Override
    public Supplier selectSupplierById(Integer supplierId) throws ReadException {
        Supplier supplier = null;
        try {
            LOGGER.info("SupplierManager: Selecting supplier by ID.");
            Query query = em.createNamedQuery("selectSupplierById");
            query.setParameter("supplierId", supplierId);
            supplier = (Supplier) query.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "SupplierManager: Exception selecting supplier by ID.{0}", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return supplier;
    }

    /**
     * Inserts a new {@link Supplier} entity.
     *
     * @param supplier The {@link Supplier} object to be inserted.
     * @throws CreateException If an exception occurs during the create
     * operation.
     */
    @Override
    public void insertSupplier(Supplier supplier) throws CreateException {
        LOGGER.info("SupplierManager: Inserting supplier.");
        try {
            em.persist(supplier);
            LOGGER.info("SupplierManager: Supplier inserted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "SupplierManager: Exception inserting supplier.{0}", e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }
}
