package model;

public class ExhibitionModel {

    private final int exhibitionID;
    private String title;
    private String startDate;
    private String endDate;
    private int visitorCount;
    private String location;
    private int curatorID;
    private int rating;

    public ExhibitionModel(int exhibitionID, String title, String startDate,
                           String endDate, int visitorCount, String location, int curatorID, int rating) {
        this.exhibitionID = exhibitionID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.visitorCount = visitorCount;
        this.location = location;
        this.curatorID = curatorID;
        this.rating = rating;
    }

    public int getExhibitionID() {
        return exhibitionID;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getVisitorCount() {
        return visitorCount;
    }

    public String getLocation() {
        return location;
    }

    public int getCuratorID() {
        return curatorID;
    }

    public int getRating() {
        return rating;
    }
}