package cus1194.medtracker;

/**
 * Created by pruan086 on 4/5/2017.
 */

public class NursePhyInfo
{

    public String name;
    public String medName;
    public String medID;
    public boolean given;

    public NursePhyInfo (){
        //nothing to put here for blank constructor
    }

    public NursePhyInfo(String name, String medName, String medID, boolean status)
    {

        this.name=name;
        this.medName = medName;
        this.medID = medID;
        this.given=status;

    }

}
