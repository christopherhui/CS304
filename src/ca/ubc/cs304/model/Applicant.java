package ca.ubc.cs304.model;


import java.sql.Date;

public class Applicant {
    // All values in the table should not be null, as they have references to other tables
    private final int sin;
    private final int year; //foreign key from Degree
    private final String major; //foreign key from Degree
    private final String firstName;
    private final String lastName;
    private final String address;
    private final Date doe;

    public Applicant(int sin, int year, String major, String firstName, String lastName, String address, Date doe) {
        this.sin = sin;
        this.doe = doe;
        this.year = Degree.getYear();
        this.major = Degree.getMajor();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getMajor() { return major; }

    public int getSin(){
        return sin;
    }

    public int getYear() {
        return year;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public Date getDoe() {
        return doe;
    }
}
