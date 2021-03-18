package com.example.eeshop.EventBus;

import com.example.eeshop.Model.ProductModel;

public class ProductItemClick {
    private boolean success;
    private ProductModel productModel;

    public ProductItemClick(boolean success, ProductModel productModel) {
        this.success = success;
        this.productModel = productModel;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }
}
