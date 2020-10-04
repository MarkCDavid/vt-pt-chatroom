package network;

public class LoginFailureNetworkMessage extends NetworkMessage {

    public static final byte code = 0x02;

    public LoginFailureNetworkMessage(String reason) {
        super(new Object[]{reason});
    }

    public LoginFailureNetworkMessage(byte[] bytes) {
        this( unpack(bytes) );
    }

    public String getReason() {
        return (String) data[0];
    }

    @Override
    public byte[] pack() {
        Packer packer = new Packer();
        packer.packByte(code);
        packer.packString(getReason());
        return packer.getArray();
    }

    public static String unpack(byte[] bytes) {
        Unpacker unpacker = new Unpacker(bytes);
        unpacker.skip(1);
        return unpacker.unpackString();
    }
}
