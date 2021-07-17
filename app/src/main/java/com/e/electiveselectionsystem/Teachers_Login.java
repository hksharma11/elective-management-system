package com.e.electiveselectionsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Teachers_Login extends AppCompatActivity {

    EditText teacherID,teacherPassword;
    Button signin;
    FirebaseFirestore mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers__login);

        teacherID=findViewById(R.id.edittext_teacherID);
        teacherPassword=findViewById(R.id.edittext_teacherPassword);
        signin=findViewById(R.id.button_teacherSignin);

        mydb=FirebaseFirestore.getInstance();



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.collection("Teachers").document("admin").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        if (documentSnapshot.exists())
                        {
                            String pass=documentSnapshot.getData().get("password").toString();
                            if (teacherPassword.getText().toString().equals(pass))
                            {
                                Shared_data shared_data=new Shared_data(Teachers_Login.this);
                                shared_data.setPhone("teacher");

                                Intent intent=new Intent(Teachers_Login.this,Teachers_Dashboard.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(Teachers_Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });


    }
}