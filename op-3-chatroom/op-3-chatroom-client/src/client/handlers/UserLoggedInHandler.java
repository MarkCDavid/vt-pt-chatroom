package client.handlers;

import client.Connection;
import network.handlers.NetworkMessageHandler;
import network.message.Message;
import network.message.SystemMessage;
import network.networkmessage.UserLoggedInNetworkMessage;

import javax.swing.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UserLoggedInHandler extends NetworkMessageHandler<Connection, UserLoggedInNetworkMessage> {

    public UserLoggedInHandler(ConcurrentLinkedQueue<Message> messages, ConcurrentLinkedQueue<String> users) {
        this.messages = messages;
        this.users = users;
    }

    @Override
    protected void handleCore(Connection connection, UserLoggedInNetworkMessage message) {
        messages.add(new SystemMessage("User Logged In", message.getUsername()));
        users.add(message.getUsername());
        message.setHandled();
    }

    private final ConcurrentLinkedQueue<Message> messages;
    private final ConcurrentLinkedQueue<String> users;
}
