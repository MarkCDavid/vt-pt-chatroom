package Network;
import java.time.ZonedDateTime;

public class ServerChatMessageNetworkMessage extends NetworkMessage {

    public static final byte code = 0x04;

    public ServerChatMessageNetworkMessage(Message message) {
        super( new Object[] {message} );
    }

    public ServerChatMessageNetworkMessage(byte[] bytes) {
        this( unpack(bytes) );
    }

    public Message getMessage() {
        return (Message) data[0];
    }

    @Override
    public byte[] pack() {
        Packer packer = new Packer();
        packer.packByte(code);
        packer.packString(getMessage().getUsername());
        packer.packZonedDateTime(getMessage().getDateTime());
        packer.packString(getMessage().getData());
        return packer.getArray();
    }

    private static Message unpack(byte[] bytes) {
        Unpacker unpacker = new Unpacker(bytes);
        unpacker.unpackByte();
        String username = unpacker.unpackString();
        ZonedDateTime dateTime = unpacker.unpackZonedDateTime();
        String data = unpacker.unpackString();
        return new Message(username, dateTime, data);
    }
}
