package ESharing.Client.Views.EditAdminView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.GeneralFunctions;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.*;

/**
 * The class in a view model layer contains all functions which are used in the edit administrator view.
 * @version 1.0
 * @author Group1
 */
public class EditAdminViewModel {

    private StringProperty usernameProperty;
    private StringProperty oldPasswordProperty;
    private StringProperty newPasswordProperty;
    private StringProperty confirmPassword;
    private StringProperty phoneProperty;
    private StringProperty warningProperty;
    private StringProperty warningStyleProperty;
    private BooleanProperty warningVisibleProperty;

    private UserActionsModel userActionsModel;
    private VerificationModel verificationModel;

    /**
     * A constructor initializes model layer for a administrator features and all fields
     */
    public EditAdminViewModel()
    {
        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        verificationModel = ModelFactory.getModelFactory().getVerificationModel();

        usernameProperty = new SimpleStringProperty();
        oldPasswordProperty = new SimpleStringProperty();
        newPasswordProperty = new SimpleStringProperty();
        phoneProperty = new SimpleStringProperty();
        confirmPassword = new SimpleStringProperty();
        warningProperty = new SimpleStringProperty();
        warningStyleProperty = new SimpleStringProperty();
        warningVisibleProperty = new SimpleBooleanProperty();
    }

    /**
     * Sends a request to the model layer for changing the password for current logged administrator
     * Sets a property of the warning pane regarding the result
     */
    public void changePassword()
    {
        User updateAdmin = LoggedUser.getLoggedUser().getUser();
        String verification = verificationModel.verifyChangePassword(oldPasswordProperty.get(), newPasswordProperty.get(), confirmPassword.get());
        if(verification == null)
        {
            updateAdmin.setPassword(newPasswordProperty.get());
            if(userActionsModel.modifyUserInformation(updateAdmin)) {
                warningProperty.setValue(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
                warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
            }
        }
        else {
            warningProperty.setValue(verification);
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        warningVisibleProperty.setValue(true);
    }

    /**
     * Sends a request to the model layer for changing the current logged administrator info
     */
    public void changeAdminInfo()
    {
        User updatedAdmin = LoggedUser.getLoggedUser().getUser();
        String verification = verificationModel.verifyUserInfo(usernameProperty.get(), phoneProperty.get());
        updatedAdmin.setUsername(usernameProperty.get());
        updatedAdmin.setPhoneNumber(phoneProperty.get());
        warningProperty.setValue(verification);
        if(GeneralFunctions.sendEditRequest(updatedAdmin, verification, warningProperty))
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
        else
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        warningVisibleProperty.setValue(true);
    }

    /**
     * Sets a default view and values
     */
    public void loadDefaultValues() {
        usernameProperty.set(LoggedUser.getLoggedUser().getUser().getUsername());
        phoneProperty.set(LoggedUser.getLoggedUser().getUser().getPhoneNumber());
        warningVisibleProperty.setValue(false);
    }

    /**
     * Sets the visible property for the warning pane
     */
    public void hideWarningPane() {
        warningVisibleProperty.setValue(false);
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
     * @return the string property of a phone number text field
     */
    public StringProperty getPhoneProperty() {
        return phoneProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a new password text field
     */
    public StringProperty getNewPasswordProperty() {
        return newPasswordProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a old password text field
     */
    public StringProperty getOldPasswordProperty() {
        return oldPasswordProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a confirm password text field
     */
    public StringProperty getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a warning label
     */
    public StringProperty getWarningProperty() {
        return warningProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the visible property of a warning pane
     */
    public BooleanProperty getWarningVisibleProperty() {
        return warningVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the style property of a warning pane
     */
    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }

}
