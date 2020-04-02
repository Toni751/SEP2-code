package ESharing.Shared.TransferedObject;

import java.io.Serializable;

public class User implements Serializable
{
  private String username;
  private String password;
  private String phoneNumber;
  private Address address;

  public User(String username, String password, String phoneNumber,
      Address address)
  {
    this.username = username;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.address = address;
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

  @Override
  public boolean equals(Object obj)
  {
    if (!(obj instanceof User))
      return false;
    User other = (User) obj;

    return username.equals(other.username);
  }
}
