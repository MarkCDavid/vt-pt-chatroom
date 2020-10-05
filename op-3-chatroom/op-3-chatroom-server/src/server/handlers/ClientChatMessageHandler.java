package server.handlers;

import network.handlers.NetworkMessageHandler;
import network.message.DirectMessage;
import network.message.RegularMessage;
import network.networkmessage.ClientChatMessageNetworkMessage;
import network.networkmessage.ServerChatMessageNetworkMessage;
import server.Connection;
import server.ServerContext;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientChatMessageHandler extends NetworkMessageHandler<Connection, ClientChatMessageNetworkMessage> {

    public ClientChatMessageHandler(ServerContext context) {
        this.context = context;
    }


    private static final Pattern dmPattern = Pattern.compile("^(/dm) (\\w+) (.*)");

    @Override
    protected void handleCore(Connection connection, ClientChatMessageNetworkMessage message) {
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

            for(Connection c: context.getConnections()) {
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

            for(Connection c: context.getConnections()) {
                c.write(serverChatMessageNM);
            }
        }

        message.setHandled();
    }


    private final ServerContext context;
}
