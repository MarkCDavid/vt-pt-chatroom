package Network;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class LoginSuccessNetworkMessage extends NetworkMessage {

    public static final byte code = 0x03;
    public LoginSuccessNetworkMessage(String token) {
        super(new Object[]{token});
    }

    public LoginSuccessNetworkMessage(byte[] bytes) {
        this(new String(bytes, 1, bytes.length - 1, StandardCharsets.UTF_8));
    }

    public String getToken() {
        return (String) data[0];
    }

    @Override
    public byte[] pack() {
        byte[] token = getToken().getBytes(StandardCharsets.UTF_8);
        ByteBuffer packed = ByteBuffer.allocate(token.length + 1);
        packed.put(code);
        packed.put(token);
        return packed.array();
    }

}


