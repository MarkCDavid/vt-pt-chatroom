package client.rendering.handlers;

import client.HTMLFormattingUtils;
import colors.ColorsTheme;
import network.handlers.MessageHandler;
import network.message.DirectMessage;

import javax.swing.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

public class DirectMessageHandler extends MessageHandler<DirectMessage> {

    public DirectMessageHandler(JLabel label, String username, ColorsTheme theme) {
        this.label = label;
        this.username = username;
        this.theme = theme;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault());
    }

    @Override
    protected void handleCore(DirectMessage message) {
        String time = spanWithColor(dateTimeFormatter.format(message.getDateTime()), theme.getGreenForeground());
        String from = spanWithColor(trySwitchUsername(message.getFrom()), theme.getMagentaBackground());
        String to = spanWithColor(trySwitchUsername(message.getTo()), theme.getMagentaBackground());
        String data = spanWithColor(message.getData(), theme.getBlackBackground());
        label.setText(String.format(FORMAT_STRING, time, from, to, data));
    }

    private String spanWithColor(String value, String color) {
        return HTMLFormattingUtils.tag("span", value, Map.of("color", color));
    }

    private String trySwitchUsername(String username) {
        if(Objects.equals(this.username, username)) return DirectMessageHandler.FROM_SELF;
        return username;
    }

    private static final String FROM_SELF = "you";
    private static final String FORMAT_STRING = "<html>[%s] &lt %s -&gt %s &gt: %s</html>";

    private final JLabel label;
    private final String username;
    private final ColorsTheme theme;
    private final DateTimeFormatter dateTimeFormatter;
}
