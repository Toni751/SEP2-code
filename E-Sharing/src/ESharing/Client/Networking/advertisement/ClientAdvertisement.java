package ESharing.Client.Networking.advertisement;

import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.rmi.RemoteException;

public interface ClientAdvertisement extends PropertyChangeSubject
{
  void initializeConnection();
  boolean addAdvertisement (Advertisement ad);
  void approveAdvertisement (Advertisement ad);
  boolean removeAdvertisement (Advertisement ad);
  boolean editAdvertisement (Advertisement ad);
}
