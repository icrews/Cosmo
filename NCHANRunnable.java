import java.io.*;
import java.net.*;
// This code was adapted from the KnockKnock protocols along with http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html

public class NCHANRunnable implements Runnable{

    protected Socket clientSocket = null;
    static UserList knownUsers;
    static UserList knownLiveUsers;

    public NCHANRunnable(Socket clientSocket, UserList users, UserList liveUsers) {
        this.clientSocket = clientSocket;
        knownUsers = users;
        knownLiveUsers = liveUsers;

        }
        public void run() {
            try (
                PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            ) {
                String inputLine, output;

                // Initiate conversation with single client
                
                THIO.setKnownUsers(knownUsers);
                THIO.setKnownLive(knownLiveUsers);
                THIO thio = new THIO(clientSocket.getInetAddress().toString(), clientSocket.getPort());
                
                
                output = thio.processInput(null);
                out.println(output);
    
                while ((inputLine = in.readLine()) != null) {
                    output = thio.processInput(inputLine);
                    out.println(output);
                    if (output.equals("<CLOSE>")){
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
