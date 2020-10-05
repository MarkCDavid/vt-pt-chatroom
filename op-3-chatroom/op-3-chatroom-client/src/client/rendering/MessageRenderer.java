package client.rendering;

import client.HTMLFormattingUtils;
import client.rendering.handlers.DirectMessageHandler;
import client.rendering.handlers.RegularMessageHandler;
import client.rendering.handlers.SystemMessageHandler;
import network.handlers.MessageProxy;
import colors.ColorsTheme;
import network.message.DirectMessage;
import network.message.Message;
import network.message.RegularMessage;
import network.message.SystemMessage;

import javax.swing.*;
import java.awt.*;

public class MessageRenderer extends JLabel implements ListCellRenderer<Message>  {

    public MessageRenderer(String username, ColorsTheme theme){
        this.proxy = new MessageProxy();
        this.proxy.subscribe(SystemMessage.class, new SystemMessageHandler(this, theme));
        this.proxy.subscribe(DirectMessage.class, new DirectMessageHandler(this, username, theme));
        this.proxy.subscribe(RegularMessage.class, new RegularMessageHandler(this, theme));

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Message> list, Message message, int index, boolean isSelected, boolean cellFocused) {
        try {
            proxy.proxy(message);
            return this;
        }
        catch (Exception exception){
            return this;
        }
    }

    private final MessageProxy proxy;
}
