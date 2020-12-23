import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

public class Producer implements Runnable {
    private File file;
    private Broker broker;

    public Producer(File file, Broker broker) {
        this.file = file;
        this.broker = broker;
    }

    @Override
    public void run() {
        try (FileInputStream input = new FileInputStream(file)) {
            byte[] chunk = new byte[1000];
            while (input.read(chunk) != -1) {
                broker.put(chunk);
                Arrays.fill(chunk, (byte) 0);
            }
        } catch (Exception e){}
        this.broker.readComplete = Boolean.TRUE;
    }
}
