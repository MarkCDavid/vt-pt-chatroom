package client;

import network.*;
import network.connection.BaseConnection;
import network.connection.IO;
import network.networkmessage.LoginFailureNetworkMessage;
import network.networkmessage.LoginRequestNetworkMessage;
import network.networkmessage.LoginSuccessNetworkMessage;
import network.networkmessage.NetworkMessage;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Connection extends BaseConnection {

    public String getUsername() { return username; }
    public String getToken() { return token; }

    public Connection(Socket socket, String username) {
        super(socket);
        this.username = username;

        this.write(new LoginRequestNetworkMessage(username));
        NetworkMessage message = this.read();

        if(message instanceof LoginSuccessNetworkMessage) {
            LoginSuccessNetworkMessage loginNM = (LoginSuccessNetworkMessage)message;
            this.token = loginNM.getToken();
            System.out.println("Connection established with " + this.getAddress());
        }
        else if(message instanceof LoginFailureNetworkMessage) {
            this.close();
            LoginFailureNetworkMessage loginNM = (LoginFailureNetworkMessage) message;
            JOptionPane.showMessageDialog(null, "Server declined your connection! Reason: " + loginNM.getReason());
        }
        else {
            this.close();
            JOptionPane.showMessageDialog(null, "Unknown server response!");
        }
    }

    private String token;
    private final String username;

}
