public class LoginRequestNetworkMessage extends NetworkMessage {

    public LoginRequestNetworkMessage(String username) {
        super(new Object[]{username});
    }

    public String getUsername() {
        return (String) data[0];
    }

    @Override
    public byte[] pack() {
        return new byte[0];
    }

}
