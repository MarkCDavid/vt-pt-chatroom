package client;

import network.*;

import javax.swing.*;
import java.io.IOException;

public class ChatRoomForm {
    public JPanel mainPanel;

    private JButton submitButton;
    private JButton logOutButton;

    private JTextArea userMessageField;

    private JList<Message> chatMessages;
    private DefaultListModel<Message> chatMessagesModel;

    private JList<String> loggedInUsers;
    private DefaultListModel<String> loggedInUsersModel;

    private final Connection connection;

    public ChatRoomForm(Connection connection) {
        this.connection = connection;

        Thread readThread = new Thread(() -> {
            try {
                while(true) {
                    NetworkMessage message = this.connection.read();
                    if (message instanceof ServerChatMessageNetworkMessage) {
                        ServerChatMessageNetworkMessage serverChatMessageNM = (ServerChatMessageNetworkMessage) message;
                        chatMessagesModel.addElement(serverChatMessageNM.getMessage());
                    }
                    else if(message instanceof UserLoggedInNetworkMessage) {
                        UserLoggedInNetworkMessage userLoggedInNM = (UserLoggedInNetworkMessage) message;
                        chatMessagesModel.addElement(new SystemMessage("User Logged In", userLoggedInNM.getUsername()));
                        loggedInUsersModel.addElement(userLoggedInNM.getUsername());
                    }
                    else if(message instanceof UserLoggedOutNetworkMessage) {
                        UserLoggedOutNetworkMessage userLoggedOutNM = (UserLoggedOutNetworkMessage) message;
                        chatMessagesModel.addElement(new SystemMessage("User Logged Out", userLoggedOutNM.getUsername()));
                        loggedInUsersModel.removeElement(userLoggedOutNM.getUsername());
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        readThread.start();

        submitButton.addActionListener(actionEvent -> {
            try {
                sendMessage(connection);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        logOutButton.addActionListener(actionEvent -> {
            try {
                sendLogoutRequest(connection);
                connection.close();
                LoginForm.show(frame);

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        loggedInUsers.addListSelectionListener(listSelectionEvent -> {
            String username = loggedInUsers.getSelectedValue();
            if (username != null) {
                userMessageField.setText(String.format("/dm %s ", username));
            }
            loggedInUsers.clearSelection();
        });

        chatMessages.addListSelectionListener(listSelectionEvent -> {
            Message message = chatMessages.getSelectedValue();
            if(message instanceof RegularMessage) {
                RegularMessage regularMessage = (RegularMessage) message;
                userMessageField.setText(String.format("/dm %s ", regularMessage.getUsername()));
            }
            chatMessages.clearSelection();
        });
    }

    private void sendLogoutRequest(Connection connection) throws IOException {
        LogoutRequestNetworkMessage message = new LogoutRequestNetworkMessage(connection.getToken());
        connection.write(message);
    }

    private void sendMessage(Connection connection) throws IOException {
        String textMessage = userMessageField.getText();
        userMessageField.setText("");
        ClientChatMessageNetworkMessage message = new ClientChatMessageNetworkMessage(connection.getToken(), textMessage);
        connection.write(message);
    }

    private void createUIComponents() {
        chatMessagesModel = new DefaultListModel<>();
        chatMessages = new JList<>(chatMessagesModel);
        chatMessages.setCellRenderer(new MessageRenderer(this.connection.getUsername()));

        loggedInUsersModel = new DefaultListModel<>();
        loggedInUsers = new JList<>(loggedInUsersModel);
        loggedInUsers.setCellRenderer(new LoggedInUsersRenderer(this.connection.getUsername()));
    }


    private JFrame frame;
    public static void show(JFrame frame, Connection connection) {
        ChatRoomForm form = new ChatRoomForm(connection);
        form.frame = frame;

        frame.setContentPane(form.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
