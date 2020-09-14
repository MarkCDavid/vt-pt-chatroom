package Network;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ClientChatMessageNetworkMessage extends NetworkMessage {


    public static final byte code = 0x05;

    public ClientChatMessageNetworkMessage(String token, String message) {
        super(new Object[]{token, message});
    }

    public ClientChatMessageNetworkMessage(byte[] bytes) {
        super(unpack(bytes));
    }

    public String getToken() {
        return (String) data[0];
    }

    public String getMessage() {
        return (String) data[1];
    }

    @Override
    public byte[] pack() {
        byte[] username = getToken().getBytes(StandardCharsets.UTF_8);
        byte[] message = getMessage().getBytes(StandardCharsets.UTF_8);

        ByteBuffer packed = ByteBuffer.allocate(Integer.BYTES + username.length + Integer.BYTES + message.length + 1);
        packed.put(code);
        packed.putInt(username.length);
        packed.put(username);
        packed.putInt(message.length);
        packed.put(message);
        return packed.array();
    }

    private static Object[] unpack(byte[] bytes) {
        int offset = 1;
        int tokenLength = ByteBuffer.wrap(bytes, offset, Integer.BYTES).getInt();
        offset += Integer.BYTES;

        String token = new String(bytes, offset, tokenLength, StandardCharsets.UTF_8);
        offset += tokenLength;

        int messageLength = ByteBuffer.wrap(bytes, offset, Integer.BYTES).getInt();
        offset += Integer.BYTES;

        String message = new String(bytes, offset, messageLength, StandardCharsets.UTF_8);
        return new Object[] { token, message };
    }
}
