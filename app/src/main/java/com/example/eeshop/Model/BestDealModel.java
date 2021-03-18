package com.example.eeshop.Model;

public class BestDealModel {
    private String menu_id, product_id,name,image;

    public BestDealModel() {
    }

    public BestDealModel(String menu_id, String product_id, String name, String image) {
        this.menu_id = menu_id;
        this.product_id = product_id;
        this.name = name;
        this.image = image;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
