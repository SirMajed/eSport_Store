package com.example.eeshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import info.hoang8f.widget.FButton;

public class contactUs extends AppCompatActivity {

    private NavController navController; // السنتاكس هذا بيودينا لأي اكتفتي نبغاه
    MaterialEditText name,phone,mssg;
    FButton submit;
    DatabaseReference ref;
    contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);





        name=(MaterialEditText)findViewById(R.id.name);
        phone=(MaterialEditText)findViewById(R.id.phone);
        mssg=(MaterialEditText)findViewById(R.id.mssg);
        submit=(FButton)findViewById(R.id.submit);

        contact=new contact();
        ref = FirebaseDatabase.getInstance().getReference().child("contact");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                contact.setName(name.getText().toString().trim());
                contact.setPhone(phone.getText().toString().trim());
                contact.setMssg(mssg.getText().toString().trim());


                if(name.getText().toString().matches("")) {
                    Toast.makeText(contactUs.this, "الرجاء كتابة الاسم", Toast.LENGTH_SHORT).show();
                } else if (phone.getText().toString().matches("") || phone.getText().toString().length() <= 9) {
                    Toast.makeText(contactUs.this, "الرجاء كتابة رقم الجوال", Toast.LENGTH_SHORT).show();
                } else if (mssg.getText().toString().matches("")){
                    Toast.makeText(contactUs.this, "الرجاء كتابة رسالتك", Toast.LENGTH_SHORT).show();
                }else {
                    ref.push().setValue(contact);

                    AlertDialog alertDialog = new AlertDialog.Builder(contactUs.this).create();
                    alertDialog.setTitle("Success !");
                    alertDialog.setMessage("تم رفع بياناتك على الداتابيس , سوف نتواصل معك قريبا");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "حسنا",


                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    name.setText("");
                                    phone.setText("");
                                    mssg.setText("");
                                    startActivity(new Intent(contactUs.this, HomeActivity.class));
                                }
                            });
                    alertDialog.show();
                }

            }
        });

    }
}
