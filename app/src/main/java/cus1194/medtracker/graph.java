package cus1194.medtracker;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by liu on 4/11/17.
 */




public class graph extends AppCompatActivity {


    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference phyID;
    private DatabaseReference patientList;
    private DatabaseReference patientName;
    private DatabaseReference vitalList;
    private DatabaseReference dates;
    public String bloodPHigh;
    public String bloodPLow;
    public String weight;
    Date date;

    protected String selectedMedKey;

    LineGraphSeries<DataPoint> series_one;
    LineGraphSeries<DataPoint> series_two;
    LineGraphSeries<DataPoint> series_three;
    LineGraphSeries<DataPoint> series_four;

    TextView textview;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);

    textview = (TextView)findViewById(R.id.graph_back);

    textview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(graph.this, PatientMain.class));
        }
    });
       // ArrayList<String>weight=new ArrayList<>();
    //ArrayList<String>sbp=new ArrayList<>();
    //ArrayList<String>hbp=new ArrayList<>();
    //ArrayList<boolean>medicine=new ArrayList<>();
        drawGraphs();
    }

    protected void drawGraphs(){
//set child
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        phyID = database.getReference(user.getUid());
        patientList = phyID.child("patientList");



        String medicationKey = MedCurrentInfo.selectedMedKey;

        String  patientKey = PatientMain.selectedPatientKey;
        Log.d("Fragment:",patientKey);
        patientName = patientList.child(patientKey);

        vitalList = patientName.child("vitalList");

        date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
        final String stringDate = dt.format(date);
        dates = vitalList.child(stringDate);
        //VitalInfo vitalz = dataSnapshot.child(stringDate).getValue(VitalInfo.class);
        //dates.setValue(vital);


        final ArrayList<Integer> weightDate = new ArrayList<>();
        final ArrayList<Integer> ww=new ArrayList<>();
        final ArrayList<Integer> SBPDate = new ArrayList<>();
        final ArrayList<Integer> ss = new ArrayList<>();
        final ArrayList<Integer> DBPDate = new ArrayList<>();
        final ArrayList<Integer> dd = new ArrayList<>();
        final ArrayList<Integer> MedDate = new ArrayList<>();
        final ArrayList<Boolean> mm = new ArrayList<>();
        final ArrayList<Integer> TF = new ArrayList<>();

//weight
        series_one = new LineGraphSeries<>();
        series_two = new LineGraphSeries<>();
        series_three = new LineGraphSeries<>();
        series_four = new LineGraphSeries<>();

        DatabaseReference graph_reference= FirebaseDatabase.getInstance().getReference(user.getUid()).
                child("patientList").child(patientKey).child("vitalList");

        DatabaseReference medlist_reference = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("patientList").child(patientKey).child("medicationList").child(medicationKey).child("medDates");

        graph_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //weight
                //dates.setValue(ww);
/*
                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    VitalInfo vt = data.getValue(VitalInfo.class);
                    ww.add(vt.weight);
                }*/
                Iterable <DataSnapshot> children = dataSnapshot.getChildren();

                GraphView graph_weight = (GraphView) findViewById(R.id.graph_one); //weight
                GraphView graph_sbp = (GraphView) findViewById(R.id.graph_two); // SBP
                GraphView graph_dbp = (GraphView) findViewById(R.id.graph_three); //DBP


                for (DataSnapshot data : children) {

                    VitalInfo vt = data.getValue(VitalInfo.class);
                    ww.add(vt.weight);
                    weightDate.add(ww.size()-1);

                    ss.add(vt.bloodPHigh);
                    SBPDate.add(ss.size()-1);

                    dd.add(vt.bloodPLow);
                    DBPDate.add(dd.size()-1);

                    series_one = new LineGraphSeries<>(new DataPoint[]{});
                    series_two = new LineGraphSeries<>(new DataPoint[]{});
                    series_three = new LineGraphSeries<>(new DataPoint[]{});

                    //for(int i=0; i<ww.size(); i++)
                    //for(int www: ww) {
                      //  for (int s: weightDate)
                       // {
                            //int date+"i"=ww.get(i);
                            //int weight+"i"= weightDate.get(i);
                       //    Log.d("WW***", ""+ww.get(i));
                       //    Log.d("S*****", ""+weightDate.get(i));



                        //}
                    //}

                    if(ww.size()==1) {
                        series_one = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(weightDate.get(0), ww.get(0))

                        });
                    }

                    if(ww.size()==2) {
                        series_one = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(weightDate.get(0), ww.get(0)),
                                new DataPoint(weightDate.get(1), ww.get(1))
                        });
                    }

                    if(ww.size()==3) {
                        series_one = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(weightDate.get(0), ww.get(0)),
                                new DataPoint(weightDate.get(1), ww.get(1)),
                                new DataPoint(weightDate.get(2), ww.get(2))
                        });
                    }

                    if(ww.size()==4) {
                        series_one = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(weightDate.get(0), ww.get(0)),
                                new DataPoint(weightDate.get(1), ww.get(1)),
                                new DataPoint(weightDate.get(2), ww.get(2)),
                                new DataPoint(weightDate.get(3), ww.get(3))
                        });
                    }

                    if(ww.size()==5) {
                        series_one = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(weightDate.get(0), ww.get(0)),
                                new DataPoint(weightDate.get(1), ww.get(1)),
                                new DataPoint(weightDate.get(2), ww.get(2)),
                                new DataPoint(weightDate.get(3), ww.get(3)),
                                new DataPoint(weightDate.get(4), ww.get(4))
                        });
                    }

                    if(ww.size()==6) {
                        series_one = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(weightDate.get(0), ww.get(0)),
                                new DataPoint(weightDate.get(1), ww.get(1)),
                                new DataPoint(weightDate.get(2), ww.get(2)),
                                new DataPoint(weightDate.get(3), ww.get(3)),
                                new DataPoint(weightDate.get(4), ww.get(4)),
                                new DataPoint(weightDate.get(5), ww.get(5))
                        });
                    }

                    if(ww.size()==7) {
                        series_one = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(weightDate.get(0), ww.get(0)),
                                new DataPoint(weightDate.get(1), ww.get(1)),
                                new DataPoint(weightDate.get(2), ww.get(2)),
                                new DataPoint(weightDate.get(3), ww.get(3)),
                                new DataPoint(weightDate.get(4), ww.get(4)),
                                new DataPoint(weightDate.get(5), ww.get(5)),
                                new DataPoint(weightDate.get(6), ww.get(6))
                        });
                    }

                    if(ss.size()==1)
                    {
                        series_two = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(SBPDate.get(0), ss.get(0))

                        });
                    }

                    if(ss.size()==2)
                    {
                        series_two = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(SBPDate.get(0), ss.get(0)),
                                new DataPoint(SBPDate.get(1), ss.get(1))

                        });
                    }

                    if(ss.size()==3)
                    {
                        series_two = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(SBPDate.get(0), ss.get(0)),
                                new DataPoint(SBPDate.get(1), ss.get(1)),
                                new DataPoint(SBPDate.get(2), ss.get(2))

                        });
                    }

                    if(ss.size()==4)
                    {
                        series_two = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(SBPDate.get(0), ss.get(0)),
                                new DataPoint(SBPDate.get(1), ss.get(1)),
                                new DataPoint(SBPDate.get(2), ss.get(2)),
                                new DataPoint(SBPDate.get(3), ss.get(3))

                        });
                    }

                    if(ss.size()==5)
                    {
                        series_two = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(SBPDate.get(0), ss.get(0)),
                                new DataPoint(SBPDate.get(1), ss.get(1)),
                                new DataPoint(SBPDate.get(2), ss.get(2)),
                                new DataPoint(SBPDate.get(3), ss.get(3)),
                                new DataPoint(SBPDate.get(4), ss.get(4))

                        });
                    }

                    if(ss.size()==6)
                    {
                        series_two = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(SBPDate.get(0), ss.get(0)),
                                new DataPoint(SBPDate.get(1), ss.get(1)),
                                new DataPoint(SBPDate.get(2), ss.get(2)),
                                new DataPoint(SBPDate.get(3), ss.get(3)),
                                new DataPoint(SBPDate.get(4), ss.get(4)),
                                new DataPoint(SBPDate.get(5), ss.get(5))

                        });
                    }

                    if(ss.size()==7)
                    {
                        series_two = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(SBPDate.get(0), ss.get(0)),
                                new DataPoint(SBPDate.get(1), ss.get(1)),
                                new DataPoint(SBPDate.get(2), ss.get(2)),
                                new DataPoint(SBPDate.get(3), ss.get(3)),
                                new DataPoint(SBPDate.get(4), ss.get(4)),
                                new DataPoint(SBPDate.get(5), ss.get(5)),
                                new DataPoint(SBPDate.get(6), ss.get(6))

                        });
                    }
                    if (dd.size()==1)
                    {
                        series_three = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(DBPDate.get(0), dd.get(0)),
                        });

                    }
                    if (dd.size()==2)
                    {
                        series_three = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(DBPDate.get(0), dd.get(0)),
                                new DataPoint(DBPDate.get(1), dd.get(1))

                        });

                    }
                    if (dd.size()==3)
                    {
                        series_three = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(DBPDate.get(0), dd.get(0)),
                                new DataPoint(DBPDate.get(1), dd.get(1)),
                                new DataPoint(DBPDate.get(2), dd.get(2))


                        });

                    }
                    if (dd.size()==4)
                    {
                        series_three = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(DBPDate.get(0), dd.get(0)),
                                new DataPoint(DBPDate.get(1), dd.get(1)),
                                new DataPoint(DBPDate.get(2), dd.get(2)),
                                new DataPoint(DBPDate.get(3), dd.get(3))

                        });

                    }
                    if (dd.size()==5)
                    {
                        series_three = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(DBPDate.get(0), dd.get(0)),
                                new DataPoint(DBPDate.get(1), dd.get(1)),
                                new DataPoint(DBPDate.get(2), dd.get(2)),
                                new DataPoint(DBPDate.get(3), dd.get(3)),
                                new DataPoint(DBPDate.get(4), dd.get(4))
                        });

                    }
                    if (dd.size()==6)
                    {
                        series_three = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(DBPDate.get(0), dd.get(0)),
                                new DataPoint(DBPDate.get(1), dd.get(1)),
                                new DataPoint(DBPDate.get(2), dd.get(2)),
                                new DataPoint(DBPDate.get(3), dd.get(3)),
                                new DataPoint(DBPDate.get(4), dd.get(4)),
                                new DataPoint(DBPDate.get(5), dd.get(5))
                        });

                    }
                    if (dd.size()==7)
                    {
                        series_three = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                new DataPoint(DBPDate.get(0), dd.get(0)),
                                new DataPoint(DBPDate.get(1), dd.get(1)),
                                new DataPoint(DBPDate.get(2), dd.get(2)),
                                new DataPoint(DBPDate.get(3), dd.get(3)),
                                new DataPoint(DBPDate.get(4), dd.get(4)),
                                new DataPoint(DBPDate.get(5), dd.get(5)),
                                new DataPoint(DBPDate.get(6), dd.get(6))

                        });

                    }
                }

                //weight = dataSnapshot.getValue(VitalInfo.class).weight.toString();

                graph_weight.addSeries(series_one);
                series_one.setDrawDataPoints(true);
                series_one.setDataPointsRadius(10);

                graph_sbp.addSeries(series_two);
                series_two.setDrawDataPoints(true);
                series_two.setDataPointsRadius(10);

                graph_dbp.addSeries(series_three);
                series_three.setDrawDataPoints(true);
                series_three.setDataPointsRadius(10);



