package ESharing.Client.Views.AdminEditUserView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
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
        User updatedUser = selectedUser;
        Address updatedAddress = new Address(streetProperty.get(), numberProperty.get(), cityProperty.get(), postalCodeProperty.get());
        String addressVerification = verificationModel.verifyAddress(updatedAddress);
        String infoVerification = verificationModel.verifyUserInfo(usernameProperty.get(), phoneNumberProperty.get());
        if(addressVerification == null && infoVerification == null) {
            updatedUser.setAddress(updatedAddress);
            updatedUser.setUsername(usernameProperty.get());
            updatedUser.setPhoneNumber(phoneNumberProperty.get());
            if(userActionsModel.modifyUserInformation(updatedUser)){
                warningLabel.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
                return true;
            }
        }
        else if(addressVerification != null) {
            warningLabel.set(addressVerification);
            return false;
        }
        else {
            warningLabel.set(infoVerification);
            return false;
        }
        return false;
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
