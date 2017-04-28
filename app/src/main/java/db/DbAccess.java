package db;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by liu on 4/28/17.
 */

public class DbAccess {

    private FirebaseDatabase database;
    private FirebaseUser user;

    public DbAccess(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
    }

    // Example
    public DatabaseReference getPatientMedId(){
        return database.getReference(user.getUid()).child("patientList").child("patientName").child("MedInfo");
    }


}
