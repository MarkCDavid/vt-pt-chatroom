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
        Packer packer = new Packer();
        packer.packByte(code);
        packer.packString(getToken());
        packer.packString(getMessage());
        return packer.getArray();
    }

    private static Object[] unpack(byte[] bytes) {
        Unpacker unpacker = new Unpacker(bytes);
        unpacker.unpackByte();
        String token = unpacker.unpackString();
        String message = unpacker.unpackString();
        return new Object[] { token, message };
    }
}
