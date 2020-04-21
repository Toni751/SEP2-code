package ESharing.Client.Model.UserAccount;

import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

public class LoggedUser {

    private int user_id;
    private String username;
    private String password;
    private String phoneNumber;
    private Address address;
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
        this.user_id = user.getUser_id();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
    }

    public void logoutUser()
    {
        loggedUser = null;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
