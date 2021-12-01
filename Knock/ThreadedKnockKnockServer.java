// Adapted from: http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class ThreadedKnockKnockServer {

    public static void main(String[] args) {
        int serverPort = 8080;

        int connectionCount = 0;
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port", e);
        }

        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted: " + connectionCount);
                new Thread(
                    new KnockKnockRunnable(clientSocket)
                    ).start();
                connectionCount++;
            } catch (IOException e) {
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
        }
    }

}
