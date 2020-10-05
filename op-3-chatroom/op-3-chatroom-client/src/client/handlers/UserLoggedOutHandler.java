package client.handlers;

import client.Connection;
import network.handlers.MessageHandler;
import network.message.Message;
import network.message.SystemMessage;
import network.networkmessage.UserLoggedInNetworkMessage;
import network.networkmessage.UserLoggedOutNetworkMessage;

import javax.swing.*;

public class UserLoggedOutHandler extends MessageHandler<Connection, UserLoggedInNetworkMessage> {

    public UserLoggedOutHandler(DefaultListModel<Message> messages, DefaultListModel<String> users) {
        this.messages = messages;
        this.users = users;
    }

    @Override
    protected void handleCore(Connection connection, UserLoggedInNetworkMessage message) {
        messages.addElement(new SystemMessage("User Logged Out", message.getUsername()));
        users.removeElement(message.getUsername());
        message.setHandled();
    }

    private final DefaultListModel<Message> messages;
    private final DefaultListModel<String> users;
}
