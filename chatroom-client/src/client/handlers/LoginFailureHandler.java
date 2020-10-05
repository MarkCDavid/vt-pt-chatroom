package client.handlers;

import client.Connection;
import network.handlers.NetworkMessageHandler;
import network.networkmessage.LoginFailureNetworkMessage;

import javax.swing.*;

public class LoginFailureHandler extends NetworkMessageHandler<Connection, LoginFailureNetworkMessage> {

    @Override
    protected void handleCore(Connection connection, LoginFailureNetworkMessage message) {
        connection.close();
        JOptionPane.showMessageDialog(null, "Server declined your connection! Reason: " + message.getReason());
    }
}
