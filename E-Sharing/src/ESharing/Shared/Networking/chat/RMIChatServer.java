package ESharing.Shared.Networking.chat;

import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIChatServer extends Remote
{
  List<Message> loadConversation (User sender, User receiver) throws RemoteException;
  int getNoUnreadMessages (User user) throws RemoteException;
  List<Message> getLastMessageWithEveryone (User user) throws RemoteException;
  void addMessage (Message message) throws RemoteException;
  void deleteMessagesForUser (User user) throws RemoteException;
  void registerChatCallback (RMIChatClient chatClient) throws RemoteException;
  void makeMessageRead(Message message) throws RemoteException;
  void unRegisterUserAsAListener() throws RemoteException;
  void userLoggedOut(User user) throws RemoteException;
  List<User> getOnlineUsers() throws RemoteException;
}
