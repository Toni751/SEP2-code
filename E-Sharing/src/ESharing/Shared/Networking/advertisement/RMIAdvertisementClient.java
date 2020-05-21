package ESharing.Shared.Networking.advertisement;

import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIAdvertisementClient extends Remote
{
  void newAdRequest (AdCatalogueAdmin ad) throws RemoteException;
  void newApprovedAd (CatalogueAd ad) throws RemoteException;
  void removedAd (int id) throws RemoteException;
  void newReportReceived(int id, int newValue) throws RemoteException;
}
