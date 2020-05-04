package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.User;

import java.util.List;

public interface AdministratorDAO{

    /**
     * Returns the collection with all users existing in the system database
     * @return the collection of all users existing in the system database
     */
    List<User> getAllUsers();
}
