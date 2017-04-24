package cus1194.medtracker;

/**
 * Created by pruan086 on 4/5/2017.
 */

public class NursePhyInfo
{

    public String Name;
    public boolean Given;

    public NursePhyInfo (){
        //nothing to put here for blank constructor
    }

    public NursePhyInfo(String Name, boolean Status)
    {

        this.Name=Name;
        this.Given=Status;

    }

}
