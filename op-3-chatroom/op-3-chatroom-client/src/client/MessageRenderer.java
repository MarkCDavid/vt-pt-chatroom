package client;

import colors.ColorsTheme;
import colors.SolarizedTheme;
import network.message.DirectMessage;
import network.message.Message;
import network.message.RegularMessage;
import network.message.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

public class MessageRenderer extends JLabel implements ListCellRenderer<Message>  {

    private final ColorsTheme theme = new SolarizedTheme();

    public MessageRenderer(String username){
        this.username = username;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Message> list,
            Message message,
            int index,
            boolean isSelected,
            boolean cellFocused) {

        try {

            if (message instanceof RegularMessage) {
                displayRegularMessage((RegularMessage) message);
            } else if (message instanceof DirectMessage) {
                displayDirectMessage((DirectMessage) message);
            } else if (message instanceof SystemMessage) {
                displaySystemMessage((SystemMessage) message);
            }
            return this;
        }
        catch (Exception exception){
            return this;
        }

    }


    // System message
    // [TIME] | reason | message
    private void displaySystemMessage(SystemMessage message) {
        String formatString = "<html>[%s] %s | %s</html>";
        String time = HTMLFormattingUtils.tag("span", dateTimeFormatter.format(message.getDateTime()), Map.of("color", theme.getGreenForeground()));
        String reason = HTMLFormattingUtils.tag("span", message.getReason(), Map.of("color", theme.getRedBackground()));
        String data = HTMLFormattingUtils.tag("span", message.getData(), Map.of("color", theme.getRedBackground()));
        setText(String.format(formatString, time, reason, data));
    }

    // Direct message
    // From you:
    // [TIME] < you -> other>: message
    // From other:
    // [TIME] < other -> you >: message
    private void displayDirectMessage(DirectMessage message) {
        String formatString = "<html>[%s] &lt %s -&gt %s &gt: %s</html>";
        String time = HTMLFormattingUtils.tag("span", dateTimeFormatter.format(message.getDateTime()), Map.of("color", theme.getGreenForeground()));
        String from = HTMLFormattingUtils.tag("span", trySwitchUsername(message.getFrom(), "you"), Map.of("color", theme.getMagentaBackground()));
        String to = HTMLFormattingUtils.tag("span", trySwitchUsername(message.getTo(), "you"), Map.of("color", theme.getMagentaBackground()));
        String data = HTMLFormattingUtils.tag("span", message.getData(), Map.of("color", theme.getBlackBackground()));
        setText(String.format(formatString, time, from, to, data));
    }

    // Regular message
    // [TIME] username: message
    private void displayRegularMessage(RegularMessage message) {
        String formatString = "<html>[%s] %s: %s</html>";
        String time = HTMLFormattingUtils.tag("span", dateTimeFormatter.format(message.getDateTime()), Map.of("color", theme.getGreenForeground()));
        String username = HTMLFormattingUtils.tag("span", message.getUsername(), Map.of("color", theme.getCyanBackground()));
        String data = HTMLFormattingUtils.tag("span", message.getData(), Map.of("color", theme.getBlackBackground()));
        setText(String.format(formatString, time, username, data));
    }



    private String trySwitchUsername(String username, String switchTo) {
        if(Objects.equals(this.username, username)) return switchTo;
        return username;
    }

    private final String username;
    private final DateTimeFormatter dateTimeFormatter;
}
