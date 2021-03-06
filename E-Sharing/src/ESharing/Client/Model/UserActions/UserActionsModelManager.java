package ESharing.Client.Model.UserActions;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Networking.Connection;
import ESharing.Client.Networking.advertisement.ClientAdvertisement;
import ESharing.Client.Networking.chat.ClientChat;
import ESharing.Client.Networking.reservation.ReservationClient;
import ESharing.Client.Networking.user.UserClient;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * The class from the model layer which contains all user features and connects them with a networking part
 * @version 1.0
 * @author Group1
 */
public class UserActionsModelManager implements UserActionsModel {

    private UserClient client;
    private ClientChat clientChat;
    private ClientAdvertisement clientAdvertisement;
    private ReservationClient reservationClient;
    private PropertyChangeSupport support;
    private Connection connection;

    /**
     * A constructor sets fields and assigns events
     */
    public UserActionsModelManager()
    {
        this.client = ClientFactory.getClientFactory().getClient();
        this.clientChat = ClientFactory.getClientFactory().getChatClient();
        this.clientAdvertisement = ClientFactory.getClientFactory().getClientAdvertisement();
        this.reservationClient = ClientFactory.getClientFactory().getReservationClient();
        support = new PropertyChangeSupport(this);
        connection = new Connection();

        client.addPropertyChangeListener(Events.UPDATE_AVATAR.toString(), this::avatarUpdated);
        connection.addPropertyChangeListener(Events.CONNECTION_FAILED.toString(), this::connectionFailed);
        client.addPropertyChangeListener(Events.NEW_USER_REPORT.toString(), this::newUserReported);
    }


    @Override
    public String createNewUser(User newUser) {
        initializeConnections();
        boolean verification = client.addUserRequest(newUser);
        if(!verification)
            return VerificationList.getVerificationList().getVerifications().get(Verifications.DATABASE_CONNECTION_ERROR);
        else {
            return null;
        }
    }

    @Override
    public String onLoginRequest(String username, String password) {
        initializeConnections();
            User requestedUser = client.loginUserRequest(username,password);
            if(requestedUser == null)
                return VerificationList.getVerificationList().getVerifications().get(Verifications.USER_NOT_EXIST);
            else {
                LoggedUser.getLoggedUser().loginUser(requestedUser);
                clientChat.registerForCallBack();
                clientAdvertisement.registerForCallBack();
                reservationClient.registerForCallback();
                return null;
            }
    }

    @Override
    public boolean modifyUserInformation(User updatedUser) {
        boolean verification = client.editUserRequest(updatedUser);
        return verification;
    }

    @Override
    public void removeAccount() {
        User accountToRemove = new User(LoggedUser.getLoggedUser());
        client.removeUserRequest(accountToRemove);
        LoggedUser.getLoggedUser().logoutUser();
    }

    @Override
    public void logoutUser() {
        client.logout();
        clientChat.userLoggedOut();
        connection.closeConnection();
    }

    @Override
    public void changeAvatar(File avatarImage) {
        try {
            byte[] avatarByte = Files.readAllBytes(avatarImage.toPath().toAbsolutePath());
            client.changeAvatar(avatarByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addNewUserReport(int user_id) {
        return client.addNewUserReport(user_id);
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

    /**
     * Initializes a server
     */
    private void initializeConnections()
    {
        client.initializeConnection();
        clientChat.initializeConnection();
        clientAdvertisement.initializeConnection();
        reservationClient.initializeConnection();
    }

    /**
     * Starts when new reportedUSer event appears. Sends another event to the view model layer.
     * @param propertyChangeEvent the received event
     */
    private void newUserReported(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }

    /**
     * Starts when new connectionFailed event appears. Sends another event to the view model layer.
     * @param propertyChangeEvent the received event
     */
    private void connectionFailed(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }

    /**
     * Starts when new avatarUpdated event appears. Check the logged user and sets the new avatar
     * @param propertyChangeEvent the received event
     */
    private void avatarUpdated(PropertyChangeEvent propertyChangeEvent) {
        int userId = (int) propertyChangeEvent.getOldValue();
        if(LoggedUser.getLoggedUser().getUser().getUser_id() == userId)
            LoggedUser.getLoggedUser().getUser().setAvatar((byte[]) propertyChangeEvent.getNewValue());
    }
}
