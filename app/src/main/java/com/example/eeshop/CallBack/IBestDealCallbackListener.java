package com.example.eeshop.CallBack;

import com.example.eeshop.Model.BestDealModel;
import com.example.eeshop.Model.PopularCategoryModel;

import java.util.List;

public interface IBestDealCallbackListener {
    void onBestDealLoadSuccess(List<BestDealModel> bestDealModels);
    void onBestDealLoadFailed(String message);
}
