package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

import java.sql.SQLException;
import java.util.List;

public interface AddressDAO
{
  Address create(int address_id,String street, int number, String city, int postcode) throws SQLException;
  Address readById(int address_id) throws SQLException;
  List<Address> readByStreet(String searchString) throws SQLException;
  void update(Address address) throws SQLException;
  void delete(Address address) throws SQLException;
}
