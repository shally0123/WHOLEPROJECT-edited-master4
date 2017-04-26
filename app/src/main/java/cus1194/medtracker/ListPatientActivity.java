package cus1194.medtracker;

/**
 * Created by liu on 4/12/17.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListPatientActivity extends AppCompatActivity {

   static int i = 0;
    Button addPat;
    Button b_analysis;
    FirebaseDatabase database;
//    private DatabaseReference databaseReference;
    private DatabaseReference phyID;
    private DatabaseReference patientList;
    private DatabaseReference patientName;
    private DatabaseReference patientInfo;

    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_selection);


        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        phyID = database.getReference( "hxIPBytiRDMpDfCz4VhPPCnUiEy1");
        patientList = phyID.child("patientList");
        patientName = patientList.child("patientName");
        patientInfo = patientList.child("patientInfo");

        final List values = new ArrayList();
        //PatientInfo[] values = new PatientInfo[100];

        addPat = (Button)findViewById(R.id.PatientSelection);
      //  b_analysis=(Button)findViewById(R.id.analysis);

      //  b_analysis.setOnClickListener(new View.OnClickListener() {
        //    @Override
       //     public void onClick(View v) {
       //         Intent intent = new Intent(ListPatientActivity.this, graph.class);
      //          startActivity(intent);
       //     }
      //  });

       ListView listView = (ListView)findViewById(R.id.List);
        //listView.setTextFilterEnabled(true);

        //Query queryPName = patientInfo.orderByChild("PName");
        patientList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot patientData : dataSnapshot.getChildren()) {
                    PatientInfo pinfo = patientData.getValue(PatientInfo.class);
                    values.add(pinfo);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ArrayAdapter<String> something = new ArrayAdapter<String>(this,R.layout.patient_selection, values);
        listView.setAdapter(something);


      //  listView.setOnItemClickListener(new OnItemClickListener() {
      //      public void onItemClick(AdapterView<?> parent, View view,
     //                               int position, long id) {
//
     //       }
     //   });

        addPat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPatientActivity.this, addPatient.class);
                startActivity(intent);
            }
        });


    }

}

