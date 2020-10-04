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

    public String getUsername() { return username; }
    public String getToken() { return token; }

    public Connection(Socket socket) {
        super(socket);

        NetworkMessage message = this.read();
        if(message instanceof LoginRequestNetworkMessage)
        {
            LoginRequestNetworkMessage loginNM = (LoginRequestNetworkMessage)message;
            this.username = loginNM.getUsername();

            if(this.username.length() < Limits.MIN_USERNAME_LENGTH || this.username.length() > Limits.MAX_USERNAME_LENGTH) {
                System.out.println("Requester " + this.getAddress() + " username [" + this.username + "] is invalid!");
                this.write(new LoginFailureNetworkMessage("Invalid username!"));
                this.close();
            }
            else {
                this.token = TokenGenerator.generateToken();
                this.write(new LoginSuccessNetworkMessage(this.token));
            }
        }
        else {
            System.out.println("Requester " + this.getAddress() + " sent an invalid message!");
            this.write(new LoginFailureNetworkMessage("Invalid message!"));
            this.close();
        }
    }

    private String username;
    private String token;

}
