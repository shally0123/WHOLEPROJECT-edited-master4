package cus1194.medtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by liu on 4/11/17.
 */

public class graph extends AppCompatActivity {
@Override
        protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.graph);

    GraphView graph_weight = (GraphView) findViewById(R.id.graph_one);


    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(10, 100),
            new DataPoint(50, 30),
            new DataPoint(20, 30)//HOW TO LINKED WITH REAL TIME DATA?
    });

graph_weight.addSeries(series);

    GraphView graph_bp = (GraphView) findViewById(R.id.graph_two);
    LineGraphSeries<DataPoint> series_two = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(0.5, 5),
            new DataPoint(1, 6),
            new DataPoint(2, 7)//HOW TO LINKED WITH REAL TIME DATA?
    });
    graph_weight.addSeries(series_two);

//weight
    graph_weight.getViewport().setXAxisBoundsManual(true);
    graph_weight.getViewport().setMinX(10);//modify to the correct unit
    graph_weight.getViewport().setMaxX(50);

    graph_weight.getViewport().setYAxisBoundsManual(true);
    graph_weight.getViewport().setMinY(10);
    graph_weight.getViewport().setMaxY(200);

    graph_weight.getViewport().setScrollable(true);

    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph_weight);
    staticLabelsFormatter.setHorizontalLabels(new String[] {"M","T","W","R","F"});
    staticLabelsFormatter.setVerticalLabels(new String[] {"50KG","100KG","150KG","200KG"});
    graph_weight.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


//bp
    graph_bp.getViewport().setXAxisBoundsManual(true);
    graph_bp.getViewport().setMinX(0.5);
    graph_bp.getViewport().setMaxX(2.5);

    graph_bp.getViewport().setYAxisBoundsManual(true);
    graph_bp.getViewport().setMinY(0.5);
    graph_bp.getViewport().setMaxY(8);

    graph_bp.getViewport().setScrollable(true);

    StaticLabelsFormatter staticLabelsFormatter_one = new StaticLabelsFormatter(graph_bp);
    staticLabelsFormatter_one.setHorizontalLabels(new String[] {"M","T","W","R","F"});
    staticLabelsFormatter_one.setVerticalLabels(new String[] {"90/60mmHG","50KG","100KG","150KG","200KG"});
    graph_bp.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter_one);

}










}


