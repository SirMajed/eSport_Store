package com.example.eeshop.ui.tools;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.eeshop.R;
import com.example.eeshop.contact;
import com.example.eeshop.contactUs;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import info.hoang8f.widget.FButton;

public class ToolsFragment extends Fragment {

    MaterialEditText name,phone,mssg;
    FButton submit;
    DatabaseReference ref;
    contact contact;
    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);



        name=(MaterialEditText) root.findViewById(R.id.name);
        phone=(MaterialEditText)root.findViewById(R.id.phone);
        mssg=(MaterialEditText)root.findViewById(R.id.mssg);
        submit=(FButton)root.findViewById(R.id.submit);

        contact=new contact();
        ref = FirebaseDatabase.getInstance().getReference().child("contact");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contact.setName(name.getText().toString().trim());
                contact.setPhone(phone.getText().toString().trim());
                contact.setMssg(mssg.getText().toString().trim());

                if(name.getText().toString().matches("")) {
                    Toast.makeText(getContext(), "الرجاء كتابة الاسم", Toast.LENGTH_SHORT).show();
                } else if (phone.getText().toString().matches("") || phone.getText().toString().length() <= 9) {
                    Toast.makeText(getContext(), "الرجاء كتابة رقم الجوال", Toast.LENGTH_SHORT).show();
                } else if (mssg.getText().toString().matches("")){
                    Toast.makeText(getContext(), "الرجاء كتابة رسالتك", Toast.LENGTH_SHORT).show();
                }else {
                    ref.push().setValue(contact);

                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("نجاح !");
                    alertDialog.setMessage("تم رفع بياناتك على الداتابيس , سوف نتواصل معك قريبا");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "حسنا",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                    name.setText("");
                                    phone.setText("");
                                    mssg.setText("");
                                }
                            });
                    alertDialog.show();
                }




            }
        });


        return root;
    }
}