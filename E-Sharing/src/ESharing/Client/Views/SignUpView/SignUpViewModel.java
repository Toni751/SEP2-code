package ESharing.Client.Views.SignUpView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.*;
import java.beans.PropertyChangeEvent;

/**
 * The class in a view model layer contains all functions which are used in the sign up view.
 * @version 1.0
 * @author Group1
 */
public class SignUpViewModel{

    private StringProperty usernameProperty;
    private StringProperty passwordProperty;
    private StringProperty confirmPasswordProperty;
    private StringProperty phoneProperty;
    private StringProperty warningLabel;
    private StringProperty streetProperty;
    private StringProperty numberProperty;
    private StringProperty cityProperty;
    private StringProperty postalCodeProperty;
    private StringProperty signUpCircleStyleProperty;
    private StringProperty addressCircleStyleProperty;
    private StringProperty personalCircleStyleProperty;
    private StringProperty warningStyleProperty;
    private DoubleProperty signUpCircleRadiusProperty;
    private DoubleProperty addressCircleRadiusProperty;
    private DoubleProperty personalCircleRadiusProperty;
    private BooleanProperty warningVisibleProperty;
    private BooleanProperty arrowDisableProperty;
    private BooleanProperty signUpDisableProperty;
    private DoubleProperty progressBarProperty1;
    private DoubleProperty progressBarProperty2;
    private BooleanProperty personalPaneVisibleProperty;
    private BooleanProperty addressPaneVisibleProperty;
    private BooleanProperty rulesPaneVisibleProperty;

    private UserActionsModel userActionsModel;
    private VerificationModel verificationModel;

    /**
     * A constructor initializes model layer for a sign up features and all fields
     */
    public SignUpViewModel() {

        this.userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        this.verificationModel = ModelFactory.getModelFactory().getVerificationModel();

        usernameProperty = new SimpleStringProperty();
        passwordProperty = new SimpleStringProperty();
        confirmPasswordProperty = new SimpleStringProperty();
        phoneProperty= new SimpleStringProperty();
        warningLabel = new SimpleStringProperty();
        streetProperty = new SimpleStringProperty();
        numberProperty = new SimpleStringProperty();
        cityProperty = new SimpleStringProperty();
        postalCodeProperty = new SimpleStringProperty();
        signUpCircleRadiusProperty = new SimpleDoubleProperty();
        addressCircleRadiusProperty = new SimpleDoubleProperty();
        personalCircleRadiusProperty = new SimpleDoubleProperty();
        addressCircleStyleProperty = new SimpleStringProperty();
        personalCircleStyleProperty = new SimpleStringProperty();
        signUpCircleStyleProperty = new SimpleStringProperty();
        warningVisibleProperty = new SimpleBooleanProperty();
        warningStyleProperty = new SimpleStringProperty();
        arrowDisableProperty = new SimpleBooleanProperty();
        signUpDisableProperty = new SimpleBooleanProperty();
        progressBarProperty1 = new SimpleDoubleProperty();
        progressBarProperty2 = new SimpleDoubleProperty();
        addressPaneVisibleProperty = new SimpleBooleanProperty();
        rulesPaneVisibleProperty = new SimpleBooleanProperty();
        personalPaneVisibleProperty = new SimpleBooleanProperty();

        userActionsModel.addPropertyChangeListener(Events.CONNECTION_FAILED.toString(), this::connectionFailed);
    }

