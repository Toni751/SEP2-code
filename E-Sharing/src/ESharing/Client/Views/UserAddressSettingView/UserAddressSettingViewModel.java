package ESharing.Client.Views.UserAddressSettingView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserAccount.LoggedUser;
import ESharing.Client.Model.UserAccount.UserSettingModel;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserAddressSettingViewModel {

    private StringProperty usernameProperty;
    private StringProperty phoneNumberProperty;
    private StringProperty streetProperty;
    private StringProperty numberProperty;
    private StringProperty cityProperty;
    private StringProperty postalCodeProperty;
    private StringProperty warningProperty;

    private UserSettingModel settingModel;
    private LoggedUser loggedUser;

    public UserAddressSettingViewModel()
    {
        loggedUser = LoggedUser.getLoggedUser();
        settingModel = ModelFactory.getModelFactory().getUserSettingModel();

        warningProperty = new SimpleStringProperty();
        cityProperty = new SimpleStringProperty();
        postalCodeProperty = new SimpleStringProperty();
        numberProperty = new SimpleStringProperty();
        streetProperty = new SimpleStringProperty();
        usernameProperty = new SimpleStringProperty();
        phoneNumberProperty = new SimpleStringProperty();
    }

    public void loadDefaultValues()
    {
        usernameProperty.set(loggedUser.getUsername());
        phoneNumberProperty.set(loggedUser.getPhoneNumber());

        numberProperty.set(loggedUser.getAddress().getNumber());
        streetProperty.set(loggedUser.getAddress().getStreet());
        cityProperty.set(loggedUser.getAddress().getCity());
        postalCodeProperty.set(loggedUser.getAddress().getPostcode());
    }

    public boolean verifyNewAddressValues()
    {
        boolean verification = true;
        boolean connection = true;
        Address updatedAddress = new Address(loggedUser.getAddress().getStreet(), loggedUser.getAddress().getNumber(), loggedUser.getAddress().getCity(), loggedUser.getAddress().getPostcode());
        if(!streetProperty.get().equals(loggedUser.getAddress().getStreet()) || !streetProperty.get().equals(""))
            updatedAddress.setStreet(streetProperty.get());
        else{
            warningProperty.set("Invalid street name");
            verification = false;
        }
        if(!numberProperty.get().equals(loggedUser.getAddress().getNumber()) || !numberProperty.get().equals(""))
            updatedAddress.setNumber(numberProperty.get());
        else {
            warningProperty.set("Invalid number");
            verification = false;
        }
        if(!cityProperty.get().equals(loggedUser.getAddress().getCity()) || !cityProperty.get().equals(""))
            updatedAddress.setCity(cityProperty.get());
        else {
            warningProperty.set("Invalid city name");
            verification = false;
        }
        if(!postalCodeProperty.get().equals(loggedUser.getAddress().getPostcode()) || !postalCodeProperty.get().equals(""))
            updatedAddress.setPostcode(postalCodeProperty.get());
        else {
            warningProperty.set("Invalid postal code");
            verification = false;
        }
        updatedAddress.setAddress_id(loggedUser.getAddress().getAddress_id());
        User updateUser = new User(loggedUser.getUsername(), loggedUser.getPassword(), loggedUser.getPhoneNumber(), updatedAddress);
        updateUser.setUser_id(loggedUser.getUser_id());
        if(verification)
           connection = settingModel.modifyUserInformation(updateUser);
            if(!connection){
                warningProperty.set("Database connection error");
                verification = false;
            }
            else
            {
                warningProperty.set("Information has successfully changed");
                loggedUser.loginUser(updateUser);
                verification = true;
            }
        return verification;
    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    public StringProperty getCityProperty() {
        return cityProperty;
    }

    public StringProperty getNumberProperty() {
        return numberProperty;
    }

    public StringProperty getPhoneNumberProperty() {
        return phoneNumberProperty;
    }

    public StringProperty getPostalCodeProperty() {
        return postalCodeProperty;
    }

    public StringProperty getStreetProperty() {
        return streetProperty;
    }

    public StringProperty getWarningProperty() {
        return warningProperty;
    }
}
