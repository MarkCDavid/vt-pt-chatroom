package network;

public class LoginSuccessNetworkMessage extends NetworkMessage {

    public static final byte code = 0x03;
    public LoginSuccessNetworkMessage(String token) {
        super(new Object[]{token});
    }

    public LoginSuccessNetworkMessage(byte[] bytes) {
        this( unpack(bytes) );
    }

    public String getToken() {
        return (String) data[0];
    }

    @Override
    public byte[] pack() {
        Packer packer = new Packer();
        packer.packByte(code);
        packer.packString(getToken());
        return packer.getArray();
    }

    public static String unpack(byte[] bytes) {
        Unpacker unpacker = new Unpacker(bytes);
        unpacker.skip(1);
        return unpacker.unpackString();
    }
}


