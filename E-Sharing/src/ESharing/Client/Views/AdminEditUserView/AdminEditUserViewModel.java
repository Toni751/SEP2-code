package ESharing.Client.Views.AdminEditUserView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.*;

/**
 * The class in a view model layer contains all functions which are used in the administrator edit user view.
 * @version 1.0
 * @author Group1
 */
public class AdminEditUserViewModel {

    private StringProperty usernameProperty;
    private StringProperty phoneNumberProperty;
    private StringProperty numberProperty;
    private StringProperty streetProperty;
    private StringProperty cityProperty;
    private StringProperty warningLabel;
    private StringProperty postalCodeProperty;
    private StringProperty warningStyleProperty;
    private BooleanProperty warningVisibleProperty;

    private AdministratorActionsModel administratorModel;
    private VerificationModel verificationModel;
    private UserActionsModel userActionsModel;
    private User selectedUser;

    /**
     * A constructor initializes the model layer for a administrator features and all fields
     */
    public AdminEditUserViewModel()
    {
        administratorModel = ModelFactory.getModelFactory().getAdministratorActionsModel();
        verificationModel = ModelFactory.getModelFactory().getVerificationModel();
        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();

        usernameProperty  = new SimpleStringProperty();
        phoneNumberProperty  = new SimpleStringProperty();
        numberProperty  = new SimpleStringProperty();
        streetProperty  = new SimpleStringProperty();
        cityProperty  = new SimpleStringProperty();
        postalCodeProperty  = new SimpleStringProperty();
        warningLabel  = new SimpleStringProperty();
        warningVisibleProperty = new SimpleBooleanProperty();
        warningStyleProperty = new SimpleStringProperty();
    }

    /**
     * Sets a default view and values
     */
    public void loadDefaultView()
    {
        usernameProperty.setValue(selectedUser.getUsername());
        phoneNumberProperty.setValue(selectedUser.getPhoneNumber());
        numberProperty.setValue(selectedUser.getAddress().getNumber());
        streetProperty.setValue(selectedUser.getAddress().getStreet());

        cityProperty.setValue("Horsens");
        postalCodeProperty.set("8700");
        warningVisibleProperty.setValue(false);
    }

    /**
     * Sends a request with values from the text fields to the verification model:
     * Result approved: Sends a request to edit given user to the model layer, displays message
     * Result not approved: Displays a warning message
     */
    public void saveChanges() {
        User updatedUser = selectedUser;
        Address updatedAddress = new Address(streetProperty.get(), numberProperty.get());
        String addressVerification = verificationModel.verifyAddress(updatedAddress);
        String infoVerification = verificationModel.verifyUserInfo(usernameProperty.get(), phoneNumberProperty.get());
        if(addressVerification == null && infoVerification == null) {
            updatedUser.setAddress(updatedAddress);
            updatedUser.setUsername(usernameProperty.get());
            updatedUser.setPhoneNumber(phoneNumberProperty.get());
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
            if(userActionsModel.modifyUserInformation(updatedUser)){
                warningLabel.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
                warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
            }
        }
        else if(addressVerification != null) {
            warningLabel.set(addressVerification);
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        else {
            warningLabel.set(infoVerification);
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        warningVisibleProperty.setValue(true);
    }

    /**
     * Sends a request to the the model layer for setting a new password for the given user
     */
    public void resetPassword()
    {
        String newPassword = administratorModel.resetUserPassword(selectedUser);

        if(newPassword != null) {
            warningLabel.setValue("New password: " + newPassword);
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
        }
        else {
            warningLabel.setValue(VerificationList.getVerificationList().getVerifications().get(Verifications.DATABASE_CONNECTION_ERROR));
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        warningVisibleProperty.setValue(true);
    }

    /**
     * Sets a user that the administrator selected from the table in a previous view
     * @param selectedUser the selected user
     */
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    /**
     * Sets a visible property of the warning pane to false
     */
    public void hideWarningPane()
    {
        warningVisibleProperty.setValue(false);
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a street text field
     */
    public StringProperty getStreetProperty() {
        return streetProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a postal code text field
     */
    public StringProperty getPostalCodeProperty() {
        return postalCodeProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a phone number text field
     */
    public StringProperty getPhoneNumberProperty() {
        return phoneNumberProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a city text field
     */
    public StringProperty getCityProperty() {
        return cityProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a street number text field
     */
    public StringProperty getNumberProperty() {
        return numberProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a username text field
     */
    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a warning label
     */
    public StringProperty getWarningLabel() {
        return warningLabel;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the visible property of the warning pane
     */
    public BooleanProperty getWarningVisibleProperty() {
        return warningVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the style property of a warning pane
     */
    public StringProperty warningStyleProperty() {
        return warningStyleProperty;
    }
}
