package ESharing.Server.Networking;

import ESharing.Server.Model.advertisement.ServerAdvertisementModel;
import ESharing.Server.Model.chat.ServerChatModel;
import ESharing.Server.Model.user.ServerUserModel;
import ESharing.Shared.Networking.chat.RMIChatClient;
import ESharing.Shared.Networking.chat.RMIChatServer;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * The server networking handler for the chat, used for RMI
 * @version 1.0
 * @author Group1
 */
public class ServerChatHandler implements RMIChatServer
{
  private ServerChatModel chatModel;
  private PropertyChangeListener listenForNewMessage;
  private PropertyChangeListener listenForOnlineUser;
  private PropertyChangeListener listenForOfflineUser;
  private PropertyChangeListener listenForMessageRead;
  private ServerUserModel serverUserModel;
  private ServerAdvertisementModel  advertisementModel;

  /**
   * A constructor which initializes the server models with the given values, and exports the object
   * @param chatModel the value to be set for the chat model
   * @param serverUserModel the value to be set for the user model
   * @param advertisementModel the value to be set for the advertisement model
   * @throws RemoteException
   */
  public ServerChatHandler(ServerChatModel chatModel, ServerUserModel serverUserModel,ServerAdvertisementModel advertisementModel) throws RemoteException
  {
    UnicastRemoteObject.exportObject(this, 0);
    this.chatModel = chatModel;
    this.serverUserModel = serverUserModel;
    this.advertisementModel =advertisementModel;
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
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    };

    try
    {
      System.out.println("Registering in SCH user: " + chatClient.getLoggedUser().getUsername());
      chatModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString() + chatClient.getLoggedUser().getUser_id(), listenForNewMessage);
      advertisementModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString() + chatClient.getLoggedUser().getUser_id(), listenForNewMessage);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }

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

    serverUserModel.addPropertyChangeListener(Events.USER_ONLINE.toString(), listenForOnlineUser);


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
    serverUserModel.addPropertyChangeListener(Events.USER_OFFLINE.toString(), listenForOfflineUser);

    listenForMessageRead = evt -> {
      try {
        chatClient.messageRead((Message) evt.getNewValue());
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    };

    try
    {
      chatModel.addPropertyChangeListener(Events.MAKE_MESSAGE_READ.toString() + chatClient.getLoggedUser().getUser_id(), listenForMessageRead);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }

    chatModel.listeners();
  }

  @Override
  public void makeMessageRead(Message message){
    chatModel.makeMessageRead(message);
  }

  @Override public void userLoggedOut(User user)
  {
    serverUserModel.userLoggedOut(user);
  }

  @Override public List<User> getOnlineUsers()
  {
    return serverUserModel.getAllOnlineUsers();
  }

  @Override
  public void unRegisterUserAsAListener(RMIChatClient client){
    System.out.println("A USER IS REMOVED AS A SERVER LISTENER");
    serverUserModel.removePropertyChangeListener(Events.USER_OFFLINE.toString(), listenForOfflineUser);
    serverUserModel.removePropertyChangeListener(Events.USER_ONLINE.toString(), listenForOnlineUser);
    System.out.print("Before: ");
    chatModel.listeners();
    try
    {
      chatModel.removePropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString() + client.getLoggedUser().getUser_id(), listenForNewMessage);
      chatModel.removePropertyChangeListener(Events.MAKE_MESSAGE_READ.toString()  + client.getLoggedUser().getUser_id(), listenForMessageRead);
      advertisementModel.removePropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString() + client.getLoggedUser().getUser_id(), listenForNewMessage);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    System.out.print("After: ");
    chatModel.listeners();
  }
}
