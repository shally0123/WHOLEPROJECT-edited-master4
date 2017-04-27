package cus1194.medtracker;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pruan086 on 3/7/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter
{

    private Context context;
    private List<String> status;
    private HashMap<String,List<String>> medications;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference phyID;
    private DatabaseReference patientList;
    private DatabaseReference patientName;
    private DatabaseReference medInfoDatabase;
    private DatabaseReference nurseInfoReference;
    private final Set<Pair<Long, Long>> mCheckedItems = new HashSet<Pair<Long, Long>>();
    MedInfo medInfo;
    NursePhyInfo NPInfo;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.status = listDataHeader;
        this.medications = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return status.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return medications.get(status.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return status.get(i);
    }

    @Override
    public Object getChild(int i, int j) {
        return medications.get(status.get(i)).get(j);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int j) {
        return j;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        //String Header = (String) getGroup(i);
        if(view==null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_current_listgroup,null);
        }
        final TextView lbListHeader = (TextView)view.findViewById(R.id.lbListHeader);

        //FirebaseUser user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        phyID = database.getReference("");
        patientList = phyID.child("patientList");

        patientName = patientList.child("-Kiee9KORmWUiRX9CAdP");
        medInfoDatabase = database.getReference("").child("medicationInfo");



        lbListHeader.setTypeface(null, Typeface.BOLD);

        medInfoDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                medInfo = dataSnapshot.getValue(MedInfo.class);
                lbListHeader.setText(medInfo.MedName);
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

        //lbListHeader.setText(medInfo.MedName);
        //Header = lbListHeader.toString().trim();
        return view;
    }

    @Override
    public View getChildView(int i, int j, boolean b, View view, ViewGroup viewGroup) {
        //final String childText = (String)getChild(i,j);

        //FirebaseUser user = firebaseAuth.getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        phyID = database.getReference("hxIPBytiRDMpDfCz4VhPPCnUiEy1");
        patientList = phyID.child("patientList");
        patientName = patientList.child("-Kiee9KORmWUiRX9CAdP");
        medInfoDatabase = database.getReference("-Kiee9KORmWUiRX9CAdP").child("medicationInfo");
        nurseInfoReference = database.getReference("-Kiee9KORmWUiRX9CAdP").child("nurseInfo");

        if (view==null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_current_listitem,null);
        }


        final EditText edName = (EditText)view.findViewById(R.id.editView1);
        final EditText edID = (EditText)view.findViewById(R.id.editView2);
        final EditText edNurse = (EditText)view.findViewById(R.id.editView3);
        final CheckBox given = (CheckBox)view.findViewById(R.id.given);


        medInfoDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                medInfo = dataSnapshot.getValue(MedInfo.class);
                edName.setText(medInfo.MedName);
                edID.setText(medInfo.MedID);
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


        nurseInfoReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                NPInfo = dataSnapshot.getValue(NursePhyInfo.class);
                edNurse.setText(NPInfo.Name);

                if(given.isChecked())
                {
                    //NPInfo.Given = true;
                    given.equals(true);
                }

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

        nurseInfoReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                NPInfo = dataSnapshot.getValue(NursePhyInfo.class);
                edNurse.setText(NPInfo.Name);
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

        final Pair<Long, Long> tag = new Pair<Long, Long>(getGroupId(i), getChildId(i,j));
        given.setTag(i);
        given.setChecked(mCheckedItems.contains(tag));
        given.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                final CheckBox given = (CheckBox) v;
                final Pair<Long, Long> tag =  (Pair<Long, Long>) v.getTag();
                if(given.isChecked())
                {
                    mCheckedItems.add(tag);
                }
                else
                {
                    mCheckedItems.remove(tag);
                }
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int j) {
        return true;
    }
}
