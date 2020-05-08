package ESharing.Shared.TransferedObject;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Objects;

public class Conversation implements Serializable {

    private User receiver;
    private User sender;
    boolean read;
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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        return Objects.equals(receiver, that.receiver) &&
                Objects.equals(sender, that.sender);
    }
}
