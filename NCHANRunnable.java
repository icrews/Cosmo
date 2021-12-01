import java.io.*;
import java.net.*;
// This code was adopted from the KnockKnock protocols along with http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html

public class NCHANRunnable implements Runnable{

    protected Socket clientSocket = null;
    public NCHANRunnable(Socket clientSocket, file that stores chats) {
        this.clientSocket = clientSocket;
        }
        public void run() {
            try (
                PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = newBufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            ) {
                String inputLine, outputLine;
                NCHAN nchanp = new NCHAN();
                outputLine = nchanp.processInput(inputLine);
                out.println(outputLine);

                while ((inputLine = in.readLine()) != null) {
                    outputLine = nchanp.processInput(inputLine);
                    out.println(outputLine);
                    if (outputLine.equals("Bye."))
                        break;
                }
            } catch (IOExpectation e) {
            e.printStackTrace();
            }
        }
    }
