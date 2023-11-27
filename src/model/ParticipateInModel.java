package model;

public class ParticipateInModel {
    private int eventID;
    private int visitorID;

    public ParticipateInModel(int eventID, int visitorID) {
        this.eventID = eventID;
        this.visitorID = visitorID;
    }

    public int getEventID() {
        return eventID;
    }

    public int getVisitorID() {
        return visitorID;
    }
}
