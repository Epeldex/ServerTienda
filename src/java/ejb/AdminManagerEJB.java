package ejb;

import ejb.local.AdminManagerEJBLocal;
import encryption.EncriptionManager;
import encryption.EncriptionManagerFactory;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Admin;
import entities.Admin;
import entities.User;
import entities.UserType;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.Base64;
import javax.persistence.PersistenceContext;

@Stateless
public class AdminManagerEJB implements AdminManagerEJBLocal {

    private static final Logger LOGGER
            = Logger.getLogger("ejb");
    @PersistenceContext
    private EntityManager em;

    private EncriptionManager encriptionManager = EncriptionManagerFactory.getEncriptionManager();

    @Override
    public void updateLastAccess(Integer id, LocalDate date) throws UpdateException {
        try {
            LOGGER.info(
                    "AdminManager: Updating the last access of the admin; id=" + id + ".");
            Query updateLastAccess = em.createNamedQuery("signIn");
            updateLastAccess.setParameter("id", id);
            updateLastAccess.setParameter("date", date);

            updateLastAccess.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Adminanager: Exception attempting the update:",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public Admin signIn(String username, String password) throws ReadException {
        try {
            LOGGER.info("AdminManager: Administrator username=" + username + " signing in.");
            Query signIn = em.createNamedQuery("signIn");
            signIn.setParameter("username", username);
            signIn.setParameter("password", Base64.getEncoder().encodeToString(encriptionManager.decryptMessage(password)));

            return encryptPassword((Admin) signIn.getSingleResult());
        } catch (Exception e) {
            LOGGER.log(
                    Level.SEVERE, "AdminManager: Exception signing in:",
                    e);
            throw new ReadException(e.getMessage());
        }
    }

    @Override
    public void createAdmin(Admin admin) throws CreateException {
        try {
            LOGGER.info("AdminManager: Creating new administrator; id=" + admin.getId() + ".");
            em.persist(admin);
        } catch (Exception e) {
            // TODO: throws the exception (CreateException??)
            LOGGER.log(Level.SEVERE, "AdminManager: Exception creating admin.",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateAdmin(Admin admin) throws UpdateException {
        try {
            LOGGER.info("AdminManager: Updating admin; id=" + admin.getId() + ".");
            if (em.contains(admin)) {
                /*
                 * crypting admin password
                 */

                em.merge(admin);
            }
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Adminanager: Exception Finding user by login:",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void removeAdmin(Integer id) throws DeleteException {
        try {
            User user;
            LOGGER.info("UserManager: Finding user by login.");
            user = new UserManagerEJB().findUserById(id);

            if (user.getUserType().equals(UserType.ADMIN)) {
                em.createNamedQuery("removeUser")
                        .setParameter("id", id)
                        .executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding user by login:",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }

    private Admin encryptPassword(Admin admin) throws Exception {
        Admin a = (Admin) admin.clone();
        a.setPassword(Base64.getEncoder().encodeToString(encriptionManager.encryptMessage(a.getPassword())));
        return a;
    }

}
