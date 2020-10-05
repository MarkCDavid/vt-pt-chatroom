package server.commands;

import network.message.DirectMessage;
import network.networkmessage.ServerChatMessageNetworkMessage;
import server.Connection;
import server.ServerContext;

import java.util.List;
import java.util.Objects;

public class DirectMessageCommand extends Command {

    private final ServerContext context;

    public DirectMessageCommand(ServerContext context) {
        super("dm");
        this.context = context;
    }

    @Override
    protected void handle(Connection connection, List<String> arguments) {
        if (arguments.isEmpty())
            return;

        String from = connection.getUsername();
        String to = arguments.get(0);

        boolean selfMessage = Objects.equals(from, to);

        String data = String.join(" ", arguments.subList(1, arguments.size()));
        DirectMessage directMessage = new DirectMessage(from, to, data);
        ServerChatMessageNetworkMessage message = new ServerChatMessageNetworkMessage(directMessage);

        for (Connection c : context.getConnections()) {
            if (Objects.equals(c.getUsername(), to))
                c.write(message);

            if (selfMessage) continue;

            if (Objects.equals(c.getUsername(), from))
                c.write(message);
        }
    }
}
