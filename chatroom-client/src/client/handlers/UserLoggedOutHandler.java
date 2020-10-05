package client.handlers;

import client.Connection;
import network.handlers.NetworkMessageHandler;
import network.message.Message;
import network.message.SystemMessage;
import network.networkmessage.UserLoggedOutNetworkMessage;

import java.util.Queue;

public class UserLoggedOutHandler extends NetworkMessageHandler<Connection, UserLoggedOutNetworkMessage> {

    private final Queue<Message> messages;
    private final Queue<String> users;

    public UserLoggedOutHandler(Queue<Message> messages, Queue<String> users) {
        this.messages = messages;
        this.users = users;
    }

    @Override
    protected void handleCore(Connection connection, UserLoggedOutNetworkMessage message) {
        messages.add(new SystemMessage("User Logged Out", message.getUsername()));
        users.add(message.getUsername());
        message.setHandled();
    }
}
