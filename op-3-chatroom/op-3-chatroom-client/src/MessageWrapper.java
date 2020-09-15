import Network.Message;

import java.time.format.DateTimeFormatter;

public class MessageWrapper {

    public MessageWrapper(Message message){
        this.message = message;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public String getUsername() {
        return message.getUsername();
    }

    public String getDateTime() {
        return dateTimeFormatter.format(message.getDateTime());
    }

    public String getData() {
        return message.getData();
    }

    @Override
    public String toString() {
        return String.format("[%s | %s]: %s", getUsername(), getDateTime(), getData());
    }

    private final Message message;
    private final DateTimeFormatter dateTimeFormatter;
}
