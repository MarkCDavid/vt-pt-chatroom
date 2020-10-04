package network.message;

import network.Packer;
import network.Unpacker;

import java.time.ZonedDateTime;

public class SystemMessage extends Message {

    public static final byte CODE = 0x02;

    public String getReason() {
        return reason;
    }

    public SystemMessage(String reason, ZonedDateTime dateTime, String message) {
        super(dateTime, message);
        this.reason = reason;
    }

    public SystemMessage(String reason, String message) {
        super(message);
        this.reason = reason;
    }

    private final String reason;

    @Override
    public byte[] pack(byte messageCode) {
        Packer packer = new Packer();
        packer.packByte(messageCode);
        packer.packByte(CODE);
        packer.packString(getReason());
        packer.packZonedDateTime(getDateTime());
        packer.packString(getData());
        return packer.getArray();
    }

    public static Message unpack(byte[] bytes) {
        Unpacker unpacker = new Unpacker(bytes);
        unpacker.skip(2);
        String reason = unpacker.unpackString();
        ZonedDateTime dateTime = unpacker.unpackZonedDateTime();
        String data = unpacker.unpackString();
        return new SystemMessage(reason, dateTime, data);
    }
}
