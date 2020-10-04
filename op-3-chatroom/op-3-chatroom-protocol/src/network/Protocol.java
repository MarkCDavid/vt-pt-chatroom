package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

public class Protocol {


// Message Exchange
// Sender [int || messageSize] -> Receiver
// Receiver -> [readInt] messageSize
// Sender [byte[] || message] -> Receiver
// Receiver -> [read(messageSize)] message

    public static int send(DataOutputStream out, NetworkMessage message) {
        try {
            byte[] byteMessage = message.pack();
            out.writeInt(byteMessage.length);
            out.write(byteMessage);
            return byteMessage.length;
        } catch (IOException exception) {
            return -1;
        }
    }

    public static NetworkMessage read(DataInputStream in) {
        try {
            int messageLength = in.readInt();
            if(messageLength == 0)
                return null;

            byte[] message = new byte[messageLength];
            in.readFully(message, 0, messageLength);
            return unpack(message);
        } catch (IOException exception) {
            return null;
        }
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

            case LogoutRequestNetworkMessage.code:
                return new LogoutRequestNetworkMessage(bytes);
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
