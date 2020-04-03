package ESharing.Shared.TransferedObject;

import java.io.Serializable;

public class User implements Serializable
{
  private int user_id;
  private String username;
  private String password;
  private int phoneNumber;
  private Address address;

  public User(String username, String password, int phoneNumber,
      Address address, int user_id)
  {
    this.username = username;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.user_id =user_id;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public int getPhoneNumber()
  {
    return phoneNumber;
  }

  public Address getAddress()
  {
    return address;
  }

  public int getUser_id()
  {
    return user_id;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (!(obj instanceof User))
      return false;
    User other = (User) obj;

    return username.equals(other.username);
  }
}
