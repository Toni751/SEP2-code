package ESharing.Client.Views.EditAdminView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.GeneralFunctions;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EditAdminViewModel {

    private StringProperty usernameProperty;
    private StringProperty oldPasswordProperty;
    private StringProperty newPasswordProperty;
    private StringProperty confirmPassword;
    private StringProperty phoneProperty;
    private StringProperty warningProperty;

    private UserActionsModel userActionsModel;
    private VerificationModel verificationModel;


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
    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    public boolean changePassword()
    {
        User updateAdmin = LoggedUser.getLoggedUser().getUser();
        String verification = verificationModel.verifyChangePassword(oldPasswordProperty.get(), newPasswordProperty.get(), confirmPassword.get());
        if(verification == null)
        {
            updateAdmin.setPassword(newPasswordProperty.get());
            if(userActionsModel.modifyUserInformation(updateAdmin)) {
                warningProperty.setValue(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
                return true;
            }
        }
        else {
            warningProperty.setValue(verification);
            return false;
        }
        return false;
    }

    public boolean changeAdminInfo()
    {
        User updatedAdmin = LoggedUser.getLoggedUser().getUser();
        String verification = verificationModel.verifyUserInfo(usernameProperty.get(), phoneProperty.get());
        updatedAdmin.setUsername(usernameProperty.get());
        updatedAdmin.setPhoneNumber(phoneProperty.get());
        return GeneralFunctions.sendEditRequest(updatedAdmin, verification, warningProperty);
    }

    public void loadDefaultValues() {
        usernameProperty.set(LoggedUser.getLoggedUser().getUser().getUsername());
        phoneProperty.set(LoggedUser.getLoggedUser().getUser().getPhoneNumber());
    }

    public StringProperty getPhoneProperty() {
        return phoneProperty;
    }

    public StringProperty getNewPasswordProperty() {
        return newPasswordProperty;
    }

    public StringProperty getOldPasswordProperty() {
        return oldPasswordProperty;
    }

    public StringProperty getConfirmPassword() {
        return confirmPassword;
    }

    public StringProperty getWarningProperty() {
        return warningProperty;
    }

}
