import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;


public class NCHAN {
    protected static UserList users = new UserList();
    
    protected static UserList liveUsers = new UserList();
    public static void main(String[] args) throws IOException {
        
    	/**
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
        **/
    	
    	if (args.length != 4){
            System.err.println("Usage: java NCHAN <username> <password> <hostname> <port number>");
            System.exit(1);
        } 
    	
    	String inUser, inPass;
    	String host;
    	int port;
    	long timeout = 60;
    	String command = null;
    	
    	inUser = args[0];
    	inPass = args[1];
    	host = args[2];
    	port = Integer.parseInt(args[3]);
    	
    			
    	listFolderStructure(inUser, inPass, host, port, timeout, command);
    
    }
    
    public static void listFolderStructure(String username, String password, 
    		  String host, int port, long defaultTimeoutSeconds, String command) throws IOException {
    		    
    		    SshClient client = SshClient.setUpDefaultClient();
    		    client.start();
    		    
    		    try (ClientSession session = client.connect(username, host, port)
    		      .verify(defaultTimeoutSeconds, TimeUnit.SECONDS).getSession()) {
    		        session.addPasswordIdentity(password);
    		        session.auth().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);
    		        
    		        while(true) {
    		        try (ByteArrayOutputStream responseStream = new ByteArrayOutputStream(); 
    		          ClientChannel channel = session.createChannel(Channel.CHANNEL_SHELL)) {
    		            channel.setOut(responseStream);
    		            try {
    		                channel.open().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);
    		                try (OutputStream pipedIn = channel.getInvertedIn()) {
    		                    pipedIn.write(command.getBytes());
    		                    pipedIn.flush();
    		                }
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

    		            
    		                channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 
    		                TimeUnit.SECONDS.toMillis(defaultTimeoutSeconds));
    		                String responseString = new String(responseStream.toByteArray());
    		                System.out.println(responseString);
    		            } finally {
    		                channel.close(false);
    		            }
    		        }
    		    } finally {
    		        client.stop();
    		    }
    		}

}