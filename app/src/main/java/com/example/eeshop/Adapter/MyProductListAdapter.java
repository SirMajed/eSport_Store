package com.example.eeshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eeshop.CallBack.IRecyclerClickListener;
import com.example.eeshop.Common.Common;
import com.example.eeshop.Database.CartDataSource;
import com.example.eeshop.Database.CartDatabase;
import com.example.eeshop.Database.CartItem;
import com.example.eeshop.Database.LocalCartDataSource;
import com.example.eeshop.EventBus.CounterCartEvent;
import com.example.eeshop.EventBus.ProductItemClick;
import com.example.eeshop.Model.ProductModel;
import com.example.eeshop.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MyProductListAdapter extends RecyclerView.Adapter<MyProductListAdapter.MyViewHolder> {

    private Context context;
    private List<ProductModel> productModelList;
    private CompositeDisposable compositeDisposable;
    private CartDataSource cartDataSource;


    public MyProductListAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
        this.compositeDisposable = new CompositeDisposable();
        this.cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(context).cartDAO());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.layout_product_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(productModelList.get(position).getImage()).into(holder.img_product_image);
        holder.txt_product_price.setText(new StringBuilder("$")
                .append(productModelList.get(position).getPrice()));
        holder.txt_product_name.setText(new StringBuilder("")
                .append(productModelList.get(position).getName()));

        //
        holder.setListener((view, pos) -> {
        Common.selectedProduct = productModelList.get(pos);
            EventBus.getDefault().postSticky(new ProductItemClick(true,productModelList.get(pos)));
        });
        //here when user click to cart icon in products list
        holder.img_cart.setOnClickListener(v -> { // هنا لمن ينضغط على ايقونة السلة في المنتجات راح تنضاف للسلة
            CartItem cartItem = new CartItem();
            cartItem.setUserPhone("");

            cartItem.setProductId(productModelList.get(position).getId()); // هنا بنجيب الاي دي حق المنتج
            cartItem.setProductName(productModelList.get(position).getName());
            cartItem.setProductImage(productModelList.get(position).getImage());
            cartItem.setProductPrice(Double.valueOf(String.valueOf(productModelList.get(position).getPrice())));
            cartItem.setProductQuantity(1);
            cartItem.setProductExtraPrice(0.0);
            cartItem.setProductAddon("Default");
            cartItem.setProductSize("Default");

            compositeDisposable.add(cartDataSource.insertOrReplaceAll(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(()->{
                Toast.makeText(context, "تم الاضافة للسلة", Toast.LENGTH_SHORT).show();
                // هنا بنرسل اشعار للاكتفتي لصفحة الرئيسية عشان السلة تتحدث
                EventBus.getDefault().postSticky(new CounterCartEvent(true));
            },throwable -> {
                Toast.makeText(context, "[CART ERROR]"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }));




        });


    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Unbinder unbinder;
        @BindView(R.id.txt_product_name)
        TextView txt_product_name;
        @BindView(R.id.txt_product_price)
        TextView txt_product_price;
        @BindView(R.id.img_product_image)
        ImageView img_product_image;

        @BindView(R.id.img_quick_cart)
        ImageView img_cart;

        // هنا بنسوي فنكشن الي لمن نضغط على المنتج بيودينا لصفحة المنتج نفسه ونشوف التفاصيل اكثر

        IRecyclerClickListener listener;

        public void setListener(IRecyclerClickListener listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onItemClickListener(view,getAdapterPosition());
        }
    }
}
