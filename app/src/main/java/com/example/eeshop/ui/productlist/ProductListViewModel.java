package com.example.eeshop.ui.productlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eeshop.Common.Common;
import com.example.eeshop.Model.ProductModel;

import java.util.List;

public class ProductListViewModel extends ViewModel {

    private MutableLiveData<List<ProductModel>> mutableLiveDataProductList;

    public ProductListViewModel() {

    }

    public MutableLiveData<List<ProductModel>> getMutableLiveDataProductList() {
        if(mutableLiveDataProductList==null)
            mutableLiveDataProductList = new MutableLiveData<>();
        mutableLiveDataProductList.setValue(Common.categorySelected.getProduct());
        return mutableLiveDataProductList;
    }
}