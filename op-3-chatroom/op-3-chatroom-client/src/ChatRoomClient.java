import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatRoomClient {

    public static void main(String[] args) {

        JFrame frame = new JFrame("ChatRoomForm");
        frame.setContentPane(new ChatRoomForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

//
//        if (args.length != 3) {
//            System.err.println("Usage: java EchoClient <host> <port> <username>");
//            System.exit(1);
//        }
//
//        String host = args[0];
//        int port = Integer.parseInt(args[1]);
//        String username = args[2];
//
//        try {
//            Socket server = new Socket(host, port);
//            {
//                PrintWriter out = new PrintWriter(server.getOutputStream(), true);
//                out.println(username);
//            }
//
//            Thread readThread = new Thread(() -> {
//                try {
//                    BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
//                    String input;
//                    while((input = in.readLine()) != null) {
//                        System.out.println(input);
//                    }
//                } catch (IOException e) {
//                    System.err.println("Error in read thread.");
//                    e.printStackTrace();
//                }
//            });
//
//            Thread writeThread = new Thread(() -> {
//                try {
//                    PrintWriter out = new PrintWriter(server.getOutputStream(), true);
//                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//                    String userInput;
//                    while ((userInput = stdIn.readLine()) != null) {
//                        out.println(userInput);
//                    }
//                } catch (IOException e) {
//                    System.err.println("Error in write thread.");
//                    e.printStackTrace();
//                }
//            });
//
//            readThread.start();
//            writeThread.start();
//
//        } catch (UnknownHostException e) {
//            System.err.println("Don't know about host " + host);
//            System.exit(1);
//        } catch (IOException e) {
//            System.err.println("Couldn't get I/O for the connection to " + host);
//            System.exit(1);
//        }
    }
}
