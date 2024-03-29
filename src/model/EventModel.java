package model;

/**
 *  The intent for this class is to update/store information about an Event
 */
public class EventModel {

    private int ticketSold;
    private final String location;
    private final String date;
    private final int capacity;
    private final int eventID;
    private final String title;

    public EventModel(int eventID, String date, String location, int ticketSold, int capacity, String title) {
        this.eventID = eventID;
        this.date = date;
        this.location = location;
        this.ticketSold = ticketSold;
        this.capacity = capacity;
        this.title = title;
    }

    // GETTER METHODS
    public int getTicketSold() {
        return ticketSold;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public int getCapacity() {
        return capacity;
    }
    public int getEventID() {
        return eventID;
    }

    public String getTitle() {
        return title;
    }
}