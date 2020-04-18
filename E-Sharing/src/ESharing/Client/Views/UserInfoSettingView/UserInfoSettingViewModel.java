package ESharing.Client.Views.UserInfoSettingView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserAccount.UserSettingModel;
import ESharing.Shared.TransferedObject.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserInfoSettingViewModel {

    private StringProperty usernameProperty;
    private StringProperty phoneProperty;
    private StringProperty oldPasswordProperty;
    private StringProperty newPasswordProperty;
    private StringProperty warningLabel;

    private UserSettingModel settingModel;
    private User loggedUser;
    private final int minPasswordLength = 8;
    private final int maxUsernameLength = 20;
    private final int phoneNumberSize = 8;

    public UserInfoSettingViewModel() {
        usernameProperty = new SimpleStringProperty();
        phoneProperty = new SimpleStringProperty();
        oldPasswordProperty = new SimpleStringProperty();
        newPasswordProperty = new SimpleStringProperty();
        warningLabel = new SimpleStringProperty();

        settingModel = ModelFactory.getModelFactory().getUserSettingModel();
    }

    public boolean verifyNewValues() {
        boolean verification = true;
        boolean connection;
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
        System.out.println(loggedUser.getUsername());
        if (!updatedUser.equals(loggedUser)) {
            updatedUser.setUser_id(loggedUser.getUser_id());
            connection = settingModel.modifyGeneralInformation(updatedUser);
            if(!connection)
            {
                warningLabel.setValue("Database connection error");
                verification = false;
            }
            else
            {
                warningLabel.setValue("Information has successfully changed");
                loggedUser.updateInformation(updatedUser);
            }
        }
        return verification;
    }


    public void loadUserInfo() {
        usernameProperty.setValue(loggedUser.getUsername());
        phoneProperty.setValue(loggedUser.getPhoneNumber());
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public StringProperty getOldPasswordProperty() {
        return oldPasswordProperty;
    }

    public StringProperty getNewPasswordProperty() {
        return newPasswordProperty;
    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    public StringProperty getPhoneProperty() {
        return phoneProperty;
    }

    public StringProperty getWarningLabel() {
        return warningLabel;
    }
}
