package com.example.eeshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.eeshop.Database.CartItem;
import com.example.eeshop.EventBus.UpdateItemInCart;
import com.example.eeshop.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyViewHolder> {


    Context context;
    List<CartItem> cartItemList;

    public MyCartAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(cartItemList.get(position).getProductImage())
                .into(holder.img_cart);
        holder.txt_product_name.setText(new StringBuilder(cartItemList.get(position).getProductName()));
        holder.txt_product_price.setText(new StringBuilder("")
        .append(cartItemList.get(position).getProductPrice()+cartItemList.get(position).getProductExtraPrice()));

        holder.numberbutton.setNumber(String.valueOf(cartItemList.get(position).getProductQuantity()));

        holder.numberbutton.setOnValueChangeListener((view, oldValue, newValue) -> {
        // لمن اليوزر بيضغط الزر راح نحدث الداتابيس
            cartItemList.get(position).setProductQuantity(newValue);
            EventBus.getDefault().postSticky(new UpdateItemInCart(cartItemList.get(position)));
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public CartItem getItemAtPosition(int pos) {
        return cartItemList.get(pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private Unbinder unbinder;
        @BindView(R.id.img_cart)
        ImageView img_cart;
        @BindView(R.id.txt_product_price)
        TextView txt_product_price;
        @BindView(R.id.txt_product_name)
        TextView txt_product_name;
        @BindView(R.id.number_button)
        ElegantNumberButton numberbutton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
