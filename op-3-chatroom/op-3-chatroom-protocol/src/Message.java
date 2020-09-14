import java.time.LocalDateTime;

public class Message {

    String getUsername() {
        return username;
    }

    LocalDateTime getDateTime() {
        return dateTime;
    }

    String getMessage() {
        return message;
    }

    public Message(String username, LocalDateTime dateTime, String message) {
        this.username = username;
        this.dateTime = dateTime;
        this.message = message;
    }

    public Message(String username, String message) {
        this(username, LocalDateTime.now(), message);
    }

    private final String username;
    private final LocalDateTime dateTime;
    private final String message;
}
