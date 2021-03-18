package com.example.eeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eeshop.Common.Common;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.w3c.dom.Text;


public class successPay extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_pay);

        MaterialEditText phone;
        phone = (MaterialEditText)findViewById(R.id.phone);

        TextView contact = (TextView) findViewById(R.id.con);
        contact.setText("We will be in touch with you soon :)");

        Button close = (Button) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(successPay.this , HomeActivity.class));


            }
        });

        Button con = (Button)findViewById(R.id.contact);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(successPay.this , contactUs.class));
            }
        });
    }
}
