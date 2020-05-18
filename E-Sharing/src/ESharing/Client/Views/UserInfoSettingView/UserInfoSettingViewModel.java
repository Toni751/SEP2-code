package ESharing.Client.Views.UserInfoSettingView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.GeneralFunctions;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.beans.PropertyChangeSupport;
import java.io.File;

/**
 * The class in a view model layer contains all functions which are used in the signUp view.
 * @version 1.0
 * @author Group1
 */
public class UserInfoSettingViewModel{

    private StringProperty usernameProperty;
    private StringProperty phoneProperty;
    private StringProperty oldPasswordProperty;
    private StringProperty newPasswordProperty;
    private StringProperty confirmPasswordProperty;
    private StringProperty warningLabel;

    private UserActionsModel userActionsModel;
    private VerificationModel verificationModel;
    private LoggedUser loggedUser;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public UserInfoSettingViewModel() {
        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        verificationModel = ModelFactory.getModelFactory().getVerificationModel();
        loggedUser = LoggedUser.getLoggedUser();

        usernameProperty = new SimpleStringProperty();
        phoneProperty = new SimpleStringProperty();
        oldPasswordProperty = new SimpleStringProperty();
        newPasswordProperty = new SimpleStringProperty();
        warningLabel = new SimpleStringProperty();
        confirmPasswordProperty = new SimpleStringProperty();
    }

    /**
     * Verifies all information and sends a request to edit user
     * @return the result of the the verification process
     */
    public boolean checkAndUpdateInformation()
    {
       User updatedUser = LoggedUser.getLoggedUser().getUser();
       String verification = verificationModel.verifyUserInfo(usernameProperty.get(), phoneProperty.get());
       updatedUser.setUsername(usernameProperty.get());
       updatedUser.setPhoneNumber(phoneProperty.get());

       return sendRequestToModel(updatedUser, verification);
    }

    /**
     * Verifies all information and sends a request to edit user
     * @return the result ot the verification process
     */
    public boolean changePassword()
    {
        User updatedUser = LoggedUser.getLoggedUser().getUser();
        System.out.println(LoggedUser.getLoggedUser().getUser().getPassword());
        String verification = verificationModel.verifyChangePassword(oldPasswordProperty.get(), newPasswordProperty.get(),confirmPasswordProperty.get());
        updatedUser.setPassword(newPasswordProperty.get());

        return sendRequestToModel(updatedUser, verification);
    }

    /**
     * Fills text fields with the logged user values
     */
    public void loadUserInfo() {
        usernameProperty.setValue(loggedUser.getUser().getUsername());
        phoneProperty.setValue(loggedUser.getUser().getPhoneNumber());
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the oldPassword text field
     */
    public StringProperty getOldPasswordProperty() {
        return oldPasswordProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the newPassword text field
     */
    public StringProperty getNewPasswordProperty() {
        return newPasswordProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the username text field
     */
    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the phone text field
     */
    public StringProperty getPhoneProperty() {
        return phoneProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the warning notification
     */
    public StringProperty getWarningLabel() {
        return warningLabel;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the confirm password text field
     */
    public StringProperty getConfirmPasswordProperty() {
        return confirmPasswordProperty;
    }

    /**
     * Sends the edit request to the model layer
     * @param updatedUser the updated user object
     * @param verification the result of the verification text fields
     * @return the result of the request
     */
    private boolean sendRequestToModel(User updatedUser, String verification) {
        return GeneralFunctions.sendEditRequest(updatedUser, verification, warningLabel);
    }

    public void changeAvatarRequest(File avatarImage) {
        userActionsModel.changeAvatar(avatarImage);
    }
}
