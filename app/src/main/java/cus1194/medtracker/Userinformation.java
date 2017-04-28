package cus1194.medtracker;

/**
 * Created by liu on 3/6/17.
 */

public class Userinformation{

    public String name;
    public int age;
    public String postiion;
    public int NPI;

    public Userinformation(){

    }


        public Userinformation(String name, int age, String postiion, int NPI) {
            this.name = name;
            this.age = age;
            this.postiion = postiion;
            this.NPI = NPI;
        }

        /*
    public String getName(){
        return name;
    }
    public String getAge() {
        return age;
    }

    public String getNPI() {
        return NPI;
    }

    public String getPostiion() {
        return postiion;
    }*/
}
