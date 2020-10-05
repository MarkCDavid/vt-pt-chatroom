package network.message;

import network.Packer;
import network.Unpacker;

import java.time.ZonedDateTime;

public class DirectMessage extends Message {

    public static final byte CODE = 0x01;
    private final String from;
    private final String to;

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

    public static Message unpack(byte[] bytes) {
        Unpacker unpacker = new Unpacker(bytes);
        unpacker.skip(2);
        String from = unpacker.unpackString();
        String to = unpacker.unpackString();
        ZonedDateTime dateTime = unpacker.unpackZonedDateTime();
        String data = unpacker.unpackString();
        return new DirectMessage(from, to, dateTime, data);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public byte[] pack(byte messageCode) {
        Packer packer = new Packer();
        packer.packByte(messageCode);
        packer.packByte(CODE);
        packer.packString(getFrom());
        packer.packString(getTo());
        packer.packZonedDateTime(getDateTime());
        packer.packString(getData());
        return packer.getArray();
    }
}
