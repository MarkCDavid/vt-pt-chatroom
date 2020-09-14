import java.io.*;
import java.net.Socket;

public class Connection {

    public String getUsername() { return username; }

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        if(!initIO()) return;

    }

    public String read() throws IOException {
        return "";
    }

    public void write(NetworkMessage message) throws IOException {
        out.write(message.pack());
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
