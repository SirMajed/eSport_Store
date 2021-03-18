package com.example.eeshop.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Cart")
public class CartItem {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "productId")
    private String productId;

    @ColumnInfo(name = "productName")
    private String productName;

    @ColumnInfo(name = "productImage")
    private String productImage;

    @ColumnInfo(name = "productPrice")
    private Double productPrice;

    @ColumnInfo(name = "productQuantity")
    private int productQuantity;

    @ColumnInfo(name = "userPhone")
    private String userPhone;

    @ColumnInfo(name = "productExtraPrice")
    private Double productExtraPrice;

    @ColumnInfo(name = "productAddon")
    private String productAddon;

    @ColumnInfo(name = "productSize")
    private String productSize;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Double getProductExtraPrice() {
        return productExtraPrice;
    }

    public void setProductExtraPrice(Double productExtraPrice) {
        this.productExtraPrice = productExtraPrice;
    }

    public String getProductAddon() {
        return productAddon;
    }

    public void setProductAddon(String productAddon) {
        this.productAddon = productAddon;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }
}
