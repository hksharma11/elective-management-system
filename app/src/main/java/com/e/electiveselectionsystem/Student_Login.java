package com.e.electiveselectionsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Student_Login extends AppCompatActivity {

    EditText regno,password;
    Button signin;
    FirebaseFirestore mydb;
    Dialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__login);

        mydb=FirebaseFirestore.getInstance();

        regno=findViewById(R.id.studentlogin_regno);
        password=findViewById(R.id.studentlogin_password);
        signin=findViewById(R.id.studentlogin_signin);

        loadingBar=new Dialog(Student_Login.this);
        loadingBar.setContentView(R.layout.loadingbar);
        loadingBar.setCancelable(false);


//        student_signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!regno.getText().toString().isEmpty() || !password.getText().toString().isEmpty())
//                {
//                    mydb.collection("students").document(regno.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                            if (task.isSuccessful())
//                            {
//                                DocumentSnapshot documentSnapshot=task.getResult();
//                                if (documentSnapshot.exists()){
//
//                                    String pass=documentSnapshot.getData().get("password").toString();
//                                    if (pass.equals(password.getText().toString()))
//                                    {
//                                        Shared_data shared_data=new Shared_data(Student_Login.this);
//                                        shared_data.setPhone(regno.getText().toString());
//
//                                        Intent intent=new Intent(Student_Login.this, Dashboard.class);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                    else
//                                    {
//
//                                        Toast.makeText(Student_Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                                else {
//
//                                    Toast.makeText(Student_Login.this, "User Not Registered", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            else {
//                                Toast.makeText(Student_Login.this, "Something went wrong!\nTry Again", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//                }else{
//                    Toast.makeText(Student_Login.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        findViewById(R.id.sign_up_for_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Student_Login.this,Student_Registration.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.forgotpassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Student_Login.this,ForgotPassword.class);
                startActivity(intent);
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidlogin())
                {
                    loadingBar.show();

                    mydb.collection("Users").document(regno.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful())
                            {
                                DocumentSnapshot documentSnapshot=task.getResult();
                                if (documentSnapshot.exists()){

                                    String pass=documentSnapshot.getData().get("password").toString();
                                    if (pass.equals(password.getText().toString()))
                                    {
                                        Shared_data shared_data=new Shared_data(Student_Login.this);
                                        shared_data.setPhone(regno.getText().toString());

                                        Intent intent=new Intent(Student_Login.this, Dashboard.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        loadingBar.hide();
                                        Toast.makeText(Student_Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    loadingBar.hide();
                                    Toast.makeText(Student_Login.this, "User Not Registered", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(Student_Login.this, "Something went wrong!\nTry Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


















    }

    boolean isValidlogin(){
        boolean result=true;
        if(regno.getText().toString().isEmpty()){
            regno.setError("Empty Field");
            result=false;
        }

        else {
            regno.setError(null);
        }

        if (password.getText().toString().isEmpty()){
            password.setError("Field Required");
            result=false;
        }
        else {
            password.setError(null);
        }

        return result;
    }
}