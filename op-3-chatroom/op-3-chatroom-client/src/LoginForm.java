import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
                    PrintWriter out = new PrintWriter(server.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                    out.println(username);
                    String unsplit = in.readLine();
                    String[] serverResponse = unsplit.split(":");
                    System.out.println(unsplit);

                    if(serverResponse.length == 2){
                        if(serverResponse[0].equals("success")){
                            String token = serverResponse[1];
                            frame.setContentPane(new ChatRoomForm().mainPanel);
                            frame.pack();
                            frame.setVisible(true);
                            frame.setEnabled(true);
                            frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        }
                        else {
                            server.close();
                            JOptionPane.showMessageDialog(null, "Server declined your connection! Reason: " + serverResponse[1]);
                        }
                    }
                    else {
                        server.close();
                        JOptionPane.showMessageDialog(null, "Unable to understand the server respone!");
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
