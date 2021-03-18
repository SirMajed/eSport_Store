package com.example.eeshop.ui.productlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eeshop.Adapter.MyProductListAdapter;
import com.example.eeshop.Model.ProductModel;
import com.example.eeshop.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductListFragment extends Fragment {

    private ProductListViewModel sendViewModel;

    Unbinder unbinder;
    @BindView(R.id.recycler_product_list)
    RecyclerView recycler_product_list;

    LayoutAnimationController layoutAnimationController;
    MyProductListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(ProductListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_product_list, container, false);
        unbinder = ButterKnife.bind(this,root);
        initViews();
        sendViewModel.getMutableLiveDataProductList().observe(this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
            adapter = new MyProductListAdapter(getContext(),productModels);
            recycler_product_list.setAdapter(adapter);
           // recycler_product_list.setLayoutAnimation(layoutAnimationController);


            }
        });
        return root;
    }

    private void initViews() {
        recycler_product_list.setHasFixedSize(true);
        recycler_product_list.setLayoutManager(new LinearLayoutManager(getContext()));
        //layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);
    }

}