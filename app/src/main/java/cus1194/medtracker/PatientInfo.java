package cus1194.medtracker;

/**
 * Created by liu on 4/19/17.
 */

public class PatientInfo
{

    private String pName;
    private String ssn;
    private String medHis;

    public PatientInfo(){
        pName = null;
        ssn = null;
        medHis = null;
    }

    public PatientInfo(String pName, String ssn, String MedHis)
    {
        this.pName = pName;
        this.ssn = ssn;
        this.medHis =MedHis;
    }

    public String getPName(){
        return pName;
    }

    public void setPName(String pName)
    {
        this.pName = pName;
    }

    public String getSSN(){
        return ssn;
    }

    public void setSsn(String ssn)
    {
        this.ssn = ssn;
    }

    public String getMedHis(){
        return medHis;
    }

    public void setMedHis(String MedHis)
    {
        this.medHis =MedHis;
    }
}


