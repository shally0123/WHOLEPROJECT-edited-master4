package cus1194.medtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by liu on 5/2/17.
 */

public class MedCurrentInfo extends AppCompatActivity
{
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference patientList;
    private DatabaseReference phyID;

    ArrayList<String> patientSSN;

    EditText edMedName;
    EditText edMedID;
    EditText edNurseName;
    CheckBox given;
    Button save;
    Button cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expanded_info);


        firebaseAuth = FirebaseAuth.getInstance();
        //FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        //firebaseAuth = FirebaseAuth.getInstance();
        //FirebaseUser userN = firebaseAuth.getCurrentUser();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        phyID = databaseReference.child(user.getUid());
        patientList = phyID.child("patientList");


        databaseReference = FirebaseDatabase.getInstance().getReference();

        edMedName = (EditText) findViewById(R.id.editView1);
        edMedID = (EditText) findViewById(R.id.editView2);
        edNurseName = (EditText) findViewById(R.id.editView3);
        given = (CheckBox) findViewById(R.id.given);
        save=(Button)findViewById(R.id.saveInfo);
        cancel=(Button)findViewById(R.id.cancelInfo);

        patientList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                patientSSN = new ArrayList<String>();

                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    patientSSN.add(String.valueOf(data.getValue(PatientInfo.class).getSSN()));
                    Log.d("patientSSN holds: ", patientSSN.get(patientSSN.size()));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}
