package ESharing.Shared.Networking.advertisement;

import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI networking interface for handling callbacks on the client regarding advertisements
 * @version 1.0
 * @author Group1
 */
public interface RMIAdvertisementClient extends Remote
{
  /**
   * Notifies the client's networking about a new advertisement request
   * @param ad the new advertisement request
   * @throws RemoteException if the remote method invocation fails
   */
  void newAdRequest (AdCatalogueAdmin ad) throws RemoteException;

  /**
   * Notifies the client's networking about a new approved ad
   * @param ad the new approved advertisement
   * @throws RemoteException if the remote method invocation fails
   */
  void newApprovedAd (CatalogueAd ad) throws RemoteException;

  /**
   * Notifies the client's networking about a removed advertisement
   * @param id the id of the removed advertisement
   * @throws RemoteException if the remote method invocation fails
   */
  void removedAd (int id) throws RemoteException;

  /**
   * Notifies the client's networking about a new report on an advertisement
   * @param id the id of the reported advertisement
   * @param newValue the new number of reports for the ad
   * @throws RemoteException if the remote method invocation fails
   */
  void newReportReceived(int id, int newValue) throws RemoteException;
}
