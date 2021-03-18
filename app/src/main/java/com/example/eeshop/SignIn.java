package com.example.eeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eeshop.Model.User;
import com.example.eeshop.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
    MaterialEditText phone,password;
    Button btnSignIn;
    TextView donthaveaccount;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //////////////
        password = (MaterialEditText)findViewById(R.id.Password);
        phone = (MaterialEditText)findViewById(R.id.phone);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        ////////////////




        ///////////
        donthaveaccount = (TextView) findViewById(R.id.donthaveaccount);
        donthaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sigUp = new Intent(SignIn.this,SignUp.class);
                startActivity(sigUp);
            }
        });
        ///////////////////


        /////////
        //Initate Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        //////////

        //Sign in function
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nm, pn, ps;

                pn = phone.getText().toString();
                ps = password.getText().toString();

                if (pn.matches("") || pn.length() <= 9) {
                    Toast.makeText(SignIn.this, "الرجاء ادخال رقم الجوال", Toast.LENGTH_SHORT).show();
                } else if (ps.matches("")) {
                    Toast.makeText(SignIn.this, "الرجاء كتابة الرقم السري", Toast.LENGTH_SHORT).show();
                } else if (!pn.matches("[0-9]+")) {
                    Toast.makeText(SignIn.this, "الرجاء ادخال رقم الجوال بشكل صحيح", Toast.LENGTH_SHORT).show();
                } else if (ps.length() <= 6) {
                    Toast.makeText(SignIn.this, "كلمة المرور يجب ان تتكون من 6 خانات على الاقل فأكثر", Toast.LENGTH_SHORT).show();
                }else {


                    final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setMessage("الرجاء الانتظار ...");
                    mDialog.show();


                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Check if user exist in DB
                            if (dataSnapshot.child(phone.getText().toString()).exists()) {


                                mDialog.dismiss();
                                //Get user PHONE NUMBER
                                User user = dataSnapshot.child(phone.getText().toString()).getValue(User.class);

                                if (user.getPassword().equals(password.getText().toString())) {
                                    Toast.makeText(SignIn.this, "تم تسجيل دخولك بنجاح !", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignIn.this, HomeActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(SignIn.this, "كلمة المرور غير صحيحة", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SignIn.this, "لايوجد مستخدم بهذا الرقم", Toast.LENGTH_SHORT).show();
                            }

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
}
