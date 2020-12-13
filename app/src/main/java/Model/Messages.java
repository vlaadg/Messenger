package Model;

import java.util.Date;

public class Messages {
    public User user;
    public String username;
    public String textMessage;
//    private ChatUser chatUser;
    private long messageDate;



    public Messages() {
    }

    public Messages(User user, String username, String textMessage) {
        this.user = user;
        this.username = username;
        this.textMessage = textMessage;
        this.messageDate = messageDate;
    }

    public Messages(String username, String textMessage ) {

        this.username = username;
        this.textMessage = textMessage;
        this.messageDate = new Date().getTime();

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public long getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(long messageDate) {
        this.messageDate = messageDate;
    }
}
