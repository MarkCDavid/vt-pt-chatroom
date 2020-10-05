package server;

import network.connection.BaseConnection;

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
