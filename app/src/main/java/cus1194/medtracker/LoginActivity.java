package cus1194.medtracker;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonSignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();




        editTextEmail=(EditText)findViewById(R.id.etEamil);
        editTextPassword=(EditText)findViewById(R.id.etPassword);
        buttonSignin=(Button)findViewById(R.id.bSignin);
        textViewSignup=(TextView)findViewById(R.id.activitysignup);

        progressDialog=new ProgressDialog(this);

        buttonSignin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

    private void userLogin() {
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

        progressDialog.setMessage("Log in account");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                            String name = user.getDisplayName();
                            String uid = user.getUid();
                            Log.v("THe user id is:", uid);
                            Toast.makeText(LoginActivity.this, "Login SUCCESSFUL!!!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),ListPatientActivity.class));

                        }
                        else
                            Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onClick(View view){
        if(view==buttonSignin){
            userLogin();

        }

        if(view==textViewSignup){
            //finish();
           // Toast.makeText(LoginActivity.this, "Clicked REGISTER", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
