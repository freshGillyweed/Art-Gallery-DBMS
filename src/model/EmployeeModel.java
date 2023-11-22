package model;

public class EmployeeModel {
    private int employeeID;
    private String phoneNum;
    private String name;

    EmployeeModel(int employeeID, String phoneNum, String name) {
        this.employeeID = employeeID;
        this.phoneNum = phoneNum;
        this.name = name;
    }

    public int getEmployeeID() {return employeeID;}

    public String getPhoneNum() {return phoneNum;}

    public String getName() {return name;}
}
