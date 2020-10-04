package network.message;


import network.Packer;
import network.Unpacker;

import java.time.ZonedDateTime;

public final class RegularMessage extends Message {

    public static final byte CODE = 0x00;

    public String getUsername() {
        return username;
    }

    public RegularMessage(String username, ZonedDateTime dateTime, String message) {
        super(dateTime, message);
        this.username = username;
    }

    public RegularMessage(String username, String message) {
        super(message);
        this.username = username;
    }

    private final String username;

    @Override
    public byte[] pack(byte messageCode) {
        Packer packer = new Packer();
        packer.packByte(messageCode);
        packer.packByte(CODE);
        packer.packString(getUsername());
        packer.packZonedDateTime(getDateTime());
        packer.packString(getData());
        return packer.getArray();
    }

    public static Message unpack(byte[] bytes) {
        Unpacker unpacker = new Unpacker(bytes);
        unpacker.skip(2);
        String username = unpacker.unpackString();
        ZonedDateTime dateTime = unpacker.unpackZonedDateTime();
        String data = unpacker.unpackString();
        return new RegularMessage(username, dateTime, data);
    }
}
