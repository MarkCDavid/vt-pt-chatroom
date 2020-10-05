package server.handlers;

import network.Limits;
import network.handlers.MessageHandler;
import network.networkmessage.LoginFailureNetworkMessage;
import network.networkmessage.LoginRequestNetworkMessage;
import network.networkmessage.LoginSuccessNetworkMessage;
import server.Connection;
import server.ServerContext;
import server.TokenGenerator;
public class LoginRequestHandler extends MessageHandler<Connection, LoginRequestNetworkMessage> {

    public LoginRequestHandler(ServerContext context) {
        this.context = context;
    }

    @Override
    protected void handleCore(Connection connection, LoginRequestNetworkMessage message) {
        connection.setUsername(message.getUsername());

        if(Limits.validUsernameLength(connection.getUsername())) {
            connection.setToken(TokenGenerator.generateToken());
            connection.write(new LoginSuccessNetworkMessage(connection.getToken()));
            context.addConnection(connection);

        }
        else {
            System.out.printf("Requester %s username '%s' is invalid!%n", connection.getAddress(), connection.getUsername());
            connection.write(new LoginFailureNetworkMessage("Invalid username!"));
            connection.close();
        }

        message.setHandled();
    }

    private final ServerContext context;
}
