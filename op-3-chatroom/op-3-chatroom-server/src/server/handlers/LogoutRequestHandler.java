package server.handlers;

import network.handlers.MessageHandler;
import network.networkmessage.LogoutRequestNetworkMessage;
import server.Connection;
import server.ServerContext;

import java.util.Objects;

public class LogoutRequestHandler  extends MessageHandler<Connection, LogoutRequestNetworkMessage> {

    public LogoutRequestHandler(ServerContext context) {
        this.context = context;
    }

    @Override
    protected void handleCore(Connection connection, LogoutRequestNetworkMessage message) {
        if(!Objects.equals(message.getToken(), connection.getToken())) {
            System.out.println("Invalid token received for disconnect request for connection " + connection.getAddress() + ". Ignoring.");
            return;
        }

        context.removeConnection(connection);
        message.setHandled();
    }

    private final ServerContext context;
}
