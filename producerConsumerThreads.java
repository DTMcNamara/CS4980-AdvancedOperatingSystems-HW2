import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class producerConsumerThreads {
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        String [][] answers = new String[2][49];
        for (int i = 1; i < 50; i++) {
            final long startTime = System.currentTimeMillis();
            Consumer.setTrueValsZero();
            Broker broker = new Broker();
            ExecutorService threadpool = Executors.newFixedThreadPool(3);
            try {
                for(int j = 0;j<i;j++) {
                    threadpool.execute(new Consumer(Integer.toString(i), broker));
                }
                Future producerstatus = threadpool.submit(new Producer(file, broker));
                producerstatus.get();
                threadpool.shutdown();
                while (!threadpool.awaitTermination(60, TimeUnit.MINUTES)) ;
                System.out.println(Consumer.trueValsString());
                final long endTime = System.currentTimeMillis();
                System.out.println("Producer//Consumer with "+i+" Consumer Thread(s)");
                System.out.println("Execution Time: " + (endTime-startTime));
                answers[0][i-1]=Integer.toString(i);
                answers[1][i-1]=Integer.toString((int)(endTime-startTime));
            } catch (Exception e) {
            }
        }
        BufferedWriter br = new BufferedWriter(new FileWriter("pac.csv"));
        StringBuilder sb = new StringBuilder();
        FileWriter csvWriter = new FileWriter("pac.csv");
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

