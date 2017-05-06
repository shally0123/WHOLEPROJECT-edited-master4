package cus1194.medtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pruan086 on 4/4/2017.
 */

public class AddMed extends AppCompatActivity
{

    EditText addMedDos;
    EditText addMedID;
    Button save;
    Button cancel;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference phyID;
    private DatabaseReference patientList;
    private DatabaseReference patientName;
    private DatabaseReference medicationList;
    private DatabaseReference medicationInfo;

    Date date;
    protected static String selectedMedKey;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_med);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        phyID = database.getReference(user.getUid());
        patientList = phyID.child("patientList");

        // get parent activity info
        String  patientKey = PatientMain.selectedPatientKey;
        Log.d("Fragment:",patientKey);

        patientName = patientList.child(patientKey);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            selectedMedKey = (String) bd.get("MEDICATION_KEY");
            Log.d("Previous med key:", selectedMedKey);
        }

        addMedDos = (EditText)findViewById(R.id.addMedDos);
        addMedID = (EditText)findViewById(R.id.addMedID);
        save = (Button)findViewById(R.id.saveMed);
        cancel = (Button)findViewById(R.id.cancelMed);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMedInformation();
                Intent intent1 = new Intent(AddMed.this, PatientMain.class);
                startActivity(intent1);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent2 = new Intent(AddMed.this, PatientMain.class);
                startActivity(intent2);
            }
        });


    }

    private void saveMedInformation() {
        String name = addMedDos.getText().toString().trim();
        String id = addMedID.getText().toString().trim();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // get parent activity info
        String  patientKey = PatientMain.selectedPatientKey;
        Log.d("Fragment:",patientKey);

        patientName = patientList.child(patientKey);

        phyID = database.getReference(user.getUid());
        patientList = phyID.child("patientList");
        medicationList = patientName.child("medicationList");
        medicationInfo = medicationList.push();

        MedInfo MedInfo = new MedInfo(name, id);
        medicationInfo.setValue(MedInfo);

    }



}
