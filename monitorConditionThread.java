public class monitorConditionThread implements Runnable {

    static int[] trueVals = new int[26];

    private int[] values = new int[26];

    private byte[] data;

    public monitorConditionThread(byte[] data) {
        this.data = data;
    }

    public static String trueValString() {
        String s = "[";
        for (int i = 0; i < 26; i++) {
            if(i==25){
                s += (trueVals[i]);
            }else{
                s += (trueVals[i] + ", ");
            }
        }
        s += "]";
        return s;
    }

    public static void setTrueValsZero(){
        for(int i=0;i<trueVals.length;i++){
            trueVals[i] = 0;
        }
    }

    private synchronized void addTotal() {
        for (int i = 0; i < 26; i++) {
            trueVals[i] += values[i];
        }
    }

    @Override
    public void run() {
        try {
            for (byte c : data) {

                if (c >= 97 && c <= 122) {
                    values[c - 97] += 1;
                } else if (c >= 65 && c <= 90) {
                    values[c - 65] += 1;
                }
            }
            addTotal();
            notify();
        } catch (Exception e) {}
    }
}
