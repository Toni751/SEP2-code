package ESharing.Shared.TransferedObject;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable
{
  private int address_id;
  private String street;
  private String number;
  private String city;
  private String postcode;

  public Address(String street, String number, String city, String postcode)
  {
    this.street = street;
    this.number = number;
    this.city = city;
    this.postcode = postcode;
//    this.address_id=address_id;
  }

  public void setAddress_id(int address_id)
  {
    this.address_id = address_id;
  }

  public String getStreet()
  {
    return street;
  }

  public String getNumber()
  {
    return number;
  }

  public String getCity()
  {
    return city;
  }

  public String getPostcode()
  {
    return postcode;
  }

  public int getAddress_id()
  {
    return address_id;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public void setCity(String city) {
    this.city = city;
  }

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
