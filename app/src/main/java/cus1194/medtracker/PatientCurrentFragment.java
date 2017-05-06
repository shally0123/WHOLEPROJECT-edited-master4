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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    ArrayList<String> medInfoKeys;

    TextView lbListHeader;
    TextView back;
    View v;
    EditText bph;
    EditText bpl;
    EditText w;
    Button addMed;
    Button analysis;
    FirebaseDatabase database;
    private DatabaseReference dates;
    private DatabaseReference phyID;
    private DatabaseReference patientList;
    private DatabaseReference patientName;
    private DatabaseReference vitalList;
    private DatabaseReference medicationList;
    private DatabaseReference medicationInfo;
    private FirebaseAuth firebaseAuth;
    EditText addMedDos;
    EditText addMedId;
    Date date;


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
        back = (TextView)v.findViewById(R.id.back);

        date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
        final String stringDate = dt.format(date);

        medInfoKeys = new ArrayList<String>();
        meds = new ArrayList<MedInfo>();

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        phyID = database.getReference(user.getUid());
        patientList = phyID.child("patientList");

        // get parent activity info
        String  patientKey = ((PatientMain) getActivity()).selectedPatientKey;
        Log.d("Fragment:",patientKey);

        patientName = patientList.child(patientKey);
        vitalList = patientName.child("vitalList");
        medicationList = patientName.child("medicationList");
        medicationInfo = medicationList.push();
        dates = vitalList.child(stringDate);

        vitalList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                VitalInfo vitalz = dataSnapshot.child(stringDate).getValue(VitalInfo.class);

                if(dataSnapshot.child(stringDate).getKey()==stringDate)
                {

                    bph.setText(vitalz.bloodPHigh);
                    bpl.setText(vitalz.bloodPLow);
                    w.setText(vitalz.weight);

                }

                Log.d("vitalList: " , stringDate+"");
               /* for(DataSnapshot vitalData: dataSnapshot.getChildren())
                {
                    Log.d("date should be:", stringDate);
                    Log.d("dataS.getChildren: ", dataSnapshot.child(stringDate).toString());
                    if(vitalData.child(stringDate).getKey()==stringDate)
                    {
                        bph.setText(vitalData.child(stringDate).getValue(VitalInfo.class).bloodPHigh);
                        bpl.setText(vitalData.child(stringDate).getValue(VitalInfo.class).bloodPLow);
                        w.setText(vitalData.child(stringDate).getValue(VitalInfo.class).weight);
                    }
                }*/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        medicationList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Toast.makeText(getContext(), dataSnapshot.getKey().toString(), Toast.LENGTH_SHORT);
                Log.d("KEY INFO", dataSnapshot.getKey().toString());


                Iterable <DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot patientData: children){
                    Log.d("KEY INFO", patientData.getKey().toString());

                    medInfoKeys.add(patientData.getKey().toString());
                    MedInfo medInfo = patientData.getValue(MedInfo.class);
                    meds.add(medInfo);


                }

                addToArrayList();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //patientSSN = new ArrayList<String>();

/*
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
*/




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PatientCurrentFragment.this.getActivity(), MedCurrentInfo.class);
                Log.d("Selected med name:",meds.get(position).MedName.toString());
                intent.putExtra("MEDICATION_KEY",medInfoKeys.get(position));
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveVitalInfo();
                Intent intent = new Intent(PatientCurrentFragment.this.getActivity(), ListPatientActivity.class);
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

        date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
        String stringDate = dt.format(date);

        VitalInfo vital = new VitalInfo(bloodPH, bloodPL, weight);

        vitalList = patientName.child("vitalList");
        dates = vitalList.child(stringDate);
        dates.setValue(vital);

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