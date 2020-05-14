package ESharing.Client.Networking.chat;

import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.List;

public interface ClientChat extends PropertyChangeSubject
{
  List<Message> loadConversation (User sender, User receiver);
  int getNoUnreadMessages (User user);
  List<Message> getLastMessageWithEveryone (User user);
  void addMessage (Message message);
  void deleteMessagesForUser (User user);
  void makeMessageRead(Message message);


  void logoutUser();
  void userLoggedOut();
  List<User> getOnlineUsers();
}
