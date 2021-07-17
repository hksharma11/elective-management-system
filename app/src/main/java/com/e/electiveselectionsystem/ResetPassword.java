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
import com.google.firebase.firestore.FirebaseFirestore;



public class ResetPassword extends AppCompatActivity {
    String phone;
    FirebaseFirestore mydb;
    EditText editText_newPassword,editText_confirmPassword;
    Button button_save;
    Dialog loading;
    String regno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
       phone=getIntent().getStringExtra("phone");
       regno=getIntent().getStringExtra("regno");
       mydb= FirebaseFirestore.getInstance();
       editText_newPassword=findViewById(R.id.ResetPassword_edittext_newpassword);
       editText_confirmPassword=findViewById(R.id.ResetPassword_edittext_confirm);
       button_save=findViewById(R.id.ResetPassword_button_save);
       loading=new Dialog(ResetPassword.this);
       loading.setContentView(R.layout.loadingbar);
       loading.setCancelable(false);

       button_save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (!editText_newPassword.getText().toString().isEmpty() && !editText_confirmPassword.getText().toString().isEmpty())
               {
                   if (editText_confirmPassword.getText().toString().matches(editText_newPassword.getText().toString()))
                   {
                       loading.show();
                       mydb.collection("Users").document(regno).update("password",editText_newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               loading.dismiss();
                               Toast.makeText(ResetPassword.this, "Password Updated", Toast.LENGTH_LONG).show();
                               Intent intent=new Intent(ResetPassword.this,Student_Login.class);
                               startActivity(intent);
                               finish();
                           }
                       });
                   }
                   else {
                       Toast.makeText(ResetPassword.this, "Confirm Password did not matched.", Toast.LENGTH_SHORT).show();
                   }

               }
               else {
                   if (editText_newPassword.getText().toString().isEmpty())
                   {
                       editText_newPassword.setError("Empty Field");
                   }

                   if (editText_confirmPassword.getText().toString().isEmpty())
                   {
                       editText_confirmPassword.setError("Empty Field");
                   }
               }
           }
       });


    }
}