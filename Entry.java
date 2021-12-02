import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Entry {
    
    private String textContent;
    private String timeSent;
    private String userSender;
    private String userReceiver;
    private int type;
    
    private static final int liveEntry = 0;
    private static final int boxEntry = 1;

    public Entry(){}

    public Entry(String text, String origin, String destination){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.setTimeSent(dtf.format(LocalDateTime.now()));
        this.setUserSender(origin);
        this.setUserReceiver(destination);
        this.setTextContent(text);
        this.setType(liveEntry);
    }

    public Entry(String text){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.setTimeSent(dtf.format(LocalDateTime.now()));
        this.setTextContent(text);
        this.setType(boxEntry);
    }

    public String getTextContent() {
        return textContent;
    }
    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
    public String getTimeSent() {
        return timeSent;
    }
    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }
    public String getUserSender() {
        return userSender;
    }
    public void setUserSender(String userSender) {
        this.userSender = userSender;
    }
    public String getUserReceiver() {
        return userReceiver;
    }
    public void setUserReceiver(String userReceiver) {
        this.userReceiver = userReceiver;
    }
    public int getType() {
        return type;
    }
    public String toString() {
        String outString = "";
        outString += this.getTimeSent();
        outString += this.getUserSender();
        outString += this.getTextContent();

        return outString;
    }

    public void setType(int type) {
        this.type = type;
    }


}