package server.commands;

import network.message.DirectMessage;
import network.networkmessage.ServerChatMessageNetworkMessage;
import server.Connection;
import server.ServerContext;

import java.util.List;
import java.util.Objects;

public class DirectMessageCommand extends Command {

    public DirectMessageCommand(ServerContext context) {
        super("dm");
        this.context = context;
    }

    @Override
    protected void handle(Connection connection, List<String> arguments) {
        if(arguments.size() != 2)
            return;

        String from = connection.getUsername();
        String to = arguments.get(0);
        String data = arguments.get(1);
        DirectMessage directMessage = new DirectMessage(from, to, data);
        ServerChatMessageNetworkMessage message = new ServerChatMessageNetworkMessage(directMessage);

        for(Connection c: context.getConnections()) {
            if(Objects.equals(c.getUsername(), to))
                c.write(message);

            if(Objects.equals(c.getUsername(), from))
                c.write(message);
        }
    }

    private final ServerContext context;
}