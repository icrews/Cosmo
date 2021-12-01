package Cosmo;

import java.io.*;
import java.util.*;

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
                    else {
                        state = LIVEBOX;
                    }
                    break;

            case LIVEIDENTIFY: 
                    thioMessage = stateLIVEIDENTIFY();
                    state = LIVEINTERACT;
                    break;
            case BOXIDENTIFY: 
                    thioMessage = stateBOXIDENTIFY();
                    state = BOXINTERACT;
                    break;
            case LIVEINTERACT: thioMessage = stateLIVEINTERACT();
                    state = LIVEINTERACT;
                    break;
            case BOXINTERACT: thioMessage = stateBOXINTERACT(userMessage);
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
        String tableEntry = "";
        tableEntry += userMessage + "," + connectedAddress + "," + connectedPort;

        boolean userExists = false;
        Scanner scanner = new Scanner("users.csv");
        while (scanner.hasNext()){
            if (scanner.next().equals(userMessage)){
                userExists = true;
            }
        }
       

        if (userExists = false){
            addUser(tableEntry);
        }

        scanner.close();
        String message = "Created User " + userMessage +". Would you like to use LIVE chat or BOX chat? [Live|Box]";
        return message;
    }

    void addUser(String tableEntry){
        try {
            PrintWriter writeToTable = new PrintWriter("COSMO/users.csv");
            writeToTable.println(tableEntry);
            writeToTable.close();
        } catch (FileNotFoundException e){
            System.err.println("Users Table: users.csv,  not found");
            System.exit(1);
        }
    }


    String stateLIVEBOX(String userMessage) {
        String message = "";
        if (userMessage.toLowerCase().equals("live")){
            message = "Please choose a Live User to connect to.";
        }
        else if (userMessage.toLowerCase().equals("box")){
            message = "Connecting to the Box.";
        }
        else {
            message = "Please choose either Live or Box!";
        }
        return message;
    }

    String stateLIVEIDENTIFY() {
        return stateCLOSING(); //filler

    }

    String stateBOXIDENTIFY() {
        String message = "";
        Scanner boxScan = new Scanner("COSMO/box.csv");
        String boxMessageLine = "";
        String boxMessageID = "";
        int parsedID = 0;
        int highestID = 0;
        
        while (boxScan.hasNextLine()){
            boxMessageLine = boxScan.nextLine();
            int firstComma = boxMessageLine.indexOf(",");
            boxMessageID = boxMessageLine.substring(0, firstComma);
            parsedID = Integer.parseInt(boxMessageID);

            if (parsedID > highestID){
                highestID = parsedID;
            }
        }


        if (highestID > 10){
            for (int i = highestID-10; i <= highestID; ++i){
                while (boxScan.hasNextLine()){
                    boxMessageLine = boxScan.nextLine();
                    int firstComma = boxMessageLine.indexOf(",");
                    boxMessageID = boxMessageLine.substring(0, firstComma);
                    parsedID = Integer.parseInt(boxMessageID);

                    if (parsedID == i){
                        message += boxMessageLine;
                    }
                }
            }
        }
        else {
            while (boxScan.hasNextLine()){
                message += boxScan.nextLine();
            }
        }
        
        message += "\n-------END OF CONVERSATION-------\n";
        message += "\nPlease Add a Message:\n";

        boxScan.close();

        return message;
    }

    String stateLIVEINTERACT() {
        return stateCLOSING(); //filler
    }

    String stateBOXINTERACT(String userMessage) {
        String tableEntry = "";
        String message = "";
        tableEntry += "0,";
        tableEntry += this.connectedAddress + ",";
        tableEntry += "'" + userMessage + "'";

        addMessage(tableEntry);

        message += tableEntry;
        return message; //filler
    }

    void addMessage(String tableEntry){
        try {
            PrintWriter writeToTable = new PrintWriter("COSMO/box.csv");
            writeToTable.println(tableEntry);
            writeToTable.close();
        } catch (FileNotFoundException e){
            System.err.println("Users Table: users.csv,  not found");
            System.exit(1);
        }
    }

    String stateCLOSING() {
        String message = "<CLOSE>";
        return message;
    }

}