package network.connection;

import network.Protocol;
import network.networkmessage.NetworkMessage;

import java.net.Socket;

public abstract class BaseConnection {

    public String getAddress() { return address; }
    public boolean isValid() { return io.isValid(); }

    public BaseConnection(Socket socket) {
        this.address = socket.getInetAddress().toString();
        System.out.printf("Trying to establish connection to %s.%n", address);

        this.io = new IO(socket);
        if(!io.isValid()) {
            System.out.printf("Failed to establish I/O with %s.%n", address);
        }
    }

    public NetworkMessage read() {
        return Protocol.read(this.io);
    }

    public int write(NetworkMessage message) {
        return Protocol.send(this.io, message);
    }

    public void close() {
        this.io.closeIO();
    }

    private final String address;

    private final IO io;
}
