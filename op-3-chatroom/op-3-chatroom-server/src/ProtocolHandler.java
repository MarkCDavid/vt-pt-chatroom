import Network.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProtocolHandler {

    ArrayList<Connection> connections = new ArrayList<>();

    public void addConnection(Connection connection) throws IOException {

        UserLoggedInNetworkMessage userLoggedInNM = new UserLoggedInNetworkMessage(connection.getUsername());
        connection.write(userLoggedInNM);
        for(Connection c: connections) {
            c.write(userLoggedInNM);
            connection.write(new UserLoggedInNetworkMessage(c.getUsername()));
        }
        connections.add(connection);
    }

    public void removeConnection(Connection connection) throws IOException {
        UserLoggedOutNetworkMessage userLoggedOutNM = new UserLoggedOutNetworkMessage(connection.getUsername());
        connections.remove(connection);
        for(Connection c: connections) {
            c.write(userLoggedOutNM);
        }
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
                    else if(message instanceof LogoutRequestNetworkMessage) {
                        LogoutRequestHandler(connection, (LogoutRequestNetworkMessage)message);
                    }
                }
            } catch (IOException e) {
                System.out.println("IO failed for " + connection.getAddress());
                try {
                    this.removeConnection(connection);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        };
    }

    private void LogoutRequestHandler(Connection connection, LogoutRequestNetworkMessage message) throws IOException {
        if(!Objects.equals(message.getToken(), connection.getToken())) {
            System.out.println("Invalid token received for disconnect request for connection " + connection.getAddress() + ". Ignoring.");
            return;
        }

        removeConnection(connection);
    }

    private static final Pattern dmPattern = Pattern.compile("^(/dm) (\\w+) (.*)");
    private void ClientChatMessageHandler(Connection connection, ClientChatMessageNetworkMessage message) throws IOException {
        if(!Objects.equals(message.getToken(), connection.getToken())) {
            System.out.println("Invalid token received for connection " + connection.getAddress());
            connection.close();
            return;
        }

        String clientMessage = message.getMessage();
        Matcher matcher = dmPattern.matcher(clientMessage);

        if(matcher.find()) {
            String from = connection.getUsername();
            String to = matcher.group(2);
            String data = matcher.group(3);

            ServerChatMessageNetworkMessage serverChatMessageNM = new ServerChatMessageNetworkMessage(
                    new DirectMessage(from, to, data)
            );

            for(Connection c: this.connections) {
                if(Objects.equals(c.getUsername(), to)) {
                    connection.write(serverChatMessageNM);
                    c.write(serverChatMessageNM);
                    break;
                }
            }
        }
        else{
            ServerChatMessageNetworkMessage serverChatMessageNM = new ServerChatMessageNetworkMessage(
                    new RegularMessage(connection.getUsername(), message.getMessage())
            );

            for(Connection c: this.connections) {
                c.write(serverChatMessageNM);
            }
        }






    }


}
