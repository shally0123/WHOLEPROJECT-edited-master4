package cus1194.medtracker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pruan086 on 4/26/2017.
 */

public class ExpandableListHeaderInfo
{

    private static FirebaseAuth firebaseAuth;
    private static FirebaseDatabase database;
    private static DatabaseReference phyId;
    private static DatabaseReference patientList;
    private static DatabaseReference patientName;
    private static DatabaseReference medicationInfo;

    static MedInfo medInfo;
    static String H;

    public static HashMap<String, List<String>> getInfo()
    {

        database = FirebaseDatabase.getInstance();
        //firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();


        phyId = database.getReference(user.getUid());
        patientList = phyId.child("patientList");
        patientName = patientList.child("patientName");
        medicationInfo = patientName.child("medicationInfo");

        medicationInfo.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                medInfo = dataSnapshot.getValue(MedInfo.class);//problem string to object class-->we could not retrive info
                H = medInfo.MedName;
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
        } );

        HashMap<String, List<String >> headerInfo = new HashMap<String , List<String >>();

        List<String > Header = new ArrayList<String >();
        Header.add(H);

        headerInfo.put(medicationInfo.getKey(), Header);

        return headerInfo;
    }

}