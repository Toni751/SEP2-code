package ESharing.Server.Networking;

import ESharing.Server.Core.ServerModelFactory;
import ESharing.Server.Model.advertisement.ServerAdvertisementModel;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementClient;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementServer;
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
  private PropertyChangeListener listenToUpdatedAd;
  private PropertyChangeListener listenToDeletedAd;

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
  public boolean approveAdvertisement(Advertisement ad)
  {
    return serverModel.approveAdvertisement(ad);
  }

  @Override
  public boolean removeAdvertisement(Advertisement ad)
  {
    return serverModel.removeAdvertisement(ad);
  }

  @Override
  public boolean editAdvertisement(Advertisement ad)
  {
    return serverModel.editAdvertisement(ad);
  }

  @Override
  public void registerClientCallback(RMIAdvertisementClient client)
  {
    listenToNewAdReq = evt -> {
      try
      {
        client.newAdRequest((Advertisement) evt.getNewValue());
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
        client.removedAd((Advertisement) evt.getNewValue());
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    };
    listenToUpdatedAd = evt -> {
      try
      {
        client.updatedAd((Advertisement) evt.getNewValue());
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    };
    serverModel.addPropertyChangeListener(Events.NEW_AD_REQUEST.toString(), listenToNewAdReq);
    serverModel.addPropertyChangeListener(Events.NEW_APPROVED_AD.toString(), listenToAdApproved);
    serverModel.addPropertyChangeListener(Events.AD_REMOVED.toString(), listenToDeletedAd);
    serverModel.addPropertyChangeListener(Events.AD_UPDATED.toString(), listenToUpdatedAd);
  }

  @Override
  public void unRegisterUserAsAListener() {

    serverModel.removePropertyChangeListener(Events.NEW_AD_REQUEST.toString(), listenToNewAdReq);
    serverModel.removePropertyChangeListener(Events.NEW_APPROVED_AD.toString(), listenToAdApproved);
    serverModel.removePropertyChangeListener(Events.AD_REMOVED.toString(), listenToDeletedAd);
    serverModel.removePropertyChangeListener(Events.AD_UPDATED.toString(), listenToUpdatedAd);
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
    return null;
  }

  @Override
  public boolean addNewAdvertisementReport(int advertisementID) {
    return serverModel.addNewAdvertisementReport(advertisementID);
  }
}
