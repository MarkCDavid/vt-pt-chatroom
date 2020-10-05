package client.handlers;

import client.Connection;
import network.handlers.NetworkMessageHandler;
import network.message.Message;
import network.message.SystemMessage;
import network.networkmessage.UserLoggedInNetworkMessage;

import java.util.Queue;

public class UserLoggedInHandler extends NetworkMessageHandler<Connection, UserLoggedInNetworkMessage> {

    public UserLoggedInHandler(Queue<Message> messages, Queue<String> users) {
        this.messages = messages;
        this.users = users;
    }

    @Override
    protected void handleCore(Connection connection, UserLoggedInNetworkMessage message) {
        messages.add(new SystemMessage("User Logged In", message.getUsername()));
        users.add(message.getUsername());
        message.setHandled();
    }

    private final Queue<Message> messages;
    private final Queue<String> users;
}
