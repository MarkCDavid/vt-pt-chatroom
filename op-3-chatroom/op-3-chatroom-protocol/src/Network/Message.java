package Network;

import java.time.*;

public class Message {

    public String getUsername() {
        return username;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public String getData() {
        return data;
    }

    public Message(String username, ZonedDateTime dateTime, String message) {
        this.username = username;
        this.dateTime = dateTime;
        this.data = message;
    }

    public Message(String username, String message) {
        this(username, ZonedDateTime.now(ZoneOffset.UTC), message);
    }

    private final String username;
    private final ZonedDateTime dateTime;
    private final String data;
}
