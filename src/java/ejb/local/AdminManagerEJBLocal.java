package ejb.local;

import java.time.LocalDate;
import entities.Admin;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import javax.ejb.Local;

@Local
public interface AdminManagerEJBLocal {

    /**
     * Updates the last access date of a specific {@link Admin}.
     *
     * @param id Id of the {@link Admin} whose last access date is to be
     * updated.
     * @param date New date for the last access of the {@link Admin}.
     * @throws UpdateException If there is any exception during the update
     * process.
     */
    @Deprecated
    public void updateLastAccess(Integer id, LocalDate date) throws UpdateException;

    /**
     * Signs in an admin using the provided username and password.
     *
     * @param username The username of the {@link Admin}.
     * @param password The password of the {@link Admin}.
     * @return The {@link Admin} object representing the signed-in admin.
     * @throws ReadException If there is any exception during the sign-in
     * process.
     */
    public Admin signIn(String username, String password) throws ReadException;

    /**
     * Creates a new admin and stores it in the underlying application storage.
     *
     * @param admin The {@link Admin} object containing the admin data to be
     * created.
     * @throws CreateException If there is any exception during the creation
     * process.
     */
    public void createAdmin(Admin admin) throws CreateException;

    /**
     * Updates an admin's data in the underlying application storage.
     *
     * @param admin The {@link Admin} object containing the updated admin data.
     * @throws UpdateException If there is any exception during the update
     * process.
     */
    public void updateAdmin(Admin admin) throws UpdateException;

    /**
     * Deletes an admin's data from the underlying application storage based on
     * the given ID.
     *
     * @param id The ID of the {@link Admin} whose data is to be deleted.
     * @throws DeleteException If there is any exception during the deletion
     * process.
     */
    public void removeAdmin(Integer id) throws DeleteException;
}
