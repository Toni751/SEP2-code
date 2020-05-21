package ESharing.Shared.Networking.advertisement;

import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIAdvertisementServer extends Remote
{
  boolean addAdvertisement (Advertisement ad) throws RemoteException;
  boolean approveAdvertisement (Advertisement ad) throws RemoteException;
  boolean removeAdvertisement (Advertisement ad) throws RemoteException;
//  boolean editAdvertisement (Advertisement ad) throws RemoteException;
  void registerClientCallback (RMIAdvertisementClient client) throws RemoteException;
  void unRegisterUserAsAListener() throws RemoteException;
  List<Advertisement> selectAllAdvertisements() throws RemoteException;
  List<CatalogueAd> getAdvertisementsCatalogue() throws RemoteException;
  Advertisement getAdvertisementById (int id) throws RemoteException;
  List<CatalogueAd> getAdvertisementsByUser(int user_id) throws RemoteException;
  boolean addNewAdvertisementReport(int advertisementID) throws RemoteException;
  List<AdCatalogueAdmin> getAdminAdCatalogue() throws RemoteException;
}
