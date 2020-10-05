package client.handlers;

import client.Connection;
import network.handlers.MessageHandler;
import network.message.Message;
import network.networkmessage.LoginSuccessNetworkMessage;
import network.networkmessage.ServerChatMessageNetworkMessage;

import javax.swing.*;

public class LoginSuccessHandler extends MessageHandler<Connection, LoginSuccessNetworkMessage> {

    @Override
    protected void handleCore(Connection connection, LoginSuccessNetworkMessage message) {
        connection.setToken(message.getToken());
        System.out.printf("Connection established with %s.%n", connection.getAddress());
        message.setHandled();
    }

}
