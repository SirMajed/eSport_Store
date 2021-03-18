package com.example.eeshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dialog.plus.ui.DialogPlusBuilder;
import com.example.eeshop.Database.CartDataSource;
import com.example.eeshop.EventBus.CounterCartEvent;
import com.example.eeshop.Model.ProductModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;

import info.hoang8f.widget.FButton;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class checkout extends AppCompatActivity {
    DatabaseReference ref;
    checkoutDB checkoutDB;
    private CartDataSource cartDataSource;



    MaterialEditText cardNumber ,mmyy,cvv,cardName,address;
    EditText test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);



        checkoutDB = new checkoutDB();
        ref = FirebaseDatabase.getInstance().getReference().child("Orders");

        FButton pay = (FButton) findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardNumber = (MaterialEditText)findViewById(R.id.cardNumber);
                mmyy = (MaterialEditText)findViewById(R.id.mmyy);
                cvv = (MaterialEditText)findViewById(R.id.cvv);
                cardName = (MaterialEditText) findViewById(R.id.cardName);
                address = (MaterialEditText)findViewById(R.id.address);

                ////////////////////
                String cardN , my , cv , cn , ad;

                cardN = cardNumber.getText().toString();
                my = mmyy.getText().toString();
                cv = cvv.getText().toString();
                cn = cardName.getText().toString();
                ad = address.getText().toString();

                //////////////////////////
                checkoutDB.setCardNumber(cardN.trim());
                checkoutDB.setCardName(cn.trim());
                checkoutDB.setMmyy(my.trim());
                checkoutDB.setCvv(cv.trim());
                checkoutDB.setAddress(ad.trim());






                ///////////////////////

                if (cardN.matches("") || cardN.length() <= 15) {
                    Toast.makeText(checkout.this, "الرجاء ادخل رقم البطاقة", Toast.LENGTH_SHORT).show();
                    return;
                } else if (cn.matches("") && cn.length() <= 39) {
                    Toast.makeText(checkout.this, "الرجاء ادخل اسم صاحب البطاقة", Toast.LENGTH_SHORT).show();
                    return;
                }else if (my.matches("") || my.length() <= 3) {
                    Toast.makeText(checkout.this, "الرجاء ادخل تاريخ انتهاء البطاقة", Toast.LENGTH_SHORT).show();
                    return;
                } else if (cv.matches("") || cv.length() <= 2) {
                    Toast.makeText(checkout.this, "الرجاء ادخل رقم السري الثلاثي", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ad.matches("")) {
                    Toast.makeText(checkout.this, "الرجاء ادخال عنوان المنزل", Toast.LENGTH_SHORT).show();
                    return;
                } else
                {


                    ref.push().setValue(checkoutDB);
                    AlertDialog alertDialog = new AlertDialog.Builder(checkout.this).create();
                    alertDialog.setTitle("Success !");
                    alertDialog.setMessage("تم ارسال طلبك بنجاح لقاعدة البيانات. سيتواصل المندوب معك قريبا");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "حسنا",


                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    cardNumber.setText("");
                                    mmyy.setText("");
                                    cvv.setText("");
                                    cardName.setText("");
                                    address.setText("");


                                    startActivity(new Intent(checkout.this, successPay.class));
                                }
                            });
                    alertDialog.show();

                }
                /////////////////////

//                ProgressDialog pd = new ProgressDialog(checkout.this);
//                pd.setMessage("الرجاء الانتظار");
//                pd.show();
//                pd.show();
//                pd.show();
//                ///////////////////
//
//                ////////////////////
//                startActivity(new Intent(checkout.this,successPay.class));

            }
        });

    }
}
