package model;

public class CuratorModel{
    private int employeeID;
    private String specialization;

    public CuratorModel(int employeeID, String specialization) {
        //super(employeeID, phoneNum, name);
        this.employeeID = employeeID;

        this.specialization = specialization;
    }

    public int getEmployeeID() {
        return employeeID;
    }



    public String getSpecialization() {return specialization;}
}
