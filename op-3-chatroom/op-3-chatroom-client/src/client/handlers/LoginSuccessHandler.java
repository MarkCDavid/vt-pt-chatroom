package client.handlers;

import client.Connection;
import network.handlers.NetworkMessageHandler;
import network.networkmessage.LoginSuccessNetworkMessage;

public class LoginSuccessHandler extends NetworkMessageHandler<Connection, LoginSuccessNetworkMessage> {

    @Override
    protected void handleCore(Connection connection, LoginSuccessNetworkMessage message) {
        connection.setToken(message.getToken());
        System.out.printf("Connection established with %s.%n", connection.getAddress());
        message.setHandled();
    }
}
