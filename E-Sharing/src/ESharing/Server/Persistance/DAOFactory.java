package ESharing.Server.Persistance;

import ESharing.Server.Persistance.address.AddressDAO;
import ESharing.Server.Persistance.address.AddressDAOManager;
import ESharing.Server.Persistance.administrator.AdministratorDAO;
import ESharing.Server.Persistance.administrator.AdministratorDAOManager;
import ESharing.Server.Persistance.advertisement.AdvertisementDAO;
import ESharing.Server.Persistance.advertisement.AdvertisementDAOManager;
import ESharing.Server.Persistance.message.MessageDAO;
import ESharing.Server.Persistance.message.MessageDAOManager;
import ESharing.Server.Persistance.user.UserDAO;
import ESharing.Server.Persistance.user.UserDAOManager;

public class DAOFactory
{
  private AddressDAO addressDAO;
  private AdministratorDAO administratorDAO;
  private AdvertisementDAO advertisementDAO;
  private MessageDAO messageDAO;
  private UserDAO userDAO;

  public DAOFactory ()
  {
    addressDAO = new AddressDAOManager();
    administratorDAO = new AdministratorDAOManager();
    messageDAO = new MessageDAOManager(administratorDAO);
    advertisementDAO = new AdvertisementDAOManager(userDAO);
    userDAO = new UserDAOManager(addressDAO, messageDAO);
  }

  public AddressDAO getAddressDAO()
  {
    return addressDAO;
  }

  public AdministratorDAO getAdministratorDAO()
  {
    return administratorDAO;
  }

  public AdvertisementDAO getAdvertisementDAO()
  {
    return advertisementDAO;
  }

  public MessageDAO getMessageDAO()
  {
    return messageDAO;
  }

  public UserDAO getUserDAO()
  {
    return userDAO;
  }
}
