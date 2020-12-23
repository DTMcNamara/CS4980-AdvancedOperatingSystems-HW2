import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Broker {
    public boolean readComplete = false;
    public static BlockingQueue<byte[]> sharedQueue = new LinkedBlockingQueue<>(1000);

    public byte[] get() throws InterruptedException{
        return this.sharedQueue.poll(3,TimeUnit.MILLISECONDS);
    }

    public void put(byte[] data) throws InterruptedException{
        this.sharedQueue.put(data);
    }
}
