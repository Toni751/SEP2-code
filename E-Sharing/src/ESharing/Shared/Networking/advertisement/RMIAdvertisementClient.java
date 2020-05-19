package ESharing.Shared.Networking.advertisement;

import ESharing.Shared.TransferedObject.Advertisement;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIAdvertisementClient extends Remote
{
  void newAdRequest (Advertisement ad) throws RemoteException;
  void newApprovedAd (Advertisement ad) throws RemoteException;
  void updatedAd (Advertisement ad) throws RemoteException;
  void removedAd (Advertisement ad) throws RemoteException;
}