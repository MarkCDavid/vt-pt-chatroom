package client.handlers;

import client.Connection;
import network.handlers.MessageHandler;
import network.message.Message;
import network.message.SystemMessage;
import network.networkmessage.ServerChatMessageNetworkMessage;
import network.networkmessage.UserLoggedInNetworkMessage;

import javax.swing.*;

public class UserLoggedInHandler extends MessageHandler<Connection, UserLoggedInNetworkMessage> {

    public UserLoggedInHandler(DefaultListModel<Message> messages, DefaultListModel<String> users) {
        this.messages = messages;
        this.users = users;
    }

    @Override
    protected void handleCore(Connection connection, UserLoggedInNetworkMessage message) {
        messages.addElement(new SystemMessage("User Logged In", message.getUsername()));
        users.addElement(message.getUsername());
        message.setHandled();
    }

    private final DefaultListModel<Message> messages;
    private final DefaultListModel<String> users;
}
