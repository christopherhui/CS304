package ca.ubc.cs304.model;

public class Degree {
    private final int year; //primary key
    private final String major; //primary key
    private final String faculty;

    public Degree(int year, String major, String faculty) {
        this.year = year;
        this.major = major;
        this.faculty = faculty;
    }

    public static int getYear() {
        return Integer.parseInt(null);
    }

    public static String getMajor() {
        return null;
    }

    public String getfaculty() {
        return faculty;
    }
}
