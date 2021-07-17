package com.e.electiveselectionsystem;

import android.content.Context;
import android.content.SharedPreferences;

public class Shared_data {

    Context context;


    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    public String getPhone() {
        phone=sharedPreferences.getString("phone","");
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        sharedPreferences.edit().putString("phone",phone).commit();
    }

    private String phone;
    SharedPreferences sharedPreferences;


    public Shared_data(Context context){
        this.context=context;

        sharedPreferences=context.getSharedPreferences("phone", Context.MODE_PRIVATE);
    }
}
