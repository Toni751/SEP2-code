package ESharing.Shared.TransferedObject;

import java.io.Serializable;

public class Message implements Serializable {

    private User sender;
    private User receiver;
    private String content;

    public Message(User sender, User receiver, String content)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }
}
