//CREATE TABLE Donor
//        (donorID INTEGER PRIMARY KEY,
//        name VARCHAR(50),
//        totalDonationValue INTEGER,
//        phoneNum VARCHAR(50));

public class DonorModel {

    private final int donorID;
    private final String name;
    private int totalDonationValue;
    private final String phoneNum;

    public DonorModel(int donorID, String name, int totalDonationValue, String phoneNum) {
        this.donorID = donorID;
        this.name = name;
        this.totalDonationValue = totalDonationValue;
        this.phoneNum = phoneNum;
    }

    public int getDonorID() {
        return donorID;
    }

    public String getName() {
        return name;
    }

    public int getTotalDonationValue() {
        return totalDonationValue;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
}