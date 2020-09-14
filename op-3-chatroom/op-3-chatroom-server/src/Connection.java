import Network.*;

import java.io.*;
import java.net.Socket;

public class Connection {

    public String getUsername() { return username; }

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        if(!initIO()) return;
        NetworkMessage message = this.read();
        if(message instanceof LoginRequestNetworkMessage)
        {
            LoginRequestNetworkMessage loginNM = (LoginRequestNetworkMessage)message;
            this.username = loginNM.getUsername();
            System.out.println(this.username);
            this.write(new LoginSuccessNetworkMessage(TokenGenerator.generateToken()));
        }
        else {
            System.out.println("Unknown message!");
            this.write(new LoginFailureNetworkMessage("Unknown message!"));
        }
    }

    public NetworkMessage read() throws IOException {
        return Protocol.read(this.in);
    }

    public void write(NetworkMessage message) throws IOException {
        Protocol.send(this.out, message);
    }

    public void close() throws IOException {
        if(socket != null && !socket.isClosed()) {
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

    private String username;
    private DataOutputStream out;
    private DataInputStream in;
    private final Socket socket;
}
