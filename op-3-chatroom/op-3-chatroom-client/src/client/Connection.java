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
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
    private final String username;

}
