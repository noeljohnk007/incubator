import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DriverTest {

    @Before
    public void setUp() throws Exception {
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);
    }

    @Test
    public void testDriver(){
        String[] args=new String[]{
                "C:\\Users\\noel.john\\Documents\\Projects\\Java\\sample\\spark\\src\\main\\resources\\sample-data.csv"
        };
        Driver.main(args);
    }
    @After
    public void tearDown() throws Exception {
    }
}