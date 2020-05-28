package ESharing.Client.Model.AdministratorModel;

import ESharing.Shared.TransferedObject.User;

import java.util.ArrayList;
import java.util.List;

/**
 * The singleton class responsible for managing information related with administrator account
 * @version 1.0
 * @author Group1
 */
public class AdministratorLists {

    private List<User> userList;
    private User selectedUser;
    private static AdministratorLists instance;

    /**
     * Private constructor sets fields
     */
    private AdministratorLists()
    {
        userList = new ArrayList<>();
    }

    /**
     * Returns the instance of this object
     * @return returns the initialized object of this class
     */
    public static AdministratorLists getInstance() {
        if(instance == null)
            instance = new AdministratorLists();
        return instance;
    }

    /**
     * Sets the list of users which are a part of the system
     * @param userList the list of users which are a part of the system
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     * Returns list of users which are a part of the system
     * @return the list of users which are a part of the system
     */
    public List<User> getUserList() {
        return userList;
    }

    /**
     * Returns the number of students which are marked as reported
     * @return the number of students which are marked as reported
     */
    public int reportedUsers()
    {
        int reported = 0;
        for(User user: userList)
        {
            if(user.getReportsNumber() > 0)
                reported++;
        }
        return reported;
    }

    /**
     * Sets the user which is selected by administrator
     * @param selectedUser the selected user
     */
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    /**
     * Returns the user which is selected by administrator
     * @return he user which is selected by administrator
     */
    public User getSelectedUser() {
        return selectedUser;
    }

    /**
     * Updates the list with users with the given user using its id
     * @param id the id of the given user
     * @param updatedUser the updated user object
     */
    public void updateUser(int id, User updatedUser)
    {
        for(int i = 0 ; i < userList.size() ; i++)
        {
            if(userList.get(i).getUser_id() == id)
            {
                userList.add(updatedUser);
                userList.remove(userList.get(i));
            }
        }

    }
}
