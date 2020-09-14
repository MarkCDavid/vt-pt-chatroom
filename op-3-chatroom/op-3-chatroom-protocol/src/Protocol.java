public class Protocol {

//    public static NetworkMessage parseMessage(String message){
//
//        String[] messageParts = message.split(":");
//        String messageType = messageParts[0];
//
//        if(Objects.equals(messageType, MessageType.LOGIN_REQUEST.code)) {
//            return new Message(MessageType.LOGIN_REQUEST, )
//        }
//    }

    public static NetworkMessage unpack(byte[] bytes){

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


// NetworkMessage - first two bytes define message type.