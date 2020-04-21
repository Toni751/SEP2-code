package ESharing.Client.Views.UserInfoSettingView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserAccount.LoggedUser;
import ESharing.Client.Model.UserAccount.UserSettingModel;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.User;
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
    private UserSettingModel settingModel;
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

        settingModel = ModelFactory.getModelFactory().getUserSettingModel();
        loggedUser = LoggedUser.getLoggedUser();
        support = new PropertyChangeSupport(this);
    }

    /**
     * Verifies all values inserted by the user. If values are incorrect sets the warning
     * otherwise sends User object with new values to the model layer.
     * @return the verification result
     */
    public boolean verifyNewValues() {
        boolean verification = true;
        boolean connection;
        int minPasswordLength = 8;
        int phoneNumberSize = 8;
        int maxUsernameLength = 20;

        User updatedUser = new User(loggedUser.getUsername(), loggedUser.getPassword(), loggedUser.getPhoneNumber(), loggedUser.getAddress());
        if ((newPasswordProperty.getValue() == null || newPasswordProperty.getValue().equals("")) && (oldPasswordProperty.getValue() == null || oldPasswordProperty.getValue().equals(""))) {
            if (usernameProperty.getValue().equals(loggedUser.getUsername())) {
                verification = false;
            } else if (usernameProperty.getValue().equals("") || usernameProperty.getValue().length() > maxUsernameLength) {
                warningLabel.setValue("A username must has 1 to 19 letters");
                verification = false;
            } else {
                updatedUser.setUsername(usernameProperty.getValue());
            }

            if (phoneProperty.getValue().equals(loggedUser.getPhoneNumber())) {
                verification = false;
            } else if (phoneProperty.getValue().equals("") || phoneProperty.getValue().length() != phoneNumberSize) {
                warningLabel.setValue("A phone number should has " + phoneNumberSize + " letters");
            } else {
                updatedUser.setPhoneNumber(phoneProperty.getValue());
            }
        } else {
            if (!oldPasswordProperty.getValue().equals(loggedUser.getPassword())) {
                warningLabel.setValue("Old password is incorrect!");
                verification = false;
            } else if (newPasswordProperty.getValue().equals("") || newPasswordProperty.getValue().length() < minPasswordLength) {
                warningLabel.setValue("A password must has more than 7 letters");
                verification = false;
            } else {
                updatedUser.setPassword(newPasswordProperty.getValue());
            }
        }
        if (!updatedUser.equals(loggedUser)) {
            updatedUser.setUser_id(loggedUser.getUser_id());
            connection = settingModel.modifyUserInformation(updatedUser);
            if(!connection)
            {
                warningLabel.setValue("Database connection error");
                verification = false;
            }
            else
            {
                warningLabel.setValue("Information has successfully changed");
                loggedUser.loginUser(updatedUser);
                verification = true;
            }
        }
        return verification;
    }

    /**
     * Fills text fields with the logged user values
     */
    public void loadUserInfo() {
        usernameProperty.setValue(loggedUser.getUsername());
        phoneProperty.setValue(loggedUser.getPhoneNumber());
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
