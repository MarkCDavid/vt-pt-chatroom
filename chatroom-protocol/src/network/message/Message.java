package network.message;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public abstract class Message {

    private final ZonedDateTime dateTime;
    private final String data;

    public Message(ZonedDateTime dateTime, String data) {
        this.dateTime = dateTime;
        this.data = data;
    }

    public Message(String data) {
        this(ZonedDateTime.now(ZoneOffset.UTC), data);
    }

    public static Message unpack(byte[] bytes) {
        byte code = bytes[1];
        return switch (code) {
            case RegularMessage.CODE -> RegularMessage.unpack(bytes);
            case DirectMessage.CODE -> DirectMessage.unpack(bytes);
            case SystemMessage.CODE -> SystemMessage.unpack(bytes);
            default -> throw new IllegalArgumentException();
        };
    }

    public abstract byte[] pack(byte code);

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public String getData() {
        return data;
    }
}