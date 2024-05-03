package com.medicalcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button updateSaveButton;
    EditText updateepf, updatename,  updatedepartment, updatediagnose, updatemedicine, updatenote, updatereported;
    TextView updatetimedate, updatetime;
    String timedate, time, epf, name, department, diagnose, medicine, note, reported;
    String key;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateSaveButton = findViewById(R.id.UpdateSaveBtn);

        updatetimedate = findViewById(R.id.UpdateTimedate);
        updatetime = findViewById(R.id.UpdateTime);
        updateepf = findViewById(R.id.UpdateEpf);
        updatename = findViewById(R.id.UpdateName);
        updatedepartment = findViewById(R.id.UpdateDepartment);
        updatediagnose = findViewById(R.id.UpdateDiagnose);
        updatemedicine = findViewById(R.id.UpdateMedicine);
        updatenote = findViewById(R.id.UpdateNote);
        updatereported = findViewById(R.id.UpdateReported);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            updatetimedate.setText(bundle.getString("Timedate"));
            updatetime.setText(bundle.getString("Time"));
            updateepf.setText(bundle.getString("Epf"));
            updatename.setText(bundle.getString("Name"));
            updatedepartment.setText(bundle.getString("Department"));
            updatediagnose.setText(bundle.getString("Diagnose"));
            updatemedicine.setText(bundle.getString("Medicine"));
            updatenote.setText(bundle.getString("Note"));
            updatereported.setText(bundle.getString("Reported"));

            key = bundle.getString("Key");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("PATIENT").child(key);

        updatetimedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        updatetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });


        //TEXT WATCHER CANT UPDATE WHEN DATE IS EMPTY
        updatetimedate.addTextChangedListener(updateTextWatcher);
        updatetime.addTextChangedListener(updateTextWatcher);
    }
    TextWatcher updateTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String timedateInput = updatetimedate.getText().toString().trim();
            String timeInput = updatetimedate.getText().toString().trim();

            updateSaveButton.setEnabled(!timedateInput.isEmpty());
            updateSaveButton.setEnabled(!timeInput.isEmpty());

            updateSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateData();
                    finish();
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
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
        TextView textView = (TextView) findViewById(R.id.UpdateTime);
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

        TextView textView = (TextView) findViewById(R.id.UpdateTimedate);
        textView.setText(currentDateString);
    }

    // UPDATE DATA TO FIREBASE
    public void updateData(){
        timedate = updatetimedate.getText().toString().trim();
        time = updatetime.getText().toString().trim();
        epf = updateepf.getText().toString().trim();
        name = updatename.getText().toString().trim();
        department = updatedepartment.getText().toString().trim();
        diagnose = updatediagnose.getText().toString().trim();
        medicine = updatemedicine.getText().toString().trim();
        note = updatenote.getText().toString().trim();
        reported = updatereported.getText().toString().trim();

        DataClass dataClass = new DataClass(timedate, time, epf, name, department, diagnose, medicine, note, reported);
        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}