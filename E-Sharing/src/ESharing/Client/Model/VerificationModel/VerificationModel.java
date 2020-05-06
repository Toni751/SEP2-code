package ESharing.Client.Model.VerificationModel;

import ESharing.Shared.TransferedObject.Address;

public interface VerificationModel {
    /**
     * Verifies user information and returns a result as a string object
     * @param username the username value from the text field
     * @param password the password value from the text field
     * @param passwordAgain the password confirmation value from the text field
     * @param phoneNumber the phone number value from the text field
     * @return the result of the verification as a string object
     */
    String verifyUserInfo(String username, String password, String passwordAgain, String phoneNumber);

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
     * @return the result of the verification as a string object
     */
    String verifyChangePassword(String oldPassword, String newPassword);
}
