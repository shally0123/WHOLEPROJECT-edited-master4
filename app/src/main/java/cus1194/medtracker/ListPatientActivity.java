package cus1194.medtracker;

/**
 * Created by liu on 4/12/17.
 */
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListPatientActivity extends AppCompatActivity {

    public static final String[] PatientSelection = new String[] { "Patient A", "Patient B", "Patient C",
            "Patient D"};

    Button addPat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_selection);

        addPat = (Button)findViewById(R.id.PatientSelection);


        ListView listView = (ListView)findViewById(R.id.List);
        listView.setTextFilterEnabled(true);

        //ArrayAdapter<String> something = new ArrayAdapter<String>(this, R.layout.patient_selection,PatientSelection);
        // listView.setAdapter(something);


        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//
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

}

