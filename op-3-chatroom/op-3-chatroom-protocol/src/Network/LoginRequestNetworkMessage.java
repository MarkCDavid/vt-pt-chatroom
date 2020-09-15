package Network;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class LoginRequestNetworkMessage extends NetworkMessage {

    public static final byte code = 0x01;

    public LoginRequestNetworkMessage(String username) {
        super(new Object[]{ username });
    }

    public LoginRequestNetworkMessage(byte[] bytes) {
        this( unpack(bytes) );
    }

    public String getUsername() {
        return (String) data[0];
    }

    @Override
    public byte[] pack() {
        Packer packer = new Packer();
        packer.packByte(code);
        packer.packString(getUsername());
        return packer.getArray();
    }

    public static String unpack(byte[] bytes) {
        Unpacker unpacker = new Unpacker(bytes);
        unpacker.skip(1);;
        return unpacker.unpackString();
    }

}
