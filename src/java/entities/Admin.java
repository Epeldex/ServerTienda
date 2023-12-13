/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dani
 */
@XmlRootElement(name = "admin")
public class Admin extends User {
    private LocalDate lastAccess;


    // getters
    public LocalDate getLastAccess() {
        return lastAccess;
    }
    // setters
    public void setLastAccess(LocalDate lastAccess) {
        this.lastAccess = lastAccess;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Admin)) 
            return false;
        
        User other = (Admin) object;
        if ((super.id == null && other.id != null) || (super.id != null && !super.id.equals(other.id)))
            return false;
        
        return true;
    }

    @Override
    public String toString() {
        return "entities.Admin[ id=" + id + " ]";
    }
}
