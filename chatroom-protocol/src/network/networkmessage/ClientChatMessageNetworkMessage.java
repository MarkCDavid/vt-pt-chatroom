package network.networkmessage;

import network.Packer;
import network.Unpacker;

public class ClientChatMessageNetworkMessage extends NetworkMessage {

    public static final byte CODE = 0x05;

    public ClientChatMessageNetworkMessage(String token, String message) {
        super(new Object[]{token, message});
    }

    public ClientChatMessageNetworkMessage(byte[] bytes) {
        super(unpack(bytes));
    }

    private static Object[] unpack(byte[] bytes) {
        Unpacker unpacker = new Unpacker(bytes);
        unpacker.skip(1);
        String token = unpacker.unpackString();
        String message = unpacker.unpackString();
        return new Object[]{token, message};
    }

    public String getToken() {
        return (String) data[0];
    }

    public String getMessage() {
        return (String) data[1];
    }

    @Override
    public byte[] pack() {
        Packer packer = new Packer();
        packer.packByte(CODE);
        packer.packString(getToken());
        packer.packString(getMessage());
        return packer.getArray();
    }
}
