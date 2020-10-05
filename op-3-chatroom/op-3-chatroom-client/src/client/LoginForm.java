package client;

import network.Limits;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginForm {

    private static final String LOCALHOST = "localhost";
    private JPanel mainPanel;
    private JTextField usernameField;
    private JTextField serverField;
    private JButton logInButton;
    private JTextField portField;
    private JFrame frame;

    public LoginForm() {
        logInButton.addActionListener(actionEvent -> {
            String username = usernameField.getText();

            if (!Limits.validUsernameLength(username)) {
                JOptionPane.showMessageDialog(null, String.format("Username too long (%s) or too short (%s)!", Limits.MAX_USERNAME_LENGTH, Limits.MIN_USERNAME_LENGTH));
                return;
            }

            String address = getAddress();
            int port = getPort();

            frame.setEnabled(false);
            try {
                Connection connection = new Connection(new Socket(address, port), username);
                if (!connection.isValid()) return;
                ChatRoomForm.show(frame, connection);
            } catch (UnknownHostException e) {
                JOptionPane.showMessageDialog(null, String.format("Don't know about host %s:%s", address, port));
            } catch (IOException | IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, String.format("Couldn't get I/O for the connection to %s:%s", address, port));
            } finally {
                frame.setEnabled(true);
            }
        });
    }

    public static void show(JFrame frame) {
        LoginForm form = new LoginForm();
        form.frame = frame;

        frame.setContentPane(form.mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private int getPort() {
        try {
            return Integer.parseInt(portField.getText());
        } catch (NumberFormatException exception) {
            return 4444;
        }
    }

    private String getAddress() {
        return serverField.getText().isBlank() ? LOCALHOST : serverField.getText();
    }

}
