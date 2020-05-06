package ESharing.Client.Views.AdminEditUserView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AdminEditUserViewModel {

    private StringProperty usernameProperty;
    private StringProperty phoneNumberProperty;
    private StringProperty numberProperty;
    private StringProperty streetProperty;
    private StringProperty cityProperty;
    private StringProperty warningLabel;
    private StringProperty postalCodeProperty;

    private AdministratorActionsModel administratorModel;
    private VerificationModel verificationModel;
    private UserActionsModel userActionsModel;
    private User selectedUser;


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
    }

    public void loadUserInformation()
    {
        usernameProperty.setValue(selectedUser.getUsername());
        phoneNumberProperty.setValue(selectedUser.getPhoneNumber());
        numberProperty.setValue(selectedUser.getAddress().getNumber());
        cityProperty.setValue(selectedUser.getAddress().getCity());
        streetProperty.setValue(selectedUser.getAddress().getStreet());
        postalCodeProperty.setValue(selectedUser.getAddress().getPostcode());
    }

    public boolean saveChanges() {
        User updatedUser;
        Address updatedAddress = new Address(streetProperty.get(), numberProperty.get(), cityProperty.get(), postalCodeProperty.get());
        String addressResult = verificationModel.verifyAddress(updatedAddress);
        String generalResult = verificationModel.verifyUserInfo(usernameProperty.get(), selectedUser.getPassword(), selectedUser.getPassword(), selectedUser.getPhoneNumber());
        if(generalResult == null && addressResult == null) {
            updatedUser = new User(usernameProperty.get(), selectedUser.getPassword(), phoneNumberProperty.get(), updatedAddress);
            updatedUser.setUser_id(selectedUser.getUser_id());
            updatedUser.setCreation_date(selectedUser.getCreation_date());
            updatedUser.setReportsNumber(selectedUser.getReportsNumber());
            String databaseResult = userActionsModel.modifyUserInformation(updatedUser);
            if(databaseResult == null) {
                warningLabel.setValue(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
                selectedUser = updatedUser;
                return true;
            }
            else {
                warningLabel.setValue(databaseResult);
                return false;
            }
        }
        else if(generalResult != null) {
            warningLabel.setValue(generalResult);
            return false;
        }
        else {
            warningLabel.setValue(addressResult);
            return false;
        }
    }

    public boolean resetPassword()
    {
        String newPassword = administratorModel.resetUserPassword(selectedUser);
        if(newPassword != null) {
            warningLabel.setValue("New password: " + newPassword);
            return true;
        }
        else {
            warningLabel.setValue(VerificationList.getVerificationList().getVerifications().get(Verifications.DATABASE_CONNECTION_ERROR));
            return false;
        }
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public StringProperty getStreetProperty() {
        return streetProperty;
    }

    public StringProperty getPostalCodeProperty() {
        return postalCodeProperty;
    }

    public StringProperty getPhoneNumberProperty() {
        return phoneNumberProperty;
    }

    public StringProperty getCityProperty() {
        return cityProperty;
    }

    public StringProperty getNumberProperty() {
        return numberProperty;
    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    public StringProperty getWarningLabel() {
        return warningLabel;
    }
}
