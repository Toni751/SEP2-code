package ESharing.Client.Views.SignUpView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.PropertyChangeSubject;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

/**
 * The class in a view model layer contains all functions which are used in the signUp view.
 * @version 1.0
 * @author Group1
 */
public class SignUpViewModel implements PropertyChangeSubject {

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
    private VerificationModel verificationModel;

    private PropertyChangeSupport support;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public SignUpViewModel() {

        this.userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        this.verificationModel = ModelFactory.getModelFactory().getVerificationModel();

        support = new PropertyChangeSupport(this);

        usernameProperty = new SimpleStringProperty();
        passwordProperty = new SimpleStringProperty();
        confirmPasswordProperty = new SimpleStringProperty();
        phoneProperty= new SimpleStringProperty();
        warningLabel = new SimpleStringProperty();

        streetProperty = new SimpleStringProperty();
        numberProperty = new SimpleStringProperty();
        cityProperty = new SimpleStringProperty();
        postalCodeProperty = new SimpleStringProperty();

        userActionsModel.addPropertyChangeListener(Events.CONNECTION_FAILED.toString(), this::connectionFailed);
    }

    private void connectionFailed(PropertyChangeEvent propertyChangeEvent) {
        warningLabel.setValue(VerificationList.getVerificationList().getVerifications().get(Verifications.SERVER_CONNECTION_ERROR));
        support.firePropertyChange(propertyChangeEvent);
    }

    /**
     * Sends a request to create a new user and waits for result
     * @return the result of new user creation request
     */
    public boolean createNewUser()
        {
        String verification = userActionsModel.createNewUser(new User(usernameProperty.get(), passwordProperty.get(), phoneProperty.get(), new Address(streetProperty.get(), numberProperty.get(), cityProperty.get(), postalCodeProperty.get())));
        if(verification == null) {
            warningLabel.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            return true;
        }
        else {
            warningLabel.set(verification);
            return false;
        }
    }

    /**
     * Sends a request with verification user information
     * @return the result of the verification
     */
    public boolean signUpPersonalRequest()
    {
        String verificationUser = verificationModel.verifyUserInfo(usernameProperty.get(), phoneProperty.get());
        String verificationPassword = verificationModel.verifyPassword(passwordProperty.get(), confirmPasswordProperty.get());
        if(verificationUser == null && verificationPassword == null)
            return true;
        else if(verificationUser != null) {
            warningLabel.set(verificationUser);
            return false;
        }
        else {
            warningLabel.set(verificationPassword);
            return false;
        }
    }

    /**
     * Sends a request with verification address information
     * @return the result of the verification
     */
    public boolean signUpAddressRequest()
    {
        Address address = new Address(streetProperty.get(), numberProperty.get(), cityProperty.get(), postalCodeProperty.get());
        String verification = verificationModel.verifyAddress(address);
        if(verification == null) {
           return true;
        }
        else {
            warningLabel.set(verification);
            return false;
        }
    }


    /**
     * Clears all fields
     */
    public void resetView()
    {
        usernameProperty.setValue("");
        passwordProperty.setValue("");
        confirmPasswordProperty.setValue("");
        phoneProperty.setValue("");
        streetProperty.setValue("");
        numberProperty.setValue("");
        cityProperty.setValue("");
        postalCodeProperty.setValue("");
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

    @Override
    public void addPropertyChangeListener(String eventName, PropertyChangeListener listener)
    {
        if ("".equals(eventName) || eventName == null)
            addPropertyChangeListener(listener);
        else
            support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String eventName, PropertyChangeListener listener)
    {
        if ("".equals(eventName) || eventName == null)
            removePropertyChangeListener(listener);
        else
            support.removePropertyChangeListener(eventName, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
