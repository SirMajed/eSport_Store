package com.example.eeshop.EventBus;

import com.example.eeshop.Model.CategoryModel;

public class CategoryClick {
    private boolean success;
    private CategoryModel categoryModel;

    public CategoryClick(boolean success, CategoryModel categoryModel) {
        this.success = success;
        this.categoryModel = categoryModel;
    }

    public boolean isSuccess() {
        return success;
    }


}
