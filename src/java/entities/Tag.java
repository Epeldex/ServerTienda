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
public class Tag {
   
    private Integer tag_id;
    private String type;
    private String label;
    private Boolean active;
    private Date createTimestamp;

    public Integer getTag_id() {
        return tag_id;
    }
    public void setTag_id(Integer tag_id) {
        this.tag_id = tag_id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
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
        result = prime * result + ((tag_id == null) ? 0 : tag_id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        Tag other = (Tag) obj;
        if (tag_id == null) {
            if (other.tag_id != null)
                return false;
        } else if (!tag_id.equals(other.tag_id))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Tag [tag_id=" + tag_id + ", type=" + type + ", label=" + label + "]";
    }

    
}
