package network;

import network.connection.IO;
import network.networkmessage.*;

import java.io.IOException;

public class Protocol {

    private Protocol() {
    }

    public static int send(IO io, NetworkMessage message) {
        try {
            byte[] byteMessage = message.pack();
            io.getOut().writeInt(byteMessage.length);
            io.getOut().write(byteMessage);
            return byteMessage.length;
        } catch (IOException exception) {
            return -1;
        }
    }

    public static NetworkMessage read(IO io) {
        try {
            int messageLength = io.getIn().readInt();
            if (messageLength == 0)
                return null;

            byte[] message = new byte[messageLength];
            io.getIn().readFully(message, 0, messageLength);
            return unpack(message);
        } catch (IOException exception) {
            return null;
        }
    }

    public static NetworkMessage unpack(byte[] bytes) {
        return switch (bytes[0]) {
            case LoginRequestNetworkMessage.CODE -> new LoginRequestNetworkMessage(bytes);
            case LoginFailureNetworkMessage.CODE -> new LoginFailureNetworkMessage(bytes);
            case LoginSuccessNetworkMessage.CODE -> new LoginSuccessNetworkMessage(bytes);
            case ServerChatMessageNetworkMessage.CODE -> new ServerChatMessageNetworkMessage(bytes);
            case ClientChatMessageNetworkMessage.CODE -> new ClientChatMessageNetworkMessage(bytes);
            case UserLoggedInNetworkMessage.CODE -> new UserLoggedInNetworkMessage(bytes);
            case UserLoggedOutNetworkMessage.CODE -> new UserLoggedOutNetworkMessage(bytes);
            case LogoutRequestNetworkMessage.CODE -> new LogoutRequestNetworkMessage(bytes);
            default -> null;
        };
    }
}
