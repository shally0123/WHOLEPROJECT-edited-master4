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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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


    View v;
    EditText bp;
    EditText w;
    Button addMed;
    Button edMed;
    TextView medicationNames;
    private DatabaseReference databaseReference;
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


        addMedDos = (EditText) v.findViewById(R.id.addMedDos);
        addMedId = (EditText) v.findViewById(R.id.addMedID);
        bp = (EditText) v.findViewById(R.id.bloodPressureEdit);
        w = (EditText)v.findViewById(R.id.weightEdit);
        listView = (ExpandableListView)v.findViewById(R.id.medicationList);
        addMed = (Button) v.findViewById(R.id.addMed);
        edMed = (Button)v.findViewById(R.id.edMed);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
        //    @Override
         //   public void onDataChange(DataSnapshot dataSnapshot) {
        //        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

         //       for (DataSnapshot child: children)
          //      {
                   //listAdapter = child.getValue(ExpandableListAdapter.class);
         //           medStatus.add(String.valueOf(listAdapter));
         //       }
         //   }

         //   @Override
        //   public void onCancelled(DatabaseError databaseError) {

       //     }
       // });


       // listAdapter = new ExpandableListAdapter(getContext(), medStatus, medications);
        //listView.setAdapter(listAdapter);

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
        //        Intent intent2 = new Intent(PatientCurrentFragment.this.getActivity(), EdMed.class);
        //        startActivity(intent2);
            }
        });


    }

    private void saveVitalInfo()
    {
        String bloodP = bp.getText().toString().trim();
        String weight = w.getText().toString().trim();

        if(bloodP == null || weight == null)
        {
            CalendarSettings cs = new CalendarSettings(getActivity());
            DatePickerDialog dialog;

            dialog = new DatePickerDialog(getActivity(), cs, year, month, day);
            Random ran = new Random();

            String timestamp = dialog.toString() +ran.nextInt(1000);
            FirebaseUser user = firebaseAuth.getCurrentUser();


            VitalInfo vitalInfo = new VitalInfo(timestamp, bloodP, weight);
            databaseReference.child(user.getUid()).setValue(vitalInfo);


        }


    }



}
