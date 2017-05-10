package cus1194.medtracker;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.series.DataPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientCalendarFragment extends Fragment {

    ListView listView;
    ArrayAdapter<MedInfo> listAdapter;

    ArrayList<MedInfo> meds;
    MedInfo[] medarray;
    String[] medString;
    ArrayList<String> medInfoKeys;

    View v;
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);


    FirebaseDatabase database;
    private DatabaseReference dates;
    private DatabaseReference phyID;
    private DatabaseReference patientList;
    private DatabaseReference patientName;
    private DatabaseReference vitalList;
    private DatabaseReference medicationList;
    private DatabaseReference medicationInfo;
    private FirebaseAuth firebaseAuth;
    Button b;

    Date date;


    public PatientCalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_calendar, container, false);
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }


    public void onViewCreated(View view,Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);

        b = (Button) v.findViewById(R.id.selectDate);
        listView = (ListView) v.findViewById(R.id.calendarList);

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
        Log.d("Fragment on cal: ",patientKey);

        patientName = patientList.child(patientKey);
        vitalList = patientName.child("vitalList");
        medicationList = patientName.child("medicationList");
        medicationInfo = medicationList.push();
        dates = vitalList.child(stringDate);




        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d("Button:", "has been clicked");
                    //CalendarSettings cs = new CalendarSettings(getActivity());
                   // DatePickerDialog dialog;
                   // dialog = new DatePickerDialog(getActivity(), cs, year, month, day);
                   // dialog.show();

                final DialogFragment newFrag = new DialogFragment();
                newFrag.show(getFragmentManager(), "date picker");


                medicationList.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> someDates = dataSnapshot.getChildren();

                        for (DataSnapshot datedate : someDates) {

                            if(newFrag.toString().contains(datedate.getChildren().toString()))
                            {
                                Log.d("dialog.tostring: ", newFrag.toString());
                                Log.d("datedate.gc.ts: ", datedate.getChildren().toString());

                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                for (DataSnapshot patientData : children)
                                 {
                                 Log.d("KEY INFO", patientData.getKey().toString());

                                 medInfoKeys.add(patientData.getKey().toString());
                                  MedInfo medInfo = patientData.getValue(MedInfo.class);
                                  meds.add(medInfo);

                                }

                            }

                            addToArrayList();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

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
