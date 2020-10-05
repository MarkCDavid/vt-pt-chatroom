package network;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ByteUtils {

    private static final ZoneId UTCZoneId = ZoneId.of(ZoneOffset.UTC.getId());

    private ByteUtils() {
    }

    public static byte[] intToBytes(int value) {
        return ByteBuffer.allocate(Integer.BYTES).putInt(value).array();
    }

    public static int intFromBytes(byte[] bytes, int offset) {
        return ByteBuffer.wrap(bytes, offset, Integer.BYTES).getInt();
    }

    public static byte[] zonedDateTimeToBytes(ZonedDateTime value) {
        return ByteBuffer.allocate(Long.BYTES).putLong(value.toEpochSecond()).array();
    }

    public static ZonedDateTime zonedDateTimeFromBytes(byte[] bytes, int offset) {
        Instant instant = Instant.ofEpochSecond(ByteBuffer.wrap(bytes, offset, Long.BYTES).getLong());
        return ZonedDateTime.ofInstant(instant, UTCZoneId);
    }
}
