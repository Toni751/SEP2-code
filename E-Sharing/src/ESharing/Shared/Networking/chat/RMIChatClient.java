package ESharing.Shared.Networking.chat;

import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIChatClient extends Remote
{
  void newMessageReceived(Message message) throws RemoteException;
  void newOnlineUser(User user) throws RemoteException;
  void newOfflineUser(User user) throws RemoteException;
}

