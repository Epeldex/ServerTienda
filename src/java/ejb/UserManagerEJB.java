package ejb;

import ejb.local.UserManagerEJBLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.User;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import javax.persistence.PersistenceContext;

@Stateless
public class UserManagerEJB implements UserManagerEJBLocal {
    /**
     * Logger of the class.
     */
    private static final Logger LOGGER =
            Logger.getLogger("ejb");

    @PersistenceContext
    private EntityManager em;
    /**
     * method that searches a user by id.
     * @param id searching user's id. to search with
     */
    @Override
    public User findUserById(Integer id) throws ReadException {
        try {
            LOGGER.info(
            "UserManager: Finding user by id=" + id + ".");
            return User.class.cast(
                em.createNamedQuery("findUserById")
                .setParameter("id", id)
                .getSingleResult()
            );
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding user by login:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }
    /**
     * 
     * @param username
     */
    @Override
    public User findUserByUsername(String username) throws ReadException {
        try {
            LOGGER.info(
            "UserManager: Finding user by username = " + username + ".");
            return User.class.cast(
                em.createNamedQuery("findUserByUsername")
                .setParameter("username", username)
                .getSingleResult());
        } catch (Exception e) {
            LOGGER.log(
                Level.SEVERE, 
            "UserManager: Exception Finding user by id:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    @Override
    public List <User> findUserByActive(Boolean active) throws ReadException {
        List <User> results;
        try {
            LOGGER.info("UserManager: Finding user by login.");
            results = em.createNamedQuery("findUserByActive")
            .setParameter("active", active)
            .getResultList();

            return results;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding user by login:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }
    /**
     * 
     * 
     */
    @Override
    public List <User> findAllUsers() throws ReadException {
        List <User> results;
        try {
            LOGGER.info("UserManager: Finding user by login.");
            results = 
                em.createNamedQuery("findAllUsers")
                .getResultList();
            
            return results;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding user by login:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }
    @Override
    public User signIn(String username, String password) throws ReadException {
        try {
            LOGGER.info("UserManager: Finding user by login.");
            Query signIn = em.createNamedQuery("signIn");
            signIn.setParameter("username", username);
            signIn.setParameter("password", password);

            return User.class.cast(signIn.getSingleResult());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding user by login:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * 
     * @param id
     * @param password
     */
    @Override
    public void updatePassword(Integer id, String password) throws UpdateException {
        try {
            LOGGER.info("UserManager: Finding user by login.");
            if (findUserById(id) != null)
                em.createNamedQuery("updatePassword");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding user by login:",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    } 
    
    /**
     * method that writes a new user into the database
     * @param user
     */
     @Override
    public void createUser(User user) throws CreateException {
        try {
            LOGGER.info("UserManager: Finding user by login.");
            em.persist(user);
        } catch (Exception e) {
            // TODO: throws the exception (CreateException??)
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding user by login:",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }
    /**
     * 
     * @param user
     */
    @Override
    public void updateUser(User user) throws UpdateException {
        try {
            LOGGER.info("UserManager: Finding user by login.");
            if (em.contains(user)) {
                /*
                 * crypting user password
                 */

                 em.merge(user);
            }
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding user by login:",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * method that removes a user by id.
     * @param id
     */
    @Override
    public void removeUser(Integer id) throws DeleteException {
        try {
            LOGGER.info("UserManager: Finding user by login.");
            if (findUserById(id) != null) 
                em.createNamedQuery("removeUser")
                .setParameter("id", id)
                .executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding user by login:",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
}
