import Network.Message;
import Network.NetworkMessage;
import Network.ServerChatMessageNetworkMessage;

import javax.swing.*;
import java.io.IOException;

public class ChatRoomForm {
    public JPanel mainPanel;
    private JEditorPane chatTextPane;
    private JButton submitButton;
    private JList loggedInList;
    private JButton logOutButton;
    private JTextArea userMessageField;

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
                        String currentText = chatTextPane.getText();
                        if(currentText.length() > 0) {
                            chatTextPane.setText(currentText + "\n" + newMessage);
                        }
                        else {
                            chatTextPane.setText(newMessage);
                        }
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        readThread.start();
    }
}
