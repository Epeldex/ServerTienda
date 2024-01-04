package rest;

import entities.Admin;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import ejb.local.AdminManagerEJBLocal;
import ejb.local.UserManagerEJBLocal;

/**
 * This class provides CRUD (Create, Read, Update, Delete) operations for Admin
 * entities using XML as the data format. It integrates with the
 * {@link AdminManagerEJBLocal} EJB for handling business logic.
 *
 * Note: Some methods have incorrect HTTP method annotations (e.g.,
 * updateLastAccess should use @PUT instead of @POST) and the correct method
 * should be applied based on the intended functionality.
 *
 * @author dani
 */
@Path("admins")
public class AdminREST {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("rest");

    /**
     * EJB for managing Admin entity CRUD operations.
     */
    @EJB
    private AdminManagerEJBLocal adminEjb;
    /**
     * EJB for managing User entity CRUD operations.
     */
    @EJB
    private UserManagerEJBLocal userEjb;

    /**
     * Updates the last access of an Admin by ID.
     *
     * @param id The ID of the Admin whose last access is to be updated.
     * @param dateString The new last access date in string format.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void updateLastAccess(Admin admin) {
        try {
            LOGGER.log(Level.INFO, "AdminRESTful service: Updating last access of admin; id={0}",
                    admin.getId());
            adminEjb.updateLastAccess(admin.getId(), LocalDate.now());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "AdminRESTful service: Exception updating last access of admin, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Signs in an Admin using XML data.
     *
     * @param admin The {@link Admin} object containing the admin data for
     * sign-in.
     * @return The signed-in {@link Admin} object.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @POST
    @Path("signin")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
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
     * @param admin The {@link Admin} object containing the admin data.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createAdmin(Admin admin) {
        try {
            LOGGER.log(Level.INFO, "AdminRESTful service: Creating new admin {0}.", admin);
            adminEjb.createAdmin(admin);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, "AdminRESTful service: Exception creating admin, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
//
//    /**
//     * Updates an existing Admin using XML data.
//     *
//     * @param admin The {@link Admin} object containing the updated admin data.
//     * @throws InternalServerErrorException If there is any Exception during
//     * processing.
//     */
//    @PUT
//    @Consumes(MediaType.APPLICATION_XML)
//    public void updateAdmin(Admin admin) {
//        try {
//            LOGGER.log(Level.INFO, "AdminRESTful service: Updating admin {0}.", admin);
//            adminEjb.updateAdmin(admin);
//        } catch (UpdateException ex) {
//            LOGGER.log(Level.SEVERE, "AdminRESTful service: Exception updating admin, {0}", ex.getMessage());
//            throw new InternalServerErrorException(ex);
//        }
//    }

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
