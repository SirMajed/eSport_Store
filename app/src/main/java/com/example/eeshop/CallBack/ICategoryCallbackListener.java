package com.example.eeshop.CallBack;

import com.example.eeshop.Model.BestDealModel;
import com.example.eeshop.Model.CategoryModel;

import java.util.List;

public interface ICategoryCallbackListener {
    void onCategoryLoadSuccess(List<CategoryModel> bestDealModels);
    void onCategoryLoadFailed(String message);
}
