package ESharing.Server.Networking;

import ESharing.Server.Core.ServerModelFactory;
import ESharing.Server.Model.advertisement.ServerAdvertisementModel;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementClient;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementServer;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.Util.Events;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerAdvertisementHandler implements RMIAdvertisementServer
{
  private ServerAdvertisementModel serverModel;
  private PropertyChangeListener listenToNewAdReq;
  private PropertyChangeListener listenToAdApproved;
  private PropertyChangeListener listenToUpdatedAd;
  private PropertyChangeListener listenToDeletedAd;

  public ServerAdvertisementHandler()
  {
    try
    {
      UnicastRemoteObject.exportObject(this, 0);
      serverModel = ServerModelFactory.getInstance().getServerAdvertisementModel();
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
  public void approveAdvertisement(Advertisement ad)
  {
    serverModel.approveAdvertisement(ad);
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
        client.newApprovedAd((Advertisement) evt.getNewValue());
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
}
