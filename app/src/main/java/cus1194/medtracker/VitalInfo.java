package cus1194.medtracker;

/**
 * Created by pruan086 on 4/4/2017.
 */

public class VitalInfo
{

    public String Time;
    public String BloodPHigh;
    public String BloodPLow;
    public String Weight;

    public VitalInfo (){
        //nothing to put here for blank constructor
    }

    public VitalInfo(String Time, String BloodPHigh, String BloodPLow, String Weight)
    {

        this.Time= Time;
        this.BloodPHigh=BloodPHigh;
        this.BloodPLow=BloodPLow;
        this.Weight=Weight;

    }

}
