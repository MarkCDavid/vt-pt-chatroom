import Network.ClientChatMessageNetworkMessage;
import Network.Message;
import Network.NetworkMessage;
import Network.ServerChatMessageNetworkMessage;

import javax.swing.*;
import java.io.IOException;

public class ChatRoomForm {
    public JPanel mainPanel;

    private JButton submitButton;
    private JButton logOutButton;

    private JTextArea userMessageField;

    private JList<String> chatMessages;
    private DefaultListModel<String> chatMessagesModel;
    private JList<String> loggedInList;

    private final Connection connection;

    public ChatRoomForm(Connection connection) {
        this.connection = connection;

        Thread readThread = new Thread(() -> {
            try {
                while(true) {
                    NetworkMessage message = this.connection.read();
                    if (message instanceof ServerChatMessageNetworkMessage) {
                        ServerChatMessageNetworkMessage serverChatMessageNM = (ServerChatMessageNetworkMessage) message;
                        Message chatMessage = serverChatMessageNM.getMessage();
                        String newMessage = "[" + chatMessage.getUsername() + " | " + chatMessage.getDateTime() + "]: " + chatMessage.getData();
                        chatMessagesModel.addElement(newMessage);
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        readThread.start();

        submitButton.addActionListener(actionEvent -> {
            String textMessage = userMessageField.getText();
            userMessageField.setText("");
            ClientChatMessageNetworkMessage message = new ClientChatMessageNetworkMessage(connection.getToken(), textMessage);
            try {
                connection.write(message);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void createUIComponents() {
        chatMessagesModel = new DefaultListModel<>();
        chatMessages = new JList<>(chatMessagesModel);
    }
}
