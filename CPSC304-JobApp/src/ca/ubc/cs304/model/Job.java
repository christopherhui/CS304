package ca.ubc.cs304.model;


public class Job {
    private final int jobNo; //primary key
    private final String companyName; //foreign key from Company
    private final String jobTitle;
    private final String description;


    public Job(int jobNo, String companyName, String jobTitle, String description) {
        this.jobNo = jobNo;
        this.companyName = Company.getName();
        this.jobTitle = jobTitle;
        this.description = description;
    }

    public static int getJobNo(){
        return Integer.parseInt(null);
    }

    public String getJobTitle(){
        return jobTitle;
    }

    public String getDescription(){
        return description;
    }
}

