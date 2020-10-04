package network;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Unpacker {

    public Unpacker(byte[] bytes) {
        this.bytes = bytes;
        this.offset = 0;
    }

    public void skip(int bytes) {
        this.offset += bytes;
    }

    public byte unpackByte() {
        return this.bytes[this.offset++];
    }

    public int unpackInt() {
        try {
            return ByteUtils.intFromBytes(bytes, offset);
        }
        finally {
            offset += Integer.BYTES;
        }
    }

    public String unpackString() {
        int length = unpackInt();
        try {
            return new String(bytes, offset, length, StandardCharsets.UTF_8);
        }
        finally {
            offset += length;
        }
    }


    public ZonedDateTime unpackZonedDateTime() {
        try {
            return ByteUtils.zonedDateTimeFromBytes(bytes, offset);
        }
        finally {
            offset += Long.BYTES;
        }
    }

    private int offset;
    private final byte[] bytes;
}
