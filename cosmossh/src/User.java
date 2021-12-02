import java.time.*;
import java.time.format.DateTimeFormatter;

public class User {


    private String userName;
    private int id;

    private String currentAddress;
    private int currentPort;




    public User(String name) {
        this.setUserName(name);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddssmmHH");
        this.setId(Integer.parseInt(dtf.format(LocalDateTime.now()).toString()));
        this.setId(0);
    }

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        String outString = "";
        outString += this.getId() + ":";
        outString += this.getUserName();

        return outString;
    }

    
    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public int getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(int currentPort) {
        this.currentPort = currentPort;
    }

}