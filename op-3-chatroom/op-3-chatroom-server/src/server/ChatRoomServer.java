package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatRoomServer {

    public static void main(String[] args) throws IOException {
        int port = 4444;
        if (args.length == 1)
            port = Integer.parseInt(args[0]);

        ServerSocket serverSocket = new ServerSocket(port);
        ProtocolHandler protocolHandler = new ProtocolHandler();

        try {
            //noinspection InfiniteLoopStatement
            while(true) {
                Connection connection = new Connection(serverSocket.accept());
                if (!connection.isValid()) {
                    System.out.println("Failed to establish connection with the requester " + connection.getAddress());
                    return;
                }

                protocolHandler.addConnection(connection);
                Thread thread = new Thread(protocolHandler.getHandler(connection));
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + port + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }


}
