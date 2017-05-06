package cus1194.medtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by pruan086 on 3/7/2017.
 */

public class PatientMain extends AppCompatActivity  {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PatientViewAdapt patientViewAdapt;
    protected static String selectedPatientKey;


    @Override
    protected void onCreate(Bundle savedIntanceState)
    {

        super.onCreate(savedIntanceState);
        setContentView(R.layout.patient_main);

        // Get String extra from previous intent
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            selectedPatientKey = (String) bd.get("PATIENT_KEY");
            Log.d("Previous patient key:", selectedPatientKey);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        String uid = user.getUid();
        Log.v("IN PATIENT MAIN:", uid);

        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        patientViewAdapt = new PatientViewAdapt(getSupportFragmentManager());

        patientViewAdapt.addFragments(new PatientCurrentFragment(), "CURRENT");
        patientViewAdapt.addFragments(new PatientCalendarFragment(), "CALENDAR");
        viewPager.setAdapter(patientViewAdapt);
        tabLayout.setupWithViewPager(viewPager);


    };

    // Retrieves list of patients and finds patient with given ssn.
    // Then finds the patient key.


}
