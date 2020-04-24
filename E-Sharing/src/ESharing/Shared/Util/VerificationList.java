package ESharing.Shared.Util;

import java.util.HashMap;
import java.util.Map;

public class VerificationList {
    private Map<Verifications, String> verifications;
    private static VerificationList verificationList;

    private VerificationList()
    {
        this.verifications = new HashMap<>();
        addAllVerifications();
    }

    public static VerificationList getVerificationList() {
        if(verificationList == null)
            verificationList = new VerificationList();
        return verificationList;
    }

    private void addAllVerifications()
    {
        verifications.put(Verifications.INVALID_USERNAME, "Invalid username");
        verifications.put(Verifications.INVALID_PASSWORD, "Invalid password");
        verifications.put(Verifications.INVALID_NUMBER, "Invalid number");
        verifications.put(Verifications.INVALID_PHONE_NUMBER, "Invalid phone number");
        verifications.put(Verifications.INVALID_CITY, "Invalid phone city");
        verifications.put(Verifications.INVALID_POSTAL_CODE, "Invalid postal code");
        verifications.put(Verifications.NOT_THE_SAME_PASSWORDS, "Passwords are not the same");
        verifications.put(Verifications.USER_NOT_EXIST, "The user does not exist");
        verifications.put(Verifications.INVALID_STREET, "Invalid street");
        verifications.put(Verifications.DATABASE_CONNECTION_ERROR, "Database connection error");
    }

    public Map<Verifications, String> getVerifications() {
        return verifications;
    }
}
