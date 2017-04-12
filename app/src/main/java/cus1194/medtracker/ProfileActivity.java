package cus1194.medtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogout;

    private DatabaseReference databaseReference;

    private EditText editTextName, editTextAge, editTextPosition, editTextNPI;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPosition = (EditText) findViewById(R.id.editTextPosition);
        editTextNPI = (EditText) findViewById(R.id.editTextNPI);

        buttonSave = (Button) findViewById(R.id.buttonSave);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        textViewUserEmail.setText("Welcome" + user.getEmail());

        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);


    }


    private void saveUserInformation() {
        String name = editTextName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String position = editTextPosition.getText().toString().trim();
        String NPI = editTextNPI.getText().toString().trim();

        Userinformation UserInformation = new Userinformation(name, age, position, NPI);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(UserInformation);

        Toast.makeText(this, "information saved...", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {

        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(view==buttonSave){
            saveUserInformation();
        }
    }
}
