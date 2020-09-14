import Network.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginForm {
    public JPanel mainPanel;
    private JTextField usernameField;
    private JTextField serverField;
    private JButton logInButton;

    public LoginForm(JFrame frame) {
        logInButton.addActionListener(actionEvent -> {
            String username = usernameField.getText();
            if(username.length() < 4) {
                JOptionPane.showMessageDialog(null, "Username too short!");
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
                    Connection connection = new Connection(server);
                    connection.write(new LoginRequestNetworkMessage(username));
                    NetworkMessage message = connection.read();
                    if(message instanceof LoginSuccessNetworkMessage) {
                        LoginSuccessNetworkMessage loginNM = (LoginSuccessNetworkMessage)message;
                        String token =  loginNM.getToken();
                        frame.setContentPane(new ChatRoomForm(connection).mainPanel);
                        frame.pack();
                        frame.setVisible(true);
                        frame.setEnabled(true);
                        frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                    else if(message instanceof LoginFailureNetworkMessage) {
                        connection.close();
                        LoginFailureNetworkMessage loginNM = (LoginFailureNetworkMessage) message;
                        JOptionPane.showMessageDialog(null, "Server declined your connection! Reason: " + loginNM.getReason());
                    }
                    else {
                        connection.close();
                        JOptionPane.showMessageDialog(null, "Unknown server response!");
                    }

                }

//                Thread readThread = new Thread(() -> {
//                    try {
//                        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
//                        String input;
//                        while((input = in.readLine()) != null) {
//                            System.out.println(input);
//                        }
//                    } catch (IOException e) {
//                        System.err.println("Error in read thread.");
//                        e.printStackTrace();
//                    }
//                });
//
//                Thread writeThread = new Thread(() -> {
//                    try {
//                        PrintWriter out = new PrintWriter(server.getOutputStream(), true);
//                        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//                        String userInput;
//                        while ((userInput = stdIn.readLine()) != null) {
//                            out.println(userInput);
//                        }
//                    } catch (IOException e) {
//                        System.err.println("Error in write thread.");
//                        e.printStackTrace();
//                    }
//                });
//
//                readThread.start();
//                writeThread.start();

            } catch (UnknownHostException e) {
                JOptionPane.showMessageDialog(null, "Don't know about host " + host);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Couldn't get I/O for the connection to " + host);
            } finally {
                frame.setEnabled(true);
            }




            });
    }
}
