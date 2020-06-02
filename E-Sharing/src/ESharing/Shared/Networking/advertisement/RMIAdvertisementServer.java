package ESharing.Shared.Networking.advertisement;

import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * RMI networking interface for remote method invocation on the server regarding advertisement
 * @version 1.0
 * @author Group1
 */
public interface RMIAdvertisementServer extends Remote
{
  /**
   * Sends a request to the server to add a new advertisement
   * @param ad the new ad to be added
   * @return true if the advertisement was added successfully, false otherwise
   * @throws RemoteException if the remote method invocation fails
   */
  boolean addAdvertisement (Advertisement ad) throws RemoteException;

  /**
   * Sends a request to the server to approve a given advertisement
   * @param id the id of the advertisement to be approved
   * @return true if the advertisement was approved successfully, false otherwise
   * @throws RemoteException if the remote method invocation fails
   */
  boolean approveAdvertisement (int id) throws RemoteException;

  /**
   * Sends a request to the server to remove a given advertisement
   * @param id the id of the advertisement to be removed
   * @return true if the advertisement was removed successfully, false otherwise
   * @throws RemoteException if the remote method invocation fails
   */
  boolean removeAdvertisement (int id) throws RemoteException;

  /**
   * Sends a request to the server to register the client for callback
   * @param client the client to be registered for callback methods
   * @throws RemoteException if the remote method invocation fails
   */
  void registerClientCallback (RMIAdvertisementClient client) throws RemoteException;

  /**
   * Sends a request to the server to unregister a user as a listener
   * @throws RemoteException if the remote method invocation fails
   */
  void unRegisterUserAsAListener() throws RemoteException;

  /**
   * Sends a request to the server to retrieve all advertisements
   * @return the list with all advertisements
   * @throws RemoteException if the remote method invocation fails
   */
  List<Advertisement> selectAllAdvertisements() throws RemoteException;

  /**
   * Sends a request to the server to retrieve the catalogue with all advertisements
   * @return the list with all advertisements, as catalogue objects
   * @throws RemoteException if the remote method invocation fails
   */
  List<CatalogueAd> getAdvertisementsCatalogue() throws RemoteException;

  /**
   * Sends a request to the server to retrieve an advertisement by its id
   * @param id the id of the advertisement to be retrieved
   * @return the advertisement with the given id, or null if it doesn't exist
   * @throws RemoteException if the remote method invocation fails
   */
  Advertisement getAdvertisementById (int id) throws RemoteException;

  /**
   * Sends a request to the server to retrieve all the advertisements belonging to a given user
   * @param user_id the id of the user
   * @return the list with all the user's advertisements listed as catalogue items
   * @throws RemoteException if the remote method invocation fails
   */
  List<CatalogueAd> getAdvertisementsByUser(int user_id) throws RemoteException;

  /**
   * Sends a request to the server to add a new report to a given advertisement
   * @param advertisementID the id of the advertisement to be reported
   * @return true if the advertisement was reported successfully, false otherwise
   * @throws RemoteException if the remote method invocation fails
   */
  boolean addNewAdvertisementReport(int advertisementID) throws RemoteException;

  /**
   * Sends a request to the server to retrieve all advertisements for the admin
   * @return the list with all ads as admin catalogue items
   * @throws RemoteException if the remote method invocation fails
   */
  List<AdCatalogueAdmin> getAdminAdCatalogue() throws RemoteException;

  /**
   * Sends a request to the server to rate a given advertisement
   * @param ad_id the id of the advertisement to be rated
   * @param user_id the id of the user who rated
   * @param rating the rating value
   * @return true if the advertisement was rated successfully, false otherwise
   * @throws RemoteException if the remote method invocation fails
   */
  boolean addRating (int ad_id, int user_id, double rating) throws RemoteException;

  /**
   * Sends a request to the server to retrieve the average rating of a given advertisement
   * @param ad_id the id of the given advertisement
   * @return the average rating of the advertisement
   * @throws RemoteException if the remote method invocation fails
   */
  double retrieveAdRating (int ad_id) throws RemoteException;

  /**
   * Sends a request to the server to retrieve the total number of advertisements
   * @return the total number of advertisements
   * @throws RemoteException if the remote method invocation fails
   */
  int getAdvertisementNumber() throws RemoteException;
}
