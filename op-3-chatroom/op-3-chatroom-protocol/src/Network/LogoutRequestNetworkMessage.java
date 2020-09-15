package Network;

public class LogoutRequestNetworkMessage extends NetworkMessage {

    public static final byte code = 0x08;

    public LogoutRequestNetworkMessage(String token) {
        super(new Object[]{token});
    }
    public LogoutRequestNetworkMessage(byte[] bytes) {
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

    private static String unpack(byte[] bytes) {
        Unpacker unpacker = new Unpacker(bytes);
        unpacker.unpackByte();
        return unpacker.unpackString();
    }
}
