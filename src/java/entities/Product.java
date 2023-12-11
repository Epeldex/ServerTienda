/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;

/**
 *
 * @author alexa
 */
public class Product {
    private Integer product_id;
    private String productNumber;
    private String brand;
    private String model;
    private String otherInfo;
    private Float weight;
    private String description;
    private Double price;
    private Date createTimestamp;

    
    public Integer getProduct_id() {
        return product_id;
    }
    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }
    public String getProductNumber() {
        return productNumber;
    }
    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getOtherInfo() {
        return otherInfo;
    }
    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
    public Float getWeight() {
        return weight;
    }
    public void setWeight(Float weight) {
        this.weight = weight;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Date getCreateTimestamp() {
        return createTimestamp;
    }
    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }
    @Override            
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((product_id == null) ? 0 : product_id.hashCode());
        return result;
    }
    @Override
    public String toString() {
        return "Product [product_id=" + product_id + ", brand=" + brand + ", model=" + model + "]";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (product_id == null) {
            if (other.product_id != null)
                return false;
        } else if (!product_id.equals(other.product_id))
            return false;
        return true;
    }

}
