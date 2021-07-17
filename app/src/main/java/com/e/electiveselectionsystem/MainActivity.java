package com.e.electiveselectionsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import org.apache.log4j.chainsaw.Main;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb=FirebaseFirestore.getInstance();


        findViewById(R.id.student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MainActivity.this,Student_Login.class);
                startActivity(intent);
            }
        });


        Shared_data shared_data=new Shared_data(MainActivity.this);
        if(shared_data.getPhone().equals("teacher"))
        {
            Intent intent=new Intent(MainActivity.this,Teachers_Dashboard.class);
            startActivity(intent);
            finish();
        }else if(shared_data.getPhone()!="")
        {
            Intent intent= new Intent(MainActivity.this,Dashboard.class);
            startActivity(intent);
            finish();
        }


        findViewById(R.id.admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Teachers_Login.class);
                startActivity(intent);
            }
        });
    }



}