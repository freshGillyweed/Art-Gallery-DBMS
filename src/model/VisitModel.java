package model;

public class VisitModel {
    private int visitorID;
    private int exhibitionID;

    public VisitModel(int visitorID, int exhibitionID) {
        this.visitorID = visitorID;
        this.exhibitionID = exhibitionID;
    }

    public int getVisitorID() {
        return visitorID;
    }

    public int getExhibitionID() {
        return exhibitionID;
    }
}
