package ESharing.Client.Views.SignUpView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserAccount.UserAccountModel;
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

    private UserAccountModel userAccountModel;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public SignUpViewModel() {

        this.userAccountModel = ModelFactory.getModelFactory().getUserAccountModel();

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

    /**
     * Creates new user using values from a gui and sends it to the model layer
     */
    public void createNewUser()
    {
        Address userAddress = new Address(streetProperty.get(), numberProperty.get(), cityProperty.get(), postalCodeProperty.get());
        User newUser = new User(usernameProperty.get(), passwordProperty.get(), phoneProperty.get(), userAddress);

        boolean validateCreation = userAccountModel.createNewUser(newUser);

        if (validateCreation) {
            System.out.println("New user created");
        }
        else {
            System.out.println("Problem with sign up");
        }
    }

    /**
     * Verifies all fields in the personal pane
     * @return the boolean value with the result of the verification
     */
    public boolean personalFieldsVerification()
    {
        boolean verification = true;
        if(usernameProperty.get() == null || usernameProperty.get().equals("")) {
        warningLabel.set("The username filed can not be empty");
        verification = false;
        }
        else if(passwordProperty.get() == null || passwordProperty.get().equals("")) {
            warningLabel.set("The password field can not be empty");
            verification = false;
        }
        else if(passwordProperty.get().length() < 8){
            warningLabel.set("The password should have more than 7 letters");
            verification = false;
        }
        else if(confirmPasswordProperty.get() == null || confirmPasswordProperty.get().equals("")) {
            warningLabel.set("Please confirm your password");
            verification = false;
        }
        else if(phoneProperty.get() == null || phoneProperty.get().equals("")) {
            warningLabel.set("The phone field can not be empty");
            verification = false;
        }
        else if(phoneProperty.get().length() != 8)
        {
            warningLabel.set("The phone number should have 8 digits");
            verification = false;
        }
        else if(!confirmPasswordProperty.get().equals(passwordProperty.get())) {
            warningLabel.set("Passwords are not the same");
            verification = false;
        }
        return verification;
    }

    /**
     * Verifies all fields in the address pane
     * @return the boolean value with the result of the verification
     */
    public boolean addressFieldsVerification()
    {
        boolean verification = true;
        if(streetProperty.get() == null || streetProperty.get().equals("")){
            warningLabel.set("The street field can not be empty");
            verification = false;
        }
        else if(numberProperty.get() == null || numberProperty.get().equals("")) {
            warningLabel.set("The number field can not be empty");
            verification = false;
        }
        else if(cityProperty.get() == null || cityProperty.get().equals("")) {
            warningLabel.set("The city field can not be empty");
            verification = false;
        }
        else if(postalCodeProperty.get() == null || postalCodeProperty.get().equals("")) {
            warningLabel.set("The postal code field can not be empty");
            verification = false;
        }
        return verification;
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
