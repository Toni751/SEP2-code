package ESharing.Client.Views.UserInfoSettingView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.beans.PropertyChangeSupport;

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
    private StringProperty warningLabel;

    private PropertyChangeSupport support;
    private UserActionsModel userActionsModel;
    private LoggedUser loggedUser;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public UserInfoSettingViewModel() {
        usernameProperty = new SimpleStringProperty();
        phoneProperty = new SimpleStringProperty();
        oldPasswordProperty = new SimpleStringProperty();
        newPasswordProperty = new SimpleStringProperty();
        warningLabel = new SimpleStringProperty();

        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        loggedUser = LoggedUser.getLoggedUser();
        support = new PropertyChangeSupport(this);
    }

    /**
     * Sends a request for verify the information inserted by user
     * @return the result of the verification
     */
    public boolean modifyUserRequest()
    {
        if((oldPasswordProperty.get() == null || oldPasswordProperty.get().equals("")))
        {
            return checkAndUpdateInformation("withoutPassword");
        }
        else {

        String passwordVerification = userActionsModel.verifyChangePassword(oldPasswordProperty.get(), newPasswordProperty.get());
        if(passwordVerification == null)
        {
            return checkAndUpdateInformation("withPassword");
        }
        else
        {
            warningLabel.set(passwordVerification);
            return false;
        }
        }
    }

    /**
     * Chooses the type of changing action and sends a request
     * @param type type of the changing action
     * @return the result of the verifiaciotn
     */
    public boolean checkAndUpdateInformation(String type)
    {
        User updateUser;
        String verification = userActionsModel.verifyUserInfo(usernameProperty.get(), loggedUser.getUser().getPassword(), loggedUser.getUser().getPassword(), getPhoneProperty().get());
        if(verification == null) {
            if(type.equalsIgnoreCase("withPassword")) {
                System.out.println("Changing with password");
                updateUser = new User(getUsernameProperty().get(), newPasswordProperty.get(), phoneProperty.get(), loggedUser.getUser().getAddress());
            }
            else {
                System.out.println("Changing without password");
                updateUser = new User(getUsernameProperty().get(), loggedUser.getUser().getPassword(), phoneProperty.get(), loggedUser.getUser().getAddress());
            }
            updateUser.setUser_id(loggedUser.getUser().getUser_id());
            if(userActionsModel.modifyUserInformation(updateUser) == null) {
                warningLabel.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
                return true;
            }
            else{
                warningLabel.set(userActionsModel.modifyUserInformation(updateUser));
                return false;
            }
        }
        else{
            warningLabel.set(verification);
            return false;
        }
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

}
