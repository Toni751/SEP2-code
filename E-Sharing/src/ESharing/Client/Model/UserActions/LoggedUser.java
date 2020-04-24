package ESharing.Client.Model.UserActions;

import ESharing.Shared.TransferedObject.User;

public class LoggedUser {

    private User user;
    private static LoggedUser loggedUser;


    private LoggedUser() {}

    public static LoggedUser getLoggedUser()
    {
        if(loggedUser == null)
            loggedUser = new LoggedUser();
        return loggedUser;
    }

    public void loginUser(User user)
    {
       this.user = user;
    }

    public void logoutUser()
    {
        loggedUser = null;
    }

    public User getUser() {
        return user;
    }
}
