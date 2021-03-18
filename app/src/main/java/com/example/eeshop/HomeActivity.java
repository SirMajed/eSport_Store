package com.example.eeshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.andremion.counterfab.CounterFab;
import com.example.eeshop.Common.Common;
import com.example.eeshop.Database.CartDataSource;
import com.example.eeshop.Database.CartDatabase;
import com.example.eeshop.Database.LocalCartDataSource;
import com.example.eeshop.EventBus.BestDealItemClick;
import com.example.eeshop.EventBus.CategoryClick;
import com.example.eeshop.EventBus.CounterCartEvent;
import com.example.eeshop.EventBus.PopularCategoryClick;
import com.example.eeshop.EventBus.ProductItemClick;
import com.example.eeshop.Model.CategoryModel;
import com.example.eeshop.Model.ProductModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavController navController; // السنتاكس هذا بيودينا لأي اكتفتي نبغاه

    private CartDataSource cartDataSource;


    android.app.AlertDialog dialog;


    @BindView(R.id.fab)
    CounterFab fab;


    @Override
    protected void onResume() {
        super.onResume();
        countCartItem();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();

        ButterKnife.bind(this);

        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab); // شعار السله
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_cart); // هنا فعلنا الزر حق الدائرة الي فيه شعار السله وبيودينا للسلة
            }
        });


         drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_menu, R.id.nav_product_detail,
                R.id.nav_contact, R.id.nav_cart, R.id.nav_product_list)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        ////////// عشان نصلح الايرور الي بالمنيو عشان نقدر ندخل اي قائمة ,
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        countCartItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        drawer.closeDrawers();
        switch (menuItem.getItemId())
        {
            case R.id.nav_home: // لمن يضغى على الرئيسية
                navController.navigate(R.id.nav_home);
                break;
            case R.id.nav_menu: // لمن يضغط على الكاتيقوري
                navController.navigate(R.id.nav_menu);
                break;
            case R.id.nav_cart: // لمن يضغط على السلة
                navController.navigate(R.id.nav_cart);
                break;
            case R.id.nav_contact: // لمن يضغط على كونتاكت اس
                navController.navigate(R.id.nav_contact);
                break;
            case R.id.nav_sign_out: // لمن يضغط عالساين اوت
                signOut();
                break;



        }
        return true;

    }

    private void signOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("تسجيل الخروج")
                .setMessage("هل تريد تسجيل الخروج ؟") // رسالة تأكيد
                .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Common.selectedProduct = null;
                Common.categorySelected = null;
                Common.currentUser = null;
                FirebaseAuth.getInstance().signOut();  // ميثود جاهزة من الفاير بيس لتسديل الخروج

                Intent intent = new Intent(HomeActivity.this , MainActivity.class); // بيوديه لصفحة الرئيسية حقت التسجيل او الدخول
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Event Bus

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onCategorySelected(CategoryClick event) // اذا المستخدم اختار كاتيقوري معينه وكانت موجوده سكسس ف راح يوديه لصفحة المنتجات
    {
        if (event.isSuccess())
        {
            navController.navigate(R.id.nav_product_list); // من هنا بيوديه لصفحة المنتجات المتعلقة بالكاتيقوري
            //Toast.makeText(this, "تجربة" , Toast.LENGTH_SHORT).show();
        }
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onProductItemClick(ProductItemClick event) // هنا نفس الشي اذا ضغط على المنتج راح يوديه على صفحة المنتتج الخاصة فيه
    {
        if (event.isSuccess())
        {
            navController.navigate(R.id.nav_product_detail);
        }
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onCartCounter(CounterCartEvent event) // في السلة لو ضغط على الزر حق الزيادة اكثر من مره راح يزداد
    {
        if (event.isSuccess())
        {
            countCartItem();
        }
    }


    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onBestDealItemClick(BestDealItemClick event){

        if (event.getBestDealModel() != null)
        {
            dialog.show();
            FirebaseDatabase.getInstance()
                    .getReference("Category")
                    .child(event.getBestDealModel().getMenu_id())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // هنا لمن نضغط على البيست ديل بيودينا على المنتج لو كان موجود
                            if (dataSnapshot.exists())
                            {
                                Common.categorySelected = dataSnapshot.getValue(CategoryModel.class);

                                //Load product
                                FirebaseDatabase.getInstance()
                                        .getReference("Category") // هنا بيروح على الكاتيقوري في الفاير بيس
                                        .child(event.getBestDealModel().getMenu_id())
                                        .child("product") // داخل الكاتيقوري بيروح على البرودكت
                                        .orderByChild("id") // بيشيك على الاي دي تبع البرودكت
                                        .equalTo(event.getBestDealModel().getProduct_id())// ياخذ الاي دي حق البرودكت بالزر
                                        .limitToLast(1)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) // اذا المنتج موجود ؟
                                                {
                                                    for(DataSnapshot itemSnapShot:dataSnapshot.getChildren())
                                                    {
                                                       Common.selectedProduct = itemSnapShot.getValue(ProductModel.class);

                                                    }
                                                    navController.navigate(R.id.nav_product_detail); // بيوديه على صفحة المنتج
                                                }
                                                else
                                                {

                                                    Toast.makeText(HomeActivity.this, "المنتج غير موجود", Toast.LENGTH_SHORT).show();
                                                }
                                                dialog.dismiss();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                dialog.dismiss();
                                                Toast.makeText(HomeActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                            else
                            {
                                dialog.dismiss();
                                Toast.makeText(HomeActivity.this, "لايوجد منتج حاليا", Toast.LENGTH_SHORT).show(); // اذا المنتج مو موجود
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            dialog.dismiss();
                            Toast.makeText(HomeActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }




    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onPopularItemClick(PopularCategoryClick event){

        if (event.getPopularCategoryModel() != null)
        {
            dialog.show();
            FirebaseDatabase.getInstance()
                    .getReference("Category")
                    .child(event.getPopularCategoryModel().getMenu_id())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                Common.categorySelected = dataSnapshot.getValue(CategoryModel.class);

                                //Load product
                                FirebaseDatabase.getInstance()
                                        .getReference("Category")
                                        .child(event.getPopularCategoryModel().getMenu_id())
                                        .child("product")
                                        .orderByChild("id")
                                        .equalTo(event.getPopularCategoryModel().getProduct_id())
                                        .limitToLast(1)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists())
                                                {
                                                    for(DataSnapshot itemSnapShot:dataSnapshot.getChildren())
                                                    {
                                                        Common.selectedProduct = itemSnapShot.getValue(ProductModel.class);

                                                    }
                                                    navController.navigate(R.id.nav_product_list);
                                                }
                                                else
                                                {

                                                    Toast.makeText(HomeActivity.this, "المنتج غير موجود", Toast.LENGTH_SHORT).show();
                                                }
                                                dialog.dismiss();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                dialog.dismiss();
                                                Toast.makeText(HomeActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                            else
                            {
                                dialog.dismiss();
                                Toast.makeText(HomeActivity.this, "لايوجد منتج حاليا", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            dialog.dismiss();
                            Toast.makeText(HomeActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }


    private void countCartItem() {
        cartDataSource.countItemInCart("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        fab.setCount(integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                            if (!e.getMessage().contains("Query returned empty"))
                        {

                            Toast.makeText(HomeActivity.this, "[COUNT CART]"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        } else
                            fab.setCount(0);

                    }
                });
    }

}
