package Network;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DirectMessage extends Message {

    public final static byte code = 0x01;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public DirectMessage(String from, String to, ZonedDateTime dateTime, String message) {
        super(dateTime, message);
        this.from = from;
        this.to = to;
    }

    public DirectMessage(String from, String to, String message) {
        super(message);
        this.from = from;
        this.to = to;
    }

    private final String from;
    private final String to;

    @Override
    public byte[] pack(byte messageCode) {
        Packer packer = new Packer();
        packer.packByte(messageCode);
        packer.packByte(code);
        packer.packString(getFrom());
        packer.packString(getTo());
        packer.packZonedDateTime(getDateTime());
        packer.packString(getData());
        return packer.getArray();
    }

    public static Message unpack(byte[] bytes) {
        Unpacker unpacker = new Unpacker(bytes);
        unpacker.skip(2);
        String from = unpacker.unpackString();
        String to = unpacker.unpackString();
        ZonedDateTime dateTime = unpacker.unpackZonedDateTime();
        String data = unpacker.unpackString();
        return new DirectMessage(from, to, dateTime, data);
    }
}
