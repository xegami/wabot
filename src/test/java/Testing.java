import org.junit.Test;

public class Testing {

    @Test
    public void run() {
        String commandLine = "/stats al mamaero";

        String username = commandLine.split(" ", 2)[1];

        System.out.println(username);

    }
}
