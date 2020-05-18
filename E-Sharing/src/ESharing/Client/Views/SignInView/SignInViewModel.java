package ESharing.Client.Views.SignInView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.PropertyChangeSubject;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The class in a view model layer contains all functions which are used in the signIn view.
 * @version 1.0
 * @author Group1
 */
public class SignInViewModel implements PropertyChangeSubject {

    private StringProperty usernameProperty;
    private StringProperty passwordProperty;
    private StringProperty warningProperty;

    private UserActionsModel userActionsModel;
    private VerificationModel verificationModel;

    private PropertyChangeSupport support;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public SignInViewModel() {
        this.userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        this.verificationModel = ModelFactory.getModelFactory().getVerificationModel();
        usernameProperty = new SimpleStringProperty();
        passwordProperty = new SimpleStringProperty();
        warningProperty= new SimpleStringProperty();

        support = new PropertyChangeSupport(this);

        userActionsModel.addPropertyChangeListener(Events.CONNECTION_FAILED.toString(), this::connectionFailed);
    }

    private void connectionFailed(PropertyChangeEvent propertyChangeEvent) {
        warningProperty.setValue(VerificationList.getVerificationList().getVerifications().get(Verifications.SERVER_CONNECTION_ERROR));
        support.firePropertyChange(propertyChangeEvent);
    }

    /**
     * Sends the login request to the model layer and waits for the verification result
     * @return the boolean value with the result of the verification
     */
    public boolean loginRequest()
    {
        String verificationFields = verificationModel.verifyUsernameAndPassword(usernameProperty.get(), passwordProperty.get());
        {
            if(verificationFields == null)
            {
                String verificationUser = userActionsModel.onLoginRequest(usernameProperty.get(), passwordProperty.get());
                if(verificationUser == null)
                    return true;
                else{
                    warningProperty.set(verificationUser);
                    return false;
                }
            }
            else{
                warningProperty.set(verificationFields);
                return false;
            }
        }
    }

    /**
     * Clears all text fields
     */
    public void resetView()
    {
        usernameProperty.setValue("");
        passwordProperty.setValue("");
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
     * @return the value used in the password text field
     */
    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the warning notification
     */
    public StringProperty getWarningProperty() {
        return warningProperty;
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
