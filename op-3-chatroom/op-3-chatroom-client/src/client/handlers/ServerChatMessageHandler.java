package client.handlers;

import client.ChatRoomForm;
import client.Connection;
import network.handlers.MessageHandler;
import network.message.Message;
import network.networkmessage.ServerChatMessageNetworkMessage;

import javax.swing.*;

public class ServerChatMessageHandler extends MessageHandler<Connection, ServerChatMessageNetworkMessage> {

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
