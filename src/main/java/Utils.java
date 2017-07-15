import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class Utils {
    private final static Logger logger = Logger.getLogger(Utils.class.getName());
    static final String HOME_DIRECTORY = "./Images/";
    static final String EMPTY_IMAGE = HOME_DIRECTORY + "empty_directory.png";
    static final String BUCKET = "";

    static ObservableList<String> getSubdirs(File directory) {
        List<String> dirs = new ArrayList<>();
        for(File dir : directory.listFiles()) if(dir.isDirectory()) dirs.add(dir.getName());
        return FXCollections.observableList(dirs);
    }

    static void deleteFilesIn(String dirString) {
        File dir = new File(HOME_DIRECTORY + dirString);

        try {
            for (File file : dir.listFiles()) file.delete();
        } catch (NullPointerException npe) {
            logger.severe("tried deleting files locally, but this happened: ");
            npe.printStackTrace();
        }

        dir.delete();
    }

    static Dimension getDimensions() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
