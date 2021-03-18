package com.example.eeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn , btnSignUp;
    TextView txtSlogan;
    ///////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);









        //// continue as guest
        TextView guest = (TextView) findViewById(R.id.guest) ;
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BAR
                /*ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "eSport",
                        "الرجاء الانتظار ...", true);
                finish();*/
                //
                startActivity(new Intent(MainActivity.this , HomeActivity.class));
            }
        });




        /////////////////

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        txtSlogan = (TextView) findViewById(R.id.txtSlogan);
        ///////////////

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sigUp = new Intent(MainActivity.this,SignUp.class);
                startActivity(sigUp);
            }
        });



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sigIn = new Intent(MainActivity.this,SignIn.class);
                startActivity(sigIn);
            }
        });




    }
}
