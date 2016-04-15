package com.towson.eventmanager.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Hall.
 */

@Document(collection = "hall")
public class Hall implements Serializable {

    @Id
    private String id;

    @Field("name")
    private String name;
    
    @Field("address")
    private String address;
    
    @Field("capacity")
    private Integer capacity;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hall hall = (Hall) o;
        if(hall.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, hall.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Hall{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", address='" + address + "'" +
            ", capacity='" + capacity + "'" +
            '}';
    }
}
