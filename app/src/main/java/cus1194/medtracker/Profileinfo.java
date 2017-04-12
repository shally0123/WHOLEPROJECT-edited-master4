package cus1194.medtracker;

/**
 * Created by liu on 3/22/17.
 */
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.media.CamcorderProfile.get;
public class Profileinfo extends AppCompatActivity {

    ExpandableListView expandableListView;

    List<String> patients;
    List<String> personal;
    Map<String, List<String>> topics;
    ExpandableListAdapter ListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = (ExpandableListView) findViewById(R.id.add_expandableListView);
        fillData();

        //ListAdapter = new MyExListAdapter(this.patients, topics);

        expandableListView.setAdapter(ListAdapter);

        expandableListView.setOnChildClickListener((new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(Profileinfo.this, patients.get(groupPosition) + " : " + topics.get(patients.get(groupPosition)).get(childPosition), Toast.LENGTH_LONG).show();

                return false;
            }
        }));
    }

    public void fillData() {
        patients = new ArrayList<>();
        personal = new ArrayList<>();
        topics = new HashMap<>();

        patients.add("New Patient");
        patients.add("Current Patient");
        patients.add("Returning Patient");

        personal.add("Name");
        personal.add("L.name");
        personal.add("SSN");

        List<String> newpatient = new ArrayList<>();
        List<String> currentpatient = new ArrayList<>();
        List<String> returningpatient = new ArrayList<>();

        newpatient.add("Create New Patient");
        newpatient.add("Edit Patient Information");
        newpatient.add("Delete Patient Information");

        currentpatient.add("Medical History");
        currentpatient.add("Active Orders");
        currentpatient.add("Intake Medication");

        returningpatient.add("Vital Signs");
        returningpatient.add("Medication Name");
        returningpatient.add("Scan Medication");

        topics.put(patients.get(0),newpatient);
        topics.put(patients.get(1),currentpatient);
        topics.put(patients.get(2),returningpatient);

    }
}

