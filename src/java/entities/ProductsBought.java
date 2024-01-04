package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The ProductsBought class represents information about products that were
 * bought, including the amount purchased and the timestamp of the purchase.
 *
 * @author alexIrusta
 */
@Entity
@Table(name = "products_bought", schema = "our_shop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "updateAmount",
            query = "UPDATE ProductsBought pb SET pb.amount = :amount "
            + "WHERE pb.customer.id = :customerId AND pb.product.id = :productId")
    ,
    @NamedQuery(name = "getProductsBought",
            query = "SELECT pb FROM ProductsBought pb WHERE pb.customer.id = :customerId")
    ,     
    @NamedQuery(name = "deleteByProductId",
            query = "DELETE FROM ProductsBought pb WHERE pb.product.id = :product_id")
    ,
    @NamedQuery(name = "deleteByCustomerId",
            query = "DELETE FROM ProductsBought pb WHERE pb.customer.id = :customer_id")

})

public class ProductsBought implements Serializable {

    // The amount of products bought
    private Integer amount;

    @EmbeddedId
    private ProductsBoughtId id;

    // The timestamp indicating when the products were bought
    @Temporal(TemporalType.TIMESTAMP)
    private Date boughtTimestamp;

    @JoinColumn(name = "customerId", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    private Customer customer;

    @JoinColumn(name = "productId", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
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

    public ProductsBoughtId getId() {
        return id;
    }

    public void setId(ProductsBoughtId id) {
        this.id = id;
    }

    @XmlTransient
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
