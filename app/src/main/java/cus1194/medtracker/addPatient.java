package cus1194.medtracker;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by liu on 4/12/17.
 */

public class addPatient extends AppCompatActivity {
    public EditText PatientName;
    public EditText SSN;
    public Button btnClick1;
    public Button btnClick2;
    public TextView MedicalHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpatient);
        btnClick1 = (Button) findViewById(R.id.Summit);
        btnClick2 = (Button) findViewById(R.id.Cancel);
        PatientName = (EditText)findViewById(R.id.Name);
        SSN = (EditText) findViewById(R.id.SSN);

        btnClick1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                savePatientinfo();
                Intent intent1 = new Intent(addPatient.this, ListPatientActivity.class);
                startActivity(intent1);
            }
        });

        btnClick2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent2 = new Intent(addPatient.this, ListPatientActivity.class);
                startActivity(intent2);
            }
        });


    }

    private void savePatientinfo()
    {
        String Pname = btnClick1.getText().toString();
        String SSNum = btnClick2.getText().toString();
        String Medhistory = MedicalHistory.getText().toString();


    }

}


