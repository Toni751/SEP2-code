package ESharing.Server.Persistance.address;

import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

import java.sql.SQLException;
import java.util.List;

public interface AddressDAO
{
  int create(Address address); //returns the id of the address
  Address readById(int address_id);
  Address readByAddress (String street, String number);
  void delete(int address_id);
}
