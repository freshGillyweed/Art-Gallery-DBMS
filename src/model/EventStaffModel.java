package model;

// EventStaffModel extends EmployeeModel to model ISA relationship
public class EventStaffModel extends EmployeeModel{
    private String department;
    private int eventID;
    public EventStaffModel(int employeeID, String phoneNum, String name, String department) {
        super(employeeID, phoneNum, name);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
}
