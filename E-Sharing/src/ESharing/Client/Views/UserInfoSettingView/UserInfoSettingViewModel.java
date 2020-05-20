package ESharing.Client.Views.UserInfoSettingView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.GeneralFunctions;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import java.io.File;

/**
 * The class in a view model layer contains all functions which are used in the edit user info view.
 * @version 1.0
 * @author Group1
 */
public class UserInfoSettingViewModel{

    private StringProperty usernameProperty;
    private StringProperty phoneProperty;
    private StringProperty oldPasswordProperty;
    private StringProperty newPasswordProperty;
    private StringProperty confirmPasswordProperty;
    private StringProperty warningLabel;
    private StringProperty warningStyleProperty;
    private BooleanProperty warningVisibleProperty;
    private BooleanProperty uploadImageVisibleProperty;
    private ObjectProperty<Paint> avatarFillProperty;
    private DoubleProperty avatarOpacityProperty;

    private UserActionsModel userActionsModel;
    private VerificationModel verificationModel;
    private LoggedUser loggedUser;

    /**
     * A constructor initializes model layer for a edit user features and all fields
     */
    public UserInfoSettingViewModel() {
        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        verificationModel = ModelFactory.getModelFactory().getVerificationModel();
        loggedUser = LoggedUser.getLoggedUser();

        usernameProperty = new SimpleStringProperty();
        phoneProperty = new SimpleStringProperty();
        oldPasswordProperty = new SimpleStringProperty();
        newPasswordProperty = new SimpleStringProperty();
        warningLabel = new SimpleStringProperty();
        confirmPasswordProperty = new SimpleStringProperty();
        warningStyleProperty = new SimpleStringProperty();
        warningVisibleProperty = new SimpleBooleanProperty();
        avatarFillProperty = new SimpleObjectProperty<>();
        uploadImageVisibleProperty = new SimpleBooleanProperty();
        avatarOpacityProperty = new SimpleDoubleProperty();
    }

    /**
     * Sends a request to the model layer for changing the user info for current logged administrator
     * Sets a property of the warning pane regarding the result
     */
    public void checkAndUpdateInformation()
    {
       User updatedUser = LoggedUser.getLoggedUser().getUser();
       String verification = verificationModel.verifyUserInfo(usernameProperty.get(), phoneProperty.get());
       updatedUser.setUsername(usernameProperty.get());
       updatedUser.setPhoneNumber(phoneProperty.get());
       if(sendRequestToModel(updatedUser, verification))
           warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
       else
           warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        warningVisibleProperty.setValue(true);
    }

    /**
     * Sends a request to the model layer for changing the user password
     * Sets a property of the warning pane regarding the result
     */
    public void changePassword()
    {
        User updatedUser = LoggedUser.getLoggedUser().getUser();
        System.out.println(LoggedUser.getLoggedUser().getUser().getPassword());
        String verification = verificationModel.verifyChangePassword(oldPasswordProperty.get(), newPasswordProperty.get(),confirmPasswordProperty.get());
        updatedUser.setPassword(newPasswordProperty.get());
        if(sendRequestToModel(updatedUser, verification))
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
        else
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        warningVisibleProperty.setValue(true);

    }

    /**
     * Sets a default view and values
     */
    public void loadDefaultView()
    {
        usernameProperty.setValue(loggedUser.getUser().getUsername());
        phoneProperty.setValue(loggedUser.getUser().getPhoneNumber());
        oldPasswordProperty.setValue("");
        newPasswordProperty.set("");
        confirmPasswordProperty.set("");
        avatarFillProperty.setValue(new ImagePattern(LoggedUser.getLoggedUser().getUser().getAvatar()));
        avatarOpacityProperty.setValue(1);
        warningVisibleProperty.setValue(false);
        uploadImageVisibleProperty.setValue(false);
    }

    /**
     * Sets the visible property for the warning pane
     */
    public void hideWarningPane() {
        warningVisibleProperty.setValue(false);
    }

    /**
     * Sets properties of the avatar circle when mouse enters the avatar circle
     */
    public void setAvatarPropertyMouseEntered()
    {
        if(!uploadImageVisibleProperty.get()) {
            uploadImageVisibleProperty.setValue(true);
            avatarOpacityProperty.setValue(0.3);
        }
    }

    /**
     * Sets properties of the avatar circle when mouse exits the avatar circle
     */
    public void setAvatarPropertyMouseExit()
    {
        uploadImageVisibleProperty.setValue(false);
        avatarOpacityProperty.setValue(1);
    }

    /**
     * Sends a request to the model layer for changing the account avatar of the current logged user
     */
    public void changeAvatarRequest(File avatarImage) {
        userActionsModel.changeAvatar(avatarImage);
    }

    /**
     * Sets the fill property of the avatar circle with the given image
     * @param image the given image
     */
    public void setSelectedAvatar(Image image) {
        avatarFillProperty.setValue(new ImagePattern(image));
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a old password text field
     */
    public StringProperty getOldPasswordProperty() {
        return oldPasswordProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a new password text field
     */
    public StringProperty getNewPasswordProperty() {
        return newPasswordProperty;
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
     * @return the string property of a phone number text field
     */
    public StringProperty getPhoneProperty() {
        return phoneProperty;
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
     * @return the string property of a confirm password text field
     */
    public StringProperty getConfirmPasswordProperty() {
        return confirmPasswordProperty;
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
     * Returns value used in the bind process between a controller and view model
     * @return the fill property of a avatar circle
     */
    public ObjectProperty<Paint> getAvatarFillProperty() {
        return avatarFillProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the visible property of a upload image
     */
    public BooleanProperty getUploadImageVisibleProperty() {
        return uploadImageVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the opacity property of a avatar circle
     */
    public DoubleProperty getAvatarOpacity() {
        return avatarOpacityProperty;
    }

    /**
     * Sends the edit request to the model layer
     * @param updatedUser the updated user object
     * @param verification the result of the verification text fields
     * @return the result of the request
     */
    private boolean sendRequestToModel(User updatedUser, String verification) {
        return GeneralFunctions.sendEditRequest(updatedUser, verification, warningLabel);
    }
}
