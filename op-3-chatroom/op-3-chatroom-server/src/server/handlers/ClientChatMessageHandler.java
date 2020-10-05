package server.handlers;

import network.handlers.NetworkMessageHandler;
import network.message.DirectMessage;
import network.message.RegularMessage;
import network.networkmessage.ClientChatMessageNetworkMessage;
import network.networkmessage.ServerChatMessageNetworkMessage;
import server.Connection;
import server.ServerContext;
import server.commands.Command;
import server.commands.DirectMessageCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientChatMessageHandler extends NetworkMessageHandler<Connection, ClientChatMessageNetworkMessage> {

    public ClientChatMessageHandler(ServerContext context) {
        this.context = context;
        this.commands = new ArrayList<>();
        this.commands.add(new DirectMessageCommand(context));
    }

    @Override
    protected void handleCore(Connection connection, ClientChatMessageNetworkMessage message) {
        if(!Objects.equals(message.getToken(), connection.getToken())) {
            System.out.printf("Invalid token received for connection %s.%n", connection.getAddress());
            connection.close();
            return;
        }

        for(Command command: commands) {
            if(command.match(connection, message.getMessage())) {
                message.setHandled();
                break;
            }
        }

        if(message.isHandled())
            return;

        RegularMessage regularMessage = new RegularMessage(connection.getUsername(), message.getMessage());
        ServerChatMessageNetworkMessage chatMessage = new ServerChatMessageNetworkMessage(regularMessage);

        for(Connection c: context.getConnections()) {
            c.write(chatMessage);
        }

        message.setHandled();
    }

    private final List<Command> commands;
    private final ServerContext context;
}
