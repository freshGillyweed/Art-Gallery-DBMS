//CREATE TABLE Exhibition
//        (exhibitionID INTEGER,
//        title VARCHAR(50),
//        startDate VARCHAR(50),
//        endDate VARCHAR(50),
//        visitorCount INTEGER,
//        location VARCHAR(50),
//        curatorID INTEGER,
//        rating INTEGER,
//        PRIMARY KEY (exhibitionID),
//        FOREIGN KEY (curatorID) REFERENCES Curator(employeeID),
//        UNIQUE (startDate, location));

public class ExhibitionModel {

    private final exhibitionID;
    private String title;
    private String startDate;
    private String endDate;
    private int visitorCount;
    private String location;
    private int curatorID;
    private int rating;

    public ExhibitionModel(String title, String startDate,
                           String endDate, int visitorCount, String location, int curatorID, int rating) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.visitorCount = visitorCount;
        this.location = location;
        this.curatorID = curatorID;
        this.rating = rating;
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