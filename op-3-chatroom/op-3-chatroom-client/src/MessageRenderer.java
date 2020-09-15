import Network.DirectMessage;
import Network.Message;
import Network.RegularMessage;
import Network.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

public class MessageRenderer extends JLabel implements ListCellRenderer<Message>  {

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

        if(message instanceof RegularMessage) {
            displayRegularMessage((RegularMessage)message);
        }
        else if (message instanceof DirectMessage) {
            displayDirectMessage((DirectMessage)message);
        }
        else if(message instanceof SystemMessage){
            displaySystemMessage((SystemMessage) message);
        }
        return this;

    }


    // System message
    // [TIME] | reason | message
    private void displaySystemMessage(SystemMessage message) {
        String formatString = "<html>[%s] %s | %s</html>";
        String time = tag("span", dateTimeFormatter.format(message.getDateTime()), Map.of("color", "#88ee44"));
        String reason = tag("span", message.getReason(), Map.of("color", "#aa88ff"));
        String data = tag("span", message.getData(), Map.of("color", "#800000"));
        setText(String.format(formatString, time, reason, data));
    }

    // Direct message
    // From you:
    // [TIME] <(you) -> (other)>: message
    // From other:
    // [TIME] <(other) -> (you)>: message
    private void displayDirectMessage(DirectMessage message) {
        String formatString = "<html>[%s] &lt %s -&gt %s &gt: %s</html>";
        String time = tag("span", dateTimeFormatter.format(message.getDateTime()), Map.of("color", "#88ee44"));
            String from = tag("span", trySwitchUsername(message.getFrom(), "you"), Map.of("color", "#aa88ff"));
        String to = tag("span", trySwitchUsername(message.getTo(), "you"), Map.of("color", "#5511ff"));
        String data = tag("span", message.getData(), Map.of("color", "#000000"));
        setText(String.format(formatString, time, from, to, data));
    }

    // Regular message
    // [TIME] <username>: message
    private void displayRegularMessage(RegularMessage message) {
        String formatString = "<html>[%s] &lt%s&gt: %s</html>";
        String time = tag("span", dateTimeFormatter.format(message.getDateTime()), Map.of("color", "#88ee44"));
        String username = tag("span", message.getUsername(), Map.of("color", "#d2d266"));
        String data = tag("span", message.getData(), Map.of("color", "#000000"));
        setText(String.format(formatString, time, username, data));
    }

    private String tag(String tag, String value, Map<String, String> css) {
        if(css == null || css.isEmpty()) return String.format("<%s>%s</%s>", tag, value, tag);
        else return String.format("<%s style=\"%s\">%s</%s>", tag, buildStyles(css), value, tag);
    }

    private String tag(String tag, String value) {
        return tag(tag, value, null);
    }

    private String trySwitchUsername(String username, String switchTo) {
        if(Objects.equals(this.username, username)) return switchTo;
        return username;
    }

    private String buildStyles(Map<String, String> css) {
        StringBuilder builder = new StringBuilder();
        for(String key: css.keySet())
        {
            builder.append(key);
            builder.append(":");
            builder.append(css.get(key));
            builder.append(";");
        }
        return builder.toString();
    }

    private final String username;
    private final DateTimeFormatter dateTimeFormatter;
}
