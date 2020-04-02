package ESharing.Shared.TransferedObject;

import java.io.Serializable;

public class Address implements Serializable
{
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
}
