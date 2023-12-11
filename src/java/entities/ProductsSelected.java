package entities;

/**
 * The ProductsSelected class represents information about selected products,
 * including the amount selected.
 * 
 * @author alexIrusta
 */
public class ProductsSelected {
    
    // The amount of products selected
    private Integer amount;

    /**
     * Gets the amount of products selected.
     * 
     * @return The amount of products selected.
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the amount of products selected.
     * 
     * @param amount The new amount of products selected.
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}