package ESharing.Server.Persistance.user;

import ESharing.Shared.TransferedObject.User;

import java.nio.file.Path;

public interface UserDAO
{
  boolean create(User user);
  User readById(int user_id);
  User readByUserNameAndPassword(String searchString, String password);
  boolean update(User user);
  boolean delete(User user);

  boolean changeAvatar(String path, int userId);

  int addNewUserReport(int user_id);
}
