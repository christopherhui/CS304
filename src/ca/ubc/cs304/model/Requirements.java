package ca.ubc.cs304.model;

import java.util.List;

public class Requirements {
    private final String location; //primary key
    private final List skills; //should this be a list? primary key
    private final int jobNo; //foreign key
    private final int citizenship;
    private final int gpa; //real number
    private final String experience;


    public Requirements(String location, List skills, int jobNo, int citizenship, int gpa, String experience) {
        this.location = location;
        this.skills = skills;
        this.jobNo = Job.getJobNo();
        this.citizenship = citizenship;
        this.gpa = gpa;
        this.experience = experience;
    }
}
