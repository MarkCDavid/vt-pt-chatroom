import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {

    public String getUsername() { return username; }

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        if(!initIO()) return;

        this.username = this.in.readLine();
        this.out.println("success:" + TokenGenerator.generateToken());
    }

    public String read() throws IOException {
        return this.in.readLine();
    }

    public void write(String message) throws IOException {
        this.out.println(message);
    }

    private boolean initIO() {
        try {
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            return true;
        }
        catch (IOException exception) {
            return false;
        }
    }



    private String username;
    private PrintWriter out;
    private BufferedReader in;
    private final Socket socket;
}
