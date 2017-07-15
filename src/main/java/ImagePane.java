import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

class ImagePane {
    private final static Logger logger = Logger.getLogger(ImagePane.class.getName());

    private static ArrayList<String> allImages = new ArrayList<>();
    private static int imgIndex = 0;
    private static String imagesPath = "";
    private static ImageView imagesView = new ImageView();
    private static Scene imagesScene = null;

    private static void createScene(String dirName) {
        imgIndex = 0;
        setup(dirName);
        setDisplayImage();

        imagesView.setFitWidth(Utils.getDimensions().getWidth());
        imagesView.setFitHeight(Utils.getDimensions().getHeight());
        imagesView.setPreserveRatio(true);
        imagesView.setSmooth(true);
        imagesView.setCache(true);

        BorderPane pane = new BorderPane();
        pane.setPrefSize(Utils.getDimensions().getWidth(), Utils.getDimensions().getHeight());
        pane.setCenter(imagesView);

        imagesScene = new Scene(pane);
        imagesScene.setFill(Color.WHITE);
    }

    private static void setup(String dirName) {
        imagesPath = Utils.HOME_DIRECTORY + dirName + "/";

        File imagesDir = new File(imagesPath);

        if (imagesDir.exists() && imagesDir.isDirectory()) allImages = new ArrayList<>(Arrays.asList(imagesDir.list()));
    }

    static void pressedLeft() {
        decrementImageIndex();
        setDisplayImage();
    }

    static void pressedRight() {
        incrementImageIndex();
        setDisplayImage();
    }

    private static void setDisplayImage() {
        Image image = null;
        String imgPath = Utils.EMPTY_IMAGE;

        if (allImages.size() > 0) imgPath = getImagePath();

        try (InputStream imageStream = new FileInputStream(new File(imgPath))){
            image = new Image(imageStream);
        } catch (IOException ioe) {
            logger.severe("tried displaying an image, but this happened: ");
            ioe.printStackTrace();
        }

        imagesView.setImage(image);
    }

    private static String getImagePath() {
        return imagesPath + allImages.get(imgIndex);
    }

    private static void incrementImageIndex() {
        if (imgIndex < allImages.size() - 1) imgIndex++;
    }

    private static void decrementImageIndex() {
        if (imgIndex > 0) imgIndex--;
    }

    static Scene getScene(String dirName) {
        createScene(dirName);
        return imagesScene;
    }

    static void deleteSelected() {
        if (allImages.size() > 0) {
            File file = new File(getImagePath());

            if (file.exists()) {
                file.delete();

                allImages.remove(imgIndex);

                if (allImages.size() > 0) {
                    if (imgIndex > allImages.size() - 1) imgIndex = 0;
                }

                setDisplayImage();
            }
        }
    }
}