package Network;

import java.time.*;

public abstract class Message {

    public Message(ZonedDateTime dateTime, String data) {
        this.dateTime = dateTime;
        this.data = data;
    }

    public Message(String data) {
        this(ZonedDateTime.now(ZoneOffset.UTC), data);
    }

    public abstract byte[] pack(byte code);

    public static Message unpack(byte[] bytes) {
        byte code = bytes[1];
        switch (code) {
            case RegularMessage.code:
                return RegularMessage.unpack(bytes);
            case DirectMessage.code:
                return DirectMessage.unpack(bytes);
            case SystemMessage.code:
                return SystemMessage.unpack(bytes);
            default:
                throw new IllegalArgumentException();
        }
    }

    public ZonedDateTime getDateTime() { return dateTime; }
    public String getData() { return data; }

    private final ZonedDateTime dateTime;
    private final String data;
}