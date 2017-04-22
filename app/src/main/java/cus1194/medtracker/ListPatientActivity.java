package cus1194.medtracker;

/**
 * Created by liu on 4/12/17.
 */
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListPatientActivity extends AppCompatActivity {

    //public static final String[] PatientSelection = new String[] { "Patient A", "Patient B", "Patient C",
    //        "Patient D"};

    Button addPat;
    Button b_analysis;
    private DatabaseReference databaseReference;
    private DatabaseReference phyInfo;
    private DatabaseReference patientList;
    PatientInfo patientInfo;

    private FirebaseAuth firebaseAuth;
    //private FirebaseDatabase database;
    EditText PName;
    EditText SSN;
    EditText MedHis;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_selection);

//        EditText PName = (EditText)findViewById(R.id.Name);
//        EditText SSN = (EditText)findViewById(R.id.SSN);
//        EditText MedHis = (EditText)findViewById(R.id.Med);
//
//        String first=PName.getText().toString();
//        String second=SSN.getText().toString();
//        String third=MedHis.getText().toString();

       // PName.setText(PName.getText().toString());
        //SSN.setText(SSN.getText().toString());
        //MedHis.setText(MedHis.getText().toString());


        databaseReference= FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(firebaseAuth.getCurrentUser()!=null){
            Log.d("firebase getCurrentUser", "We are in");
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }


        Toast.makeText(this, user.getUid(), Toast.LENGTH_SHORT);

        phyInfo = databaseReference.child(user.getUid());
//        patientInfo = new PatientInfo(first, second, third);
        patientList= phyInfo.child("patientInfo");
//        patientList.setValue(patientInfo);

        addPat = (Button)findViewById(R.id.PatientSelection);
      //  b_analysis=(Button)findViewById(R.id.analysis);

      //  b_analysis.setOnClickListener(new View.OnClickListener() {
        //    @Override
       //     public void onClick(View v) {
       //         Intent intent = new Intent(ListPatientActivity.this, graph.class);
      //          startActivity(intent);
       //     }
      //  });

       // ListView listView = (ListView)findViewById(R.id.List);
        //listView.setTextFilterEnabled(true);


        //ArrayAdapter<String> something = new ArrayAdapter<String>(this, R.layout.patient_selection,PatientSelection);
        // listView.setAdapter(something);


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

