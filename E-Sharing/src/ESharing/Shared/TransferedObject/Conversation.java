package ESharing.Shared.TransferedObject;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;

public class Conversation implements Serializable {

    private User receiver;
    private User sender;
    private ArrayList<Message> messages;

    public Conversation(User sender, User receiver)
    {
        this.receiver = receiver;
        this.sender = sender;
        messages = new ArrayList<>();
    }


    public void addMessage(Message message)
    {
        messages.add(message);
    }

    public User getReceiver() {
        return receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
