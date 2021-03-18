package com.example.eeshop.ui.productDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eeshop.Common.Common;
import com.example.eeshop.Model.ProductModel;

public class productDetailViewModel extends ViewModel {

    private MutableLiveData<ProductModel> mutableLiveDataProduct;

    public productDetailViewModel() {


    }

    public MutableLiveData<ProductModel> getMutableLiveDataProduct() {
        if(mutableLiveDataProduct == null)
        mutableLiveDataProduct = new MutableLiveData<>();
        mutableLiveDataProduct.setValue(Common.selectedProduct);


        return mutableLiveDataProduct;
    }
}