package ESharing.Client.Model.AdministratorModel;

import ESharing.Shared.TransferedObject.User;

import java.util.ArrayList;
import java.util.List;

public class AdministratorLists {

    private List<User> userList;
    private User selectedUser;
    //advertisements list

    private static AdministratorLists instance;

    private AdministratorLists()
    {
        userList = new ArrayList<>();
    }

    public static AdministratorLists getInstance() {
        if(instance == null)
            instance = new AdministratorLists();
        return instance;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

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

    public void setSelectedUser(User selectedUser) {
        System.out.println(selectedUser);
        this.selectedUser = selectedUser;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

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
