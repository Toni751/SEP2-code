package ESharing.Client.Model.AdministratorModel;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Networking.user.UserClient;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Random;

public class AdministratorActionModelManager implements AdministratorActionsModel {

    private UserClient client;
    private PropertyChangeSupport support;

    public AdministratorActionModelManager() {
        client = ClientFactory.getClientFactory().getClient();
        support = new PropertyChangeSupport(this);
        client.addPropertyChangeListener(Events.NEW_USER_CREATED.toString(), this :: newUserCreated);
        client.addPropertyChangeListener(Events.USER_REMOVED.toString(), this :: userRemovedFromTheSystem);
        client.addPropertyChangeListener(Events.USER_UPDATED.toString(), this :: userUpdated);
    }

    private void userUpdated(PropertyChangeEvent propertyChangeEvent) {
        User updatedUser = (User) propertyChangeEvent.getNewValue();
        if(LoggedUser.getLoggedUser().getUser().getUser_id() == updatedUser.getUser_id())
            LoggedUser.getLoggedUser().loginUser(updatedUser);
        support.firePropertyChange(propertyChangeEvent);
        AdministratorLists.getInstance().updateUser(updatedUser.getUser_id(), updatedUser);
        System.out.println("Got updated user");
    }

    private void userRemovedFromTheSystem(PropertyChangeEvent propertyChangeEvent) {
        User removedUser = (User) propertyChangeEvent.getNewValue();
        if(LoggedUser.getLoggedUser().getUser().getUser_id() == removedUser.getUser_id() ||
            LoggedUser.getLoggedUser().getUser().isAdministrator())
                support.firePropertyChange(propertyChangeEvent);
        AdministratorLists.getInstance().getUserList().remove(removedUser);
        System.out.println("Got removed user request");
    }

    private void newUserCreated(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
        AdministratorLists.getInstance().getUserList().add((User) propertyChangeEvent.getNewValue());
        System.out.println("New user in the admin model");
    }

    @Override
    public List<User> getAllUsers() {
        return client.getAllUsersRequest();
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
}
