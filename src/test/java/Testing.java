import org.junit.Test;

import java.util.Calendar;

public class Testing {

    @Test
    public void run() {
        String cadena = "/wins al mamaero pc";

        Calendar c = javax.xml.bind.DatatypeConverter.parseDateTime("2019-01-20T18:53:08.3500000");

        System.out.println(c.get(Calendar.DAY_OF_MONTH));
    }
}
