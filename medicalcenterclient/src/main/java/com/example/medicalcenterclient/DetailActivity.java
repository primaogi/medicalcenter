package com.example.medicalcenterclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {
    TextView detaildate, detailepf, detailname, detaildepartment, detaildiagnose, detailmedicine, detailnote, detailreported;
    Button deleteButton, updateButton;
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detaildate = findViewById(R.id.detailDate);
        detailepf = findViewById(R.id.detailEpf);
        detailname = findViewById(R.id.detailName);
        detaildepartment = findViewById(R.id.detailDept);
        detaildiagnose = findViewById(R.id.detailDiagnose);
        detailmedicine = findViewById(R.id.detailMedicine);
        detailnote = findViewById(R.id.detailNote);
        detailreported = findViewById(R.id.detailReported);

        deleteButton = findViewById(R.id.deleteBtn);
        updateButton = findViewById(R.id.updateBtn);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detaildate.setText(bundle.getString("Date"));
            detailepf.setText(bundle.getString("Epf"));
            detailname.setText(bundle.getString("Name"));
            detaildepartment.setText(bundle.getString("Department"));
            detaildiagnose.setText(bundle.getString("Diagnose"));
            detailmedicine.setText(bundle.getString("Medicine"));
            detailnote.setText(bundle.getString("Note"));
            detailreported.setText(bundle.getString("Reported"));

            key = bundle.getString("Key");
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PATIENT");
                reference.child(key).removeValue();
                Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, UpdateActivity.class)
                        .putExtra("Date", detaildate.getText().toString())
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
}