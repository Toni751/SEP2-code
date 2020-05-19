package ESharing.Client.Model.UserActions;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Networking.Connection;
import ESharing.Client.Networking.advertisement.ClientAdvertisement;
import ESharing.Client.Networking.chat.ClientChat;
import ESharing.Client.Networking.user.Client;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.scene.image.Image;
import jdk.jfr.Event;

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

    private Client client;
    private ClientChat clientChat;
    private ClientAdvertisement clientAdvertisement;
    private PropertyChangeSupport support;
    private Connection connection;

    /**
     * A constructor initializes all fields
     */
    public UserActionsModelManager()
    {
        this.client = ClientFactory.getClientFactory().getClient();
        this.clientChat = ClientFactory.getClientFactory().getChatClient();
        this.clientAdvertisement = ClientFactory.getClientFactory().getClientAdvertisement();
        support = new PropertyChangeSupport(this);
        connection = new Connection();

        client.addPropertyChangeListener(Events.UPDATE_AVATAR.toString(), this::avatarUpdated);
        connection.addPropertyChangeListener(Events.CONNECTION_FAILED.toString(), this::connectionFailed);
    }

    private void connectionFailed(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }

    private void avatarUpdated(PropertyChangeEvent propertyChangeEvent) {
        LoggedUser.getLoggedUser().getUser().setAvatar((byte[]) propertyChangeEvent.getNewValue());
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
       // connection.closeConnection();
        initializeConnections();
            User requestedUser = client.loginUserRequest(username,password);
            if(requestedUser == null)
                return VerificationList.getVerificationList().getVerifications().get(Verifications.USER_NOT_EXIST);
            else {
                LoggedUser.getLoggedUser().loginUser(requestedUser);
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
        //LoggedUser.getLoggedUser().logoutUser();
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

    private void initializeConnections()
    {
        client.initializeConnection();
        clientChat.initializeConnection();
        clientAdvertisement.initializeConnection();
    }
}
