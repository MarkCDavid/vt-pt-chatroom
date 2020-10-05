package client.handlers;

import client.Connection;
import network.handlers.NetworkMessageHandler;
import network.message.Message;
import network.networkmessage.ServerChatMessageNetworkMessage;

import javax.swing.*;

public class ServerChatMessageHandler extends NetworkMessageHandler<Connection, ServerChatMessageNetworkMessage> {

    public ServerChatMessageHandler(DefaultListModel<Message> model) {
        this.model = model;
    }

    @Override
    protected void handleCore(Connection connection, ServerChatMessageNetworkMessage message) {
        model.addElement(message.getMessage());
        message.setHandled();
    }

    private final DefaultListModel<Message> model;
}
