package Network;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class LoginRequestNetworkMessage extends NetworkMessage {

    public static final byte code = 0x01;

    public LoginRequestNetworkMessage(String username) {
        super(new Object[]{ username });
    }

    public LoginRequestNetworkMessage(byte[] bytes) {
        this(new String(bytes, 1, bytes.length - 1, StandardCharsets.UTF_8));
    }

    public String getUsername() {
        return (String) data[0];
    }

    @Override
    public byte[] pack() {
        byte[] username = getUsername().getBytes(StandardCharsets.UTF_8);
        ByteBuffer packed = ByteBuffer.allocate(username.length + 1);
        packed.put(code);
        packed.put(username);
        return packed.array();
    }


}
