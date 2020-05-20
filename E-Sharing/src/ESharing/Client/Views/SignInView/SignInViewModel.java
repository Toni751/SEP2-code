package ESharing.Client.Views.SignInView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

/**
 * The class in a view model layer contains all functions which are used in the sign in view.
 * @version 1.0
 * @author Group1
 */
public class SignInViewModel{

    private StringProperty usernameProperty;
    private StringProperty passwordProperty;
    private StringProperty warningProperty;
    private StringProperty warningStyleProperty;
    private BooleanProperty warningVisibleProperty;

    private UserActionsModel userActionsModel;
    private VerificationModel verificationModel;

    private PropertyChangeSupport support;

    /**
     * A constructor initializes model layer for a sign up features and all fields
     */
    public SignInViewModel() {
        this.userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        this.verificationModel = ModelFactory.getModelFactory().getVerificationModel();

        usernameProperty = new SimpleStringProperty();
        passwordProperty = new SimpleStringProperty();
        warningProperty = new SimpleStringProperty();
        warningStyleProperty = new SimpleStringProperty();
        warningVisibleProperty = new SimpleBooleanProperty();

        support = new PropertyChangeSupport(this);

        userActionsModel.addPropertyChangeListener(Events.CONNECTION_FAILED.toString(), this::connectionFailed);
    }

    /**
     * Sends a request to the model layer for singing in user with the given username and password
     * Sets a property of the warning pane regarding the result
     */
    public String loginRequest() {
        String verificationFields = verificationModel.verifyUsernameAndPassword(usernameProperty.get(), passwordProperty.get());
        {
            if (verificationFields == null) {
                String verificationUser = userActionsModel.onLoginRequest(usernameProperty.get(), passwordProperty.get());
                if (verificationUser == null) {
                    if (LoggedUser.getLoggedUser().getUser().isAdministrator())
                        return "ADMIN";
                    else
                        return "USER";
                } else {
                    warningProperty.set(verificationUser);
                    warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
                    warningVisibleProperty.setValue(true);
                }
            } else {
                warningProperty.set(verificationFields);
                warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
                warningVisibleProperty.setValue(true);
            }
        }
        return null;
    }

    /**
     * Sets a default view and values
     */
    public void defaultView() {
        usernameProperty.setValue("");
        passwordProperty.setValue("");
        warningVisibleProperty.setValue(false);
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
     * @return the string property of a password text field
     */
    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a warning label
     */
    public StringProperty getWarningProperty() {
        return warningProperty;
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
     * @return the style property of a warning pane
     */
    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }

    /**
     * Sets properties of the warning pane when the server connection error event appears
     * @param propertyChangeEvent the server connection error event
     */
    private void connectionFailed(PropertyChangeEvent propertyChangeEvent) {
        warningProperty.setValue(VerificationList.getVerificationList().getVerifications().get(Verifications.SERVER_CONNECTION_ERROR));
        warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        warningVisibleProperty.setValue(true);
    }
}
