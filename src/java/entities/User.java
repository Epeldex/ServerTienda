package entities;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * @author dani
 */
@NamedQueries({
    @NamedQuery(
        name = "findUserById", 
        query = "SELECT * users u WHERE u.id = :id"),

    @NamedQuery(
        name = "findUserByUsername", 
        query = "SELECT u FROM users u WHERE u.username = :username"),

    @NamedQuery(
        name = "findUserByActive",
        query = "SELECT u FROM users u WHERE u.active = :active"),

    @NamedQuery(
        name = "findAllUsers",
        query = "SELECT u FROM users u"),


    @NamedQuery(
        name = "updatePassword", 
        query = "UPDATE users u SET u.password = :password WHERE u.id = :id"),
    
        
    
    @NamedQuery(
        name = "signIn", 
        query = "SELECT u FROM users u WHERE u.username = :username AND u.password = :password"),

    @NamedQuery(
        name = "removeUser", 
        query = "DELETE FROM users u WHERE u.id = :id")
})

@Entity
@XmlRootElement(name = "user")
@Table(name = "user", schema = "our_shop")
public class User implements Serializable {
    
    @Id
    protected Integer id;
    private String username;
    private String password;
    private boolean active;
    private UserType userType;

    // getters
    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public boolean isActive() {
        return active;
    }

    // setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User))
            return false;

        User other = (User) obj;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
            return false;

        return true;
    }
    /**
     * 
     * @param obj object to compare with this user
     * @return true if every attribute of both objects 
     * are identical, including the id. false if not.
     */
    public boolean everythingIsEqual(Object obj) {
        if (!equals(obj))
            return false;

        User user = User.class.cast(obj);
        
        return 
            this.username == user.getUsername() &&
            this.password == user.getPassword() &&
            this.active == user.active &&
            this.userType == user.getUserType(); 
    }

    @Override
    public String toString() {
        return "entities.User[ id=" + id + " ]";
    }
}
