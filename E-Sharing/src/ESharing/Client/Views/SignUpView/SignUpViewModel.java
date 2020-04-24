package ESharing.Client.Views.SignUpView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The class in a view model layer contains all functions which are used in the signUp view.
 * @version 1.0
 * @author Group1
 */
public class SignUpViewModel {

    private StringProperty usernameProperty;
    private StringProperty passwordProperty;
    private StringProperty confirmPasswordProperty;
    private StringProperty phoneProperty;
    private StringProperty warningLabel;
    private StringProperty streetProperty;
    private StringProperty numberProperty;
    private StringProperty cityProperty;
    private StringProperty postalCodeProperty;

    private UserActionsModel userActionsModel;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public SignUpViewModel() {

        this.userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();

        usernameProperty = new SimpleStringProperty();
        passwordProperty = new SimpleStringProperty();
        confirmPasswordProperty = new SimpleStringProperty();
        phoneProperty= new SimpleStringProperty();
        warningLabel = new SimpleStringProperty();

        streetProperty = new SimpleStringProperty();
        numberProperty = new SimpleStringProperty();
        cityProperty = new SimpleStringProperty();
        postalCodeProperty = new SimpleStringProperty();
    }

//    /**
//     * Creates new user using values from a gui and sends it to the model layer
//     */
    public boolean createNewUser()
        {
        String verification = userActionsModel.createNewUser(new User(usernameProperty.get(), passwordProperty.get(), phoneProperty.get(), new Address(streetProperty.get(), numberProperty.get(), cityProperty.get(), postalCodeProperty.get())));
        if(verification == null)
            return true;
        else {
            warningLabel.set(verification);
            return false;
        }
//        Address userAddress = new Address(streetProperty.get(), numberProperty.get(), cityProperty.get(), postalCodeProperty.get());
//        User newUser = new User(usernameProperty.get(), passwordProperty.get(), phoneProperty.get(), userAddress);
//
//        boolean validateCreation = userActionsModel.createNewUser(newUser);
//
//        if (validateCreation) {
//            System.out.println("New user created");
//        }
//        else {
//            System.out.println("Problem with sign up");
//        }
    }

    public boolean signUpPersonalRequest()
    {
        String verification = userActionsModel.verifyUserInfo(usernameProperty.get(), passwordProperty.get(), confirmPasswordProperty.get(), phoneProperty.get());
        if(verification == null)
            return true;
        else {
            warningLabel.set(verification);
            return false;
        }
    }

    public boolean signUpAddressRequest()
    {
        Address address = new Address(streetProperty.get(), numberProperty.get(), cityProperty.get(), postalCodeProperty.get());
        String verification = userActionsModel.verifyAddress(address);
        if(verification == null) {
           return true;
        }
        else {
            warningLabel.set(verification);
            return false;
        }
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
     * @return the value used in the password text field
     */
    public StringProperty getPasswordProperty() {
        return passwordProperty;
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
     * @return the value used in the confirm password text field
     */
    public StringProperty getConfirmPasswordProperty() {
        return confirmPasswordProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the city text field
     */
    public StringProperty getCityProperty() {
        return cityProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the postal code text field
     */
    public StringProperty getPostalCodeProperty() {
        return postalCodeProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the street number text field
     */
    public StringProperty getNumberProperty() {
        return numberProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the street text field
     */
    public StringProperty getStreetProperty() {
        return streetProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the warning notification
     */
    public StringProperty getWarningLabel() {
        return warningLabel;
    }
}
