package com.packdata.packdata.model;

import java.util.List;

public class Customer {
    private int id;
    private int customer_id;
    private List<Inventory> pack_data;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    public List<Inventory> getPack_data() {
        return pack_data;
    }
    public void setPack_data(List<Inventory> pack_data) {
        this.pack_data = pack_data;
    }

    

}
