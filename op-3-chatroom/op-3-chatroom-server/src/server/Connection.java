package server;

import network.*;
import network.connection.BaseConnection;
import network.networkmessage.LoginFailureNetworkMessage;
import network.networkmessage.LoginRequestNetworkMessage;
import network.networkmessage.LoginSuccessNetworkMessage;
import network.networkmessage.NetworkMessage;

import java.io.*;
import java.net.Socket;

public class Connection extends BaseConnection {

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public Connection(Socket socket) {
        super(socket);
    }

    private String username;
    private String token;
}
