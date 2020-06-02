package ESharing.Server.Persistance.address;

import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

import java.sql.SQLException;
import java.util.List;

/**
 * The DAO interface for managing the address database table
 * @version 1.0
 * @author Group1
 */
public interface AddressDAO
{
  /**
   * Adds a new address to the database
   * @param address the address to be added in the database
   * @return the id of the created address, if it was created, 0 otherwise
   */
  int create(Address address);
//  Address readById(int address_id);

  /**
   * Reads from the database table an adress by the street and number
   * @param street the given street
   * @param number the given number
   * @return the address which has the given street and number, if it exists, null otherwise
   */
  Address readByAddress (String street, String number);

  /**
   * Deleted the address with a given id from the database
   * @param address_id the id of the address to be deleted
   */
  void delete(int address_id);
}
