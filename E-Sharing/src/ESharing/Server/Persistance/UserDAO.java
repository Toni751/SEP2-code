package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO
{
  User create(String username, String password, int phoneNumber,
      Address address, int user_id) throws SQLException;
  User readById(int user_id) throws SQLException;
  List<User> readByUserName(String searchString) throws SQLException;
  void update(User user) throws SQLException;
  void delete(User user) throws SQLException;

}
