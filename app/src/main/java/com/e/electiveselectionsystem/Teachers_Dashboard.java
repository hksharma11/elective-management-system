package com.e.electiveselectionsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.squareup.okhttp.internal.framed.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class Teachers_Dashboard extends AppCompatActivity {
    AsyncHttpClient client;
    Workbook workbook;

    LinearLayout teachers_operation;
    Button upload_elective;
    RadioGroup radioGroup;
    String url="";
    FirebaseFirestore mydb;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String collection="4th_semester";
    LinearLayout formfilled;
    TextView numerator,denominator;
    Dialog loading;
    TextView selectedsemester;
    int buttonpressed=0;
    Button generateList;
    TextView cgpa_uploaded;
    TextView subject_uploaded;
    TextView upload_student_cgpa;
    Button downloadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teachers__dashboard);
        radioGroup=findViewById(R.id.teachers_dashboard_radioGroup);
        upload_elective=findViewById(R.id.teachers_dashboard_button_upload_elective);
        upload_student_cgpa=findViewById(R.id.teachers_dashboard_button_upload_students_cgpa);

        subject_uploaded=findViewById(R.id.teachers_dashboard_textview_subject_uploaded);
        cgpa_uploaded=findViewById(R.id.cgpa_uploaded);
        selectedsemester=findViewById(R.id.selected_semestertecher);

        formfilled=findViewById(R.id.LinearLayout_formfilled);
        numerator=findViewById(R.id.numerator);
        denominator=findViewById(R.id.denominator);

        generateList=findViewById(R.id.generateList);
        downloadList=findViewById(R.id.downloadList);


        teachers_operation=findViewById(R.id.teachers_dashboard_linearlayout_operations);

        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingbar);
        loading.setCancelable(false);

        mydb= FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.teachers_dashboard_radiobutton1 :
                        collection="4th_semester";
                        selectedsemester.setVisibility(View.VISIBLE);
                        selectedsemester.setText(collection);
                        teachers_operation.setVisibility(View.VISIBLE);

                        break;


                    case R.id.teachers_dashboard_radiobutton2 :
                        collection="5th_semester";
                        selectedsemester.setVisibility(View.VISIBLE);
                        selectedsemester.setText(collection);
                        teachers_operation.setVisibility(View.GONE);
                        Toast.makeText(Teachers_Dashboard.this, "Work in Progress", Toast.LENGTH_SHORT).show();

                        break;


                    case R.id.teachers_dashboard_radiobutton3 :
                        collection="6th_semester";
                        selectedsemester.setVisibility(View.VISIBLE);
                        selectedsemester.setText(collection);
                        teachers_operation.setVisibility(View.GONE);
                        Toast.makeText(Teachers_Dashboard.this, "Work in Progress", Toast.LENGTH_SHORT).show();

                        break;


                    case R.id.teachers_dashboard_radiobutton4 :
                        collection="7th_semester";
                        selectedsemester.setVisibility(View.VISIBLE);
                        selectedsemester.setText(collection);
                        teachers_operation.setVisibility(View.GONE);
                        Toast.makeText(Teachers_Dashboard.this, "Work in Progress", Toast.LENGTH_SHORT).show();

                        break;

                }
            }
        });




        upload_elective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonpressed=1;
                selectFile();
            }
        });


            generateList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mydb.collection("Subjects").document(collection).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc1=task.getResult();
                            if (doc1.exists())
                            {
                                String elec1=doc1.getData().get("elec0").toString();
                                String elec2=doc1.getData().get("elec1").toString();
                                String elec3=doc1.getData().get("elec2").toString();
                                String elec4=doc1.getData().get("elec3").toString();
                                String elec5=doc1.getData().get("elec4").toString();
                                String elec6=doc1.getData().get("elec5").toString();
                                String elec7=doc1.getData().get("elec6").toString();

                                final HashMap<String,Objects> test=new HashMap<>();
                                mydb.collection("Subjects").document(collection).collection(elec1).document("1").set(test);
                                mydb.collection("Subjects").document(collection).collection(elec2).document("1").set(test);
                                mydb.collection("Subjects").document(collection).collection(elec3).document("1").set(test);
                                mydb.collection("Subjects").document(collection).collection(elec4).document("1").set(test);
                                mydb.collection("Subjects").document(collection).collection(elec5).document("1").set(test);
                                mydb.collection("Subjects").document(collection).collection(elec6).document("1").set(test);
                                mydb.collection("Subjects").document(collection).collection(elec7).document("1").set(test);

                                Log.d("#alpha","task1");

                                mydb.collection("Subjects").document(collection).collection("students").orderBy("cgpa", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        Log.d("#alpha","task2");

                                        for(final QueryDocumentSnapshot doc:value)
                                        {
                                            final String reg=doc.getData().get("regno").toString();
                                            Log.d("#alpha",reg);
                                            mydb.collection("Subjects").document(collection).collection("Preferences").document(reg).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful())
                                                    {
                                                        DocumentSnapshot documentSnapshot=task.getResult();
                                                        if (documentSnapshot.exists())
                                                        {
                                                            final String pref1=documentSnapshot.getData().get("pref1").toString();
                                                            final String pref2=documentSnapshot.getData().get("pref2").toString();
                                                            final String pref3=documentSnapshot.getData().get("pref3").toString();
                                                            final String pref4=documentSnapshot.getData().get("pref4").toString();
                                                            final String pref5=documentSnapshot.getData().get("pref5").toString();
                                                            final String pref6=documentSnapshot.getData().get("pref6").toString();
                                                            final String pref7=documentSnapshot.getData().get("pref7").toString();

                                                            mydb.collection("Subjects").document(collection).collection(pref1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if (task.getResult().size()<=40)
                                                                    {
                                                                        final  HashMap<String,String> alloted=new HashMap<>();
                                                                        alloted.put("regno",reg);
                                                                        alloted.put("sub",pref1);

                                                                        mydb.collection("Subjects").document(collection).collection(pref1).document(reg).set(alloted);
                                                                        Toast.makeText(Teachers_Dashboard.this, "List Generated", Toast.LENGTH_SHORT).show();
                                                                        downloadList.setVisibility(View.VISIBLE);
                                                                    }else{
                                                                        mydb.collection("Subjects").document(collection).collection(pref2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                if (task.getResult().size()<=40)
                                                                                {
                                                                                    final  HashMap<String,String> alloted=new HashMap<>();
                                                                                    alloted.put("regno",reg);
                                                                                    alloted.put("sub",pref2);

                                                                                    mydb.collection("Subjects").document(collection).collection(pref2).document(reg).set(alloted);
                                                                                    Toast.makeText(Teachers_Dashboard.this, "List Generated", Toast.LENGTH_SHORT).show();
                                                                                    downloadList.setVisibility(View.VISIBLE);
                                                                                }else{
                                                                                    mydb.collection("Subjects").document(collection).collection(pref3).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                            if (task.getResult().size()<=40)
                                                                                            {
                                                                                                final  HashMap<String,String> alloted=new HashMap<>();
                                                                                                alloted.put("regno",reg);
                                                                                                alloted.put("sub",pref3);

                                                                                                mydb.collection("Subjects").document(collection).collection(pref3).document(reg).set(alloted);
                                                                                                Toast.makeText(Teachers_Dashboard.this, "List Generated", Toast.LENGTH_SHORT).show();
                                                                                                downloadList.setVisibility(View.VISIBLE);
                                                                                            }else{
                                                                                                mydb.collection("Subjects").document(collection).collection(pref4).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                        if (task.getResult().size()<=40)
                                                                                                        {
                                                                                                            final  HashMap<String,String> alloted=new HashMap<>();
                                                                                                            alloted.put("regno",reg);
                                                                                                            alloted.put("sub",pref4);

                                                                                                            mydb.collection("Subjects").document(collection).collection(pref4).document(reg).set(alloted);
                                                                                                            Toast.makeText(Teachers_Dashboard.this, "List Generated", Toast.LENGTH_SHORT).show();
                                                                                                            downloadList.setVisibility(View.VISIBLE);
                                                                                                        }else{
                                                                                                            mydb.collection("Subjects").document(collection).collection(pref5).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                                    if (task.getResult().size()<=40)
                                                                                                                    {
                                                                                                                        final  HashMap<String,String> alloted=new HashMap<>();
                                                                                                                        alloted.put("regno",reg);
                                                                                                                        alloted.put("sub",pref5);

                                                                                                                        mydb.collection("Subjects").document(collection).collection(pref5).document(reg).set(alloted);
                                                                                                                        Toast.makeText(Teachers_Dashboard.this, "List Generated", Toast.LENGTH_SHORT).show();
                                                                                                                        downloadList.setVisibility(View.VISIBLE);
                                                                                                                    }else{
                                                                                                                        mydb.collection("Subjects").document(collection).collection(pref6).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                                            @Override
                                                                                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                                                if (task.getResult().size()<=40)
                                                                                                                                {
                                                                                                                                    final  HashMap<String,String> alloted=new HashMap<>();
                                                                                                                                    alloted.put("regno",reg);
                                                                                                                                    alloted.put("sub",pref6);

                                                                                                                                    mydb.collection("Subjects").document(collection).collection(pref6).document(reg).set(alloted);
                                                                                                                                    Toast.makeText(Teachers_Dashboard.this, "List Generated", Toast.LENGTH_SHORT).show();
                                                                                                                                    downloadList.setVisibility(View.VISIBLE);
                                                                                                                                }else{
                                                                                                                                    mydb.collection("Subjects").document(collection).collection(pref7).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                                                            if (task.getResult().size()<=40)
                                                                                                                                            {
                                                                                                                                                final  HashMap<String,String> alloted=new HashMap<>();
                                                                                                                                                alloted.put("regno",reg);
                                                                                                                                                alloted.put("sub",pref7);

                                                                                                                                                mydb.collection("Subjects").document(collection).collection(pref7).document(reg).set(alloted);
                                                                                                                                                Toast.makeText(Teachers_Dashboard.this, "List Generated", Toast.LENGTH_SHORT).show();
                                                                                                                                                downloadList.setVisibility(View.VISIBLE);
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    });
                                                                                                                                }
                                                                                                                            }
                                                                                                                        });
                                                                                                                    }
                                                                                                                }
                                                                                                            });
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                            }
                                                                                        }
                                                                                    });
                                                                                }
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            });






                                                        }else{
                                                            Toast.makeText(Teachers_Dashboard.this, "Preference not uploaded "+reg, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            });


            downloadList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mydb.collection("Subjects").document(collection).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc1=task.getResult();

                            String elec1=doc1.getData().get("elec0").toString();
                            final String elec2=doc1.getData().get("elec1").toString();
                            final String elec3=doc1.getData().get("elec2").toString();
                            final String elec4=doc1.getData().get("elec3").toString();
                            final String elec5=doc1.getData().get("elec4").toString();
                            final String elec6=doc1.getData().get("elec5").toString();
                            final String elec7=doc1.getData().get("elec6").toString();
                            final StringBuilder data=new StringBuilder();
                            data.append("Registration Number,Subject");
                            mydb.collection("Subjects").document(collection).collection(elec1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    for(QueryDocumentSnapshot doc:task.getResult())
                                    {
                                        if (doc.getData().containsKey("sub"))
                                        {
                                            data.append("\n"+doc.getData().get("regno").toString()+","+doc.getData().get("sub").toString());
                                        }

                                    }

                                    mydb.collection("Subjects").document(collection).collection(elec2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for(QueryDocumentSnapshot doc:task.getResult())
                                            {
                                                if (doc.getData().containsKey("sub"))
                                                {
                                                    data.append("\n"+doc.getData().get("regno").toString()+","+doc.getData().get("sub").toString());
                                                }
                                            }

                                            mydb.collection("Subjects").document(collection).collection(elec3).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    for(QueryDocumentSnapshot doc:task.getResult())
                                                    {
                                                        if (doc.getData().containsKey("sub"))
                                                        {
                                                            data.append("\n"+doc.getData().get("regno").toString()+","+doc.getData().get("sub").toString());
                                                        }
                                                    }

                                                    mydb.collection("Subjects").document(collection).collection(elec4).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            for(QueryDocumentSnapshot doc:task.getResult())
                                                            {
                                                                if (doc.getData().containsKey("sub"))
                                                                {
                                                                    data.append("\n"+doc.getData().get("regno").toString()+","+doc.getData().get("sub").toString());
                                                                }
                                                            }

                                                            mydb.collection("Subjects").document(collection).collection(elec5).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    for(QueryDocumentSnapshot doc:task.getResult())
                                                                    {
                                                                        if (doc.getData().containsKey("sub"))
                                                                        {
                                                                            data.append("\n"+doc.getData().get("regno").toString()+","+doc.getData().get("sub").toString());
                                                                        }
                                                                    }

                                                                    mydb.collection("Subjects").document(collection).collection(elec6).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                            for(QueryDocumentSnapshot doc:task.getResult())
                                                                            {
                                                                                if (doc.getData().containsKey("sub"))
                                                                                {
                                                                                    data.append("\n"+doc.getData().get("regno").toString()+","+doc.getData().get("sub").toString());
                                                                                }
                                                                            }

                                                                            mydb.collection("Subjects").document(collection).collection(elec7).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                    for(QueryDocumentSnapshot doc:task.getResult())
                                                                                    {
                                                                                        if (doc.getData().containsKey("sub"))
                                                                                        {
                                                                                            data.append("\n"+doc.getData().get("regno").toString()+","+doc.getData().get("sub").toString());
                                                                                        }
                                                                                    }
                                                                                    try {
                                                                                        FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
                                                                                        out.write((data.toString()).getBytes());
                                                                                        out.close();


                                                                                        Context context=getApplicationContext();
                                                                                        File filelocation=new File(getFilesDir(),"data.csv");
                                                                                        Uri path = FileProvider.getUriForFile(context,"com.e.electiveselectionsystem.fileprovider",filelocation);
                                                                                        Intent fileIntent=new Intent(Intent.ACTION_SEND);
                                                                                        fileIntent.setType("test/csv");
                                                                                        fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Data");
                                                                                        fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                                                        fileIntent.putExtra(Intent.EXTRA_STREAM,path);
                                                                                        startActivity(Intent.createChooser(fileIntent,"Send Mail"));

                                                                                    }catch (Exception e)
                                                                                    {
                                                                                        e.printStackTrace();
                                                                                    }


                                                                                }
                                                                            });
                                                                        }
                                                                    });
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });

                                }
                            });
                        }
                    });



                }
            });




        // checking if subjects are uploaded

        mydb.collection("Subjects").document(collection).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot=task.getResult();
                assert documentSnapshot != null;
                if (documentSnapshot.exists())
                {
                    try{
                        if (documentSnapshot.getData().get("elec0").toString()!=null)
                        {
                            subject_uploaded.setVisibility(View.VISIBLE);
                        }
                        else {
                            subject_uploaded.setVisibility(View.GONE);
                        }
                    }catch (Exception e)
                    {

                    }

                }
            }
        });

        mydb.collection("Subjects").document(collection).collection("students").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                assert value != null;
                if (value.size()>0)
                {
                    cgpa_uploaded.setVisibility(View.VISIBLE);
                    formfilled.setVisibility(View.VISIBLE);
                }
                int i=0;
                for (QueryDocumentSnapshot doc: value)
                {
                        denominator.setText(Integer.toString(++i));
                }
            }
        });


        mydb.collection("Subjects").document(collection).collection("Preferences").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                int i=0;
                for (QueryDocumentSnapshot doc: value)
                {
                    numerator.setText(Integer.toString(++i));
                }
            }
        });

        upload_student_cgpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonpressed=2;
                selectFile();
            }
        });


        findViewById(R.id.teacherlogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared_data shared_data=new Shared_data(Teachers_Dashboard.this);
                shared_data.removeUser();

                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(Teachers_Dashboard.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });







    }

    private void selectFile() {
        Intent intent = new Intent();
        intent.setType("application/vnd.ms-excel");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select File"),1);

        Log.d("#36323236","task1");


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            Log.d("#36323236","task2");
            loading.show();
            if (buttonpressed==1)
            {
                uploadFile(data.getData());
            }

            if (buttonpressed==2)
            {
                uploadFile2(data.getData());
            }

        }

    }

    private void uploadFile(Uri data) {

        StorageReference reference = storageReference.child(Calendar.getInstance().getTimeInMillis() +".xls");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("#36323236","task3");
                        Task<Uri> uri= taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Log.d("#36323236","task4");
                        Uri u=uri.getResult();
                        Log.d("#36323236","task5");
                        url = u.toString();

                        Log.d("#36323236","task6");

                        Log.d("#36323236",url);

                        updatedatabase(url);



                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

    }

    private void uploadFile2(Uri data) {

        StorageReference reference = storageReference.child(Calendar.getInstance().getTimeInMillis() +".xls");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("#36323236","task3");
                        Task<Uri> uri= taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Log.d("#36323236","task4");
                        Uri u=uri.getResult();
                        Log.d("#36323236","task5");
                        url = u.toString();

                        Log.d("#36323236","task6");

                        Log.d("#36323236",url);
                        if (buttonpressed==1)
                        {
                            updatedatabase(url);
                        }
                        if (buttonpressed==2)
                        {
                            updatedatabase2(url);
                        }



                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

    }


    void updatedatabase(final String link)
    {
        Log.d("#36323236","task7");
        //String link="https://firebasestorage.googleapis.com/v0/b/elective-selection-syste-47709.appspot.com/o/uploads%2Fdatanew1%20(2).xls?alt=media&token=d7d7ae2a-8f90-41e9-a4a8-3d49d6c702a5";
        client=new AsyncHttpClient();
        client.get(link, new FileAsyncHttpResponseHandler(Teachers_Dashboard.this) {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, File file) {
                Toast.makeText(Teachers_Dashboard.this, "Download Failed", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                Log.d("#36323236",throwable.getLocalizedMessage());
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, File file) {
                Toast.makeText(Teachers_Dashboard.this, "Downloaded", Toast.LENGTH_SHORT).show();
                Log.d("#36323236","task8");
                WorkbookSettings ws= new WorkbookSettings();
                ws.setGCDisabled(true);

                if (file!=null) {
                    try {
                        Log.d("#36323236","task9");
                        workbook = workbook.getWorkbook(file);

                        Sheet sheet= workbook.getSheet(0);

                        final HashMap<String, Object> test = new HashMap<>();
                        mydb.collection("Subjects").document(collection).set(test);

                        for(int i=0 ; i<sheet.getRows(); i++){
                            Log.d("#36323236","task10");
                            Cell[] row=sheet.getRow(i);
                            Log.d("#36323236",row.toString());
                            Log.d("#36323236",row[0].getContents());
                            final HashMap<String, Object> elective = new HashMap<>();
                            elective.put("elec"+i,row[0].getContents());

//                            mydb.collection("Subjects").document(collection).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                    DocumentSnapshot snapshot=task.getResult();
//                                    if(snapshot.exists())
//                                    {
//                                        mydb.collection("Subjects").document(collection).update(elective);
//                                        subject_uploaded.setVisibility(View.VISIBLE);
//                                        loading.dismiss();
//                                    }else{
//                                        mydb.collection("Subjects").document(collection).set(elective);
//                                        subject_uploaded.setVisibility(View.VISIBLE);
//                                        loading.dismiss();
//                                    }
//                                }
//                            });


                            mydb.collection("Subjects").document(collection).update(elective).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    loading.dismiss();
                                    subject_uploaded.setVisibility(View.VISIBLE);
                                    Toast.makeText(Teachers_Dashboard.this, "Done", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    } catch (IOException e) {
                        Log.d("#36323236",e.toString());
                        e.printStackTrace();
                    } catch (BiffException e) {
                        Log.d("#36323236",e.toString());
                        e.printStackTrace();
                    }
                }
            }



        });
    }

    void updatedatabase2(final String link)
    {
        Log.d("#36323236","task7");
        //String link="https://firebasestorage.googleapis.com/v0/b/elective-selection-syste-47709.appspot.com/o/uploads%2Fdatanew1%20(2).xls?alt=media&token=d7d7ae2a-8f90-41e9-a4a8-3d49d6c702a5";
        client=new AsyncHttpClient();
        client.get(link, new FileAsyncHttpResponseHandler(Teachers_Dashboard.this) {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, File file) {
                Toast.makeText(Teachers_Dashboard.this, "Download Failed", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                Log.d("#36323236",throwable.getLocalizedMessage());
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, File file) {
                Toast.makeText(Teachers_Dashboard.this, "Downloaded", Toast.LENGTH_SHORT).show();
                Log.d("#36323236","task8");
                WorkbookSettings ws= new WorkbookSettings();
                ws.setGCDisabled(true);

                if (file!=null) {
                    try {
                        Log.d("#36323236","task9");
                        workbook = workbook.getWorkbook(file);

                        Sheet sheet= workbook.getSheet(0);
                        int i;
                        for(i=0 ; i<sheet.getRows(); i++){
                            Log.d("#36323236","task10");
                            Cell[] row=sheet.getRow(i);
//                            Log.d("#36323236",row.toString());
//                            Log.d("#36323236",row[0].getContents());
//                            final HashMap<String, Object> elective = new HashMap<>();
//                            elective.put("elec"+i,row[0].getContents());

                            final String regno=row[0].getContents();
                            final String cgpa=row[1].getContents();

                            mydb.collection("Subjects").document(collection).collection("students").document(regno).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful())
                                    {
                                        DocumentSnapshot documentSnapshot=task.getResult();
                                        if (documentSnapshot.exists())
                                        {
                                            mydb.collection("Subjects").document(collection).collection("students").document(regno).update("cgpa",cgpa);
                                            loading.dismiss();
                                            cgpa_uploaded.setVisibility(View.VISIBLE);
                                            formfilled.setVisibility(View.VISIBLE);
                                        }else{
                                            final HashMap<String, Object> student = new HashMap<>();
                                            long reg=Long.parseLong(regno);
                                            float cg=Float.parseFloat(cgpa);
                                            student.put("regno",reg);
                                            student.put("cgpa",cg);
                                            mydb.collection("Subjects").document(collection).collection("students").document(regno).set(student);
                                            loading.dismiss();
                                            cgpa_uploaded.setVisibility(View.VISIBLE);
                                            formfilled.setVisibility(View.VISIBLE);
                                        }
                                    }else{
                                        loading.dismiss();
                                    }
                                }
                            });



//                            mydb.collection("Subjects").document(collection).update(elective).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    loading.dismiss();
//                                    subject_uploaded.setVisibility(View.VISIBLE);
//                                    Toast.makeText(Teachers_Dashboard.this, "Done", Toast.LENGTH_SHORT).show();
//                                }
//                            });

                        }


                    } catch (IOException e) {
                        Log.d("#36323236",e.toString());
                        e.printStackTrace();
                    } catch (BiffException e) {
                        Log.d("#36323236",e.toString());
                        e.printStackTrace();
                    }
                }
            }



        });
    }





}