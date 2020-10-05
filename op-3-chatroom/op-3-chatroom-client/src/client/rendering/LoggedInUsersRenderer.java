package client.rendering;

import client.HTMLFormattingUtils;
import colors.ColorsTheme;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Objects;

public class LoggedInUsersRenderer  extends JLabel implements ListCellRenderer<String>  {

    private final ColorsTheme theme;

    public LoggedInUsersRenderer(String username, ColorsTheme theme){
        this.username = username;
        this.theme = theme;
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

            String data = HTMLFormattingUtils.tag("span", username, Map.of(COLOR_FIELD, theme.getGreenForeground()));
            String you = HTMLFormattingUtils.tag("span", "(you)", Map.of(COLOR_FIELD, theme.getBlackBackground()));
            setText(String.format(formatString, data, you));
        }
        else {
            String formatString = "<html> %s </html>";
            String data = HTMLFormattingUtils.tag("span", username, Map.of(COLOR_FIELD, theme.getBlackBackground()));
            setText(String.format(formatString, data));
        }

        return this;

    }

    private static final String COLOR_FIELD = "color";

    private final String username;
}