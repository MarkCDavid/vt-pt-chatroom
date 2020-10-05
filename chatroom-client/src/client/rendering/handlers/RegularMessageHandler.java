package client.rendering.handlers;

import client.HTMLFormattingUtils;
import colors.ColorsTheme;
import network.handlers.MessageHandler;
import network.message.RegularMessage;

import javax.swing.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class RegularMessageHandler extends MessageHandler<RegularMessage> {

    private static final String FORMAT_STRING = "<html>[%s] %s: %s</html>";
    private final JLabel label;
    private final ColorsTheme theme;
    private final DateTimeFormatter dateTimeFormatter;

    public RegularMessageHandler(JLabel label, ColorsTheme theme) {
        this.label = label;
        this.theme = theme;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault());
    }

    @Override
    protected void handleCore(RegularMessage message) {
        String time = spanWithColor(dateTimeFormatter.format(message.getDateTime()), theme.getGreenForeground());
        String username = spanWithColor(message.getUsername(), theme.getCyanBackground());
        String data = spanWithColor(message.getData(), theme.getBlackBackground());
        label.setText(String.format(FORMAT_STRING, time, username, data));
    }

    private String spanWithColor(String value, String color) {
        return HTMLFormattingUtils.tag("span", value, Map.of("color", color));
    }
}
