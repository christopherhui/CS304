package ca.ubc.cs304.model;

public class Company {
    private final int hiringAmt;
    private final String type;
    private final String name; //this is primary key

    public Company(String name, int hiringAmt, String type) {
        this.hiringAmt = hiringAmt;
        this.type = type;
        this.name = name;
    }

    public int getHiringAmt(){
        return hiringAmt;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
