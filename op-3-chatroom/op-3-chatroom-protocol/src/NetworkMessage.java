import java.time.LocalDateTime;

public abstract class NetworkMessage {

    public NetworkMessage(Object[] data) {
        this.data = data;
    }

    public abstract byte[] pack();

    protected final Object[] data;
}

