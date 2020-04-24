package ESharing.Client.Views.UserAddressSettingView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
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

    private UserActionsModel userActionsModel;
    private LoggedUser loggedUser;

    public UserAddressSettingViewModel()
    {
        loggedUser = LoggedUser.getLoggedUser();
        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();

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
        usernameProperty.set(loggedUser.getUser().getUsername());
        phoneNumberProperty.set(loggedUser.getUser().getPhoneNumber());

        numberProperty.set(loggedUser.getUser().getAddress().getNumber());
        streetProperty.set(loggedUser.getUser().getAddress().getStreet());
        cityProperty.set(loggedUser.getUser().getAddress().getCity());
        postalCodeProperty.set(loggedUser.getUser().getAddress().getPostcode());
    }

    public boolean modifyAddressRequest()
    {
        Address updatedAddress = new Address(streetProperty.get(), numberProperty.get(), cityProperty.get(), postalCodeProperty.get());
        if(!updatedAddress.equals(loggedUser.getUser().getAddress()))
        {
            System.out.println("Address is different");
            User updatedUser = new User(loggedUser.getUser().getUsername(), loggedUser.getUser().getPassword(), loggedUser.getUser().getPhoneNumber(), updatedAddress);
            if(userActionsModel.verifyAddress(updatedAddress) == null)
            {
                String databaseVerification = userActionsModel.modifyUserInformation(updatedUser);
                if(databaseVerification == null)
                    return true;
                else{
                 warningProperty.set(databaseVerification);
                 return false;
                }
            }
        }
        return true;
    }

//    public boolean verifyNewAddressValues()
//    {
//        boolean verification = true;
//        boolean connection = true;
//        Address updatedAddress = new Address(loggedUser.getUser().getAddress().getStreet(), loggedUser.getUser().getAddress().getNumber(), loggedUser.getUser().getAddress().getCity(), loggedUser.getUser().getAddress().getPostcode());
//        if(!streetProperty.get().equals(loggedUser.getUser().getAddress().getStreet()) || !streetProperty.get().equals(""))
//            updatedAddress.setStreet(streetProperty.get());
//        else{
//            warningProperty.set("Invalid street name");
//            verification = false;
//        }
//        if(!numberProperty.get().equals(loggedUser.getUser().getAddress().getNumber()) || !numberProperty.get().equals(""))
//            updatedAddress.setNumber(numberProperty.get());
//        else {
//            warningProperty.set("Invalid number");
//            verification = false;
//        }
//        if(!cityProperty.get().equals(loggedUser.getUser().getAddress().getCity()) || !cityProperty.get().equals(""))
//            updatedAddress.setCity(cityProperty.get());
//        else {
//            warningProperty.set("Invalid city name");
//            verification = false;
//        }
//        if(!postalCodeProperty.get().equals(loggedUser.getUser().getAddress().getPostcode()) || !postalCodeProperty.get().equals(""))
//            updatedAddress.setPostcode(postalCodeProperty.get());
//        else {
//            warningProperty.set("Invalid postal code");
//            verification = false;
//        }
//        updatedAddress.setAddress_id(loggedUser.getUser().getAddress().getAddress_id());
//        User updateUser = new User(loggedUser.getUser().getUsername(), loggedUser.getUser().getPassword(), loggedUser.getUser().getPhoneNumber(), updatedAddress);
//        updateUser.setUser_id(loggedUser.getUser().getUser_id());
//        if(verification)
//           connection = settingModel.modifyUserInformation(updateUser);
//            if(!connection){
//                warningProperty.set("Database connection error");
//                verification = false;
//            }
//            else
//            {
//                warningProperty.set("Information has successfully changed");
//                loggedUser.loginUser(updateUser);
//            }
//        return verification;
//    }

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
