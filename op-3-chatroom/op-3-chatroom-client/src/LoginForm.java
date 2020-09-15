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

    public LoginForm() {
        logInButton.addActionListener(actionEvent -> {
            String username = usernameField.getText();
            // TODO: DISALLOW SPACES IN NAME ??
            if(username.length() < Limits.MIN_USERNAME_LENGTH) {
                JOptionPane.showMessageDialog(null, "Username too short!");
                return;
            }

            if(username.length() > Limits.MAX_USERNAME_LENGTH) {
                JOptionPane.showMessageDialog(null, "Username too long!");
                return;
            }

            String address = serverField.getText();
            if(address.length() == 0) address = "localhost:4444";

            frame.setEnabled(false);

            String[] result = address.split(":");
            String host = result[0];
            int port = 4444;
            if(result.length == 2) {
                try {
                    port = Integer.parseInt(result[1]);
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Port " + result[1] + " is invalid!");
                    return;
                }
            }

            try {
                Socket server = new Socket(host, port);
                {
                    Connection connection = new Connection(server, username);
                    if(!connection.isValid()) return;
                    ChatRoomForm.show(frame, connection);
                }

            } catch (UnknownHostException e) {
                JOptionPane.showMessageDialog(null, "Don't know about host " + host);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Couldn't get I/O for the connection to " + host);
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
