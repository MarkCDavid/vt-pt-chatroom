package network.networkmessage;

import network.Packer;
import network.Unpacker;

public class LoginRequestNetworkMessage extends NetworkMessage {

    public static final byte CODE = 0x01;

    public LoginRequestNetworkMessage(String username) {
        super(new Object[]{username});
    }

    public LoginRequestNetworkMessage(byte[] bytes) {
        this(unpack(bytes));
    }

    public static String unpack(byte[] bytes) {
        Unpacker unpacker = new Unpacker(bytes);
        unpacker.skip(1);
        return unpacker.unpackString();
    }

    public String getUsername() {
        return (String) data[0];
    }

    @Override
    public byte[] pack() {
        Packer packer = new Packer();
        packer.packByte(CODE);
        packer.packString(getUsername());
        return packer.getArray();
    }

}
