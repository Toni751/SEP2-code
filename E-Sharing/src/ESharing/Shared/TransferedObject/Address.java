package ESharing.Shared.TransferedObject;

import java.io.Serializable;
import java.util.Objects;

/**
 * A class representing the address used in the system
 * @version 1.0
 * @author Group1
 */
public class Address implements Serializable
{
  private int address_id;
  private String street;
  private String number;
  private String city;
  private String postcode;

  /**
   * A constructor which sets all fields
   * @param street the value of street field
   * @param number the value of house number field
   * @param city the value of city field
   * @param postcode the value of postal code field
   */
  public Address(String street, String number, String city, String postcode)
  {
    this.street = street;
    this.number = number;
    this.city = city;
    this.postcode = postcode;
  }

  /**
   * Sets value of address id
   * @param address_id the value of address id
   */
  public void setAddress_id(int address_id)
  {
    this.address_id = address_id;
  }

  /**
   * Returns value of a street
   * @return the street value
   */
  public String getStreet()
  {
    return street;
  }

  /**
   * Returns value of a house number
   * @return the house number value
   */
  public String getNumber()
  {
    return number;
  }

  /**
   * Returns value of a city
   * @return the city value
   */
  public String getCity()
  {
    return city;
  }

  /**
   * Returns value of a postal code
   * @return the postal code value
   */
  public String getPostcode()
  {
    return postcode;
  }

  /**
   * Returns value of an address_id
   * @return the address_id value
   */
  public int getAddress_id()
  {
    return address_id;
  }

  /**
   * Sets value of street
   * @param street the value of street
   */
  public void setStreet(String street) {
    this.street = street;
  }

  /**
   * Sets value of house number
   * @param number the value of house number
   */
  public void setNumber(String number) {
    this.number = number;
  }

  /**
   * Sets value of city
   * @param city the value of city
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Sets value of postal code
   * @param postcode the value of postal code
   */
  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Address address = (Address) o;
    return Objects.equals(street, address.street) &&
            Objects.equals(number, address.number) &&
            Objects.equals(city, address.city) &&
            Objects.equals(postcode, address.postcode);
  }

  @Override
  public String toString()
  {
    return "Address{" + "address_id=" + address_id + ", street='" + street
        + '\'' + ", number='" + number + '\'' + ", city='" + city + '\''
        + ", postcode='" + postcode + '\'' + '}';
  }
}