//weight
                graph_weight.getViewport().setXAxisBoundsManual(true);
                graph_weight.getViewport().setMinX(0);
                graph_weight.getViewport().setMaxX(6);

                graph_weight.getViewport().setYAxisBoundsManual(true);
                graph_weight.getViewport().setMinY(0);
                graph_weight.getViewport().setMaxY(300);

                graph_weight.getViewport().setScrollable(true);

                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph_weight);
                staticLabelsFormatter.setHorizontalLabels(new String[]{"D1", "D2", "D3", "D4", "D5", "D6", "D7"});
                //staticLabelsFormatter.setVerticalLabels(new String[] {"50KG","100KG","150KG","200KG"});
                graph_weight.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


//sbp
                graph_dbp.getViewport().setXAxisBoundsManual(true);
                graph_dbp.getViewport().setMinX(0);
                graph_dbp.getViewport().setMaxX(6);

                graph_dbp.getViewport().setYAxisBoundsManual(true);
                graph_dbp.getViewport().setMinY(0);
                graph_dbp.getViewport().setMaxY(250);

                graph_dbp.getViewport().setScrollable(true);

                StaticLabelsFormatter staticLabelsFormatter_one = new StaticLabelsFormatter(graph_dbp);
                staticLabelsFormatter_one.setHorizontalLabels(new String[]{"D1", "D2", "D3", "D4", "D5", "D6", "D7"});
                //staticLabelsFormatter_one.setVerticalLabels(new String[] {"60mmHG","120mmHG","180mmHG","240mmHG","300mmHG"});
                graph_dbp.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter_one);

