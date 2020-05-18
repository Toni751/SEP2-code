package ESharing.Shared.TransferedObject;

import ESharing.Client.Model.UserActions.LoggedUser;
import javafx.scene.image.Image;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A class representing the user used in the system
 * @version 1.0
 * @author Group1
 */
public class User implements Serializable
{
  private int user_id;
  private String username;
  private String password;
  private String phoneNumber;
  private Address address;
  private int reportsNumber;
  private String creation_date;
  boolean administrator;
  byte[] avatar;


  /**
   * A constructor which sets all user fields
   * @param username the value of a user username
   * @param password the value of a user password
   * @param phoneNumber the value of a user phone number
   * @param address the address object representing the user address
   */
  public User(String username, String password, String phoneNumber, Address address)
  {
    this.username = username;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

  /**
   * One-argument constructor which sets all fields using the LoggedUser object
   * @param loggedUser the LoggedUser object used to set all fields
   */
  public User(LoggedUser loggedUser)
  {
    this.username = loggedUser.getUser().getUsername();
    this.password = loggedUser.getUser().getPassword();
    this.user_id = loggedUser.getUser().getUser_id();
    this.address = loggedUser.getUser().getAddress();
    this.phoneNumber = loggedUser.getUser().getPhoneNumber();
  }

  /**
   * Sets current user as administrator
   */
  public void setAsAdministrator()
  {
    administrator = true;
  }

  /**
   * Returns if logged user is set as administrator
   * @return the boolean value which represents the administrator status
   */

  public boolean isAdministrator()
  {
    return administrator;
  }
  /**
   * Sets value of user id
   * @param user_id the value of user id
   */
  public void setUser_id(int user_id)
  {
    this.user_id = user_id;
  }

  /**
   * Returns value of a username
   * @return the username value
   */
  public String getUsername()
  {
    return username;
  }

  /**
   * Returns value of a password
   * @return the password value
   */
  public String getPassword()
  {
    return password;
  }

  /**
   * Returns value of a phone number
   * @return the phone number value
   */
  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  /**
   * Returns value of an address
   * @return the address value
   */
  public Address getAddress()
  {
    return address;
  }

  /**
   * Sets value of a username
   * @param username the value of username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns number of reports
   * @return the number of reports
   */
  public int getReportsNumber() {
    return reportsNumber;
  }

  /**
   * Add new report to the user account
   */
  public void addNewReport()
  {
    reportsNumber++;
  }

  /**
   * Sets value of a phone number
   * @param phoneNumber the value of phone number
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Sets value of a password
   * @param password the value of password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Sets value of address
   * @param address the value of address
   */
  public void setAddress(Address address) {
    this.address = address;
  }

  /**
   * Returns value of a user id
   * @return the user id value
   */
  public int getUser_id()
  {
    return user_id;
  }

  /**
   * Sets the number of reports belong to the user
   * @param reportsNumber the number of reports
   */
  public void setReportsNumber(int reportsNumber) {
    this.reportsNumber = reportsNumber;
  }

  /**
   * Returns a string object with date of creation the account
   * @return the string with date of creation the account
   */
  public String getCreation_date() {
    return creation_date;
  }

  /**
   * Sets the creation account date
   * @param creation_date the date of creation the account
   */
  public void setCreation_date(String creation_date) {
    this.creation_date = creation_date;
  }

  public void setAvatar(byte[] avatar)
  {
    this.avatar = avatar;
  }

  public Image getAvatar() {
    Image avatar = new Image(new ByteArrayInputStream(this.avatar));
    return avatar;
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
