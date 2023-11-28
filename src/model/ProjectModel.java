package model;

public class ProjectModel {
    private int projectID;
    private String title;
    private double budget;
    private String status;
    private String startDate;
    private String endDate;

    public ProjectModel(int projectID, String title, double budget, String status, String startDate, String endDate) {
        this.projectID = projectID;
        this.title = title;
        this.budget = budget;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public int getProjectID() {
        return projectID;
    }
    public String getTitle() {
        return title;
    }
    public double getBudget() {
        return budget;
    }
    public String getStatus() {
        return status;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate(){
        return endDate;
    }
}
