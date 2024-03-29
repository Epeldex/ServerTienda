package rest;

import entities.Admin;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;
import ejb.local.AdminManagerEJBLocal;

/**
 * This class provides CRUD (Create, Read, Update, Delete) operations for Admin
 * entities using XML as the data format. It integrates with the
 * {@link AdminManagerEJBLocal} EJB for handling business logic.
 *
 * Note: The HTTP method annotations for some methods have been corrected (e.g.,
 * updateLastAccess now uses @PUT instead of @Deprecated).
 *
 * @author dani
 */
@Path("admins")
public class AdminREST {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("AdminREST");

    /**
     * EJB for managing Admin entity CRUD operations.
     */
    @EJB
    private AdminManagerEJBLocal adminEjb;

//    /**
//     * Updates the last access of an Admin by ID.
//     *
//     * @param admin The Admin object containing the ID and new last access date.
//     * @throws InternalServerErrorException If there is any Exception during
//     * processing.
//     */
//    @PUT
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public void updateLastAccess(Admin admin) {
//        try {
//            LOGGER.log(Level.INFO, "AdminRESTful service: Updating last access of admin; id={0}",
//                    admin.getId());
//            adminEjb.updateLastAccess(admin.getId(), LocalDate.now());
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, "AdminRESTful service: Exception updating last access of admin, {0}",
//                    ex.getMessage());
//            throw new InternalServerErrorException(ex);
//        }
//    }

    /**
     * Signs in an Admin using XML data.
     *
     * @param admin The Admin object containing the admin data for sign-in.
     * @return The signed-in Admin object.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @POST
    @Path("signin")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Admin signIn(Admin admin) {
        try {
            LOGGER.info("AdminRESTful service: Signing in admin.");
            return adminEjb.signIn(admin.getUsername(), admin.getPassword());
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "AdminRESTful service: Exception signing in admin, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Creates a new Admin using XML data.
     *
     * @param admin The Admin object containing the admin data.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createAdmin(Admin admin) {
        try {
            LOGGER.log(Level.INFO, "AdminRESTful service: Creating new admin {0}.", admin);
            adminEjb.createAdmin(admin);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, "AdminRESTful service: Exception creating admin, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Updates an existing Admin using XML data.
     *
     * @param admin The Admin object containing the updated admin data.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateAdmin(Admin admin) {
        try {
            LOGGER.log(Level.INFO, "AdminRESTful service: Updating admin {0}.", admin);
            adminEjb.updateAdmin(admin);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, "AdminRESTful service: Exception updating admin, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Removes an Admin by ID.
     *
     * @param id The ID of the Admin to be removed.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @DELETE
    @Path("{id}")
    public void removeAdmin(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "AdminRESTful service: Removing admin by id={0}.", id);
            adminEjb.removeAdmin(id);
        } catch (DeleteException ex) {
            LOGGER.log(Level.SEVERE, "AdminRESTful service: Exception removing admin, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
}
