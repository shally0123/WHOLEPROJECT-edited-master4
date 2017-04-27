package cus1194.medtracker;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogout;

    private DatabaseReference databaseReference;
    private DatabaseReference phyId;
    private DatabaseReference patientList;

    private EditText editTextName, editTextAge, editTextPosition, editTextNPI;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

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


        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        textViewUserEmail.setText("Welcome " + user.getEmail());

        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

        retrieveuserinfo();


    }

    private void retrieveuserinfo(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(user.getUid()).child("physicianInfo");

        // Attach a listener to read the data at our posts reference
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Userinformation userinformation = dataSnapshot.getValue(Userinformation.class);
                editTextName.setText(userinformation.name);
                editTextAge.setText(userinformation.age);
                editTextNPI.setText(userinformation.NPI);
                editTextPosition.setText(userinformation.postiion);
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
    }//retrieve informaiton from google firebase


    private void saveUserInformation() {
        String name = editTextName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String position = editTextPosition.getText().toString().trim();
        String NPI = editTextNPI.getText().toString().trim();

        Userinformation UserInformation = new Userinformation(name, age, position, NPI);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        phyId = databaseReference.child(user.getUid());//.child("physicianId").setValue(UserInformation);//pid
        phyId.child("physicianInfo").setValue(UserInformation);

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
