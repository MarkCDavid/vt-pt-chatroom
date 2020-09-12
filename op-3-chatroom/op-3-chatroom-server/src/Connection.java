import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {

    Socket socket;
    String username;
    PrintWriter writer;

    public Connection(Socket socket, String username) throws IOException {
        this.socket = socket;
        this.username = username;
        this.writer = new PrintWriter(this.socket.getOutputStream(), true);
    }

}
