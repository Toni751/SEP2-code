package ESharing.Server.Core;

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

/**
 * The DAO factory class, which creates and retrieves all DAO classes
 * @version 1.0
 * @author Group1
 */
public class DAOFactory
{
  private AddressDAO addressDAO;
  private AdministratorDAO administratorDAO;
  private AdvertisementDAO advertisementDAO;
  private MessageDAO messageDAO;
  private UserDAO userDAO;
  private ReservationDAO reservationDAO;

  /**
   * No-argument constructor which initializes all the DAO manager instances
   */
  public DAOFactory ()
  {
    addressDAO = new AddressDAOManager();
    administratorDAO = new AdministratorDAOManager();
    messageDAO = new MessageDAOManager(administratorDAO);
    userDAO = new UserDAOManager(addressDAO, messageDAO);
    reservationDAO = new ReservationDAOManager(userDAO);
    advertisementDAO = new AdvertisementDAOManager(userDAO, reservationDAO,messageDAO);
  }

  /**
   * Returns the address dao
   * @return the address dao
   */
  public AddressDAO getAddressDAO()
  {
    return addressDAO;
  }

  /**
   * Returns the administrator dao
   * @return the administrator dao
   */
  public AdministratorDAO getAdministratorDAO()
  {
    return administratorDAO;
  }

  /**
   * Returns the advertisement dao
   * @return the advertisement dao
   */
  public AdvertisementDAO getAdvertisementDAO()
  {
    return advertisementDAO;
  }

  /**
   * Returns the message dao
   * @return the message dao
   */
  public MessageDAO getMessageDAO()
  {
    return messageDAO;
  }

  /**
   * Returns the user dao
   * @return the user dao
   */
  public UserDAO getUserDAO()
  {
    return userDAO;
  }

  /**
   * Returns the reservation dao
   * @return the reservation dao
   */
  public ReservationDAO getReservationDAO() {
    return reservationDAO;
  }
}
