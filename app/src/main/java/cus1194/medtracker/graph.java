package cus1194.medtracker;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

/**
 * Created by liu on 4/11/17.
 */




public class graph extends AppCompatActivity {
    int avg;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);
        ArrayList<String>weight=new ArrayList<>();
    ArrayList<String>sbp=new ArrayList<>();
    ArrayList<String>hbp=new ArrayList<>();
    //ArrayList<boolean>medicine=new ArrayList<>();
        drawGraphs();
    }

    protected void drawGraphs(){
//weight
        GraphView graph_weight = (GraphView) findViewById(R.id.graph_one);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,100),
                new DataPoint(1,30),
                new DataPoint(2,60)//HOW TO LINKED WITH input DATA?
        });
        graph_weight.addSeries(series);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);

        //bp
        GraphView graph_dbp = (GraphView) findViewById(R.id.graph_two);
        LineGraphSeries<DataPoint> series_two = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 60),
                new DataPoint(1, 74),
                new DataPoint(2, 80),
                new DataPoint(3,90)//HOW TO LINKED WITH input DATA?
        });
        graph_dbp.addSeries(series_two);
        series_two.setTitle("DBP");
        series_two.setDrawDataPoints(true);
        series_two.setDataPointsRadius(10);
        series_two.setColor(Color.RED);


        GraphView graph_sbp = (GraphView) findViewById(R.id.graph_two);
        LineGraphSeries<DataPoint> series_three = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 90),
                new DataPoint(1, 120),
                new DataPoint(2, 200),
                new DataPoint(3, 180)//HOW TO LINKED WITH input DATA?
        });
        graph_sbp.addSeries(series_three);
        series_three.setTitle("SBP");
        series_three.setColor(Color.BLUE);
        series_three.setDrawDataPoints(true);
        series_three.setDataPointsRadius(10);



        //medtracker
        GraphView graph_tracker = (GraphView) findViewById(R.id.graph_three);
        LineGraphSeries<DataPoint> series_four = new  LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 0),
                new DataPoint(2, 0)//HOW TO LINKED WITH input DATA?
        });
        graph_tracker.addSeries(series_four);
        series_four.setDrawDataPoints(true);
        series_four.setDataPointsRadius(10);

        //Analysis
        //avg=graph_dbp.getGraphContentTop()+graph_sbp.getGraphContentTop();
        GraphView graph_analysis = (GraphView) findViewById(R.id.graph_four);
        LineGraphSeries<DataPoint> series_five = new  LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1,1 ),
                new DataPoint(2, 0)//HOW TO LINKED WITH input DATA?
        });
        graph_analysis.addSeries(series_five);
        series_five.setDrawDataPoints(true);
        series_five.setDataPointsRadius(10);

//weight
        graph_weight.getViewport().setXAxisBoundsManual(true);
        graph_weight.getViewport().setMinX(0);
        graph_weight.getViewport().setMaxX(4);

        graph_weight.getViewport().setYAxisBoundsManual(true);
        graph_weight.getViewport().setMinY(10);
        graph_weight.getViewport().setMaxY(200);

        graph_weight.getViewport().setScrollable(true);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph_weight);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"D1","D2","D3","D4","D5"});
        //staticLabelsFormatter.setVerticalLabels(new String[] {"50KG","100KG","150KG","200KG"});
        graph_weight.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


//bp
        graph_dbp.getViewport().setXAxisBoundsManual(true);
        graph_dbp.getViewport().setMinX(0);
        graph_dbp.getViewport().setMaxX(4);

        graph_dbp.getViewport().setYAxisBoundsManual(true);
        graph_dbp.getViewport().setMinY(0);
        graph_dbp.getViewport().setMaxY(300);

        graph_dbp.getViewport().setScrollable(true);

        StaticLabelsFormatter staticLabelsFormatter_one = new StaticLabelsFormatter(graph_dbp);
        staticLabelsFormatter_one.setHorizontalLabels(new String[] {"D1","D2","D3","D4","D5"});
        //staticLabelsFormatter_one.setVerticalLabels(new String[] {"60mmHG","120mmHG","180mmHG","240mmHG","300mmHG"});
        graph_dbp.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter_one);

        //tracker
        graph_tracker.getViewport().setXAxisBoundsManual(true);
        graph_tracker.getViewport().setMinX(0);
        graph_tracker.getViewport().setMaxX(4);

        graph_tracker.getViewport().setYAxisBoundsManual(true);
        graph_tracker.getViewport().setMinY(0);
        graph_tracker.getViewport().setMaxY(1);

        graph_tracker.getViewport().setScrollable(true);

        StaticLabelsFormatter staticLabelsFormatter_two = new StaticLabelsFormatter(graph_tracker);
        staticLabelsFormatter_two.setHorizontalLabels(new String[] {"D1","D2","D3","D4","D5"});
        staticLabelsFormatter_two.setVerticalLabels(new String[] {"NO","YES"});
        graph_tracker.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter_two);

        //analysis
        graph_analysis.getViewport().setXAxisBoundsManual(true);
        graph_analysis.getViewport().setMinX(0);
        graph_analysis.getViewport().setMaxX(4);

        graph_analysis.getViewport().setYAxisBoundsManual(true);
        graph_analysis.getViewport().setMinY(0);
        graph_analysis.getViewport().setMaxY(1);//modify range later

        graph_analysis.getViewport().setScrollable(true);

        StaticLabelsFormatter staticLabelsFormatter_three = new StaticLabelsFormatter(graph_analysis);
        staticLabelsFormatter_three.setHorizontalLabels(new String[] {"D1","D2","D3","D4","D5"});
        staticLabelsFormatter_three.setVerticalLabels(new String[] {"NO","YES"});
        graph_analysis.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter_three);


    }




}


