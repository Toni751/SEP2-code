package ESharing.Client.Views.UserView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

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

    public void reportUser() {
        if(userActionsModel.addNewUserReport(selectedUser.getUser_id()))
        {
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
            warningVisibleProperty.setValue(true);
        }
    }

    public ObjectProperty<Paint> getAvatarFillProperty() {
        return avatarFillProperty;
    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    public StringProperty getPhoneProperty() {
        return phoneProperty;
    }

    public StringProperty getNumberProperty() {
        return numberProperty;
    }

    public StringProperty getStreetProperty() {
        return streetProperty;
    }

    public StringProperty getWarningProperty() {
        return warningProperty;
    }

    public BooleanProperty getWarningVisibleProperty() {
        return  warningVisibleProperty;
    }

    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }
}
