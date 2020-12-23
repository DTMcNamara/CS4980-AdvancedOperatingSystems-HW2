public class Consumer implements Runnable {
    private static int[] trueVals = new int[26];
    private Broker broker;
    private String name;
    private int[] values = new int[26];
    private byte[] data;

    public Consumer(String name, Broker broker) {
        this.name = name;
        this.broker = broker;
    }

    public static void setTrueValsZero() {
        for (int i = 0; i < trueVals.length; i++) {
            trueVals[i] = 0;
        }
    }

    public static String trueValsString() {
        String s = "[";
        for (int i = 0; i < 26; i++) {
            if (i == 25) {
                s += (trueVals[i]);
            } else {
                s += (trueVals[i] + ", ");
            }
        }
        s += "]";
        return s;
    }

    private void addTotal() {
        for (int i = 0; i < 26; i++) {
            trueVals[i] = values[i] + trueVals[i];
        }
    }

    private synchronized void consume() throws InterruptedException {
        data = broker.get();
        while (!broker.readComplete || data != null) {
            for (byte c : data) {
                if (c >= 97 && c <= 122) {
                    values[c - 97]++;
                } else if (c >= 65 && c <= 90) {
                    values[c - 65]++;
                }
            }
            addTotal();
            data = broker.get();
        }
    }

    @Override
    public void run() {
        try {
            consume();
            notifyAll();
        } catch (Exception e) {
        }
    }

}