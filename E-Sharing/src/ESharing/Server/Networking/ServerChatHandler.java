package ESharing.Server.Networking;

import ESharing.Server.Model.chat.ServerChatModel;
import ESharing.Server.Model.user.ServerModel;
import ESharing.Shared.Networking.chat.RMIChatServer;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerChatHandler implements RMIChatServer
{
  private ServerChatModel chatModel;

  public ServerChatHandler(ServerChatModel chatModel) throws RemoteException
  {
    UnicastRemoteObject.exportObject(this, 0);
    this.chatModel = chatModel;
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
    return getLastMessageWithEveryone(user);
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
}
