package entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Represents a product entity with various attributes such as product ID,
 * product number, brand, model, weight, description, price, and creation
 * timestamp. Implements hashCode, equals, and toString methods for object
 * comparison and representation.
 *
 * @author Alexander Epelde
 */
@Entity
@Table(name = "product", schema = "our_shop")
@XmlRootElement

@NamedQueries({
    @NamedQuery(name = "updateProduct",
            query = "UPDATE Product p SET  p.brand = :brand, p.model = :model, p.otherInfo = :otherInfo, p.weight = :weight, p.description = :description, p.price = :price WHERE p.product_id = :productId")
    ,
   @NamedQuery(name = "deleteProduct",
            query = "DELETE FROM Product p WHERE p.product_id = :productId")
    ,
   @NamedQuery(name = "selectAllProducts",
            query = "SELECT p FROM Product p ORDER BY p.product_id ASC")
    ,
    @NamedQuery(name = "selectProductById",
            query = "SELECT p FROM Product p WHERE p.product_id = :product_id")
    , 
    @NamedQuery(name = "selectProductWithTagId",
            query = "SELECT p.id FROM Product p WHERE p.tag.id = :tag_id")
    ,
    @NamedQuery(name = "selectProductWithSupplierId",
            query = "SELECT p.id FROM Product p WHERE p.supplier.id = :supplier_id")
    ,
    @NamedQuery(name = "deleteProductByTagId",
            query = "DELETE FROM Product p WHERE p.tag.id = :tag_id")
    , 
    @NamedQuery(name = "deleteProductBySupplierId",
            query = "DELETE FROM Product p WHERE p.supplier.id = :supplier_id")
})
public class Product implements Serializable {

    /**
     * Unique identifier for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer product_id;

    /**
     * Unique product number associated with the product.
     */
    @NotNull
    private String productNumber;

    /**
     * Brand of the product.
     */
    private String brand;

    /**
     * Model of the product.
     */
    private String model;

    /**
     * Additional information about the product.
     */
    private String otherInfo;

    /**
     * Weight of the product.
     */
    private Float weight;

    /**
     * Description of the product.
     */
    private String description;

    /**
     * Price of the product.
     */
    private Double price;

    /**
     * Supplier of the Product
     */
    @JoinColumn(name = "supplier_id")
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Supplier supplier;

    /**
     * Tag of the product
     *
     */
    @JoinColumn(name = "tag_id")
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Tag tag;

    /**
     * Collection of products bought by the customer
     */
    @OneToMany(mappedBy = "product", fetch = EAGER)
    private Set<ProductsBought> productsBought;
    /**
     * Timestamp indicating when the product was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Date createTimestamp;

    /**
     * Gets the product ID.
     *
     * @return the product ID
     */
    public Integer getProduct_id() {
        return product_id;
    }

    /**
     * Sets the product ID.
     *
     * @param product_id the product ID to set
     */
    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    /**
     * Gets the product number.
     *
     * @return the product number
     */
    public String getProductNumber() {
        return productNumber;
    }

    /**
     * Sets the product number.
     *
     * @param productNumber the product number to set
     */
    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    /**
     * Gets the brand of the product.
     *
     * @return the brand of the product
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand of the product.
     *
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the model of the product.
     *
     * @return the model of the product
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the product.
     *
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets additional information about the product.
     *
     * @return additional information about the product
     */
    public String getOtherInfo() {
        return otherInfo;
    }

    /**
     * Sets additional information about the product.
     *
     * @param otherInfo additional information to set
     */
    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    /**
     * Gets the weight of the product.
     *
     * @return the weight of the product
     */
    public Float getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the product.
     *
     * @param weight the weight to set
     */
    public void setWeight(Float weight) {
        this.weight = weight;
    }

    /**
     * Gets the description of the product.
     *
     * @return the description of the product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the product.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the price of the product.
     *
     * @return the price of the product
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Gets the creation timestamp of the product.
     *
     * @return the creation timestamp of the product
     */
    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    /**
     * Sets the creation timestamp of the product.
     *
     * @param createTimestamp the creation timestamp to set
     */
    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    /**
     * Gets the supplier of the product.
     *
     * @return the supplier of the product
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier of the product.
     *
     * @param supplier the supplier to set
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /**
     * Gets the tag associated with the product.
     *
     * @return the tag associated with the product
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * Sets the tag associated with the product.
     *
     * @param tag the tag to set
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /**
     * Gets the set of products bought by the customer.
     *
     * @return the set of products bought by the customer
     */
    @XmlTransient
    public Set<ProductsBought> getProductsBought() {
        return productsBought;
    }

    /**
     * Sets the set of products bought by the customer.
     *
     * @param productsBought the set of products bought to set
     */
    public void setProductsBought(Set<ProductsBought> productsBought) {
        this.productsBought = productsBought;
    }

    /**
     * Generates a hash code for the product based on its ID.
     *
     * @return the hash code for the product
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((product_id == null) ? 0 : product_id.hashCode());
        return result;
    }

    /**
     * Returns a string representation of the product.
     *
     * @return a string representation of the product
     */
    @Override
    public String toString() {
        return "Product [product_id=" + product_id + ", brand=" + brand + ", model=" + model + "]";
    }

    /**
     * Compares two Product objects for equality based on their ID.
     *
     * @param obj the other Product object to compare to
     * @return true if the products have the same ID, otherwise false
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
        Product other = (Product) obj;
        if (product_id == null) {
            if (other.product_id != null) {
                return false;
            }
        } else if (!product_id.equals(other.product_id)) {
            return false;
        }
        return true;
    }
}
