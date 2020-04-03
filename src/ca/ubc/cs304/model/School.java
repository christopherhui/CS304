package ca.ubc.cs304.model;

public class School {
    private final String name;
    private final String location;


    public School(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getSchool(){
        return name;

    }

    public String getLocation(){
        return location;
    }
}
