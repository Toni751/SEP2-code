package ESharing.Client.Views.UserView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;


/**
 * The class in a view model layer contains all functions which are used in the user view.
 * @version 1.0
 * @author Group1
 */
public class UserViewModel {

    private StringProperty usernameProperty;
    private StringProperty phoneProperty;
    private StringProperty numberProperty;
    private StringProperty warningProperty;
    private StringProperty warningStyleProperty;
    private BooleanProperty warningVisibleProperty;
    private StringProperty streetProperty;
    private ObjectProperty<Paint> avatarFillProperty;

    private User selectedUser;
    private UserActionsModel userActionsModel;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public UserViewModel()
    {
        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();

        usernameProperty = new SimpleStringProperty();
        phoneProperty = new SimpleStringProperty();
        numberProperty = new SimpleStringProperty();
        warningStyleProperty = new SimpleStringProperty();
        streetProperty = new SimpleStringProperty();
        warningProperty = new SimpleStringProperty();
        warningVisibleProperty = new SimpleBooleanProperty();
        avatarFillProperty = new SimpleObjectProperty<>();
    }

    /**
     * Sets a default view and values
     */
    public void setDefaultView()
    {
        selectedUser = LoggedUser.getLoggedUser().getSelectedUser();

        usernameProperty.set(selectedUser.getUsername());
        phoneProperty.set(selectedUser.getPhoneNumber());
        numberProperty.set(selectedUser.getAddress().getNumber());
        streetProperty.set(selectedUser.getAddress().getStreet());
        avatarFillProperty.setValue(new ImagePattern(selectedUser.getAvatar()));
        warningVisibleProperty.set(false);
    }

    /**
     * Sends a request for reporting user
     */
    public void reportUser() {
        if(userActionsModel.addNewUserReport(selectedUser.getUser_id()))
        {
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
            warningVisibleProperty.setValue(true);
        }
    }
    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a avatar
     */
    public ObjectProperty<Paint> getAvatarFillProperty() {
        return avatarFillProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a usermane
     */
    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }
    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a phone
     */
    public StringProperty getPhoneProperty() {
        return phoneProperty;
    }
    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a number
     */
    public StringProperty getNumberProperty() {
        return numberProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a street
     */
    public StringProperty getStreetProperty() {
        return streetProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a warning
     */
    public StringProperty getWarningProperty() {
        return warningProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a warning
     */
    public BooleanProperty getWarningVisibleProperty() {
        return  warningVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a warning
     */
    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }
}
