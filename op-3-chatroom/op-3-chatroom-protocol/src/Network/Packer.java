package Network;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public class Packer {

    public boolean isFinished() { return this.finished; }

    public byte[] getArray() {
        if(!this.finished) pack();
        return this.buffer.array();
    }

    public Packer() {
        bufferSize = 0;
        toPack = new LinkedList<>();
    }


    public void packByte(byte _byte){

        if(this.finished) return;

        this.bufferSize += 1;

        this.toPack.add(new byte[] { _byte });
    }

    public void packString(String string) {

        if(this.finished) return;

        this.bufferSize += Integer.BYTES;
        this.bufferSize += string.length();

        this.toPack.add(ByteBuffer.allocate(Integer.BYTES).putInt(string.length()).array());
        this.toPack.add(string.getBytes(StandardCharsets.UTF_8));
    }

    public void packZonedDateTime(ZonedDateTime dateTime) {

        if(this.finished) return;

        this.bufferSize += Long.BYTES;

        this.toPack.add(ByteBuffer.allocate(Long.BYTES).putLong(dateTime.toEpochSecond()).array());
    }

    private void pack() {
        this.finished = true;
        this.buffer = ByteBuffer.allocate(this.bufferSize);
        for (byte[] bytes : toPack) this.buffer.put(bytes);
    }

    private int bufferSize;
    private ByteBuffer buffer;
    private boolean finished;
    private final List<byte[]> toPack;
}
