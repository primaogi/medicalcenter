package com.medicalcenter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UploadActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button saveUploadButton;
    EditText  uploadepf, uploadname,  uploaddepartment, uploaddiagnose, uploadmedicine, uploadnote, uploadreported;
    TextView uploadtimedate, uploadtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadtimedate = findViewById(R.id.UploadTimedate);
        uploadtime = findViewById(R.id.UploadTime);
        uploadepf = findViewById(R.id.UploadEpf);
        uploadname = findViewById(R.id.UploadName);
        uploaddepartment = findViewById(R.id.UploadDepartment);
        uploaddiagnose = findViewById(R.id.UploadDiagnose);
        uploadmedicine = findViewById(R.id.UploadMedicine);
        uploadnote = findViewById(R.id.UploadNote);
        uploadreported = findViewById(R.id.UploadReported);
        saveUploadButton = findViewById(R.id.UploadSaveBtn);

        uploadtimedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        uploadtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        //TEXT WATCHER CANT UPLOAD WHEN FIELDS IS EMPTY

        uploadtimedate.addTextChangedListener(uploadTextWatcher);
        uploadtime.addTextChangedListener(uploadTextWatcher);
        uploadepf.addTextChangedListener(uploadTextWatcher);
        uploadname.addTextChangedListener(uploadTextWatcher);
        uploaddepartment.addTextChangedListener(uploadTextWatcher);
        uploaddiagnose.addTextChangedListener(uploadTextWatcher);
        uploadmedicine.addTextChangedListener(uploadTextWatcher);
        uploadnote.addTextChangedListener(uploadTextWatcher);
        uploadreported.addTextChangedListener(uploadTextWatcher);
    }
    TextWatcher uploadTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String timedateInput = uploadtimedate.getText().toString().trim();
            String timeInput = uploadtimedate.getText().toString().trim();
            String epfInput = uploadepf.getText().toString().trim();
            String nameInput = uploadname.getText().toString().trim();
            String departmentInput = uploaddepartment.getText().toString().trim();
            String diagnoseInput = uploaddiagnose.getText().toString().trim();
            String medicineInput = uploadmedicine.getText().toString().trim();
            String noteInput = uploadnote.getText().toString().trim();
            String reportedInput = uploadreported.getText().toString().trim();

            saveUploadButton.setEnabled(!timedateInput.isEmpty() && !timeInput.isEmpty() && !epfInput.isEmpty() && !nameInput.isEmpty() && !departmentInput.isEmpty() && !reportedInput.isEmpty() && !diagnoseInput.isEmpty() && !medicineInput.isEmpty() && !noteInput.isEmpty());

            saveUploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadData();
                }
            });

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //POP UP TIME PICKER
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView) findViewById(R.id.UploadTime);
        textView.setText("" + hourOfDay + ":" + minute + " WIB");
    }

    //POP UP CALENDAR
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.UploadTimedate);
        textView.setText(currentDateString);
    }

    // UPLOAD DATA STRING TO FIREBASE
    public void uploadData() {
        String timedate = uploadtimedate.getText().toString();
        String time = uploadtime.getText().toString();
        String epf = uploadepf.getText().toString();
        String name = uploadname.getText().toString();
        String department = uploaddepartment.getText().toString();
        String diagnose = uploaddiagnose.getText().toString();
        String medicine = uploadmedicine.getText().toString();
        String note = uploadnote.getText().toString();
        String reported = uploadreported.getText().toString();

        DataClass dataClass = new DataClass(timedate, time, epf, name, department, diagnose, medicine, note, reported);

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("PATIENT").child(currentDate)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Toast.makeText(UploadActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}