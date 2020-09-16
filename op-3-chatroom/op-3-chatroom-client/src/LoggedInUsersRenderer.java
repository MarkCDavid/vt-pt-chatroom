import Colors.ColorsTheme;
import Colors.SolarizedTheme;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Objects;

public class LoggedInUsersRenderer  extends JLabel implements ListCellRenderer<String>  {

    private final ColorsTheme theme = new SolarizedTheme();

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
            String formatString = "<html> %s %s </html>";

            String data = HTMLFormattingUtils.tag("span", username, Map.of("color", theme.getGreenForeground()));
            String you = HTMLFormattingUtils.tag("span", "(you)", Map.of("color", theme.getBlackBackground()));
            setText(String.format(formatString, data, you));
        }
        else {
            String formatString = "<html> %s </html>";
            String data = HTMLFormattingUtils.tag("span", username, Map.of("color", theme.getBlackBackground()));
            setText(String.format(formatString, data));
        }

        return this;

    }
    private final String username;
}