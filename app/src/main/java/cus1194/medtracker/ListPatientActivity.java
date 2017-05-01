package cus1194.medtracker;

/**
 * Created by liu on 4/12/17.
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListPatientActivity extends AppCompatActivity {

    Button addPat;
    FirebaseDatabase database;
//    private DatabaseReference databaseReference;
    private DatabaseReference phyID;
    private DatabaseReference patientList;
    private DatabaseReference patientName;
    private DatabaseReference patientInfo;

    private FirebaseAuth firebaseAuth;

    ListView listView;
    ArrayList<PatientInfo> values;
    PatientInfo[] patientarray;
    String[] patientsString;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_selection);

        values = new ArrayList<PatientInfo>();

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        phyID = database.getReference( user.getUid()/*"hxIPBytiRDMpDfCz4VhPPCnUiEy1"*/);
        patientList = phyID.child("patientList");
        //patientName = patientList.child("patientName");
        //patientInfo = patientList.child("patientInfo");




        addPat = (Button)findViewById(R.id.PatientSelection);
      //  b_analysis=(Button)findViewById(R.id.analysis);

      //  b_analysis.setOnClickListener(new View.OnClickListener() {
        //    @Override
       //     public void onClick(View v) {
       //         Intent intent = new Intent(ListPatientActivity.this, graph.class);
      //          startActivity(intent);
       //     }
      //  });

        listView = (ListView)findViewById(R.id.patientList);

        patientList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getBaseContext(), dataSnapshot.getKey().toString(), Toast.LENGTH_SHORT);
                Log.d("KEY INFO", dataSnapshot.getKey().toString());


                Iterable <DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot patientData: children){
                    Log.d("KEY INFO", patientData.getKey().toString());

                    PatientInfo pinfo = patientData.getValue(PatientInfo.class);
                    values.add(pinfo);

                }


                addToArrayList();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });



        addPat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPatientActivity.this, addPatient.class);
                startActivity(intent);
            }
        });


    }

    public void addToArrayList() {
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) != null)
                Log.d("Value Size is", values.size() + " ");
        }

        patientarray = values.toArray(new PatientInfo[values.size()]);
        patientsString = new String [patientarray.length];


        for (int i = 0; i < patientarray.length; i++) {
            Log.d("PatientArray", patientarray[i].getPName() + " ");
            patientsString[i] = patientarray[i].getPName().toString();
        }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listviewtest, patientsString);
            listView.setAdapter(adapter);

        }


}

