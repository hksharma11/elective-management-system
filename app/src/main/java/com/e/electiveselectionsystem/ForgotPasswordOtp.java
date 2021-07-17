package com.e.electiveselectionsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;




public class ForgotPasswordOtp extends AppCompatActivity {

    private  String verificationId;
    private FirebaseAuth mAuth;
   
    private EditText editText_otp;
    String phone_with_code;
    FirebaseFirestore mydb;
    String phone;
    TextView resend, phonenumber;
    HashMap<String,Object> user;
    String regno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);

        mAuth= FirebaseAuth.getInstance();
        
        editText_otp= findViewById(R.id.otp_edittext);
        mydb= FirebaseFirestore.getInstance();
        phone_with_code=getIntent().getStringExtra("phone_with_code");
        phone=getIntent().getStringExtra("phone");
        regno=getIntent().getStringExtra("regno");
        resend=findViewById(R.id.otp_textview_resend);
        phonenumber=findViewById(R.id.otp_textview_phonenumber);
        phonenumber.setText(phone);



        sendVerificationCode(phone_with_code);


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode(phone_with_code);
                Toast.makeText(ForgotPasswordOtp.this, "OTP Send Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.otp_button_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editText_otp.getText().toString().isEmpty()) {


                    String code = editText_otp.getText().toString().trim();
                    if (code.isEmpty() || code.length() < 6)
                    {
                        editText_otp.setError("Enter OTP");
                        editText_otp.requestFocus();
                    }


                    verifyCode(code);

                }
                else {
                    Toast.makeText(ForgotPasswordOtp.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void verifyCode(String code){
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){




                                    Intent intent=new Intent(ForgotPasswordOtp.this, ResetPassword.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("phone",phone);
                                    intent.putExtra("regno",regno);
                                    startActivity(intent);



                        }
                        else {
                            Toast.makeText(ForgotPasswordOtp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }


    private void sendVerificationCode(String number){
        //progressBar.setVisibility(View.VISIBLE);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                30,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack

        );

        // progressBar.setVisibility(View.GONE);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code=phoneAuthCredential.getSmsCode();
            if(code !=null){
                editText_otp.setText(code);

                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(ForgotPasswordOtp.this,e.getMessage(), Toast.LENGTH_LONG).show();

        }
    };





}

