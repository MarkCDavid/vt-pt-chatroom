package network.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class IO {

    private final Socket socket;
    private boolean valid = true;
    private DataOutputStream out;
    private DataInputStream in;

    public IO(Socket socket) {
        this.socket = socket;

        try {
            this.out = new DataOutputStream(this.socket.getOutputStream());
            this.in = new DataInputStream(this.socket.getInputStream());
        } catch (IOException exception) {
            valid = false;
        }

    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public boolean isValid() {
        return valid;
    }

    public Socket getSocket() {
        return socket;
    }

    public void closeIO() {
        try {
            if (this.out != null)
                this.out.close();
        } catch (IOException exception) {
            valid = false;
        }

        try {
            if (this.in != null)
                this.in.close();
        } catch (IOException exception) {
            valid = false;
        }

        try {
            if (this.socket != null)
                this.socket.close();
        } catch (IOException exception) {
            valid = false;
        }

        valid = false;
    }
}
