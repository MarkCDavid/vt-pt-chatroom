import Network.ClientChatMessageNetworkMessage;
import Network.Message;
import Network.NetworkMessage;
import Network.ServerChatMessageNetworkMessage;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class ProtocolHandler {

    ArrayList<Connection> connections = new ArrayList<>();

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public Runnable getHandler(Connection connection) {
        return () -> {
            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    NetworkMessage message = connection.read();
                    if(message instanceof ClientChatMessageNetworkMessage) {
                        ClientChatMessageHandler(connection, (ClientChatMessageNetworkMessage)message);
                    }
                }
            } catch (IOException e) {
                System.out.println("Exception caught when listening for a connection");
                System.out.println(e.getMessage());
            }
        };
    }

    private void ClientChatMessageHandler(Connection connection, ClientChatMessageNetworkMessage message) throws IOException {
        if(!Objects.equals(message.getToken(), connection.getToken())) {
            System.out.println("Invalid token received for connection " + connection.getAddress());
            connection.close();
            return;
        }

        ServerChatMessageNetworkMessage serverChatMessageNM = new ServerChatMessageNetworkMessage(
                new Message(connection.getUsername(), message.getMessage())
        );

        for(Connection c: this.connections) {
            c.write(serverChatMessageNM);
        }
    }


}
