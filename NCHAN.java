package Cosmo;

import java.io.*;
import java.net.*;

public class NCHAN {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 1){
            System.err.println("Usage: java NCHAN <port number>");
            System.exit(1);
        } 

        int portNumber = Integer.parseInt(args[0]);

        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("<ESTABLISHED>");
            String inputLine, outputLine;

            // Initiate conversation with single client
            THIO thio = new THIO(clientSocket.getInetAddress().toString(), clientSocket.getPort());
            outputLine = thio.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = thio.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("<CLOSE>")){
                    break;
                }
            }

        }
        catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
            + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}