
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Main {
    static Logger log = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        BasicConfigurator.configure();
        log.info("Test");
    }
}