package ESharing.Server.Persistance.administrator;

import ESharing.Shared.TransferedObject.User;

import java.util.List;

/**
 * The DAO interface for managing the database requests made by the administrator
 * @version 1.0
 * @author Group1
 */
public interface AdministratorDAO{

    /**
     * Returns the collection with all users existing in the system database
     * @return the collection of all users existing in the system database
     */
    List<User> getAllUsers();
}
