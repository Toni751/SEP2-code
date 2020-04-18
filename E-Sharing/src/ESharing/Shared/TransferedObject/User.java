package ESharing.Shared.TransferedObject;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable
{
  private int user_id;
  private String username;
  private String password;
  private String phoneNumber;
  private Address address;

  public User(String username, String password, String phoneNumber, Address address)
  {
    this.username = username;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.address = address;
//    this.user_id =user_id;
  }

  public void setUser_id(int user_id)
  {
    this.user_id = user_id;
  }

  public void updateInformation(User updatedUser)
  {
    this.username = updatedUser.getUsername();
    this.password = updatedUser.getPassword();
    this.user_id = updatedUser.getUser_id();
    this.address = updatedUser.getAddress();
    this.phoneNumber = updatedUser.getPhoneNumber();
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public Address getAddress()
  {
    return address;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public void setUserId(int userId)
  {
    this.user_id = userId;
  }

  public int getUser_id()
  {
    return user_id;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return  Objects.equals(username, user.username) &&
            Objects.equals(password, user.password) &&
            Objects.equals(phoneNumber, user.phoneNumber) &&
            address.equals(user.address);
  }
}
