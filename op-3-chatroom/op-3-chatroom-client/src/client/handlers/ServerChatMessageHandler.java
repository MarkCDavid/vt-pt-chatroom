package client.handlers;

import client.Connection;
import network.handlers.NetworkMessageHandler;
import network.message.Message;
import network.networkmessage.ServerChatMessageNetworkMessage;

import javax.swing.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerChatMessageHandler extends NetworkMessageHandler<Connection, ServerChatMessageNetworkMessage> {

    public ServerChatMessageHandler(ConcurrentLinkedQueue<Message> model) {
        this.model = model;
    }

    @Override
    protected void handleCore(Connection connection, ServerChatMessageNetworkMessage message) {
        model.add(message.getMessage());
        message.setHandled();
    }

    private final ConcurrentLinkedQueue<Message> model;
}
