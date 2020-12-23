import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class monitorConditionDriver {
    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File(args[0]);
        long startTime, endTime;
        String [][] answers = new String[2][49];
        for(int j=1;j<50;j++) {
            startTime = System.currentTimeMillis();
            monitorConditionThread.setTrueValsZero();
            int i = j;
            FileInputStream input = new FileInputStream(file);
            int chunkSize = (int) Math.ceil(file.length() / (double) i);
            byte[] chunk = new byte[chunkSize];
            ExecutorService executor = Executors.newCachedThreadPool();
            while (input.read(chunk) != -1) {
                monitorConditionThread m = new monitorConditionThread(Arrays.copyOf(chunk, chunkSize));
                Thread t = new Thread(m);
                executor.execute(t);
                Arrays.fill(chunk, (byte) 0);
            }
            executor.shutdown();
            while (!executor.awaitTermination(60, TimeUnit.MINUTES)) ;
            System.out.println(monitorConditionThread.trueValString());
            endTime = System.currentTimeMillis();
            System.out.println("Monitor & Condition with "+i+" Thread(s)");
            System.out.println("Execution Time: " + (endTime-startTime));
            answers[0][i-1]=Integer.toString(i);
            answers[1][i-1]=Integer.toString((int)(endTime-startTime));
        }
        BufferedWriter br = new BufferedWriter(new FileWriter("mc.csv"));
        StringBuilder sb = new StringBuilder();
        FileWriter csvWriter = new FileWriter("mc.csv");
        csvWriter.append("Threads");
        csvWriter.append(",");
        csvWriter.append("Runtime");
        csvWriter.append("\n");
        for(String element : answers[0]){
            sb.append(element);
            sb.append(",");
        }
        sb.append("\n");
        for(String element : answers[1]){
            sb.append(element);
            sb.append(",");
        }
        br.write(sb.toString());
        br.close();
    }
}
