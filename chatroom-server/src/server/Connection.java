package server;

import network.connection.BaseConnection;

import java.net.Socket;

public class Connection extends BaseConnection {

    private String username;
    private String token;

    public Connection(Socket socket) {
        super(socket);
    }

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
}
