package ESharing.Shared.TransferedObject;

import java.io.Serializable;

public class Message implements Serializable {

    private User sender;
    private User receiver;
    private String content;
    private String date;
    private boolean read;

    public Message(User sender, User receiver, String content, boolean read)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.read = read;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead() {
        this.read = true;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender=" + sender.getUsername() +
                ", receiver=" + receiver.getUsername() +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", read=" + read +
                '}';
    }
}
