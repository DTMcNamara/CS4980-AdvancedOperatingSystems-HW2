import java.util.concurrent.atomic.AtomicIntegerArray;

public class atomicThreads implements Runnable {

    static AtomicIntegerArray atomics = new AtomicIntegerArray(26);

    private byte[] data;

    public atomicThreads(byte[] data) {
        this.data = data;
    }

    public static void setAtomicsZero(){
        for(int i=0;i<atomics.length();i++){
            atomics.getAndSet(i,0);
        }
    }

    @Override
    public void run() {
        try {
            for (byte c : data) {

                if (c >= 97 && c <= 122) {
                    atomics.getAndIncrement(c - 97);
                } else if (c >= 65 && c <= 90) {
                    atomics.getAndIncrement(c - 65);
                }
            }
        } catch (Exception e) {
        }
    }
}
