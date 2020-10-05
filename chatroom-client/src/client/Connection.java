package client;

import network.connection.BaseConnection;

import java.net.Socket;

public class Connection extends BaseConnection {

    private final String username;
    private String token;

    public Connection(Socket socket, String username) {
        super(socket);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
