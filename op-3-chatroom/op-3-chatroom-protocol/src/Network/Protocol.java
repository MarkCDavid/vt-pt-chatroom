package Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Protocol {


// Message Exchange
// Sender [int || messageSize] -> Receiver
// Receiver -> [readInt] messageSize
// Sender [byte[] || message] -> Receiver
// Receiver -> [read(messageSize)] message

    public static void send(DataOutputStream out, NetworkMessage message) throws IOException {
        byte[] byteMessage = message.pack();
        int messageLength = byteMessage.length;
        out.writeInt(messageLength);
        out.write(byteMessage);
    }

    public static NetworkMessage read(DataInputStream in) throws IOException {
        int messageLength = in.readInt();
        if(messageLength > 0) {
            byte[] message = new byte[messageLength];
            in.readFully(message, 0, messageLength);
            return unpack(message);
        }

        // TODO: Consider other solutions
        return null;
    }

    // NetworkMessage - first byte defines message type.
    public static NetworkMessage unpack(byte[] bytes){
        byte messageType = bytes[0];

        switch (messageType) {
            case LoginRequestNetworkMessage.code:
                return new LoginRequestNetworkMessage(bytes);

            case LoginFailureNetworkMessage.code:
                return new LoginFailureNetworkMessage(bytes);

            case LoginSuccessNetworkMessage.code:
                return new LoginSuccessNetworkMessage(bytes);

            case ServerChatMessageNetworkMessage.code:
                return new ServerChatMessageNetworkMessage(bytes);

            case ClientChatMessageNetworkMessage.code:
                return new ClientChatMessageNetworkMessage(bytes);

            case UserLoggedInNetworkMessage.code:
                return new UserLoggedInNetworkMessage(bytes);

            case UserLoggedOutNetworkMessage.code:
                return new UserLoggedOutNetworkMessage(bytes);
        }

        // TODO: Consider other solutions
        return null;

    }



}


//  === CLIENT <-- SERVER ===
//  Login Success          token {1}
//  Login Failure          reason {1}
//  User Logged In         username {1}
//  User Logged Out        username, reason {1}
//  Message                username, time, message {1, }

//  === CLIENT --> SERVER ===
//  Login Request          username {1}
//  Logout Request         token {1}
//  Message                token, message {1}
