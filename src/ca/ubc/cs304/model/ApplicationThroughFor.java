package ca.ubc.cs304.model;

public class ApplicationThroughFor {
    private int SIN;
    private String Company_Name;
    private String App_ID;
    private String Platform_Name;
    private String Documents;
    private String Status;

    public ApplicationThroughFor(int SIN, String Company_Name, String App_ID, String platform_Name, String Documents, String Status) {
        this.SIN = SIN;
        this.Company_Name = Company_Name;
        this.App_ID = App_ID;
        this.Platform_Name = platform_Name;
        this.Documents = Documents;
        this.Status = Status;
    }

    public int getSIN() {
        return SIN;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public String getApp_ID() {
        return App_ID;
    }

    public String getPlatform_Name() {
        return Platform_Name;
    }

    public String getDocuments() {
        return Documents;
    }

    public String getStatus() {
        return Status;
    }
}
