package entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity representing users. It contains fields such as user ID, username,
 * password, user type, and active status. It also provides methods for getting
 * and setting these fields, as well as overridden methods for hash code,
 * equality, and string representation.
 *
 * @author dani
 */
@Entity
@XmlRootElement(name = "user")
@Table(name = "user", schema = "our_shop")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(
            name = "findUserById",
            query = "SELECT u FROM User u WHERE u.id = :id")
    ,
    @NamedQuery(
            name = "findUserByUsername",
            query = "SELECT u FROM User u WHERE u.username = :username")
    ,
    @NamedQuery(
            name = "findUserByActive",
            query = "SELECT u FROM User u WHERE u.active = :active")
    ,
    @NamedQuery(
            name = "findAllUsers",
            query = "SELECT u FROM User u")
    ,
    @NamedQuery(
            name = "updatePassword",
            query = "UPDATE User u SET u.password = :password WHERE u.id = :id")
    ,
    @NamedQuery(
            name = "signIn",
            query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
    ,
    @NamedQuery(
            name = "removeUser",
            query = "DELETE FROM User u WHERE u.id = :id")
})

public class User implements Serializable, Cloneable {

    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
    @NotNull
    private String username;
    @NotNull
    private String password;

    private boolean active;

    @Enumerated(EnumType.ORDINAL)
    private UserType userType;

    /**
     * Get the user ID.
     *
     * @return the user ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Get the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the user type.
     *
     * @return the user type
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Check if the user is active.
     *
     * @return true if the user is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    // setters
    /**
     * Set the user ID.
     *
     * @param id the user ID to be set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set the username.
     *
     * @param username the username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the password.
     *
     * @param password the password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the user type.
     *
     * @param userType the user type to be set
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * Set the user's active status.
     *
     * @param active true if the user is active, false otherwise
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    public static User getInnerUser(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setId(user.getId());
        newUser.setActive(user.isActive());
        newUser.setUserType(user.getUserType());
        return newUser;
    }

    /**
     * Generate a hash code for the user based on its ID.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compare two User objects for equality based on their ID.
     *
     * @param obj The other User object to compare to.
     * @return true if IDs are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }

        User other = (User) obj;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    /**
     * Compare two User objects for equality based on all attributes.
     *
     * @param obj object to compare with this user
     * @return true if every attribute of both objects are identical, including
     * the id. false if not.
     */
    public boolean everythingIsEqual(Object obj) {
        if (!equals(obj)) {
            return false;
        }

        User user = User.class.cast(obj);

        return this.username == user.getUsername()
                && this.password == user.getPassword()
                && this.active == user.active
                && this.userType == user.getUserType();
    }

    /**
     * Obtain a string representation of the User.
     *
     * @return The String representing the User.
     */
    @Override
    public String toString() {
        return "entities.User[ id=" + id + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}
