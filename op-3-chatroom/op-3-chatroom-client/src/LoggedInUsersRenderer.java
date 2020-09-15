import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoggedInUsersRenderer  extends JLabel implements ListCellRenderer<String>  {

    public LoggedInUsersRenderer(String username){
        this.username = username;
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends String> list,
            String username,
            int index,
            boolean isSelected,
            boolean cellFocused) {

        boolean clientUsername = Objects.equals(this.username, username);
        if(clientUsername) {
            setText(String.format("<html><span style=\"color:green;\">%s</span> (you)</html>", username));
        }
        else {
            setText(username);
        }

        return this;

    }
    private final String username;
}