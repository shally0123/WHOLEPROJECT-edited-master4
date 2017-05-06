package cus1194.medtracker;

/**
 * Created by pruan086 on 4/5/2017.
 */

public class NursePhyInfo
{

    public String name;
    public boolean given = false;

    public NursePhyInfo (){
        //nothing to put here for blank constructor
    }

    public NursePhyInfo(String name, boolean given)
    {

        this.name=name;
        this.given=given;

    }

}
