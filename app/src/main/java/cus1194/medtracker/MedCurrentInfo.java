package cus1194.medtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by liu on 5/2/17.
 */

public class MedCurrentInfo extends AppCompatActivity
{
    FirebaseDatabase database;
    private DatabaseReference dates;
    private DatabaseReference phyID;
    private DatabaseReference patientList;
    private DatabaseReference patientName;
    private DatabaseReference medicationList;
    private DatabaseReference medicationInfo;
    private FirebaseAuth firebaseAuth;

    ArrayList<String> patientSSN;

    TextView edMedName;
    TextView edMedID;
    EditText edNurseName;
    CheckBox givenOrNotGiven;
    boolean check;
    Button save;
    Button cancel;
    NursePhyInfo np;

    Date date;

    protected String selectedMedKey;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expanded_info);


        edMedName = (TextView) findViewById(R.id.editView1);
        edMedID = (TextView) findViewById(R.id.editView2);
        edNurseName = (EditText) findViewById(R.id.editView3);
        givenOrNotGiven = (CheckBox) findViewById(R.id.given);
        save=(Button)findViewById(R.id.saveInfo);
        cancel=(Button)findViewById(R.id.cancelInfo);

        check = false;
        np = new NursePhyInfo();
        np.given = false;

        date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
        String stringDate = dt.format(date);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        phyID = database.getReference(user.getUid());
        patientList = phyID.child("patientList");

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            selectedMedKey = (String) bd.get("MEDICATION_KEY");
            Log.d("Previous med key:", selectedMedKey + " ");
        }

        // get parent activity info
        String  patientKey = PatientMain.selectedPatientKey;
        Log.d("Fragment:",patientKey);
        //String medKey = PatientCurrentFragment.selectedMedKey;
        //String medKey = ((AddMed) getActivity()).selectedMedKey;

        patientName = patientList.child(patientKey);
        medicationList = patientName.child("medicationList");
        medicationInfo = medicationList.child(selectedMedKey);
        dates = medicationInfo.child(stringDate);

        medicationInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MedInfo medInfo = dataSnapshot.getValue(MedInfo.class);
                edMedName.setText(medInfo.MedName);
                edMedID.setText(medInfo.MedID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedNurseGivenInfo();
                startActivity(new Intent(MedCurrentInfo.this, PatientMain.class));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedCurrentInfo.this, PatientMain.class));
            }
        });

    }

    public void savedNurseGivenInfo()
    {
        String nurseName = edNurseName.getText().toString().trim();
        check = np.given;


        if(givenOrNotGiven.isChecked())
        {
            check=true;
        }
        else
        {
            check=false;
        }

        NursePhyInfo nursePhyInfo = new NursePhyInfo(nurseName,check);

        dates.setValue(nursePhyInfo);

    }


}
