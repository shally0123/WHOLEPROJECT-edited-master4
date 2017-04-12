package cus1194.medtracker;

/**
 * Created by pruan086 on 4/4/2017.
 */

public class VitalInfo
{

    public String Time;
    public String BloodP;
    public String Weight;

    public VitalInfo (){
        //nothing to put here for blank constructor
    }

    public VitalInfo(String Time, String BloodP, String Weight)
    {

        this.Time= Time;
        this.BloodP=BloodP;
        this.Weight=Weight;

    }

}
