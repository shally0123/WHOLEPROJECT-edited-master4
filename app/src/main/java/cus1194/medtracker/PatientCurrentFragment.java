package cus1194.medtracker;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientCurrentFragment extends Fragment
{

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> medStatus;
    private HashMap<String, List<String>> medications;

    TextView lbListHeader;
    View v;
    EditText bph;
    EditText bpl;
    EditText w;
    Button addMed;
    Button edMed;
    FirebaseDatabase database;
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
        listView = (ExpandableListView)v.findViewById(R.id.medicationList);
        addMed = (Button) v.findViewById(R.id.addMed);
        edMed = (Button)v.findViewById(R.id.edMed);



        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        phyID = database.getReference(user.getUid());
        patientList = phyID.child("patientList");
        patientName = patientList.child("patientName");
        vitalInfo = patientName.child("vitalInfo");
        medicationInfo = patientName.child("medicationInfo");


        medStatus = new ArrayList<String>();
        //medStatus.add(lbListHeader.toString());

        medicationInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MedInfo medInfo = dataSnapshot.getValue(MedInfo.class);
                lbListHeader.setText(medInfo.MedName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query queryRef = medicationInfo.orderByKey().limitToFirst(medStatus.size());
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> value = (Map<String,String>)dataSnapshot.getValue();
                medStatus.add(String.valueOf(value));
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


        vitalInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                VitalInfo vital = dataSnapshot.getValue(VitalInfo.class);
                bph.setText(vital.BloodPHigh);
                bpl.setText(vital.BloodPLow);
                w.setText(vital.Weight);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        listAdapter = new ExpandableListAdapter(getContext(), medStatus, medications);
        listView.setAdapter(listAdapter);

        addMed.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                saveVitalInfo();
                Intent intent1 = new Intent(PatientCurrentFragment.this.getActivity(),AddMed.class);
                startActivity(intent1);
            }
        });

        edMed.setOnClickListener(new View.OnClickListener() {
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

        patientName.child("vitalInfo").setValue(vital);

    }



}