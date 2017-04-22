package cus1194.medtracker;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by liu on 4/12/17.
 */

public class addPatient extends AppCompatActivity {
    public EditText PatientName;
    public EditText SSN;
    public TextView MedicalHistory;
    public Button summit;
    public Button cancel;
    private DatabaseReference databaseReference;
    FirebaseDatabase database;
    PatientInfo patientInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpatient);
        summit = (Button) findViewById(R.id.Summit);
        cancel = (Button) findViewById(R.id.Cancel);
        PatientName = (EditText)findViewById(R.id.Name);
        SSN = (EditText) findViewById(R.id.SSN);


        summit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                savePatientinfo();
                Intent intent1 = new Intent(addPatient.this, ListPatientActivity.class);
                startActivity(intent1);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent2 = new Intent(addPatient.this, ListPatientActivity.class);
                startActivity(intent2);
            }
        });


    }

    private void savePatientinfo()
    {

        databaseReference = database.getReference("p_info");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                patientInfo = dataSnapshot.getValue(PatientInfo.class);

                PatientName.setText(PatientName.getText().toString());
                SSN.setText(SSN.getText().toString());
                MedicalHistory.setText(MedicalHistory.getText().toString());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}


