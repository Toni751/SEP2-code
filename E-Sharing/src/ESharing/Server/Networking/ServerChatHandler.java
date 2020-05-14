package ESharing.Server.Networking;

import ESharing.Server.Model.chat.ServerChatModel;
import ESharing.Server.Model.user.ServerModel;
import ESharing.Shared.Networking.chat.RMIChatClient;
import ESharing.Shared.Networking.chat.RMIChatServer;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerChatHandler implements RMIChatServer
{
  private ServerChatModel chatModel;
  private PropertyChangeListener listenForNewMessage;
  private PropertyChangeListener listenForOnlineUser;
  private PropertyChangeListener listenForOfflineUser;
  private ServerModel serverModel;

  public ServerChatHandler(ServerChatModel chatModel, ServerModel serverModel) throws RemoteException
  {
    UnicastRemoteObject.exportObject(this, 0);
    this.chatModel = chatModel;
    this.serverModel =serverModel;
  }

  @Override
  public List<Message> loadConversation(User sender, User receiver)
  {
    return chatModel.loadConversation(sender, receiver);
  }

  @Override
  public int getNoUnreadMessages(User user)
  {
    return chatModel.getNoUnreadMessages(user);
  }

  @Override
  public List<Message> getLastMessageWithEveryone(User user)
  {
    return chatModel.getLastMessageWithEveryone(user);
  }

  @Override
  public void addMessage(Message message)
  {
    chatModel.addMessage(message);
  }

  @Override
  public void deleteMessagesForUser(User user)
  {
    chatModel.deleteMessagesForUser(user);
  }

  @Override
  public void registerChatCallback(RMIChatClient chatClient){
    listenForNewMessage = evt -> {
      try {
        chatClient.newMessageReceived((Message) evt.getNewValue());
        System.out.println("new message server side");
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    };

    chatModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), listenForNewMessage);

    listenForOnlineUser = evt ->{
      try{
        chatClient.newOnlineUser((User) evt.getNewValue());
        System.out.println("new user online");
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    };

    serverModel.addPropertyChangeListener(Events.USER_ONLINE.toString(), listenForOnlineUser);


    listenForOfflineUser = evt ->{
      try{
        chatClient.newOfflineUser((User) evt.getNewValue());
        System.out.println("the user is offline");
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    };
    serverModel.addPropertyChangeListener(Events.USER_OFFLINE.toString(), listenForOfflineUser);
  }

  @Override
  public void makeMessageRead(Message message){
    chatModel.makeMessageRead(message);
  }

  @Override
  public void unRegisterUserAsAListener() throws RemoteException {
    //Oliwer
  }

  @Override public void userLoggedOut(User user) throws RemoteException
  {
    serverModel.userLoggedOut(user);
  }

  @Override public List<User> getOnlineUsers()
  {
    return serverModel.getAllOnlineUsers();
  }
}
