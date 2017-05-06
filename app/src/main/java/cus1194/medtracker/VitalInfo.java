package cus1194.medtracker;

/**
 * Created by pruan086 on 4/4/2017.
 */

public class VitalInfo
{

    public String bloodPHigh;
    public String bloodPLow;
    public String weight;

    public VitalInfo (){
        //nothing to put here for blank constructor
    }

    public VitalInfo(String BloodPHigh, String BloodPLow, String Weight)
    {

        this.bloodPHigh =BloodPHigh;
        this.bloodPLow =BloodPLow;
        this.weight =Weight;

    }

}
