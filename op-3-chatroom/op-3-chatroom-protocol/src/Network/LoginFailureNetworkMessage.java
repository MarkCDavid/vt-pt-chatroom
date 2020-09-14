package Network;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class LoginFailureNetworkMessage extends NetworkMessage {

    public static final byte code = 0x02;

    public LoginFailureNetworkMessage(String reason) {
        super(new Object[]{reason});
    }

    public LoginFailureNetworkMessage(byte[] bytes) {
        this(new String(bytes, 1, bytes.length - 1, StandardCharsets.UTF_8));
    }

    public String getReason() {
        return (String) data[0];
    }

    @Override
    public byte[] pack() {
        byte[] username = getReason().getBytes(StandardCharsets.UTF_8);
        ByteBuffer packed = ByteBuffer.allocate(username.length + 1);
        packed.put(code);
        packed.put(username);
        return packed.array();
    }
}
