package Network;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ServerChatMessageNetworkMessage extends NetworkMessage {

    public static final byte code = 0x04;

    public ServerChatMessageNetworkMessage(Message message) {
        super( new Object[] {message} );
    }

    public ServerChatMessageNetworkMessage(byte[] bytes) {
        this( unpack(bytes) );
    }

    public Message getMessage() {
        return (Message) data[0];
    }

    @Override
    public byte[] pack() {
        Message message = getMessage();
        byte[] username = message.getUsername().getBytes(StandardCharsets.UTF_8);
        long dateTime = message.getDateTime().toEpochSecond();
        System.out.println(dateTime);
        byte[] data = getMessage().getData().getBytes(StandardCharsets.UTF_8);

        ByteBuffer packed = ByteBuffer.allocate(Integer.BYTES + username.length + Long.BYTES + Integer.BYTES + data.length + 1);
        packed.put(code);
        packed.putInt(username.length);
        packed.put(username);
        packed.putLong(dateTime);
        packed.putInt(data.length);
        packed.put(data);

        return packed.array();
    }

    public static Message unpack(byte[] bytes) {
        int offset = 1;
        int usernameLength = ByteBuffer.wrap(bytes, offset, Integer.BYTES).getInt();
        offset += Integer.BYTES;

        String username = new String(bytes, offset, usernameLength, StandardCharsets.UTF_8);
        offset += usernameLength;

        ZoneId UTCid = ZoneId.of(ZoneOffset.UTC.getId());
        Instant instant = Instant.ofEpochSecond(ByteBuffer.wrap(bytes, offset, Long.BYTES).getLong());
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, UTCid);
        offset += Long.BYTES;

        int dataLength = ByteBuffer.wrap(bytes, offset, Integer.BYTES).getInt();
        offset += Integer.BYTES;

        String data = new String(bytes, offset, dataLength, StandardCharsets.UTF_8);
        return new Message(username, dateTime, data);
    }
}
