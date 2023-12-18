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

@Path("users")
public class UserREST {

    private static final Logger LOGGER = Logger.getLogger("rest");

    @EJB
    private UserManagerEJBLocal ejb;

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

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public User findUserById(@PathParam("id") Integer id) {
        User user = null;
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: find User by id={0}.", id);
            user = ejb.findUserById(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception reading user by id, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return user;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<User> findAllUsers() {
        List<User> users = null;
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: find all users.");
            users = ejb.findAllUsers();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception reading all users, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return users;
    }

    @GET
    @Path("username/{username}")
    @Produces(MediaType.APPLICATION_XML)
    public User findUserByUsername(@PathParam("username") String username) {
        User user = null;
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: find User by username={0}.", username);
            user = ejb.findUserByUsername(username);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception reading user by username, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return user;
    }

    @GET
    @Path("active/{active}")
    @Produces(MediaType.APPLICATION_XML)
    public List<User> findUserByActive(@PathParam("active") Boolean active) {
        List<User> users = null;
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: find users by active status {0}.", active);
            users = ejb.findUserByActive(active);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception reading users by active status, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return users;
    }

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

    @PUT
    @Path("updatepassword/{id}/{password}")
    public void updatePassword(@PathParam("id") Integer id, @PathParam("password") String password) {
        try {
            LOGGER.info("UserRESTful service: Updating user password.");
            ejb.updatePassword(id, password);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception updating user password, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
}