//dbp
                graph_sbp.getViewport().setXAxisBoundsManual(true);
                graph_sbp.getViewport().setMinX(0);
                graph_sbp.getViewport().setMaxX(6);

                graph_sbp.getViewport().setYAxisBoundsManual(true);
                graph_sbp.getViewport().setMinY(0);
                graph_sbp.getViewport().setMaxY(250);

                graph_sbp.getViewport().setScrollable(true);

                StaticLabelsFormatter staticLabelsFormatter_two = new StaticLabelsFormatter(graph_sbp);
                staticLabelsFormatter_two.setHorizontalLabels(new String[]{"D1", "D2", "D3", "D4", "D5", "D6", "D7"});
                //staticLabelsFormatter_two.setVerticalLabels(new String[]{"NO", "YES"});
                graph_sbp.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter_two);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        medlist_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable <DataSnapshot> children = dataSnapshot.getChildren();

                GraphView graph_med = (GraphView) findViewById(R.id.graph_four);

                for (DataSnapshot data : children) {

                    Log.d("data.getV: ", data.getValue().toString());

                    NursePhyInfo np = data.getValue(NursePhyInfo.class);

                    mm.add(np.given);
                    MedDate.add(mm.size() -1);

                    series_four = new LineGraphSeries<>(new DataPoint[]{});

                    for(int i = 0; i < mm.size(); i++)
                    {
                        //Log.d("mm hold: ", mm.get(i).toString());

                       if(mm.get(i)==true)
                       {
                            TF.add(1);
                        }
                        else
                        {
                            TF.add(0);
                        }
                    }

                    if(mm.size()==1) {

                        //Log.d("TF", TF.get(1).toString());

                        series_four = new LineGraphSeries<>(new DataPoint[]{

                                //new DataPoint(0,1),
                                new DataPoint(MedDate.get(0), TF.get(0))

                        });
                    }

                    if(mm.size()==2) {
                        series_four = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(MedDate.get(0), TF.get(0)),
                                new DataPoint(MedDate.get(1), TF.get(1))
                        });
                    }

                    if(mm.size()==3) {
                        series_four = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(MedDate.get(0), TF.get(0)),
                                new DataPoint(MedDate.get(1), TF.get(1)),
                                new DataPoint(MedDate.get(2), TF.get(2))
                        });
                    }

                    if(mm.size()==4) {
                        series_four = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(MedDate.get(0), TF.get(0)),
                                new DataPoint(MedDate.get(1), TF.get(1)),
                                new DataPoint(MedDate.get(2), TF.get(2)),
                                new DataPoint(MedDate.get(3), TF.get(3))
                        });
                    }

                    if(mm.size()==5) {
                        series_four = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(MedDate.get(0), TF.get(0)),
                                new DataPoint(MedDate.get(1), TF.get(1)),
                                new DataPoint(MedDate.get(2), TF.get(2)),
                                new DataPoint(MedDate.get(3), TF.get(3)),
                                new DataPoint(MedDate.get(4), TF.get(4))
                        });
                    }

                    if(mm.size()==6) {
                        series_four = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(MedDate.get(0), TF.get(0)),
                                new DataPoint(MedDate.get(1), TF.get(1)),
                                new DataPoint(MedDate.get(2), TF.get(2)),
                                new DataPoint(MedDate.get(3), TF.get(3)),
                                new DataPoint(MedDate.get(4), TF.get(4)),
                                new DataPoint(MedDate.get(5), TF.get(5))
                        });
                    }

                    if(mm.size()==7) {
                        series_four = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(MedDate.get(0), TF.get(0)),
                                new DataPoint(MedDate.get(1), TF.get(1)),
                                new DataPoint(MedDate.get(2), TF.get(2)),
                                new DataPoint(MedDate.get(3), TF.get(3)),
                                new DataPoint(MedDate.get(4), TF.get(4)),
                                new DataPoint(MedDate.get(5), TF.get(5)),
                                new DataPoint(MedDate.get(6), TF.get(6))
                        });
                    }


                    //analysis
                    graph_med.getViewport().setXAxisBoundsManual(true);
                    graph_med.getViewport().setMinX(0);
                    graph_med.getViewport().setMaxX(6);

                    graph_med.getViewport().setYAxisBoundsManual(true);
                    graph_med.getViewport().setMinY(0);
                    graph_med.getViewport().setMaxY(1);//modify range later

                    graph_med.getViewport().setScrollable(true);

                    graph_med.addSeries(series_four);
                    series_four.setDrawDataPoints(true);
                    series_four.setDataPointsRadius(10);

                    StaticLabelsFormatter staticLabelsFormatter_three = new StaticLabelsFormatter(graph_med);
                    staticLabelsFormatter_three.setHorizontalLabels(new String[]{"D1", "D2", "D3", "D4", "D5", "D6", "D7"});
                    staticLabelsFormatter_three.setVerticalLabels(new String[]{"NOT GIVEN", "GIVEN"});
                    graph_med.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter_three);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }




}


