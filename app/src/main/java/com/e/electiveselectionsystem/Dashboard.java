package com.e.electiveselectionsystem;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class Dashboard extends AppCompatActivity {

    private SmartMaterialSpinner spinner1,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7;

    private List<String> provinceList;
    RadioGroup radioGroup;
    LinearLayout choices;
    LinearLayout buttons;
    FirebaseFirestore mydb;
    Button submit,reset;


    List<String> subject_from_database;
    HashMap<String, Object> preferences;

    Button preview_submit;
    String selected_sem="";

    TextView selected_semester;
    LinearLayout submitted_successfully;


    TextView preview1,preview2,preview3,preview4,preview5,preview6,preview7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        radioGroup=findViewById(R.id.radioGroup);
        choices=findViewById(R.id.choices2);
        buttons=findViewById(R.id.buttons);
        mydb=FirebaseFirestore.getInstance();
        preferences = new HashMap<>();
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        spinner5 = findViewById(R.id.spinner5);
        spinner6 = findViewById(R.id.spinner6);
        spinner7 = findViewById(R.id.spinner7);
        selected_semester=findViewById(R.id.selected_semester);
        submit=findViewById(R.id.submit);

        submitted_successfully=findViewById(R.id.submitted_successfully);



        final AlertDialog.Builder builder=new AlertDialog.Builder(Dashboard.this);
        final View preview=getLayoutInflater().inflate(R.layout.preview,null);
        builder.setView(preview);
        final AlertDialog preview_dialog=builder.create();

        preview1=preview.findViewById(R.id.preview_textview1);
        preview2=preview.findViewById(R.id.preview_textview2);
        preview3=preview.findViewById(R.id.preview_textview3);
        preview4=preview.findViewById(R.id.preview_textview4);
        preview5=preview.findViewById(R.id.preview_textview5);
        preview6=preview.findViewById(R.id.preview_textview6);
        preview7=preview.findViewById(R.id.preview_textview7);

        preview_submit=preview.findViewById(R.id.preview_button_submit);















        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                preferences.put("pref1",subject_from_database.get(position));
                preview1.setText("1. "+subject_from_database.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                preferences.put("pref2",subject_from_database.get(position));
                preview2.setText("2. "+subject_from_database.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                preferences.put("pref3",subject_from_database.get(position));
                preview3.setText("3. "+subject_from_database.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                preferences.put("pref4",subject_from_database.get(position));
                preview4.setText("4. "+subject_from_database.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                preferences.put("pref5",subject_from_database.get(position));
                preview5.setText("5. "+subject_from_database.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                preferences.put("pref6",subject_from_database.get(position));
                preview6.setText("6. "+subject_from_database.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                preferences.put("pref7",subject_from_database.get(position));
                preview7.setText("7. "+subject_from_database.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preferences.size()<7)
                {
                    Toast.makeText(Dashboard.this, "Fill all the preferences", Toast.LENGTH_SHORT).show();
                }else{



                    preview_dialog.show();
                }
            }
        });


        preview_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Shared_data shared_data=new Shared_data(Dashboard.this);
                    String regno=shared_data.getPhone();
                    mydb.collection("Subjects").document(selected_sem).collection("Preferences").document(regno).set(preferences).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            preview_dialog.dismiss();
                            Toast.makeText(Dashboard.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                            choices.setVisibility(View.GONE);
                            submitted_successfully.setVisibility(View.VISIBLE);
                            buttons.setVisibility(View.GONE);

                        }
                    });
            }
        });














        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.radiobutton1 :
                        selected_sem="4th_semester";
                       // subjectList("4th_semester");
                        selected_semester.setVisibility(View.VISIBLE);
                        selected_semester.setText(selected_sem);
                        isSubmitted(selected_sem);
                        break;

                    case R.id.radiobutton2 :
                        selected_sem="5th_semester";
                        selected_semester.setVisibility(View.VISIBLE);
                        selected_semester.setText(selected_sem);
                        isSubmitted(selected_sem);



                          break;


                    case R.id.radiobutton3 :
                        selected_sem="6th_semester";
                        selected_semester.setVisibility(View.VISIBLE);
                        selected_semester.setText(selected_sem);
                        isSubmitted(selected_sem);



                        break;


                    case R.id.radiobutton4 :
                        selected_sem="7th_semester";
                        selected_semester.setVisibility(View.VISIBLE);
                        selected_semester.setText(selected_sem);
                        isSubmitted(selected_sem);



                        break;

                }
            }
        });


        findViewById(R.id.studentlogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared_data shared_data=new Shared_data(Dashboard.this);
                shared_data.removeUser();

                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(Dashboard.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



    }

    private void initSpinner(List<String> subject_from_database) {
        Log.d("969696","task1");

        Log.d("969696","task2");


        spinner1.setItem(subject_from_database);
        spinner2.setItem(subject_from_database);
        spinner3.setItem(subject_from_database);
        spinner4.setItem(subject_from_database);
        spinner5.setItem(subject_from_database);
        spinner6.setItem(subject_from_database);
        spinner7.setItem(subject_from_database);
        Log.d("969696","task3");


    }



    void subjectList(String sem)
    {
        mydb.collection("Subjects").document(sem).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot=task.getResult();
                assert documentSnapshot != null;

                if (documentSnapshot.exists())
                {
                    choices.setVisibility(View.VISIBLE);
                    buttons.setVisibility(View.VISIBLE);
                    subject_from_database=new ArrayList<>();
                    subject_from_database.add(Objects.requireNonNull(Objects.requireNonNull(documentSnapshot.getData()).get("elec0")).toString());
                    subject_from_database.add(Objects.requireNonNull(Objects.requireNonNull(documentSnapshot.getData()).get("elec1")).toString());
                    subject_from_database.add(Objects.requireNonNull(Objects.requireNonNull(documentSnapshot.getData()).get("elec2")).toString());
                    subject_from_database.add(Objects.requireNonNull(Objects.requireNonNull(documentSnapshot.getData()).get("elec3")).toString());
                    subject_from_database.add(Objects.requireNonNull(Objects.requireNonNull(documentSnapshot.getData()).get("elec4")).toString());
                    subject_from_database.add(Objects.requireNonNull(Objects.requireNonNull(documentSnapshot.getData()).get("elec5")).toString());
                    subject_from_database.add(Objects.requireNonNull(Objects.requireNonNull(documentSnapshot.getData()).get("elec6")).toString());

                    initSpinner(subject_from_database);

                }else{
                    choices.setVisibility(View.GONE);
                    buttons.setVisibility(View.GONE);
                    Toast.makeText(Dashboard.this, "No Form Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        void isSubmitted(final String sem)
        {
            Log.d("#testing","task1");
            Shared_data shared_data=new Shared_data(Dashboard.this);
            String regno=shared_data.getPhone();
            mydb.collection("Subjects").document(sem).collection("Preferences").document(regno).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value.exists())
                    {
                        submitted_successfully.setVisibility(View.VISIBLE);
                    }
                    else{
                        submitted_successfully.setVisibility(View.GONE);
                        subjectList(sem);
                    }
                }
            });
        }

}
