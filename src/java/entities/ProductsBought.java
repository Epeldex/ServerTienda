package entities;

import java.util.Date;

/**
 * The ProductsBought class represents information about products that were bought,
 * including the amount purchased and the timestamp of the purchase.
 * 
 * @author alexIrusta
 */
public class ProductsBought {

    // The amount of products bought
    private Integer amount;

    // The timestamp indicating when the products were bought
    private Date boughtTimestamp;

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
}
