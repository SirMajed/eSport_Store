package com.example.eeshop.ui.productDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.andremion.counterfab.CounterFab;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.eeshop.Common.Common;
import com.example.eeshop.Model.ProductModel;
import com.example.eeshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class productDetailFragment extends Fragment {

    private productDetailViewModel slideshowViewModel;

    private Unbinder unbinder;

    @BindView(R.id.img_product)
    ImageView img_product;

    @BindView(R.id.product_name)
    TextView product_name;
    @BindView(R.id.product_description)
    TextView product_description;
    @BindView(R.id.product_price)
    TextView product_price;
    /////
    //@BindView(R.id.number_button)
    //ElegantNumberButton numberButton;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(productDetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);
        unbinder = ButterKnife.bind(this,root);

        slideshowViewModel.getMutableLiveDataProduct().observe(this, (ProductModel productModel) -> {
            displayInfo(productModel);
            
        });
        return root;


    }

    private void displayInfo(ProductModel productModel) {
        Glide.with(getContext()).load(productModel.getImage()).into(img_product); // بنجيب الصورة من الفاير بيس حقت المنتد
        product_name.setText(new StringBuilder(productModel.getName())); // بنجيب اسم المنتج
        product_description.setText(new StringBuilder(productModel.getDescription())); // بنجيب الوصف حق المنتج
        product_price.setText(new StringBuilder(productModel.getPrice().toString())); // بنجيب سعر المنتج

        ((AppCompatActivity)getActivity())
                .getSupportActionBar()
                .setTitle(Common.selectedProduct.getName());




    }
}