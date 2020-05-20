package ESharing.Client.Views.UserAddressSettingView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

/**
 * The class in a view model layer contains all functions which are used in the edit user address view.
 * @version 1.0
 * @author Group1
 */
public class UserAddressSettingViewModel {

    private StringProperty usernameProperty;
    private StringProperty phoneNumberProperty;
    private StringProperty streetProperty;
    private StringProperty numberProperty;
    private StringProperty cityProperty;
    private StringProperty postalCodeProperty;
    private StringProperty warningProperty;
    private StringProperty warningStyleProperty;
    private BooleanProperty warningVisibleProperty;
    private ObjectProperty<Paint> avatarCircleFillProperty;

    private UserActionsModel userActionsModel;
    private VerificationModel verificationModel;
    private LoggedUser loggedUser;

    /**
     * A constructor initializes model layer for a edit user features and all fields
     */
    public UserAddressSettingViewModel() {
        loggedUser = LoggedUser.getLoggedUser();
        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        verificationModel = ModelFactory.getModelFactory().getVerificationModel();

        warningProperty = new SimpleStringProperty();
        cityProperty = new SimpleStringProperty();
        postalCodeProperty = new SimpleStringProperty();
        numberProperty = new SimpleStringProperty();
        streetProperty = new SimpleStringProperty();
        usernameProperty = new SimpleStringProperty();
        phoneNumberProperty = new SimpleStringProperty();
        warningStyleProperty = new SimpleStringProperty();
        warningVisibleProperty = new SimpleBooleanProperty();
        avatarCircleFillProperty = new SimpleObjectProperty<>();
    }

    /**
     * Sets a default view and values
     */
    public void loadDefaultView() {
        usernameProperty.set(loggedUser.getUser().getUsername());
        phoneNumberProperty.set(loggedUser.getUser().getPhoneNumber());
        numberProperty.set(loggedUser.getUser().getAddress().getNumber());
        streetProperty.set(loggedUser.getUser().getAddress().getStreet());

        cityProperty.setValue("Horsens");
        postalCodeProperty.setValue("8700");

        warningVisibleProperty.setValue(false);
        avatarCircleFillProperty.setValue(new ImagePattern(LoggedUser.getLoggedUser().getUser().getAvatar()));
    }

    /**
     * Sends a request to the model layer for changing the user address
     * Sets a property of the warning pane regarding the result
     */
    public void modifyAddressRequest() {

        User updatedUser = loggedUser.getUser();
        Address updatedAddress = new Address(streetProperty.get(), numberProperty.get());
        String addressVerification = verificationModel.verifyAddress(updatedAddress);
        if(addressVerification == null)
        {
            updatedUser.setAddress(updatedAddress);
            if(userActionsModel.modifyUserInformation(updatedUser))
            {
                warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
                warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
            }
        }
        else{
            warningProperty.set(addressVerification);
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        warningVisibleProperty.setValue(true);
    }

    /**
     * Sets the visible property for the warning pane
     */
    public void hideWarningPane() {
        warningVisibleProperty.setValue(false);
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a username label
     */
    public StringProperty getUsernameProperty() {
        return usernameProperty;
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
     * @return the string property of a street number text field
     */
    public StringProperty getNumberProperty() {
        return numberProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a phone number label
     */
    public StringProperty getPhoneNumberProperty() {
        return phoneNumberProperty;
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
     * @return the string property of a street text field
     */
    public StringProperty getStreetProperty() {
        return streetProperty;
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
     * @return the fill property of a avatar circle
     */
    public ObjectProperty<Paint> getAvatarCircleFillProperty() {
        return avatarCircleFillProperty;
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
}
