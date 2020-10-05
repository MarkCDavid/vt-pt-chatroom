package network.networkmessage;

import network.Packer;
import network.Unpacker;

public class LogoutRequestNetworkMessage extends NetworkMessage {

    public static final byte CODE = 0x08;

    public LogoutRequestNetworkMessage(String token) {
        super(new Object[]{token});
    }

    public LogoutRequestNetworkMessage(byte[] bytes) {
        this(unpack(bytes));
    }

    private static String unpack(byte[] bytes) {
        Unpacker unpacker = new Unpacker(bytes);
        unpacker.skip(1);
        return unpacker.unpackString();
    }

    public String getToken() {
        return (String) data[0];
    }

    @Override
    public byte[] pack() {
        Packer packer = new Packer();
        packer.packByte(CODE);
        packer.packString(getToken());
        return packer.getArray();
    }
}
