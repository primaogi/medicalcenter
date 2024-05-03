package com.medicalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {

    TextView detaildate, detailtime, detailepf, detailname, detaildepartment, detaildiagnose, detailmedicine, detailnote, detailreported;
    Button deleteButton, updateButton;
    String key = "";
    DatabaseReference userDbRef;
    Boolean isAdmin = false;
    Boolean isNurse = false;
    Boolean isSuperuser = false;
    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detaildate = findViewById(R.id.detailDate);
        detailtime = findViewById(R.id.detailTime);
        detailepf = findViewById(R.id.detailEpf);
        detailname = findViewById(R.id.detailName);
        detaildepartment = findViewById(R.id.detailDept);
        detaildiagnose = findViewById(R.id.detailDiagnose);
        detailmedicine = findViewById(R.id.detailMedicine);
        detailnote = findViewById(R.id.detailNote);
        detailreported = findViewById(R.id.detailReported);

        deleteButton = findViewById(R.id.deleteBtn);
        updateButton = findViewById(R.id.updateBtn);

        getUserInfo();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detaildate.setText(bundle.getString("Date"));
            detailtime.setText(bundle.getString("Time"));
            detailepf.setText(bundle.getString("Epf"));
            detailname.setText(bundle.getString("Name"));
            detaildepartment.setText(bundle.getString("Department"));
            detaildiagnose.setText(bundle.getString("Diagnose"));
            detailmedicine.setText(bundle.getString("Medicine"));
            detailnote.setText(bundle.getString("Note"));
            detailreported.setText(bundle.getString("Reported"));

            key = bundle.getString("Key");
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, UpdateActivity.class)
                        .putExtra("Date", detaildate.getText().toString())
                        .putExtra("Time", detailtime.getText().toString())
                        .putExtra("Epf", detailepf.getText().toString())
                        .putExtra("Name", detailname.getText().toString())
                        .putExtra("Department", detaildepartment.getText().toString())
                        .putExtra("Diagnose", detaildiagnose.getText().toString())
                        .putExtra("Medicine", detailmedicine.getText().toString())
                        .putExtra("Note", detailnote.getText().toString())
                        .putExtra("Reported", detailreported.getText().toString())
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });
    }

    private void getUserInfo() {
        userDbRef = FirebaseDatabase.getInstance().getReference("USER");
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        eventListener = userDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                        UserModel user = itemSnapshot.getValue(UserModel.class);
                        if(user != null){
                            if(currentUser.contains("nurse")){
                                isNurse = true;
                            } else if (currentUser.contains("admin")){
                                isAdmin = true;
                            } else if(currentUser.contains("superuser")){
                                isSuperuser = true;
                            } else {
                                isAdmin = false;
                                isNurse = false;
                                isSuperuser = false;
                            }
                        }
                    }
                }

                setDeleteBtn();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    private void setDeleteBtn(){
        if (isAdmin){
            deleteButton.setVisibility(View.GONE);
        } else if(isNurse){
            deleteButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.GONE);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PATIENT");
                reference.child(key).removeValue();
                Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}