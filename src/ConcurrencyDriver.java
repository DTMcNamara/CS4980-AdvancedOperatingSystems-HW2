import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ConcurrencyDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        String[] arguments = new String[1];
        arguments[0] = "C:\\Users\\Dtmcn\\Documents\\School Docs\\U of Iowa\\CS4980-Topics in CSII, Advanced Operating Systems\\HW2\\test.txt";
        monitorConditionDriver.main(arguments);
        atomicDriver.main(arguments);
        producerConsumerThreads.main(arguments);
    }

}
