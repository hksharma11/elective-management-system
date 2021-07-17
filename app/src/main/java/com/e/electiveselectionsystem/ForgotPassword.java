package com.e.electiveselectionsystem;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



public class ForgotPassword extends AppCompatActivity {
    FirebaseFirestore mydb;
    EditText editText_phone;
    Dialog loading;
    Button button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        mydb= FirebaseFirestore.getInstance();
        editText_phone=findViewById(R.id.forgotPassword_edittext_phone);
        loading=new Dialog(ForgotPassword.this);
        loading.setContentView(R.layout.loadingbar);
        loading.setCancelable(false);
        button_send=findViewById(R.id.forgotPassword_button_send);




        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText_phone.getText().toString().isEmpty())
                {
                    loading.show();


                mydb.collection("Users").document(editText_phone.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        if (documentSnapshot.exists())
                        {
                            String phonenumber=documentSnapshot.getData().get("phone").toString();
                            String phone_with_code="+91"+phonenumber;
                            String phone=phonenumber;
                            Intent intent=new Intent(ForgotPassword.this,ForgotPasswordOtp.class);
                            intent.putExtra("phone",phone);
                            intent.putExtra("phone_with_code",phone_with_code);
                            intent.putExtra("regno",editText_phone.getText().toString());
                            loading.dismiss();
                            startActivity(intent);


                        }else{
                            loading.dismiss();
                            Toast.makeText(ForgotPassword.this, "User Not Registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                }
                else {
                    editText_phone.setError("Empty Field");
                }





            }
        });
    }
}