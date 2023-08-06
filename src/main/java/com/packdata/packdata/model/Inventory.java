package com.packdata.packdata.model;

public class Inventory {
    private String inventory_code;
    private String ingredient;
    private String unit;
    private int quantity;


    public String getInventory_code() {
        return inventory_code;
    }
    public void setInventory_code(String inventory_code) {
        this.inventory_code = inventory_code;
    }
    public String getIngredient() {
        return ingredient;
    }
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
