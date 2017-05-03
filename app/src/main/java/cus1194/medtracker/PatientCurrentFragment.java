package cus1194.medtracker;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;



/**
 * A simple {@link Fragment} subclass.
 */
public class PatientCurrentFragment extends Fragment
{

    ListView listView;
    ArrayAdapter<MedInfo> listAdapter;

    ArrayList<MedInfo> meds;
    MedInfo[] medarray;
    String[] medString;
    ArrayList<String> patientSSN;

    TextView lbListHeader;
    View v;
    EditText bph;
    EditText bpl;
    EditText w;
    Button addMed;
    Button analysis;
    FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference phyID;
    private DatabaseReference patientList;
    private DatabaseReference patientName;
    private DatabaseReference vitalInfo;
    private DatabaseReference medicationInfo;
    private FirebaseAuth firebaseAuth;
    EditText addMedDos;
    EditText addMedId;
    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);


    public PatientCurrentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_current, container, false);
        return v;

    }



    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        lbListHeader = (TextView) v.findViewById(R.id.lbListHeader);
        addMedDos = (EditText) v.findViewById(R.id.addMedDos);
        addMedId = (EditText) v.findViewById(R.id.addMedID);
        bph = (EditText) v.findViewById(R.id.bloodPressureHighEdit);
        bpl = (EditText) v.findViewById(R.id.bloodPressureLowEdit);
        w = (EditText)v.findViewById(R.id.weightEdit);
        listView = (ListView)v.findViewById(R.id.medicationList);
        addMed = (Button) v.findViewById(R.id.addMed);
        analysis = (Button)v.findViewById(R.id.edAnalysis);


/*
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //firebaseAuth = FirebaseAuth.getInstance();
        //FirebaseUser userN = firebaseAuth.getCurrentUser();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        phyID = databaseReference.child(user.getUid());
        patientList = phyID.child("patientList");
        //String patientN=patientList.getKey();
        //patientName=patientList.child("patientName");

*/

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        phyID = database.getReference(user.getUid());
        patientList = phyID.child("patientList");

        // get parent activity info
        String  patientKey = ((PatientMain) getActivity()).selectedPatientKey;
        Log.d("Fragment:",patientKey);

        patientName = patientList.child(patientKey);
        vitalInfo = patientName.child("vitalInfo");
        medicationInfo = patientName.child("medicationInfo");



        patientSSN = new ArrayList<String>();

        //vitalInfo = patientName.child("vitalInfo");
        //medicationInfo = patientName.child("medicationInfo");

        patientList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    patientSSN.add(String.valueOf(data.getValue(PatientInfo.class).getSSN()));
                    Log.d("paitentSSN holds: ", data.getValue(PatientInfo.class).getSSN());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MedCurrentInfo.class);
                startActivity(intent);
            }
        });



        addMed.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                saveVitalInfo();
                Intent intent1 = new Intent(PatientCurrentFragment.this.getActivity(),AddMed.class);
                startActivity(intent1);
            }
        });

        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    private void saveVitalInfo()
    {
        String bloodPH = bph.getText().toString().trim();
        String bloodPL = bpl.getText().toString().trim();
        String weight = w.getText().toString().trim();


        CalendarSettings cs = new CalendarSettings(getActivity());
        DatePickerDialog dialog;

        dialog = new DatePickerDialog(getActivity(), cs, year, month, day);
        Random ran = new Random();

        String timestamp = dialog.toString() +ran.nextInt(1000);

        VitalInfo vital = new VitalInfo(timestamp, bloodPH, bloodPL, weight);

        patientName.child("vitalInfo");
        DatabaseReference vitalRef = patientName.push();
        vitalRef.setValue(vital);

    }

    public void addToArrayList() {
        for (int i = 0; i < meds.size(); i++) {
            if (meds.get(i) != null)
                Log.d("Value Size is", meds.size() + " ");
        }

        medarray = meds.toArray(new MedInfo[meds.size()]);
        medString = new String [medarray.length];


        for (int i = 0; i < medarray.length; i++) {
            Log.d("PatientArray", medarray[i].MedName + " ");
            medString[i] = medarray[i].MedName.toString();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.fragment_current_listgroup, medString);
        listView.setAdapter(adapter);

    }



}