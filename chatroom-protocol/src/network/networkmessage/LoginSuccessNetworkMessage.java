package network.networkmessage;

import network.Packer;
import network.Unpacker;

public class LoginSuccessNetworkMessage extends NetworkMessage {

    public static final byte CODE = 0x03;

    public LoginSuccessNetworkMessage(String token) {
        super(new Object[]{token});
    }

    public LoginSuccessNetworkMessage(byte[] bytes) {
        this(unpack(bytes));
    }

    public static String unpack(byte[] bytes) {
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


