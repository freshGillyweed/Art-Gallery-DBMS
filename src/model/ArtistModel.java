package model;


public class ArtistModel {


    private final int artistID;
    private final String name;
    private final String dateOfBirth;
    private String dateOfDeath;
    private int skillLevel;

    public ArtistModel(int artistID, String name, String dateOfBirth, String dateOfDeath, int skillLevel) {
        this.artistID = artistID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.skillLevel = skillLevel;
    }

    public int getArtistID() {
        return artistID;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }

    public int getSkillLevel() {
        return skillLevel;
    }
}