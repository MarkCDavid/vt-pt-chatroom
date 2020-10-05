package client;

import client.handlers.*;
import client.rendering.LoggedInUsersRenderer;
import client.rendering.MessageRenderer;
import colors.SolarizedTheme;
import network.handlers.NetworkMessageProxy;
import network.message.Message;
import network.message.RegularMessage;
import network.networkmessage.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatRoomForm {
    public JPanel mainPanel;

    private JButton submitButton;
    private JButton logOutButton;

    private JTextArea userMessageField;

    private JList<Message> chatMessages;
    private DefaultListModel<Message> chatMessagesModel;
    private ConcurrentLinkedQueue<Message> chatMessagesQueue;

    private JList<String> loggedInUsers;
    private DefaultListModel<String> loggedInUsersModel;
    private ConcurrentLinkedQueue<String> loggedInUsersQueue;
    private ConcurrentLinkedQueue<String> loggedOutUsersQueue;

    private final Connection connection;
    private static final String DIRECT_MESSAGE = "/dm \"%s\" ";

    public ChatRoomForm(Connection connection) {
        this.connection = connection;
        new Thread(getMessageHandler()).start();
        subscribeEvents(connection);
    }

    private void subscribeEvents(Connection connection) {
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
                userMessageField.setText(String.format(DIRECT_MESSAGE, username));
            }
            loggedInUsers.clearSelection();
        });

        chatMessages.addListSelectionListener(listSelectionEvent -> {
            Message message = chatMessages.getSelectedValue();
            if(message instanceof RegularMessage) {
                RegularMessage regularMessage = (RegularMessage) message;
                userMessageField.setText(String.format(DIRECT_MESSAGE, regularMessage.getUsername()));
            }
            chatMessages.clearSelection();
        });
    }

    private Runnable getMessageHandler() {
        return () -> {

            NetworkMessageProxy<Connection> proxy = new NetworkMessageProxy<>();
            proxy.subscribe(LoginSuccessNetworkMessage.class, new LoginSuccessHandler());
            proxy.subscribe(LoginFailureNetworkMessage.class, new LoginFailureHandler());
            proxy.subscribe(ServerChatMessageNetworkMessage.class, new ServerChatMessageHandler(chatMessagesQueue));
            proxy.subscribe(UserLoggedInNetworkMessage.class, new UserLoggedInHandler(chatMessagesQueue, loggedInUsersQueue));
            proxy.subscribe(UserLoggedOutNetworkMessage.class, new UserLoggedOutHandler(chatMessagesQueue, loggedOutUsersQueue));

            connection.write(new LoginRequestNetworkMessage(connection.getUsername()));
            while (true) {

                NetworkMessage message = this.connection.read();
                if(message == null || !proxy.proxy(connection, message)) {
                    connection.close();
                    LoginForm.show(frame);
                    break;
                }

                buildQueueUpdateWorker().run();
            }
        };
    }

    private SwingWorker<Object, Object> buildQueueUpdateWorker() {
        return new SwingWorker<>() {
            @Override
            protected Object doInBackground() {
                return null;
            }

            @Override
            protected void done() {
                while(!chatMessagesQueue.isEmpty()) {
                    chatMessagesModel.addElement(chatMessagesQueue.poll());
                }

                while (!loggedInUsersQueue.isEmpty()) {
                    loggedInUsersModel.addElement(loggedInUsersQueue.poll());
                }

                while (!loggedOutUsersQueue.isEmpty()) {
                    loggedInUsersModel.removeElement(loggedOutUsersQueue.poll());
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
        chatMessagesQueue = new ConcurrentLinkedQueue<>();
        chatMessages.setCellRenderer(new MessageRenderer(this.connection.getUsername(), new SolarizedTheme()));

        loggedInUsersModel = new DefaultListModel<>();
        loggedInUsers = new JList<>(loggedInUsersModel);
        loggedInUsers.setCellRenderer(new LoggedInUsersRenderer(this.connection.getUsername(), new SolarizedTheme()));
        loggedInUsersQueue = new ConcurrentLinkedQueue<>();
        loggedOutUsersQueue = new ConcurrentLinkedQueue<>();
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
