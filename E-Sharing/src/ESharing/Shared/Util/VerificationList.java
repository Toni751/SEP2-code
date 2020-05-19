package ESharing.Shared.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * A class which sets and stores all verification labels with their id
 * @version 1.0
 * @author Group1
 */
public class VerificationList {
    private Map<Verifications, String> verifications;
    private static VerificationList verificationList;

    /**
     * A private constructor initializes variables and uses function to add all verification to the map
     */
    private VerificationList()
    {
        this.verifications = new HashMap<>();
        addAllVerifications();
    }

    /**
     * Returns VerificationList object if it exists, otherwise creates new object
     * @return the VerificationList object
     */
    public static VerificationList getVerificationList() {
        if(verificationList == null)
            verificationList = new VerificationList();
        return verificationList;
    }

    /**
     * Creates and adds all verification labels to the map
     */
    private void addAllVerifications()
    {
        verifications.put(Verifications.INVALID_USERNAME, "Invalid username");
        verifications.put(Verifications.INVALID_PASSWORD, "Invalid password");
        verifications.put(Verifications.INVALID_NUMBER, "Invalid number");
        verifications.put(Verifications.INVALID_PHONE_NUMBER, "Invalid phone number");
        verifications.put(Verifications.INVALID_CITY, "Invalid city");
        verifications.put(Verifications.INVALID_POSTAL_CODE, "Invalid postal code");
        verifications.put(Verifications.NOT_THE_SAME_PASSWORDS, "Passwords are not the same");
        verifications.put(Verifications.USER_NOT_EXIST, "The user does not exist");
        verifications.put(Verifications.INVALID_STREET, "Invalid street");
        verifications.put(Verifications.DATABASE_CONNECTION_ERROR, "Database connection error");
        verifications.put(Verifications.ACTION_SUCCESS, "Action successful");
        verifications.put(Verifications.SERVER_CONNECTION_ERROR, "Server connection error");
        verifications.put(Verifications.INVALID_PRICE, "Invalid price");
        verifications.put(Verifications.INVALID_TITLE, "Invalid title");
        verifications.put(Verifications.INVALID_DESCRIPTION, "Invalid description");
        verifications.put(Verifications.INVALID_VEHICLE_TYPE, "Invalid vehicle type");
        verifications.put(Verifications.NO_ADVERTISEMENT_PHOTOS, "You have to select at least one photo");
    }

    /**
     * Returns the map object with all verification labels
     * @return
     */
    public Map<Verifications, String> getVerifications() {
        return verifications;
    }
}
