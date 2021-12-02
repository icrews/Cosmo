import java.io.*;
import java.util.*;

public class THIO {
    private static final int BEGINTHIO = 0;
    private static final int IDENTIFY = 1;
    private static final int LIVEBOX = 2;
    private static final int LIVEIDENTIFY = 3;
    private static final int BOXSTATUS = 4;
    private static final int LIVEINTERACT = 5;
    private static final int BOXOPEN = 6;
    private static final int BOXINTERACT = 7;
    private static final int CLOSING = 8;

    private int state = BEGINTHIO;

    private String thioMessage = "";
    private String connectedAddress;
    private int connectedPort;

    private ArrayList<BOX> boxes = new ArrayList<BOX>();
    
    static UserList knownUsers;
    static UserList knownLive;

    private BOX openBox;
    
    
    public THIO() {

    }

    public THIO(String connectedAddress, int connectedPort) {
        this.connectedAddress = connectedAddress;
        this.connectedPort = connectedPort;
        this.thioMessage = "";
    }

    public String processInput(String userMessage) {
        
        if (userMessage != null){
            if (userMessage.toLowerCase().equals("end")){
                state = CLOSING;
            }
        }
        switch (state) {
            case BEGINTHIO: 
                    thioMessage = stateBEGINTHIO();
                    state = IDENTIFY;
                    break;

            case IDENTIFY: 
                    thioMessage = stateIDENTIFY(userMessage);
                    state = LIVEBOX;
                    break;

            case LIVEBOX: 
                    thioMessage = stateLIVEBOX(userMessage);;
                    if (userMessage.toLowerCase().equals("live")){
                        state = LIVEIDENTIFY;
                    }
                    else if (userMessage.toLowerCase().equals("box")){
                         state = BOXSTATUS;
                    }
                    else {
                        state = LIVEBOX;
                    }
                    break;

            case LIVEIDENTIFY: 
                    thioMessage = stateLIVEIDENTIFY();
                    state = LIVEINTERACT;
                    break;
            case BOXSTATUS: 
                    thioMessage = stateBOXSTATUS();
                    state = BOXOPEN;
                    break;
            case LIVEINTERACT: thioMessage = stateLIVEINTERACT();
                    state = LIVEINTERACT;
                    break;
            case BOXOPEN: 
                    thioMessage = stateBOXOPEN(userMessage);
                    state = BOXINTERACT;
                    break; 
            case BOXINTERACT: 
                    thioMessage = stateBOXINTERACT(userMessage);
                    state = BOXINTERACT;
                    break;
            case CLOSING: thioMessage = stateCLOSING();
                    break;

        }


        return thioMessage;
    }

    String stateBEGINTHIO() {
        String message = "Welcome, user! I am THIO and I will be helping you today. You may cease this connection at any time by saying END. Please Identify Yourself.";
        return message;
    }

    String stateIDENTIFY(String userMessage) {
        String message = "";
        User user = new User(userMessage);
        boolean userExists = knownUsers.contains(user);
       

        if (userExists == false){
            knownUsers.addUser(user);
            message += "Created new User [" + user.toString() + "]. ";
        }
        else {
            message += "User found: " + user.toString() + ". ";
        }


        message += "Would you like to use LIVE chat or BOX chat? [Live|Box]";
        return message;
    }


    String stateLIVEBOX(String userMessage) {
        String message = "";
        if (userMessage.toLowerCase().equals("live")){
            message = "Please choose a Live User to connect to.";
        }
        else if (userMessage.toLowerCase().equals("box")){
            message = "Currently open BOXes: [Press Enter]";
        }
        else {
            message = "Please choose either Live or Box!";
        }
        return message;
    }

    String stateLIVEIDENTIFY() {
        return stateCLOSING(); //filler

    }

    String stateBOXSTATUS() {
        String message = "";
        if (boxes.isEmpty()){
            message += "No open BOXes. ";
            message += "Please choose the name for your first BOX:";
        }
        else {
            for (BOX box : boxes){
                message += box.getName() + ", ";
            }
            message += ". Please choose one of these BOXes to display. You can also type a name not shown to create a new BOX.";

            
        }
        return message;
    }

    String stateLIVEINTERACT() {
        return stateCLOSING(); //filler
    }

   
    String stateBOXOPEN(String userMessage) {
        String message = "";
        for (BOX box : this.getBoxes()){
            String currentName = box.getName();
            if (currentName.toLowerCase().equals(userMessage.toLowerCase())){
                openBox = box;
            }
        }
        if (openBox == null){
            BOX newBox = new BOX(userMessage);
            boxes.add(newBox);
            message += "<CREATING NEW BOX: " + userMessage + ">";
            openBox = newBox;
        }

        message += "<PRINTING BOX CONTENTS>";
        message += openBox.toString();
        message += "<END OF BOX CONTENTS>";

        message += "Add entry to " + openBox.getName() + ":";
        

        return message;

    }

    String stateBOXINTERACT(String userMessage){
        String message = "";

        openBox.addEntry(userMessage);

        message += "<PRINTING BOX CONTENTS>";
        message += openBox.toString();
        message += "<END OF BOX CONTENTS>";
        message += "Add entry to " + openBox.getName() + ":";
        return message;
    }


    String stateCLOSING() {
        String message = "<CLOSE>";
        return message;
    }

    
    public ArrayList<BOX> getBoxes() {
        return boxes;
    }

    public static UserList getKnownUsers() {
        return knownUsers;
    }

    public static void setKnownUsers(UserList usersKnown) {
        THIO.knownUsers = usersKnown;
    }

    public static UserList getKnownLive() {
        return knownLive;
    }

    public static void setKnownLive(UserList liveKnown) {
        THIO.knownLive = liveKnown;
    }

}