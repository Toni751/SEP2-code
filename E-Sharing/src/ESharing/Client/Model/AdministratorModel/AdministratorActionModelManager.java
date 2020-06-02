package ESharing.Client.Model.AdministratorModel;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Networking.user.UserClient;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.User;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The class from the model layer which contains all administrator features and connects them with a networking part
 * @version 1.0
 * @author Group1
 */
public class AdministratorActionModelManager implements AdministratorActionsModel {

    private UserClient client;
    private PropertyChangeSupport support;

    /**
     * A constructor sets fields and assigns events
     */
    public AdministratorActionModelManager() {
        client = ClientFactory.getClientFactory().getClient();
        support = new PropertyChangeSupport(this);
        client.addPropertyChangeListener(Events.NEW_USER_CREATED.toString(), this :: newUserCreated);
        client.addPropertyChangeListener(Events.USER_REMOVED.toString(), this :: userRemovedFromTheSystem);
        client.addPropertyChangeListener(Events.USER_UPDATED.toString(), this :: userUpdated);
    }

    @Override
    public List<User> getAllUsers() {
        return client.getAllUsersRequest();
//        List<User> dummyUsers = new ArrayList<>();
//        Address address = new Address("Yes", "1");
//        dummyUsers.add(new User("asrgjar", "password", "123", address));
//        dummyUsers.add(new User("ertyb", "password", "123", address));
//        dummyUsers.add(new User("aafgfb", "password", "123", address));
//        dummyUsers.add(new User("rtcvwe", "password", "123", address));
//        dummyUsers.add(new User("xcfgrv", "password", "123", address));
//        dummyUsers.add(new User("afgb", "password", "123", address));
//        dummyUsers.add(new User("sdrgdf", "password", "123", address));
//        dummyUsers.add(new User("adgber", "password", "123", address));
//        dummyUsers.add(new User("rtvxfc", "password", "123", address));
//        dummyUsers.add(new User("asftbrtv", "password", "123", address));
//        return dummyUsers;
    }

    @Override
    public boolean removeUserAccount(User user) {
        return client.removeUserRequest(user);
    }

    @Override
    public String resetUserPassword(User user) {
        String newPassword = generatePassword(12);
        user.setPassword(newPassword);
        if(client.editUserRequest(user))
            return newPassword;
        else
            return null;
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
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Starts when new userUpdated event appears. Check is current user is a part of the event and sends another event to the view model layer.
     * @param propertyChangeEvent the received event
     */
    private void userUpdated(PropertyChangeEvent propertyChangeEvent) {
        User updatedUser = (User) propertyChangeEvent.getNewValue();
        if(LoggedUser.getLoggedUser().getUser().getUser_id() == updatedUser.getUser_id())
            LoggedUser.getLoggedUser().loginUser(updatedUser);
        support.firePropertyChange(propertyChangeEvent);
        AdministratorLists.getInstance().updateUser(updatedUser.getUser_id(), updatedUser);
    }

    /**
     * Starts when new userRemoved event appears. Check is current user is a part of the event and sends another event to the view model layer.
     * @param propertyChangeEvent the received event
     */
    private void userRemovedFromTheSystem(PropertyChangeEvent propertyChangeEvent) {
        User removedUser = (User) propertyChangeEvent.getNewValue();
        if(LoggedUser.getLoggedUser().getUser().getUser_id() == removedUser.getUser_id() ||
                LoggedUser.getLoggedUser().getUser().isAdministrator())
            support.firePropertyChange(propertyChangeEvent);
        AdministratorLists.getInstance().getUserList().remove(removedUser);
        System.out.println("Got removed user request");
    }

    /**
     * Starts when new userCreated event appears. Check is current user is a part of the event and sends another event to the view model layer.
     * @param propertyChangeEvent the received event
     */
    private void newUserCreated(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
        AdministratorLists.getInstance().getUserList().add((User) propertyChangeEvent.getNewValue());
        System.out.println("New user in the admin model");
    }

    /**
     * Generates new random password
     * @param size the size of a rendom password
     * @return the new random password as a string object
     */
    private String generatePassword(int size)
    {
        Random random = new Random();

        String lowerChar = "abcdefghijklmnopqrstuvwxyz";
        String upperChar = lowerChar.toUpperCase();
        String numbers = "0123456789";
        String signs = "@#$%&*!";
        String[] generatorLists = {lowerChar, upperChar, numbers, signs};

        String randomPassword = "";

        for(int i = 0 ; i < size ; i++)
        {
            String currentList = generatorLists[random.nextInt(generatorLists.length-1)];
            randomPassword += currentList.charAt(random.nextInt(currentList.length()-1));
        }

        return randomPassword;
    }
}
