package rest;

import entities.User;
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
import ejb.local.UserManagerEJBLocal;

// ... (Previous imports and class-level comments)
/**
 * This class provides CRUD (Create, Read, Update, Delete) operations for User
 * entities using XML as the data format. It integrates with the
 * {@link UserManagerEJBLocal} EJB for handling business logic.
 *
 * Note: Some methods have incorrect HTTP method annotations (e.g.,
 * findUserByActive should use @GET instead of @POST) and the correct method
 * should be applied based on the intended functionality.
 *
 * @author dani
 */
@Path("users")
public class UserREST {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("rest");

    /**
     * EJB for managing User entity CRUD operations.
     */
    @EJB
    private UserManagerEJBLocal ejb;

    /**
     * Creates a new User using XML data.
     *
     * @param user The {@link User} object containing the user data.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createUser(User user) {
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: create {0}.", user);
            ejb.createUser(user);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception creating user, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Updates an existing User using XML data.
     *
     * @param user The {@link User} object containing the updated user data.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void updateUser(User user) {
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: update {0}.", user);
            ejb.updateUser(user);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception updating user, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Deletes a User by its ID.
     *
     * @param id The ID of the User to be deleted.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @DELETE
    @Path("{id}")
    public void deleteUser(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: delete User by id={0}.", id);
            ejb.removeUser(id);
        } catch (DeleteException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception deleting user by id, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Retrieves a User by its ID.
     *
     * @param id The ID of the User to be retrieved.
     * @return The retrieved {@link User} object.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public User findUserById(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: find User by id={0}.", id);
            return ejb.findUserById(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception reading user by id, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Retrieves all Users.
     *
     * @return A List of {@link User} objects representing all users.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<User> findAllUsers() {

        try {
            LOGGER.log(Level.INFO, "UserRESTful service: find all users.");
            return ejb.findAllUsers();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception reading all users, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Retrieves a User by its username.
     *
     * @param username The username of the User to be retrieved.
     * @return The retrieved {@link User} object.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @GET
    @Path("username/{username}")
    @Produces(MediaType.APPLICATION_XML)
    public User findUserByUsername(@PathParam("username") String username) {
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: find User by username={0}.", username);
            return ejb.findUserByUsername(username);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception reading user by username, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Retrieves Users by their active status.
     *
     * @param active The active status of the Users to be retrieved.
     * @return A List of {@link User} objects representing users with the given
     * active status.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @GET
    @Path("active/{active}")
    @Produces(MediaType.APPLICATION_XML)
    public List<User> findUserByActive(@PathParam("active") Boolean active) {
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: find users by active status {0}.", active);
            return ejb.findUserByActive(active);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception reading users by active status, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Signs in a User using XML data.
     *
     * @param user The {@link User} object containing the user data for sign-in.
     * @return The signed-in {@link User} object.
     * @throws InternalServerErrorException If there is any Exception during
     * processing.
     */
    @POST
    @Path("signin")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public User signIn(User user) {
        try {
            LOGGER.info("UserRESTful service: Signing in user.");
            return ejb.signIn(user.getUsername(), user.getPassword());
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception signing in user, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

//   
//      Updates the password of a User by ID.
//     
//      @param id The ID of the User whose password is to be updated.
//     @param password The new password.
//      @throws InternalServerErrorException If there is any Exception during
//      processing.
//     
//    @PUT
//    @Consumes(MediaType.APPLICATION_XML)
//    public void updatePassword(User user) {
//        try {
//            LOGGER.info("UserRESTful service: Updating user password.");
//            ejb.updatePassword(user.getId(), user.getPassword());
//        } catch (UpdateException ex) {
//            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception updating user password, {0}", ex.getMessage());
//            throw new InternalServerErrorException(ex);
//        }
//    }
}
