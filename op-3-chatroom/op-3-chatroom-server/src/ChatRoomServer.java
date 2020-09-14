import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoomServer {

    static ArrayList<Connection> connections = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java ChatRoomServer <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);

        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));

        try {
            //noinspection InfiniteLoopStatement
            while(true) {
                Connection connection = new Connection(serverSocket.accept());
                connections.add(connection);

                Thread thread = new Thread(() -> {
                    try {
                        String inputLine;
                        while ((inputLine = connection.read()) != null) {
                            for(Connection c : connections) {
                                //c.write(c.getUsername() + ": " + inputLine);
                            }
                        }
                    }
                    catch (IOException e){
                        System.out.println("Exception caught when listening for a connection");
                        System.out.println(e.getMessage());
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + port + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
