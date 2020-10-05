package network.networkmessage;

import network.message.Message;

public class ServerChatMessageNetworkMessage extends NetworkMessage {

    public static final byte CODE = 0x04;

    public ServerChatMessageNetworkMessage(Message message) {
        super(new Object[]{message});
    }

    public ServerChatMessageNetworkMessage(byte[] bytes) {
        this(unpack(bytes));
    }

    private static Message unpack(byte[] bytes) {
        return Message.unpack(bytes);
    }

    public Message getMessage() {
        return (Message) data[0];
    }

    @Override
    public byte[] pack() {
        return getMessage().pack(CODE);
    }
}
