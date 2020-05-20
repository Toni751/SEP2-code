package ESharing.Server.Networking;

import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Server.Core.ServerModelFactory;
import ESharing.Server.Model.chat.ServerChatModel;
import ESharing.Server.Model.user.ServerModel;
import ESharing.Shared.Networking.chat.RMIChatClient;
import ESharing.Shared.Networking.chat.RMIChatServer;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerChatHandler implements RMIChatServer
{
  private ServerChatModel chatModel;
  private PropertyChangeListener listenForNewMessage;
  private PropertyChangeListener listenForOnlineUser;
  private PropertyChangeListener listenForOfflineUser;
  private PropertyChangeListener listenForMessageRead;
  private ServerModel serverModel;

  public ServerChatHandler(ServerChatModel chatModel, ServerModel serverModel) throws RemoteException
  {
    UnicastRemoteObject.exportObject(this, 0);
    this.chatModel = chatModel;
    this.serverModel = serverModel;
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
    serverModel.userLoggedOut(user);
  }

  @Override public List<User> getOnlineUsers()
  {
    return serverModel.getAllOnlineUsers();
  }

  @Override
  public void unRegisterUserAsAListener(RMIChatClient client){
    System.out.println("A USER IS REMOVED AS A SERVER LISTENER");
    serverModel.removePropertyChangeListener(Events.USER_OFFLINE.toString(), listenForOfflineUser);
    serverModel.removePropertyChangeListener(Events.USER_ONLINE.toString(), listenForOnlineUser);
    System.out.print("Before: ");
    chatModel.listeners();
    try
    {
      chatModel.removePropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString() + client.getLoggedUser().getUser_id(), listenForNewMessage);
      chatModel.removePropertyChangeListener(Events.MAKE_MESSAGE_READ.toString()  + client.getLoggedUser().getUser_id(), listenForMessageRead);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    System.out.print("After: ");
    chatModel.listeners();
  }
}
