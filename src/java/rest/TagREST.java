package rest;

import ejb.local.TagManagerEJBLocal;
import entities.Tag;
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
 * This class provides CRUD (Create, Read, Update, Delete) operations for Tag
 * entities using XML as the data format. It integrates with the
 * {@link TagManagerEJBLocal} EJB for handling business logic.
 *
 * @author Alexander Epelde
 */
@Path("tags")
public class TagREST {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("our_shop");

    /**
     * EJB for managing Tag entity CRUD operations.
     */
    @EJB
    private TagManagerEJBLocal ejb;

    /**
     * Creates a new Tag using XML data.
     *
     * @param tag The {@link Tag} object containing the tag data.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void create(Tag tag) {
        try {
            LOGGER.log(Level.INFO, "TagRESTful service: create {0}.", tag);
            ejb.insertTag(tag);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, "TagRESTful service: Exception creating tag, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Updates an existing Tag using XML data.
     *
     * @param tag The {@link Tag} object containing the updated tag data.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void update(Tag tag) {
        try {
            LOGGER.log(Level.INFO, "TagRESTful service: update {0}.", tag);
            ejb.updateTag(tag);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, "TagRESTful service: Exception updating tag, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Deletes a Tag by its ID.
     *
     * @param id The ID of the Tag to be deleted.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "TagRESTful service: delete Tag by id={0}.", id);
            ejb.deleteTag(id);
        } catch (DeleteException ex) {
            LOGGER.log(Level.SEVERE, "TagRESTful service: Exception deleting tag by id, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Retrieves a Tag by its ID.
     *
     * @param id The ID of the Tag to be retrieved.
     * @return The retrieved {@link Tag} object.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Tag find(@PathParam("id") Integer id) {
        Tag tag = null;
        try {
            LOGGER.log(Level.INFO, "TagRESTful service: find Tag by id={0}.", id);
            tag = ejb.selectTagById(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "TagRESTful service: Exception reading tag by id, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return tag;
    }

    /**
     * Retrieves all Tags.
     *
     * @return A List of {@link Tag} objects representing all tags.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Tag> findAll() {
        List<Tag> tags = null;
        try {
            LOGGER.log(Level.INFO, "TagRESTful service: find all tags.");
            tags = ejb.selectAllTags();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "TagRESTful service: Exception reading all tags, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return tags;
    }
}
