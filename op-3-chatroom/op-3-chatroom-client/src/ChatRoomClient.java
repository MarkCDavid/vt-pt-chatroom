import javax.swing.*;

public class ChatRoomClient {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Chat Room");
        frame.setContentPane(new LoginForm(frame).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
