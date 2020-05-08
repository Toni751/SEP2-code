package ESharing.Client.Views.UserAddressSettingView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The class in a view model layer contains all functions which are used in the UserAddressSetting view.
 * @version 1.0
 * @author Group1
 */
public class UserAddressSettingViewModel {

    private StringProperty usernameProperty;
    private StringProperty phoneNumberProperty;
    private StringProperty streetProperty;
    private StringProperty numberProperty;
    private StringProperty cityProperty;
    private StringProperty postalCodeProperty;
    private StringProperty warningProperty;

    private UserActionsModel userActionsModel;
    private VerificationModel verificationModel;
    private LoggedUser loggedUser;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public UserAddressSettingViewModel() {
        loggedUser = LoggedUser.getLoggedUser();
        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        verificationModel = ModelFactory.getModelFactory().getVerificationModel();

        warningProperty = new SimpleStringProperty();
        cityProperty = new SimpleStringProperty();
        postalCodeProperty = new SimpleStringProperty();
        numberProperty = new SimpleStringProperty();
        streetProperty = new SimpleStringProperty();
        usernameProperty = new SimpleStringProperty();
        phoneNumberProperty = new SimpleStringProperty();
    }

    /**
     * Fills all text fields with a current logged user information
     */
    public void loadDefaultValues() {
        usernameProperty.set(loggedUser.getUser().getUsername());
        phoneNumberProperty.set(loggedUser.getUser().getPhoneNumber());

        numberProperty.set(loggedUser.getUser().getAddress().getNumber());
        streetProperty.set(loggedUser.getUser().getAddress().getStreet());
        cityProperty.set(loggedUser.getUser().getAddress().getCity());
        postalCodeProperty.set(loggedUser.getUser().getAddress().getPostcode());
    }

    /**
     * Calls the modify function in model, waits for results and sets the information label
     * @return the verification result
     */
    public boolean modifyAddressRequest() {

        User updatedUser = LoggedUser.getLoggedUser().getUser();
        Address updatedAddress = new Address(streetProperty.get(), numberProperty.get(), cityProperty.get(), postalCodeProperty.get());
        String addressVerification = verificationModel.verifyAddress(updatedAddress);
        if(addressVerification == null)
        {
            updatedUser.setAddress(updatedAddress);
            if(userActionsModel.modifyUserInformation(updatedUser))
            {
                warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
                return true;
            }
        }
        else{
            warningProperty.set(addressVerification);
            return false;
        }
        return false;
//        Address updatedAddress = new Address(streetProperty.get(), numberProperty.get(), cityProperty.get(), postalCodeProperty.get());
//        if (!updatedAddress.equals(loggedUser.getUser().getAddress())) {
//            User updatedUser = new User(loggedUser.getUser().getUsername(), loggedUser.getUser().getPassword(), loggedUser.getUser().getPhoneNumber(), updatedAddress);
//            updatedUser.setUser_id(loggedUser.getUser().getUser_id());
//            if (verificationModel.verifyAddress(updatedAddress) == null) {
//                String databaseVerification = userActionsModel.modifyUserInformation(updatedUser);
//                if (databaseVerification == null) {
//                    warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
//                    return true;
//                } else {
//                    warningProperty.set(databaseVerification);
//                    return false;
//                }
//            } else {
//                warningProperty.set(verificationModel.verifyAddress(updatedAddress));
//                return false;
//            }
//        }
//        return true;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the username label
     */
    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the city text field
     */
    public StringProperty getCityProperty() {
        return cityProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the number text field
     */
    public StringProperty getNumberProperty() {
        return numberProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the phone number label
     */
    public StringProperty getPhoneNumberProperty() {
        return phoneNumberProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the postal code text field
     */
    public StringProperty getPostalCodeProperty() {
        return postalCodeProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the street text field
     */
    public StringProperty getStreetProperty() {
        return streetProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the warning label
     */
    public StringProperty getWarningProperty() {
        return warningProperty;
    }
}
