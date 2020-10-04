import Network.*;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginForm {

    public JPanel mainPanel;

    private JTextField usernameField;
    private JTextField serverField;
    private JButton logInButton;
    private JTextField portField;

    public LoginForm() {
        logInButton.addActionListener(actionEvent -> {
            String username = usernameField.getText();

            if(!Limits.validUsernameLength(username)) {
                JOptionPane.showMessageDialog(null, String.format("Username too long (%s) or too short (%s)!", Limits.MAX_USERNAME_LENGTH, Limits.MIN_USERNAME_LENGTH));
                return;
            }

            String address = serverField.getText();
            if(address.length() == 0) address = "localhost";

            int port;
            try {
                port = Integer.parseInt(portField.getText());
            }
            catch (NumberFormatException exception) {
                port = 4444;
            }

            frame.setEnabled(false);

            try (Socket server = new Socket(address, port)) {
                Connection connection = new Connection(server, username);
                if(!connection.isValid()) return;
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


    private JFrame frame;
    public static void show(JFrame frame) {
        LoginForm form = new LoginForm();
        form.frame = frame;

        frame.setContentPane(form.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
