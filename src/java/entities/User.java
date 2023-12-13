/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dani
 */
@XmlRootElement(name = "user")
public class User {
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
    public boolean equals(Object object) {
        if (!(object instanceof User)) 
            return false;
        
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
            return false;
        
        return true;
    }

    @Override
    public String toString() {
        return "entities.User[ id=" + id + " ]";
    }
}
