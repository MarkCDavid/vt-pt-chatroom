import Network.NetworkMessage;
import Network.Protocol;

import java.io.*;
import java.net.Socket;

public class Connection {

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        if(!initIO()) return;

    }

    public NetworkMessage read() throws IOException {
        NetworkMessage message = null;
        while(message == null)
            message = Protocol.read(this.in);
        return message;
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

    private DataOutputStream out;
    private DataInputStream in;
    private final Socket socket;
}
