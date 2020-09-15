import Network.*;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Connection {

    public String getUsername() { return username; }
    public String getToken() { return token; }
    public String getAddress() { return this.socket.getInetAddress().toString(); }
    public boolean isValid() { return valid; }

    public Connection(Socket socket, String username) throws IOException {
        this.valid = true;
        this.socket = socket;
        this.username = username;
        System.out.println("Trying to establish connection to " + this.getAddress());

        if(!initIO()) {
            System.out.println("Failed to establish I/O with the server at " + this.getAddress());
            valid = false;
        }

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

    public NetworkMessage read() throws IOException {
        if(valid) {
            return Protocol.read(this.in);
        }
        else{
            return null;
        }
    }

    public void write(NetworkMessage message) throws IOException {
        if(valid) {
            Protocol.send(this.out, message);
        }
    }

    public void close() throws IOException {
        if(socket != null && !socket.isClosed()) {
            this.closeIO();
            socket.close();
        }
    }

    private boolean initIO() {
        try {
            this.out = new DataOutputStream(this.socket.getOutputStream());
            this.in = new DataInputStream(this.socket.getInputStream());
            return true;
        }
        catch (IOException exception) {
            return false;
        }
    }

    private void closeIO() {
        try {
            if(this.out != null) {
                this.out.close();
            }
            if(this.in != null) {
                this.in.close();
            }
        }
        catch (IOException ignored) {

        }
        finally {
            valid = false;
        }
    }

    private boolean valid;

    private String username;
    private String token;

    private DataOutputStream out;
    private DataInputStream in;

    private final Socket socket;

}
