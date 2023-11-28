//CREATE TABLE Artwork
//        (artworkID INTEGER,
//        artistID INTEGER,
//        title VARCHAR(50) NOT NULL,
//        dimensions VARCHAR(50),
//        dateCreated VARCHAR(50) NOT NULL,
//        displayMedium VARCHAR(50) NOT NULL,
//        donorID INTEGER,
//        featureID INTEGER,
//        value INTEGER,
//        PRIMARY KEY (artworkID, artistID),
//        FOREIGN KEY (artistID) REFERENCES Artist(artistID) ON DELETE CASCADE,
//        FOREIGN KEY (donorID) REFERENCES Donor(donorID) ON DELETE SET NULL,
//        FOREIGN KEY (featureID) REFERENCES Exhibition(exhibitionID) ON DELETE SET NULL,
//        UNIQUE (dateCreated, title, artistID));


public class ArtworkModel {

    private final int artworkID;
    private final artistID;
    private final String title;
    private final String dimensions;
    private final String dateCreated;
    private final String displayMedium;
    private int donorID;
    private int featureID;
    private int value;

    public ArtworkModel(int artworkID, String title, String dimensions,
                        String dateCreated, String displayMedium, int donorID, int featureID, int value) {
        this.artworkID = artworkID;
        this.title = title;
        this.dimensions = dimensions;
        this.dateCreated = dateCreated;
        this.displayMedium = displayMedium;
        this.donorID = donorID;
        this.featureID = featureID;
        this.value = value;
    }

    public int getArtworkID() {
        return artworkID;
    }

    public String getTitle() {
        return title;
    }

    public String getDimensions() {
        return dimensions;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDisplayMedium() {
        return displayMedium;
    }

    public int getDonorID() {
        return donorID;
    }

    public int getFeatureID() {
        return featureID;
    }

    public int getValue() {
        return value;
    }
}