// Adapted from: http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html

import java.io.*;
import java.net.Socket;

public class KnockKnockRunnable implements Runnable{

    protected Socket clientSocket = null;

    public KnockKnockRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try (
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            // Communicate with the KnockKnock protocol
            String inputLine, outputLine;
            
            // Initiate conversation with client
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }
            
            // try-with-resources automatically closes input and output streams
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}
