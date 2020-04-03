package ESharing.Shared.TransferedObject;

import java.io.Serializable;

public class Address implements Serializable
{
  private int address_id;
  private String street;
  private int number;
  private String city;
  private int postcode;

  public Address(int address_id,String street, int number, String city, int postcode)
  {
    this.street = street;
    this.number = number;
    this.city = city;
    this.postcode = postcode;
    this.address_id=address_id;
  }

  public String getStreet()
  {
    return street;
  }

  public int getNumber()
  {
    return number;
  }

  public String getCity()
  {
    return city;
  }

  public int getPostcode()
  {
    return postcode;
  }

  public int getAddress_id()
  {
    return address_id;
  }
}
