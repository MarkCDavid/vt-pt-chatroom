package network;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

public class Packer {

    public boolean isFinished() { return this.finished; }

    public byte[] getArray() {
        pack();
        return this.stream.toByteArray();
    }

    public Packer() {
        this.stream = new ByteArrayOutputStream();
    }

    public void packByte(byte value){
        if(this.finished)
            return;
        this.stream.write(value);
    }

    public void packInt(int value) {
        if(this.finished)
            return;

        this.stream.writeBytes(ByteUtils.intToBytes(value));
    }

    public void packString(String value) {
        if(this.finished)
            return;

        packInt(value.length());
        this.stream.writeBytes(value.getBytes(StandardCharsets.UTF_8));
    }

    public void packZonedDateTime(ZonedDateTime dateTime) {
        if(this.finished) return;
        stream.writeBytes(ByteUtils.zonedDateTimeToBytes(dateTime));
    }

    private void pack() {
        this.finished = true;
    }

    private final ByteArrayOutputStream stream;
    private boolean finished;
}
