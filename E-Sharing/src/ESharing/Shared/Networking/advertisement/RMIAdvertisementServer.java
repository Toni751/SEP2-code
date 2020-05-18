package ESharing.Shared.Networking.advertisement;

import ESharing.Shared.TransferedObject.Advertisement;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIAdvertisementServer extends Remote
{
  boolean addAdvertisement (Advertisement ad) throws RemoteException;
  void approveAdvertisement (Advertisement ad) throws RemoteException;
  boolean removeAdvertisement (Advertisement ad) throws RemoteException;
  boolean editAdvertisement (Advertisement ad) throws RemoteException;
  void registerClientCallback (RMIAdvertisementClient client) throws RemoteException;
  void unRegisterUserAsAListener() throws RemoteException;
}
