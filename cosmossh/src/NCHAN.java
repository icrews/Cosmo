import java.io.*;
import java.net.*;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;


public class NCHAN {
    protected static UserList users = new UserList();
    
    protected static UserList liveUsers = new UserList();
    public static void main(String[] args) throws IOException {
        
        if (args.length != 1){
            System.err.println("Usage: java NCHAN <port number>");
            System.exit(1);
        } 

        int portNumber = Integer.parseInt(args[0]);
        int connectionCount = 0;
        ServerSocket serverSocket;
        // this needs to be done in a loop as it will allow it to accept multiple connections that it hears, right now stops listening after 1 connection is established
        // it also exits once that connection is closed
        // ThreadedKnockKnock it creates a new thread on lines 26-28 that allows it stores the connection and work while still looking for other connections
        // .start() begin to run code from KnockKnockRunnable look there to create the protocol that runs on each thread
 
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port", e);
        }

        
        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted: " + connectionCount);
                new Thread(
                    new NCHANRunnable(clientSocket, users, liveUsers)
                    ).start();
                connectionCount++;
                System.out.println("<ESTABLISHED NEW CONNECTION>");
            } catch (IOException e){
                System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
    
    }

}