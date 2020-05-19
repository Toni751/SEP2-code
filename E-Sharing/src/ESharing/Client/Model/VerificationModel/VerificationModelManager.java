package ESharing.Client.Model.VerificationModel;

import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;

public class VerificationModelManager implements VerificationModel{

    @Override
    public String verifyAddress(Address address)
    {
        if(address.getStreet() == null || address.getStreet().equals(""))
            return VerificationList.getVerificationList().getVerifications().get(Verifications.INVALID_STREET);
        else if(address.getNumber() == null || address.getNumber().equals(""))
            return VerificationList.getVerificationList().getVerifications().get(Verifications.INVALID_NUMBER);
//        else if(address.getCity() == null || address.getCity().equals(""))
//            return VerificationList.getVerificationList().getVerifications().get(Verifications.INVALID_CITY);
//        else if(address.getPostcode() == null || address.getPostcode().equals(""))
//            return VerificationList.getVerificationList().getVerifications().get(Verifications.INVALID_POSTAL_CODE);
        else
            return null;
    }

    @Override
    public String verifyChangePassword(String oldPassword, String newPassword, String confirmPassword) {
        if(oldPassword == null || oldPassword.equals(""))
            return VerificationList.getVerificationList().getVerifications().get(Verifications.INVALID_PASSWORD);
        if(verifyPassword(newPassword, confirmPassword) != null)
            return verifyPassword(newPassword, confirmPassword);
        if(!oldPassword.equals(LoggedUser.getLoggedUser().getUser().getPassword()))
            return VerificationList.getVerificationList().getVerifications().get(Verifications.NOT_THE_SAME_PASSWORDS);
        return null;
    }

    @Override
    public String verifyPassword(String password, String confirmPassword) {
        if(password == null || password.equals(""))
            return VerificationList.getVerificationList().getVerifications().get(Verifications.INVALID_PASSWORD);
        if(confirmPassword == null || !confirmPassword.equals(password))
            return VerificationList.getVerificationList().getVerifications().get(Verifications.NOT_THE_SAME_PASSWORDS);
        return null;
    }

    @Override
    public boolean verifyMessage(String message) {
        if(message == null || message.equals(""))
            return false;
        return true;
    }

    @Override
    public String verifyUserInfo(String username,  String phoneNumber)
    {
        if(username == null || username.equals(""))
            return VerificationList.getVerificationList().getVerifications().get(Verifications.INVALID_USERNAME);
        else if(phoneNumber == null || phoneNumber.equals(""))
            return VerificationList.getVerificationList().getVerifications().get(Verifications.INVALID_PHONE_NUMBER);
        else
            return null;
    }

    @Override
    public String verifyUsernameAndPassword(String username, String password)
    {
        if(username == null || username.equals(""))
            return VerificationList.getVerificationList().getVerifications().get(Verifications.INVALID_USERNAME);
        else if(password == null || password.equals(""))
            return VerificationList.getVerificationList().getVerifications().get(Verifications.INVALID_PASSWORD);
        else
            return null;
    }

    @Override
    public String verifyAdvertisement(String title, String type, String description, String price, int images) {
        if(title == null || title.equals(""))
            return VerificationList.getVerificationList().getVerifications().get((Verifications.INVALID_TITLE));
        if(description == null || description.equals(""))
            return VerificationList.getVerificationList().getVerifications().get((Verifications.INVALID_DESCRIPTION));
        if(type == null || type.equals(""))
            return VerificationList.getVerificationList().getVerifications().get((Verifications.INVALID_VEHICLE_TYPE));
        if(price == null || price.equals(""))
            return VerificationList.getVerificationList().getVerifications().get((Verifications.INVALID_PRICE));
        if(images == 0)
            return VerificationList.getVerificationList().getVerifications().get(Verifications.NO_ADVERTISEMENT_PHOTOS);
        try { Double.parseDouble(price); } catch (NumberFormatException e) {
            return VerificationList.getVerificationList().getVerifications().get(Verifications.INVALID_PRICE);
        }
        return null;
    }
}
