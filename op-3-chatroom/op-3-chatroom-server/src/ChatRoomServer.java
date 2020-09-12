import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String username = in.readLine();
                System.out.println("Username: " + username);

                connections.add(new Connection(clientSocket, username));
                Thread thread = new Thread(() -> {
                    try {
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            for(Connection connection : connections) {
                                connection.writer.println(username + ": " + inputLine);
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
