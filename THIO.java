package Cosmo;

import java.io.*;

public class THIO {
    private static final int BEGINTHIO = 0;
    private static final int IDENTIFY = 1;
    private static final int LIVEBOX = 2;
    private static final int LIVEIDENTIFY = 3;
    private static final int BOXIDENTIFY = 4;
    private static final int LIVEINTERACT = 5;
    private static final int BOXINTERACT = 6;
    private static final int CLOSING = 7;

    private int state = BEGINTHIO;

    private String thioMessage = "";
    private String connectedAddress;
    private int connectedPort;

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
                         state = BOXIDENTIFY;
                    }
                    break;

            case LIVEIDENTIFY: 
                    thioMessage = stateLIVEIDENTIFY();
                    state = LIVEINTERACT;
                    break;
            case BOXIDENTIFY: 
                    thioMessage = stateBOXINTERACT();
                    state = BOXINTERACT;
                    break;
            case LIVEINTERACT: thioMessage = stateLIVEINTERACT();
                    state = LIVEINTERACT;
                    break;
            case BOXINTERACT: thioMessage = stateBOXINTERACT();
                    state = BOXINTERACT;
                    break;

            case CLOSING: thioMessage = stateCLOSING();
                    break;

        }


        return thioMessage;
    }

    String stateBEGINTHIO() {
        String message = "Welcome, user! I am THIO and I will be helping you today.\nYou may cease this connection at any time by saying END.\nPlease Identify Yourself.";
        return message;
    }

    String stateIDENTIFY(String userMessage) {
        String userEntry = "";
        userEntry += userMessage + "," + connectedAddress + "," + connectedPort;

        try {
            PrintWriter writeToTable = new PrintWriter("users.csv");
            writeToTable.println(userEntry);
            writeToTable.close();
        } catch (FileNotFoundException e){
            System.err.println("Users Table: users.csv,  not found");
            System.exit(1);
        }

        String message = "Created User " + userMessage +". Would you like to use LIVE chat or BOX chat? [Live|Box]";
        return message;
    }


    String stateLIVEBOX(String userMessage) {
        String message = "";
        if (userMessage.toLowerCase().equals("live")){
            message = "Please choose a Live User to connect to.";
        }
        else if (userMessage.toLowerCase().equals("box")){
            message = "Please choose a Box to connect to.";
        }
        else {

        }
        return message;
    }

    String stateLIVEIDENTIFY() {
        return stateCLOSING(); //filler

    }

    String stateBOXIDENTIFY() {
        return stateCLOSING(); //filler
    }

    String stateLIVEINTERACT() {
        return stateCLOSING(); //filler
    }

    String stateBOXINTERACT() {
        return stateCLOSING(); //filler
    }

    String stateCLOSING() {
        String message = "<CLOSE>";
        return message;
    }

}