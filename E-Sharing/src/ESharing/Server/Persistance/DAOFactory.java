package ESharing.Server.Persistance;

import ESharing.Server.Persistance.address.AddressDAO;
import ESharing.Server.Persistance.address.AddressDAOManager;
import ESharing.Server.Persistance.administrator.AdministratorDAO;
import ESharing.Server.Persistance.administrator.AdministratorDAOManager;
import ESharing.Server.Persistance.advertisement.AdvertisementDAO;
import ESharing.Server.Persistance.advertisement.AdvertisementDAOManager;
import ESharing.Server.Persistance.message.MessageDAO;
import ESharing.Server.Persistance.message.MessageDAOManager;
import ESharing.Server.Persistance.reservation.ReservationDAO;
import ESharing.Server.Persistance.reservation.ReservationDAOManager;
import ESharing.Server.Persistance.user.UserDAO;
import ESharing.Server.Persistance.user.UserDAOManager;

public class DAOFactory
{
  private AddressDAO addressDAO;
  private AdministratorDAO administratorDAO;
  private AdvertisementDAO advertisementDAO;
  private MessageDAO messageDAO;
  private UserDAO userDAO;
  private ReservationDAO reservationDAO;

  public DAOFactory ()
  {
    addressDAO = new AddressDAOManager();
    administratorDAO = new AdministratorDAOManager();
    messageDAO = new MessageDAOManager(administratorDAO);
    userDAO = new UserDAOManager(addressDAO, messageDAO);
    reservationDAO = new ReservationDAOManager(userDAO);
    advertisementDAO = new AdvertisementDAOManager(userDAO, reservationDAO);
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

  public ReservationDAO getReservationDAO() {
    return reservationDAO;
  }
}
