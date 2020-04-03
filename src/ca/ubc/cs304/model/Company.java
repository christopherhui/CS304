package ca.ubc.cs304.model;

public class Company {
    private final int hiringAmt;
    private final int type;
    private final String name; //this is primary key

    public Company(int hiringAmt, int type, String name) {
        this.hiringAmt = hiringAmt;
        this.type = type;
        this.name = name;
    }

    public int getHiringAmt(){
        return hiringAmt;
    }

    public int getType() {
        return type;
    }

    public static String getName() {
        return null;
    }
}
