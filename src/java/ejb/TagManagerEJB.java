package ejb;

import ejb.local.TagManagerEJBLocal;
import entities.Tag;
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
 * EJB class for managing {@link Tag} entity CRUD operations.
 *
 * @author Alexander Epelde
 */
@Stateless
public class TagManagerEJB implements TagManagerEJBLocal {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("our_shop");

    /**
     * Entity manager object for interacting with the database.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Updates a {@link Tag}'s data in the underlying application storage.
     *
     * @param tag The {@link Tag} object containing the updated data.
     * @throws UpdateException If there is any exception during processing.
     */
    @Override
    public void updateTag(Tag tag) throws UpdateException {
        LOGGER.info("TagManager: Updating tag.");
        try {
            // Executes a named query to update {@link Tag} entity data.
            em.createNamedQuery("updateTag")
                    .setParameter("type", tag.getType())
                    .setParameter("label", tag.getLabel())
                    .setParameter("active", tag.getActive())
                    .setParameter("tagId", tag.getTag_id())
                    .executeUpdate();
            LOGGER.info("TagManager: Tag updated.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TagManager: Exception updating tag.", e);
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Deletes a {@link Tag}'s data in the underlying application storage.
     *
     * @param tagId The ID of the {@link Tag} to be deleted.
     * @throws DeleteException If there is any exception during processing.
     */
    @Override
    public void deleteTag(Integer tagId) throws DeleteException {
        LOGGER.info("TagManager: Deleting tag.");
        try {
            // Executes a named query to delete {@link Tag} entity data.
            em.createNamedQuery("deleteTag")
                    .setParameter("tagId", tagId)
                    .executeUpdate();
            LOGGER.info("TagManager: Tag deleted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TagManager: Exception deleting tag.", e);
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Retrieves a List of all {@link Tag} objects from the underlying
     * application storage.
     *
     * @return A List of {@link Tag} objects.
     * @throws ReadException If there is any exception during processing.
     */
    @Override
    public List<Tag> selectAllTags() throws ReadException {
        List<Tag> tags = null;
        try {
            LOGGER.info("TagManager: Selecting all tags.");
            // Executes a named query to select all {@link Tag} entities.
            tags = em.createNamedQuery("selectAllTags", Tag.class).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TagManager: Exception selecting all tags.", e);
            throw new ReadException(e.getMessage());
        }
        return tags;
    }

    /**
     * Retrieves a {@link Tag} object by its ID from the underlying application
     * storage.
     *
     * @param tagId The ID of the {@link Tag} to be retrieved.
     * @return The {@link Tag} object with the specified ID.
     * @throws ReadException If there is any exception during processing.
     */
    @Override
    public Tag selectTagById(Integer tagId) throws ReadException {
        Tag tag = null;
        try {
            LOGGER.info("TagManager: Selecting tag by ID.");
            // Executes a named query to select a {@link Tag} entity by ID.
            tag = em.createNamedQuery("selectTagById", Tag.class)
                    .setParameter("tagId", tagId)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TagManager: Exception selecting tag by ID.", e);
            throw new ReadException(e.getMessage());
        }
        return tag;
    }

    /**
     * Inserts a new {@link Tag} into the underlying application storage.
     *
     * @param tag The {@link Tag} object to be inserted.
     * @throws CreateException If there is any exception during processing.
     */
    @Override
    public void insertTag(Tag tag) throws CreateException {
        LOGGER.info("TagManager: Inserting tag.");
        try {
            // Persists a new {@link Tag} entity.
            em.persist(tag);
            LOGGER.info("TagManager: Tag inserted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TagManager: Exception inserting tag.", e);
            throw new CreateException(e.getMessage());
        }
    }
}
