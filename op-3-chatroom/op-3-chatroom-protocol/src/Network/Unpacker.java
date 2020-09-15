package Network;

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

    public String unpackString() {
        int stringLength = ByteBuffer.wrap(bytes, offset, Integer.BYTES).getInt();
        offset += Integer.BYTES;

        String string = new String(bytes, offset, stringLength, StandardCharsets.UTF_8);
        offset += stringLength;

        return string;
    }

    private final static ZoneId UTCZoneId = ZoneId.of(ZoneOffset.UTC.getId());
    public ZonedDateTime unpackZonedDateTime() {

        long epochSeconds = ByteBuffer.wrap(bytes, offset, Long.BYTES).getLong();
        Instant instant = Instant.ofEpochSecond(epochSeconds);
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, UTCZoneId);
        offset += Long.BYTES;

        return dateTime;
    }

    private int offset;
    private final byte[] bytes;
}
