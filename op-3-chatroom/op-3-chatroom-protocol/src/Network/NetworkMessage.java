package Network;

public abstract class NetworkMessage {

    public NetworkMessage(Object[] data) {
        this.data = data;
    }

    public NetworkMessage(byte[] bytes) {
        this.data = new Object[0];
    }

    public abstract byte[] pack();

    protected final Object[] data;
}

