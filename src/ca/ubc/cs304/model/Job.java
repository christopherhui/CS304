package ca.ubc.cs304.model;


public class Job {
    private int jobNo = 0; //primary key
    private final String companyName; //foreign key from Company
    private final String jobTitle;
    private final String description;


    public Job(int jobNo, String companyName, String jobTitle, String description) {
        this.jobNo = jobNo;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.description = description;
    }

    public int getJobNo(){
        return jobNo;
    }

    public String getJobTitle(){
        return jobTitle;
    }

    public String getDescription(){
        return description;
    }
}

