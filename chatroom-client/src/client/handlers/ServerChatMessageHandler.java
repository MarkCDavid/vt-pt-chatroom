package client.handlers;

import client.Connection;
import network.handlers.NetworkMessageHandler;
import network.message.Message;
import network.networkmessage.ServerChatMessageNetworkMessage;

import java.util.Queue;

public class ServerChatMessageHandler extends NetworkMessageHandler<Connection, ServerChatMessageNetworkMessage> {

    private final Queue<Message> model;

    public ServerChatMessageHandler(Queue<Message> model) {
        this.model = model;
    }

    @Override
    protected void handleCore(Connection connection, ServerChatMessageNetworkMessage message) {
        model.add(message.getMessage());
        message.setHandled();
    }
}
