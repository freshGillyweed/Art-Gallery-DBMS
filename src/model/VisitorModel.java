package model;

public class VisitorModel {
    private final int visitorID;
    private final String name;
    private final int phoneNum;

    public VisitorModel(int visitorID, String name, int phoneNum) {
        this.visitorID = visitorID;
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public int getVisitorID() {
        return visitorID;
    }

    public String getName() {
        return name;
    }

    public int getPhoneNum() {
        return phoneNum;
    }
}
