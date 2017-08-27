import javax.swing.*;
import java.util.logging.Logger;

public class IWasFramedIn extends JFrame {
    private final static Logger logger = Logger.getLogger(IWasFramedIn.class.getName());

    public static void main(String[] args) {
        logger.info("IWasFramed:In started");
        AWSHandler.fetch();
        ScreenController.prepareFrames();
    }
}