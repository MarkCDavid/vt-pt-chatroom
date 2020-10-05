package network.networkmessage;

public abstract class NetworkMessage {

    public NetworkMessage(Object[] data) {
        this.data = data;
    }

    public NetworkMessage(byte[] bytes) {
        this.data = new Object[0];
    }

    public abstract byte[] pack();

    protected final Object[] data;

    public void setHandled() {
        isHandled = true;
    }

    public boolean isHandled() {
        return isHandled;
    }

    private boolean isHandled;
}

