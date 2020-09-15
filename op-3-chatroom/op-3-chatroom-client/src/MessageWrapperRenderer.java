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


        setText(String.format("<html><span style=\"color:gray;\">[<span style=\"color:red;\">%s</span>| %s]</span>: %s</html>",
                message.getUsername(),
                message.getDateTime(),
                message.getData().replaceAll("\n", "<br>")
        ));
        return this;

    }
}
