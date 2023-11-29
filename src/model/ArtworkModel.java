package model;

public class ArtworkModel {

    private final int artworkID;
    private final int artistID;
    private final String title;
    private final String dimensions;
    private final String dateCreated;
    private final String displayMedium;
    private int donorID;
    private int featureID;
    private int value;

    public ArtworkModel(int artistID, int artworkID, String title, String dimensions,
                        String dateCreated, String displayMedium, int donorID, int featureID, int value) {
        this.artistID = artistID;
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

    public int getArtistID() {
        return artistID;
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