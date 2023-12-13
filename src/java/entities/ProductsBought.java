package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The ProductsBought class represents information about products that were
 * bought, including the amount purchased and the timestamp of the purchase.
 *
 * This class is an entity mapped to the "products_bought" table in the "our_shop" schema.
 * It includes associations with the Customer and Product entities through foreign keys.
 *
 * @author alexIrusta
 */
@Entity
@Table(name = "products_bought", schema = "our_shop")
@XmlRootElement
public class ProductsBought implements Serializable {

    // The amount of products bought
    private Integer amount;

    @EmbeddedId
    private ProductsBoughtId id;

    // The timestamp indicating when the products were bought
    @Temporal(TemporalType.TIMESTAMP)
    private Date boughtTimestamp;

    @MapsId("customerId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @MapsId("productId")
    @ManyToOne()
    private Product product;

    /**
     * Gets the amount of products bought.
     *
     * @return The amount of products bought.
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the amount of products bought.
     *
     * @param amount The new amount of products bought.
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Gets the timestamp of when the products were bought.
     *
     * @return The timestamp of the purchase.
     */
    public Date getBoughtTimestamp() {
        return boughtTimestamp;
    }

    /**
     * Sets the timestamp of when the products were bought.
     *
     * @param boughtTimestamp The new timestamp of the purchase.
     */
    public void setBoughtTimestamp(Date boughtTimestamp) {
        this.boughtTimestamp = boughtTimestamp;
    }

    /**
     * Computes the hash code for this object based on its identifier.
     *
     * @return The hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Checks if this object is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal; otherwise, false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductsBought other = (ProductsBought) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}