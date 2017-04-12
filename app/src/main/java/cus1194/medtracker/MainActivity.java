package cus1194.medtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("onCreate method", "We are in");

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            Log.d("firebase getCurrentUser", "We are in");
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }


        progressDialog=new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.bRegister);
        editTextEmail = (EditText) findViewById(R.id.etEamil);
        editTextPassword = (EditText) findViewById(R.id.etPassword);
        textViewSignin = (TextView) findViewById(R.id.activitylogin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter eamil", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("create account");
        progressDialog.show();
        progressDialog.dismiss();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                            Toast.makeText(MainActivity.this,"Registered Successful",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Registered Failure",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    @Override
    public void onClick(View view){
        if(view==buttonRegister){
            registerUser();
        }

        if(view==textViewSignin){
            startActivity(new Intent(this,LoginActivity.class));

        }
    }
}
