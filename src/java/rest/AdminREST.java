package rest;

import entities.Admin;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import ejb.local.AdminManagerEJBLocal;

@Path("admins")
public class AdminREST {

    private static final Logger LOGGER = Logger.getLogger("rest");

    @EJB
    private AdminManagerEJBLocal ejb;

    @PUT
    @Path("updateLastAccess/{id}/{date}")
    public void updateLastAccess(@PathParam("id") Integer id, @PathParam("date") String dateString) {
        try {
            LocalDate date = LocalDate.parse(dateString);
            LOGGER.log(Level.INFO, "AdminRESTful service: Updating last access of admin; id={0}, date={1}.", new Object[]{id, date});
            ejb.updateLastAccess(id, date);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "AdminRESTful service: Exception updating last access of admin, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    @POST
    @Path("signin")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Admin signIn(Admin admin) {
        try {
            LOGGER.info("AdminRESTful service: Signing in admin.");
            return ejb.signIn(admin.getUsername(), admin.getPassword());
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "AdminRESTful service: Exception signing in admin, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createAdmin(Admin admin) {
        try {
            LOGGER.log(Level.INFO, "AdminRESTful service: Creating new admin {0}.", admin);
            ejb.createAdmin(admin);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, "AdminRESTful service: Exception creating admin, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void updateAdmin(Admin admin) {
        try {
            LOGGER.log(Level.INFO, "AdminRESTful service: Updating admin {0}.", admin);
            ejb.updateAdmin(admin);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, "AdminRESTful service: Exception updating admin, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void removeAdmin(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "AdminRESTful service: Removing admin by id={0}.", id);
            ejb.removeAdmin(id);
        } catch (DeleteException ex) {
            LOGGER.log(Level.SEVERE, "AdminRESTful service: Exception removing admin, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    // Additional methods can be added here as needed.

}
