package ESharing.Client.Model.VerificationModel;

import ESharing.Shared.TransferedObject.Address;

/**
 * The interface used to connect a view model layer with a model layer for all verification features.
 * @version 1.0
 * @author Group1
 */
public interface VerificationModel {
    /**
     * Verifies user information and returns a result as a string object
     * @param username the username value from the text field
     * @param phoneNumber the phone number value from the text field
     * @return the result of the verification as a string object
     */
    String verifyUserInfo(String username, String phoneNumber);

    /**
     * Verifies address information and returns a result as a string object
     * @param address the address object for verification
     * @return the result of the verification as a string object
     */
    String verifyAddress(Address address);

    /**
     * Verifies change password action
     * @param oldPassword the old user password
     * @param newPassword the new password
     * @param confirmPassword the second string with a new password
     * @return the result of the verification as a string object
     */
    String verifyChangePassword(String oldPassword, String newPassword, String confirmPassword);

    /**
     * Verifies new password
     * @param password the new password
     * @param confirmPassword the confirmation of the new password
     * @return the result of the verification as a string object
     */
    String verifyPassword(String password, String confirmPassword);

    /**
     * Verifies message which user want to send
     * @param message the content of the message
     * @return the result of the verification process
     */
    boolean verifyMessage(String message);

    /**
     * Verifies values from the username and the password text fields
     * @param username the value from the username text field
     * @param password the value from the password textfield
     * @return the result of the verification as a string object
     */
    String verifyUsernameAndPassword(String username, String password);

    /**
     * Verifies values from creation a new advertisement
     * @param title the given title of the advertisement
     * @param type the given type of the vehicle
     * @param description the given description of the advertisement
     * @param price the price per day of the advertisement
     * @return the result of the verification as a string object
     */
    String verifyAdvertisement(String title, String type, String description, String price, int images);


}
