package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

import java.sql.SQLException;
import java.util.List;

public interface AddressDAO
{
  int create(Address address); //returns the id of the address
  Address readById(int address_id);
  Address readByAddress (String street, String number, String city, String postcode);
  void delete(int address_id);
}
