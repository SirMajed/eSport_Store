package com.example.eeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eeshop.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    MaterialEditText phone ,name,password;
    Button btnSignUp;
    TextView clickheretosignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //////////////////

        clickheretosignin = (TextView) findViewById(R.id.clickheretosignin);
        //
        clickheretosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(SignUp.this,SignIn.class);
                startActivity(signIn);
            }
        });
        //////////////////

        name = (MaterialEditText)findViewById(R.id.name);
        password = (MaterialEditText)findViewById(R.id.Password);
        phone = (MaterialEditText)findViewById(R.id.phone);

        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        ////////

        //Initate Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_User = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nm, pn, ps;

                nm = name.getText().toString();
                pn = phone.getText().toString();
                ps = password.getText().toString();

                if (pn.matches("") || pn.length() <=9) {
                    Toast.makeText(SignUp.this, "الرجاء كتابة رقم الجوال", Toast.LENGTH_SHORT).show();
                } else if (nm.matches("")) {
                    Toast.makeText(SignUp.this, "الرجاء كتابة الاسم", Toast.LENGTH_SHORT).show();
                } else if (ps.matches("")) {
                    Toast.makeText(SignUp.this, "الرجاء كتابة الرقم السري", Toast.LENGTH_SHORT).show();
                } else if (!pn.matches("[0-9]+")){
                    Toast.makeText(SignUp.this, "الرجاء ادخال رقم الجوال بشكل صحيح", Toast.LENGTH_SHORT).show();
                }  else if (ps.length() < 6) {
                    Toast.makeText(SignUp.this, "كلمة المرور يجب ان تتكون من 6 خانات على الاقل فأكثر", Toast.LENGTH_SHORT).show();

                } else {

                    final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                    mDialog.setMessage("الرجاء الانتظار ...");
                    mDialog.show();


                    table_User.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Check if user exists
                            if (dataSnapshot.child(phone.getText().toString()).exists()) // بيشيك اذا اليوزر موجود في الفايربيس
                            {
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this, "رقم الهاتف مسجل مسبقا", Toast.LENGTH_SHORT).show();
                            } else // اذا  اليوزر مو موجود :
                            {
                                mDialog.dismiss();
                                User user = new User(name.getText().toString(), password.getText().toString()); // بياخذ بيانات اليوزر من النموذج
                                table_User.child(phone.getText().toString()).setValue(user); // بيخزنها في الفاير بيس
                                Toast.makeText(SignUp.this, "تم التسجيل بنجاح !", Toast.LENGTH_SHORT).show();
                                finish();
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