    /**
     * Sends a request to the model layer for creating a new user
     * Sets a property of the warning pane regarding the result
     */
    public void createNewUser() {
        String verification = userActionsModel.createNewUser(new User(usernameProperty.get(), passwordProperty.get(), phoneProperty.get(), new Address(streetProperty.get(), numberProperty.get())));
        if(verification == null) {
            warningLabel.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
        }
        else {
            warningLabel.set(verification);
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        warningVisibleProperty.setValue(true);
        arrowDisableProperty.setValue(true);
        signUpDisableProperty.setValue(true);
    }

    /**
     * Sends a request to the model layer for verifying personal information
     * Sets a property of the warning pane regarding the result
     * If approved goes to the address information pane
     */
    public void signUpPersonalRequest()
    {
        String verificationUser = verificationModel.verifyUserInfo(usernameProperty.get(), phoneProperty.get());
        String verificationPassword = verificationModel.verifyPassword(passwordProperty.get(), confirmPasswordProperty.get());
        if(verificationUser == null && verificationPassword == null) {
           setAddressPaneSelected();
        }
        else if(verificationUser != null) {
            warningLabel.set(verificationUser);
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
            warningVisibleProperty.setValue(true);
        }
        else {
            warningLabel.set(verificationPassword);
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
            warningVisibleProperty.setValue(true);
        }
    }

    /**
     * Sends a request to the model layer for verifying user address information
     * Sets a property of the warning pane regarding the result
     * If approved goes to the creation process
     */
    public void signUpAddressRequest()
    {
        Address address = new Address(streetProperty.get(), numberProperty.get());
        String verification = verificationModel.verifyAddress(address);
        if(verification != null) {
            warningLabel.set(verification);
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
            warningVisibleProperty.setValue(true);
        }
        else
            createNewUser();
    }

    /**
     * Sets default values and  properties for all components
     */
    public void resetView()
    {
        usernameProperty.setValue("");
        passwordProperty.setValue("");
        confirmPasswordProperty.setValue("");
        phoneProperty.setValue("");
        streetProperty.setValue("");
        numberProperty.setValue("");
        cityProperty.setValue("Horsens");
        postalCodeProperty.setValue("8700");

        addressCircleStyleProperty.setValue("-fx-fill: #4cdbc4");
        addressCircleRadiusProperty.setValue(16);
        personalCircleStyleProperty.setValue("-fx-fill: #4cdbc4");
        personalCircleRadiusProperty.setValue(16);
        signUpCircleStyleProperty.setValue("-fx-fill: #4cdbc4");
        signUpCircleRadiusProperty.setValue(16);

        progressBarProperty1.setValue(0);
        progressBarProperty2.setValue(0);

        warningVisibleProperty.setValue(false);

        setPersonalPaneSelected();
    }

    /**
     * Sets default style and visible properties for circle components
     */
    public void resetCirclesProperties(){
        addressCircleStyleProperty.setValue("-fx-fill: #4cdbc4");
        addressCircleRadiusProperty.setValue(16);
        personalCircleStyleProperty.setValue("-fx-fill: #4cdbc4");
        personalCircleRadiusProperty.setValue(16);
        signUpCircleStyleProperty.setValue("-fx-fill: #4cdbc4");
        signUpCircleRadiusProperty.setValue(16);
    }

    /**
     * Sets visible properties of the all panes as false
     */
    public void setPanesVisiblePropertyFalse()
    {
        addressPaneVisibleProperty.setValue(false);
        personalPaneVisibleProperty.setValue(false);
        rulesPaneVisibleProperty.setValue(false);
    }

    /**
     * Sets style and visible properties of the personal pane and circle to make it selected
     */
    public void setPersonalPaneSelected()
    {
        resetCirclesProperties();
        setPanesVisiblePropertyFalse();

        personalCircleRadiusProperty.setValue(20);
        personalCircleStyleProperty.setValue("-fx-fill: orange");
        personalPaneVisibleProperty.setValue(true);
    }

    /**
     * Sets style and visible properties of the address pane and circle to make it selected
     */
    public void setAddressPaneSelected()
    {
        resetCirclesProperties();
        setPanesVisiblePropertyFalse();

        addressCircleRadiusProperty.setValue(20);
        addressCircleStyleProperty.setValue("-fx-fill: orange");
        addressPaneVisibleProperty.setValue(true);
    }

    /**
     * Sets style and visible properties of the rules pane to make it selected
     */
    public void setRulesPaneSelected()
    {
        setPanesVisiblePropertyFalse();
        rulesPaneVisibleProperty.setValue(true);
    }

    /**
     * Sets style property of the sign up circle to make it selected
     */
    public void setSignUpCircleSelectedProperties()
    {
        resetCirclesProperties();

        signUpCircleRadiusProperty.setValue(20);
        signUpCircleStyleProperty.setValue("-fx-fill: orange");
    }

    /**
     * Sets a visible property of the warning pane as false
     */
    public void hideWarningPane() {
        warningVisibleProperty.setValue(false);
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a phone number text field
     */
    public StringProperty getPhoneProperty() {
        return phoneProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a password text field
     */
    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a username text field
     */
    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a confirm password text field
     */
    public StringProperty getConfirmPasswordProperty() {
        return confirmPasswordProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a city text field
     */
    public StringProperty getCityProperty() {
        return cityProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a postal code text field
     */
    public StringProperty getPostalCodeProperty() {
        return postalCodeProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a street number text field
     */
    public StringProperty getNumberProperty() {
        return numberProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a street text field
     */
    public StringProperty getStreetProperty() {
        return streetProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a warning label
     */
    public StringProperty getWarningLabel() {
        return warningLabel;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the style property of a sign up circle
     */
    public StringProperty getSignUpCircleStyleProperty() {
        return signUpCircleStyleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the style property of a address circle
     */
    public StringProperty getAddressCircleStyleProperty() {
        return addressCircleStyleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the style property of a personal circle
     */
    public StringProperty getPersonalCircleStyleProperty() {
        return personalCircleStyleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the radius property of a sign up circle
     */
    public DoubleProperty getSignUpCircleRadiusProperty() {
        return signUpCircleRadiusProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the radius property of a personal circle
     */
    public DoubleProperty getPersonalCircleRadiusProperty() {
        return personalCircleRadiusProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the radius property of a address circle
     */
    public DoubleProperty getAddressCircleRadiusProperty() {
        return addressCircleRadiusProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the visible property of a sign up button
     */
    public BooleanProperty getSignUpButtonDisableProperty() {
        return signUpDisableProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the style property of a warning pane
     */
    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the visible property of a warning pane
     */
    public BooleanProperty getWarningVisibleProperty() {
        return warningVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the disable property of a back arrow
     */
    public BooleanProperty backArrowDisableProperty() {
        return arrowDisableProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the progress property of the first progressbar
     */
    public DoubleProperty getProgressBar1Property() {
        return progressBarProperty1;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the progress property of the second progressbar
     */
    public DoubleProperty getProgressBar2Property() {
        return progressBarProperty2;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the visible property of the personal pane
     */
    public BooleanProperty getPersonalPaneVisibleProperty() {
        return personalPaneVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the visible property of the address pane
     */
    public BooleanProperty getAddressPaneVisibleProperty() {
        return addressPaneVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the visible property of the rules pane
     */
    public BooleanProperty getRulesVisibleProperty() {
        return rulesPaneVisibleProperty;
    }

    /**
     * Sets properties of the warning pane nad label when server connection error event appears
     * @param propertyChangeEvent the sever connection error event
     */
    private void connectionFailed(PropertyChangeEvent propertyChangeEvent) {
        warningLabel.setValue(VerificationList.getVerificationList().getVerifications().get(Verifications.SERVER_CONNECTION_ERROR));
        warningVisibleProperty.setValue(true);
    }
}
