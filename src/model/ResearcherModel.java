package model;

// ResearcherModel extends EmployeeModel to model ISA relationship
public class ResearcherModel extends EmployeeModel{
    private String researchInterest;

    public ResearcherModel(int employeeID, String phoneNum, String name, String researchInterest) {
        super(employeeID, phoneNum, name);
        this.researchInterest = researchInterest;
    }
    public String getResearchInterest() {
        return researchInterest;
    }
}
