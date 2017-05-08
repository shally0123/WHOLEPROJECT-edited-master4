package cus1194.medtracker;

/**
 * Created by pruan086 on 4/4/2017.
 */

public class VitalInfo
{

    public int bloodPHigh;
    public int bloodPLow;
    public int weight;

    public VitalInfo (){
        //nothing to put here for blank constructor
    }

    public VitalInfo( int BloodPHigh, int BloodPLow, int Weight)
    {

        this.bloodPHigh =BloodPHigh;
        this.bloodPLow =BloodPLow;
        this.weight =Weight;

    }

}
