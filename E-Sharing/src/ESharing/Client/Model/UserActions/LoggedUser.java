package ESharing.Client.Model.UserActions;

import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.Views;
import java.util.ArrayList;
import java.util.List;

/**
 * The class represents a current logged user
 * @version 1.0
 * @author Group1
 */
public class LoggedUser {

    private User user;
    private User selectedUser;
    private static LoggedUser loggedUser;
    private List<Message> currentOpenConversation = new ArrayList<>();
    private Advertisement selectedAdvertisement;
    private Views selectedView;

    /**
     * Returns LoggedUser object if it exists, otherwise creates new object
     * @return the LoggedUser object
     */
    public static LoggedUser getLoggedUser()
    {
        if(loggedUser == null)
            loggedUser = new LoggedUser();
        return loggedUser;
    }

    /**
     * Sets a user object as a current logged user
     * @param user
     */
    public void loginUser(User user)
    {
       this.user = user;
    }

    /**
     * Sets a current logged user as null
     */
    public void logoutUser()
    {
        loggedUser = null;
    }

    /**
     * Returns a user object which is currently set as a logged user
     * @return the current logged user object
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets a current selected conversation
     * @param currentOpenConversation the current selected conversation
     */
    public void setCurrentOpenConversation(List<Message> currentOpenConversation) {
        this.currentOpenConversation = currentOpenConversation;
    }

    /**
     * Returns a current selected advertisement
     * @return a current selected advertisement
     */
    public Advertisement getSelectedAdvertisement()
    {
        return selectedAdvertisement;
    }

    /**
     * Sets a current selected advertisement
     * @param selectedAdvertisement a current selected advertisement
     */
    public void selectAdvertisement(Advertisement selectedAdvertisement)
    {
        this.selectedAdvertisement = selectedAdvertisement;
    }

    /**
     * Returns a current selected conversation
     * @return the current selected conversation
     */
    public List<Message> getCurrentOpenConversation() {
        return currentOpenConversation;
    }

    /**
     * Returns a current selected user
     * @return the current selected user
     */
    public User getSelectedUser() {
        return selectedUser;
    }

    /**
     * Sets a current selected user
     * @param selectedUser the current selected user
     */
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    /**
     * Sets a current selected view to open
     * @param selectedView the current selected view
     */
    public void setSelectedView(Views selectedView) {
        this.selectedView = selectedView;
    }

    /**
     * Returns a current selected view to open
     * @return the current selected view
     */
    public Views getSelectedView() {
        return selectedView;
    }
}
