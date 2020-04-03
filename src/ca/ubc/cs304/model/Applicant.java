package ca.ubc.cs304.model;

public class Applicant {
    private final int sin;
    private final int year; //foreign key from Degree
    private final String major; //foreign key from Degree
    private final String firstName;
    private final String lastName;
    private final String address;

    public Applicant(int sin, int year, String major, String firstName, String lastName, String address) {
        this.sin = sin;
        this.year = Degree.getYear();
        this.major = Degree.getMajor();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }


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
}
