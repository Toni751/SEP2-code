package ESharing.Server.Networking;

import ESharing.Server.Core.ServerModelFactory;
import ESharing.Server.Model.advertisement.ServerAdvertisementModel;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementClient;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementServer;
import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.Events;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerAdvertisementHandler implements RMIAdvertisementServer
{
  private ServerAdvertisementModel serverModel;
  private PropertyChangeListener listenToNewAdReq;
  private PropertyChangeListener listenToAdApproved;
  private PropertyChangeListener listenToDeletedAd;
  private PropertyChangeListener listenToNewReport;

  public ServerAdvertisementHandler(ServerAdvertisementModel serverModel)
  {
    try
    {
      UnicastRemoteObject.exportObject(this, 0);
      this.serverModel = serverModel;
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public boolean addAdvertisement(Advertisement ad)
  {
    return serverModel.addAdvertisement(ad);
  }

  @Override
  public boolean approveAdvertisement(int id)
  {
    return serverModel.approveAdvertisement(id);
  }

  @Override
  public boolean removeAdvertisement(int id)
  {
    return serverModel.removeAdvertisement(id);
  }

//  @Override
//  public boolean editAdvertisement(Advertisement ad)
//  {
//    return serverModel.editAdvertisement(ad);
//  }

  @Override
  public void registerClientCallback(RMIAdvertisementClient client)
  {
    listenToNewAdReq = evt -> {
      try
      {
        client.newAdRequest((AdCatalogueAdmin) evt.getNewValue());
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    };
    listenToAdApproved = evt -> {
      try
      {
        client.newApprovedAd((CatalogueAd) evt.getNewValue());
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    };
    listenToDeletedAd = evt -> {
      try
      {
        client.removedAd((int) evt.getNewValue());
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    };

    listenToNewReport = evt -> {

      try {
        client.newReportReceived((int)evt.getOldValue() ,(int) evt.getNewValue());
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    };
    serverModel.addPropertyChangeListener(Events.NEW_AD_REQUEST.toString(), listenToNewAdReq);
    serverModel.addPropertyChangeListener(Events.NEW_APPROVED_AD.toString(), listenToAdApproved);
    serverModel.addPropertyChangeListener(Events.AD_REMOVED.toString(), listenToDeletedAd);
    serverModel.addPropertyChangeListener(Events.NEW_ADVERTISEMENT_REPORT.toString(), listenToNewReport);
  }

  @Override
  public void unRegisterUserAsAListener() {

    serverModel.removePropertyChangeListener(Events.NEW_AD_REQUEST.toString(), listenToNewAdReq);
    serverModel.removePropertyChangeListener(Events.NEW_APPROVED_AD.toString(), listenToAdApproved);
    serverModel.removePropertyChangeListener(Events.AD_REMOVED.toString(), listenToDeletedAd);
    serverModel.removePropertyChangeListener(Events.NEW_ADVERTISEMENT_REPORT.toString(), listenToNewReport);
  }

  @Override
  public List<Advertisement> selectAllAdvertisements() {
    return serverModel.selectAllAdvertisements();
  }

  @Override
  public List<CatalogueAd> getAdvertisementsCatalogue()
  {
    return serverModel.getAdvertisementsCatalogue();
  }

  @Override
  public Advertisement getAdvertisementById(int id)
  {
    return serverModel.getAdvertisementById(id);
  }

  @Override
  public List<CatalogueAd> getAdvertisementsByUser(int user_id)
  {
    return serverModel.getAdvertisementsCatalogueForUser(user_id);
  }

  @Override
  public boolean addNewAdvertisementReport(int advertisementID) {
    return serverModel.addNewAdvertisementReport(advertisementID);
  }

  @Override
  public List<AdCatalogueAdmin> getAdminAdCatalogue()
  {
    return serverModel.getAdminAdCatalogue();
  }

  @Override
  public boolean addRating(int ad_id, int user_id, double rating)
      throws RemoteException
  {
    return serverModel.addRating(ad_id, user_id, rating);
  }

  @Override
  public double retrieveAdRating(int ad_id) throws RemoteException
  {
    return serverModel.retrieveAdRating(ad_id);
  }
}
