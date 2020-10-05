package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatRoomServer {

    public static void main(String[] args) {
        int port = 4444;
        if (args.length == 1)
            port = Integer.parseInt(args[0]);

        ServerContext serverContext = new ServerContext();
        ServerSocket serverSocket = establishServerSocket(port);
        if (serverSocket == null)
            return;

        try {
            //noinspection InfiniteLoopStatement
            while(true) {
                Connection connection = new Connection(serverSocket.accept());
                Thread thread = new Thread(serverContext.getProxy(serverContext, connection));
                thread.start();
            }
        } catch (IOException e) {
            System.out.printf("Exception caught when trying to listen on port %s or listening for a connection.%n", port);
            System.out.println(e.getMessage());
        }
    }

    private static ServerSocket establishServerSocket(int port) {
        try {
            return new ServerSocket(port);
        } catch (IOException exception) {
            System.out.printf("Failed to establish a listening socket on port %s.%n", port);
            System.out.println(exception.getMessage());
            return null;
        }
    }
}
