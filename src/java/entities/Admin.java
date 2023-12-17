package entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dani
 */
@Entity
@XmlRootElement(name = "admin")
@Table(name = "admin", schema = "our_shop")
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
