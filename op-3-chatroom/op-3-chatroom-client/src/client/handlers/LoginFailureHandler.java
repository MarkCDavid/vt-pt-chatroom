package client.handlers;

import client.Connection;
import client.LoginForm;
import network.handlers.MessageHandler;
import network.message.Message;
import network.networkmessage.LoginFailureNetworkMessage;
import network.networkmessage.ServerChatMessageNetworkMessage;

import javax.swing.*;

public class LoginFailureHandler extends MessageHandler<Connection, LoginFailureNetworkMessage> {

    @Override
    protected void handleCore(Connection connection, LoginFailureNetworkMessage message) {
        connection.close();
        JOptionPane.showMessageDialog(null, "Server declined your connection! Reason: " + message.getReason());
    }

}
