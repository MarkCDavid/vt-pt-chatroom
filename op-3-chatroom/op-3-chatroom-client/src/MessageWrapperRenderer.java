import javax.swing.*;
import java.awt.*;

public class MessageWrapperRenderer extends JLabel implements ListCellRenderer<MessageWrapper>  {

    @Override
    public Component getListCellRendererComponent(
            JList<? extends MessageWrapper> list,
            MessageWrapper message,
            int index,
            boolean isSelected,
            boolean cellFocused) {


        setText(String.format("<html><i>[%s | %s]</i>: %s<html>",
                message.getUsername(),
                message.getDateTime(),
                message.getData()
        ));
        return this;

    }
}
