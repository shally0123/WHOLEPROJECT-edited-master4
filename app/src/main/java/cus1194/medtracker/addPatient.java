package cus1194.medtracker;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by liu on 4/12/17.
 */

public class addPatient extends AppCompatActivity {
    public EditText PatientName;
    public EditText SSN;
    public EditText MedicalHistory;
    public Button summit;
    public Button cancel;
    private DatabaseReference databaseReference;
    FirebaseDatabase database;
    PatientInfo patientInfo;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference phyID;
    private DatabaseReference patientList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpatient);
        summit = (Button) findViewById(R.id.Summit);
        cancel = (Button) findViewById(R.id.Cancel);
        PatientName = (EditText)findViewById(R.id.Name);
        SSN = (EditText) findViewById(R.id.SSN);
        MedicalHistory= (EditText)findViewById(R.id.MedHis);


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

        //retrievePatientInfo();
    }

    private void savePatientinfo()
    {

        String PN = PatientName.getText().toString();
        String SS = SSN.getText().toString();
        String MH = MedicalHistory.getText().toString();


        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

        phyID = database.getReference(user.getUid()/* "hxIPBytiRDMpDfCz4VhPPCnUiEy1"*/);
        patientList = phyID.child("patientList");



        PatientInfo PatientInfo = new PatientInfo(PN, SS, MH);

        DatabaseReference patientRef = patientList.push();
        patientRef.setValue(PatientInfo);
        String patient = patientRef.getKey();

    }
    /*
private void retrievePatientInfo(){
    FirebaseUser user = firebaseAuth.getCurrentUser();
    // Get a reference to our posts
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference(user.getUid()).child("patientInfo");

    // Attach a listener to read the data at our posts reference
    ref.addValueEventListener(new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            PatientInfo patientinfo = dataSnapshot.getValue(PatientInfo.class);
            PatientName.setText(patientinfo.PName);
            SSN.setText(patientinfo.SSN);
            MedicalHistory.setText(patientinfo.MedHis);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());
        }
    });
}//retrieve informaiton from google firebase
*/
}



