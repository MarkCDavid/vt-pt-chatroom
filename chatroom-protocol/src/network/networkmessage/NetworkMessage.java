package network.networkmessage;

public abstract class NetworkMessage {

    protected final Object[] data;
    private boolean isHandled;

    public NetworkMessage(Object[] data) {
        this.data = data;
    }

    public NetworkMessage() {
        this.data = new Object[0];
    }

    public abstract byte[] pack();

    public void setHandled() {
        isHandled = true;
    }

    public boolean isHandled() {
        return isHandled;
    }
}

