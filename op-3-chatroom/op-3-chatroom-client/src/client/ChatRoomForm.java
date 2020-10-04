package client;

import network.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

        Thread readThread = new Thread(getMessageHandler());

        readThread.start();

        userMessageField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                handleEnter(e);
            }
            @Override
            public void keyPressed(KeyEvent e) {
                handleEnter(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Interface segregation principle violation
            }

            private void handleEnter(KeyEvent e) {
                if(e.getKeyCode() != KeyEvent.VK_ENTER)
                    return;

                if(e.isShiftDown()) {
                    addNewLineToMessage();
                }
                else {
                    e.consume();
                    sendMessage(connection);
                }
            }
        });

        submitButton.addActionListener(actionEvent -> sendMessage(connection));

        logOutButton.addActionListener(actionEvent -> {
            sendLogoutRequest(connection);
            connection.close();
            LoginForm.show(frame);
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

    private Runnable getMessageHandler() {
        return () -> {
            while (true) {
                NetworkMessage message = this.connection.read();
                if (message instanceof ServerChatMessageNetworkMessage) {
                    ServerChatMessageNetworkMessage serverChatMessageNM = (ServerChatMessageNetworkMessage) message;
                    chatMessagesModel.addElement(serverChatMessageNM.getMessage());
                } else if (message instanceof UserLoggedInNetworkMessage) {
                    UserLoggedInNetworkMessage userLoggedInNM = (UserLoggedInNetworkMessage) message;
                    chatMessagesModel.addElement(new SystemMessage("User Logged In", userLoggedInNM.getUsername()));
                    loggedInUsersModel.addElement(userLoggedInNM.getUsername());
                } else if (message instanceof UserLoggedOutNetworkMessage) {
                    UserLoggedOutNetworkMessage userLoggedOutNM = (UserLoggedOutNetworkMessage) message;
                    chatMessagesModel.addElement(new SystemMessage("User Logged Out", userLoggedOutNM.getUsername()));
                    loggedInUsersModel.removeElement(userLoggedOutNM.getUsername());
                }
                else {
                    break;
                }
            }
        };
    }

    private void sendLogoutRequest(Connection connection) {
        LogoutRequestNetworkMessage message = new LogoutRequestNetworkMessage(connection.getToken());
        connection.write(message);
    }

    private void sendMessage(Connection connection) {
        String textMessage = userMessageField.getText();
        userMessageField.setText("");
        ClientChatMessageNetworkMessage message = new ClientChatMessageNetworkMessage(connection.getToken(), textMessage);
        connection.write(message);
    }

    private void addNewLineToMessage() {
        userMessageField.setText(String.format("%s%n", userMessageField.getText()));
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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
