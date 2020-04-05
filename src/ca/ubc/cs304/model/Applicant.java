package ca.ubc.cs304.model;


public class Applicant {
    // All values in the table should not be null, as they have references to other tables
    private final int sin;
    private final int year; //foreign key from Degree
    private final String major; //foreign key from Degree
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int doe;

    public Applicant(int sin, int year, String major, String firstName, String lastName, String address, int doe) {
        this.sin = sin;
        this.doe = doe;
        this.year = year;
        this.major = major;
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

    public int getDoe() {
        return doe;
    }
}
