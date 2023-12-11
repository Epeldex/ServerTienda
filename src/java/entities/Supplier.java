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
public class Supplier {
    private Integer supplier_id;
    private String name;
    private String phone;
    private String country;
    private Integer zip;
    private Date createTimestamp;

    public Integer getSupplier_id() {
        return supplier_id;
    }
    public void setSupplier_id(Integer supplier_id) {
        this.supplier_id = supplier_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public Integer getZip() {
        return zip;
    }
    public void setZip(Integer zip) {
        this.zip = zip;
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
        result = prime * result + ((supplier_id == null) ? 0 : supplier_id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Supplier other = (Supplier) obj;
        if (supplier_id == null) {
            if (other.supplier_id != null)
                return false;
        } else if (!supplier_id.equals(other.supplier_id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Supplier [supplier_id=" + supplier_id + ", name=" + name + "]";
    }
    
    

}
