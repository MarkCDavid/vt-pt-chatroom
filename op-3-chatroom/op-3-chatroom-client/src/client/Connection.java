package client;

import network.connection.BaseConnection;
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
