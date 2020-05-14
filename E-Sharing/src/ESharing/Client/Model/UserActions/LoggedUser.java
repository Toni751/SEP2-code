package ESharing.Client.Model.UserActions;

import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.util.ArrayList;
import java.util.List;

/**
 * The class represents a current logged user
 * @version 1.0
 * @author Group1
 */
public class LoggedUser {

    private User user;
    private static LoggedUser loggedUser;
    private List<Message> currentOpenConversation = new ArrayList<>();

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

    public void setCurrentOpenConversation(List<Message> currentOpenConversation) {
        this.currentOpenConversation = currentOpenConversation;
    }

    public List<Message> getCurrentOpenConversation() {
        return currentOpenConversation;
    }
}
