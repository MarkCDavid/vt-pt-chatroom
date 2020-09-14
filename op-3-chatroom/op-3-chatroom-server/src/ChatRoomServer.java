import Network.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatRoomServer {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java ChatRoomServer <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);

        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
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
