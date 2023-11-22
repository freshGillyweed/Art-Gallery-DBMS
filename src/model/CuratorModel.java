package model;

// CuratorModel extends EmployeeModel to model ISA relationship
public class CuratorModel extends EmployeeModel{
    private String specialization;
    public CuratorModel(int employeeID, String phoneNum, String name, String specialization) {
        super(employeeID, phoneNum, name);
        this.specialization = specialization;
    }
    public String getSpecialization() {return specialization;}
}
